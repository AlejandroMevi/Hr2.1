package com.venturessoft.human.humanrhdapp.ui.home.ui
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.view.View
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.badge.BadgeUtils
import com.venturessoft.human.humanrhdapp.R
import com.venturessoft.human.humanrhdapp.databinding.ActivityHomeBinding
import com.venturessoft.human.humanrhdapp.ui.home.MainInterface
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.reportesEstacionesLibres.FilterStationFreeFragment
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.main.data.MenuModel
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.main.ui.vm.WelcomeFragmentViewModel
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.core.vm.TimeManagementVM
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.massiveInputPutput.ui.MassiveInputOutputFilterDialog
import com.venturessoft.human.humanrhdapp.ui.login.ui.LoginActivity
import com.venturessoft.human.humanrhdapp.utilis.complements.Constants
import com.venturessoft.human.humanrhdapp.utilis.complements.DialogListener
import com.venturessoft.human.humanrhdapp.utilis.complements.User
import com.venturessoft.human.humanrhdapp.utilis.complements.Utilities
import de.hdodenhof.circleimageview.CircleImageView

class HomeActivity : AppCompatActivity(), MainInterface {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private val timeManagementVM: TimeManagementVM by viewModels()
    private val w : WelcomeFragmentViewModel by viewModels()
    private lateinit var badgeDrawable : BadgeDrawable
    private var filterTypeMain = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        badgeDrawable = BadgeDrawable.createFromResource(this,R.xml.badge_style)
        getCatalogs()
        initToolbar()
        initializeView()
        setBadgeCount()
        setContentView(binding.root)
    }
    private fun getCatalogs(){
        timeManagementVM.getZona()
        timeManagementVM.getSupervisor()
        timeManagementVM.getRolTurn()
        timeManagementVM.getReasons()
        timeManagementVM.getCalendar()
        timeManagementVM.getDepartment()
        timeManagementVM.getCategory()
        timeManagementVM.getConcepto()
        timeManagementVM.codid()
        timeManagementVM.catalogoPermisos()
        timeManagementVM.station()
        timeManagementVM.catalogoGrupos()
        timeManagementVM.catalogoProceso()
    }
    companion object {
        var showButtonBar = MutableLiveData(true)
        var mr = MutableLiveData<Boolean>(null)
        var progressIsShow = false
        var showStations = MutableLiveData(false)
    }
    private fun initToolbar() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        binding.toolBar.setNavigationOnClickListener {
            binding.drawerLayout.open()
        }
        binding.drawerLayout.setScrimColor(this.getColor(R.color.scrim_color))
        val header = binding.navigationView.getHeaderView(0)
        val nameUser = header.findViewById<AppCompatTextView>(R.id.tv_name_user)
        val imageUser = header.findViewById<CircleImageView>(R.id.imgEmployee)
        nameUser.text = User.nombreSupervisor
        try {
            if (User.foto.contains("File not foundjava", ignoreCase = true) || User.foto.isEmpty()) {
                imageUser.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.user_icon_big))
            } else {
                val imagenBase64 = Base64.decode(User.foto, Base64.DEFAULT)
                val imagenconverBitmap =
                    BitmapFactory.decodeByteArray(imagenBase64, 0, imagenBase64.size)
                imageUser.setImageBitmap(imagenconverBitmap)
            }
        }catch (_:Exception){ }
        binding.navigationView.setNavigationItemSelectedListener { menuItem ->
            w.idMenu.value = MenuModel(id = menuItem.itemId, name = menuItem.title.toString())
            when (menuItem.itemId) {
                R.id.item_1,R.id.item_2,R.id.item_3,R.id.item_4,R.id.item_5,R.id.item_6,R.id.item_11 -> {
                    val bundle = Bundle()
                    bundle.putBoolean(Constants.MR, false)
                    findNavController(R.id.nav_host_fragment).navigate(R.id.to_listEmployeeFragment, bundle)
                }
                R.id.item_8,R.id.item_9 ,R.id.item_10-> {
                    val bundle = Bundle()
                    bundle.putBoolean(Constants.MR, true)
                    findNavController(R.id.nav_host_fragment).navigate(R.id.to_listEmployeeFragment, bundle)
                }
                R.id.item_101 ->{
                    findNavController(R.id.nav_host_fragment).navigate(R.id.to_entradasSalidasMasivasFragment)
                }
                R.id.item_7 -> {
                    findNavController(R.id.nav_host_fragment).navigate(R.id.to_menuReportesControlDeAusentismosFragment)
                }
                R.id.item_12 -> {
                    findNavController(R.id.nav_host_fragment).navigate(R.id.to_reportesFragment)
                }
                R.id.item_13 -> {
                    findNavController(R.id.nav_host_fragment).navigate(R.id.to_aboutUsFragment)
                }
                R.id.item_14 -> {
                    showExitDialog()
                }
                R.id.item_22 -> {
                    findNavController(R.id.nav_host_fragment).navigate(R.id.to_vacationAuthorizationFragment)
                }
                R.id.item_23 -> {
                    findNavController(R.id.nav_host_fragment).navigate(R.id.to_reportesEstacionesLibresFragment)
                }
                R.id.item_24 -> {
                    findNavController(R.id.nav_host_fragment).navigate(R.id.to_pruebFragment)
                }
            }
            binding.drawerLayout.close()
            true
        }
        setSupportActionBar(binding.toolBar)
        setupActionBarWithNavController(true)
        supportActionBar?.title = ""
        showStations.observe(this){
            binding.stations.isVisible = it
        }
    }
    private fun setupActionBarWithNavController(isPrincipalUser:Boolean) {
        appBarConfiguration = if (isPrincipalUser){
            AppBarConfiguration(navController.graph, binding.drawerLayout)
        }else{
            AppBarConfiguration(setOf())
        }
        setupActionBarWithNavController(navController, appBarConfiguration)
    }
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration)
    }
    override fun setTextToolbar(text: String) {
        binding.ivLogo.isVisible = text.isEmpty()
        supportActionBar?.title = text
    }
    override fun showLottie(isVisible: Boolean) {
        if (isVisible) {
            binding.toolBar.visibility = View.VISIBLE
        } else {
            binding.toolBar.visibility = View.INVISIBLE
        }
    }
    private fun initializeView() {
    }
    override fun showLoading(isShowing: Boolean) {
        binding.progress.root.isVisible = isShowing
        progressIsShow = isShowing
        if (isShowing) {
            window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        } else {
            window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        }
    }

    override fun showFilter(isShowing: Boolean,filterType:Int ) {
        binding.filter.isVisible = isShowing
        filterTypeMain = if (isShowing)filterType else 0
    }
    @SuppressLint("UnsafeOptInUsageError")
    override fun showBadgeDrawable(isShowing: Boolean) {
        if (isShowing) {
            val imageView = binding.filter
            badgeDrawable.isVisible = true
            BadgeUtils.attachBadgeDrawable(badgeDrawable, imageView, null)
        } else {
            val imageView = binding.filter
            badgeDrawable.number = 0
            badgeDrawable.clearNumber()
            badgeDrawable.isVisible = false
            BadgeUtils.detachBadgeDrawable(badgeDrawable, imageView)
        }
    }

    override fun showStations(gafete:Long) {
        showStations.value = true
        binding.stations.setOnClickListener {
            val bundle = Bundle()
            bundle.putLong(Constants.DATA_KEY,gafete)
            findNavController(R.id.nav_host_fragment).navigate(R.id.to_generalStationsFragment,bundle)
        }
    }
    override fun onBackPressed() {
        if (!progressIsShow){
            onBackPressedDispatcher.onBackPressed()
        }
    }
    private fun showExitDialog(){
        Utilities.showDialog(
            title = getString(R.string.signoff_title),
            message = getString(R.string.signoff_message),
            positiveButtonText = getString(R.string.signoff_postive),
            negativeButtonText = getString(R.string.signoff_negative),
            context = this,
            listener = object : DialogListener {
                override fun onPositiveButtonClicked() {
                    val intent = Intent(this@HomeActivity, LoginActivity::class.java)
                    intent.putExtra(Constants.FROM_LOG_OUT, true)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
                    User.listUsuTrue.clear()
                    User.listUsuFalse.clear()
                    finish()
                }
                override fun onNegativeButtonClicked() {}
            }
        )
    }

    private fun setBadgeCount() {
        binding.filter.setOnClickListener {
            when (filterTypeMain){
                1->{
                    val fullScreenDialogFragment = FilterStationFreeFragment()
                    fullScreenDialogFragment.show(this.supportFragmentManager, "FullScreenDialogFragment")
                }
                2->{
                    val fullScreenDialogFragment = MassiveInputOutputFilterDialog()
                    fullScreenDialogFragment.show(this.supportFragmentManager, "FullScreenDialogFragment")
                }
            }
        }
    }
}