package com.venturessoft.human.humanrhdapp.ui.login.ui

import android.os.Bundle
import android.view.WindowManager
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.venturessoft.human.humanrhdapp.ui.login.LoginInterface
import com.venturessoft.human.humanrhdapp.network.Response.ItemItem
import com.venturessoft.human.humanrhdapp.R
import com.venturessoft.human.humanrhdapp.databinding.ActivityLoginBinding
import com.venturessoft.human.humanrhdapp.utilis.complements.Constants
import com.venturessoft.human.humanrhdapp.utilis.complements.Utilities

class LoginActivity : AppCompatActivity(),LoginInterface {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private var enabledBack = true
    companion object{
        lateinit var usuariosListaArrayResponse: java.util.ArrayList<ItemItem>
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        initToolbar()
        messageLogout()
        setContentView(binding.root)
    }
    private fun initToolbar(){
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        setSupportActionBar(binding.toolBar)
        appBarConfiguration = AppBarConfiguration(setOf(R.id.loginFragment))
        binding.toolBar.setupWithNavController(navController, appBarConfiguration)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration)
    }
    override fun setTextToolbar(text:String){
        binding.tvTitle.text = text
    }
    private fun messageLogout() {
        val isFromLogOut = intent.getBooleanExtra(Constants.FROM_LOG_OUT, false)
        if (isFromLogOut) {
            Utilities.showDialog(
                title = getString(R.string.signoff_title),
                message = getString(R.string.validate_jwt),
                positiveButtonText = getString(R.string.signoff_postive),
                context = this,
                listener = null
            )
        }
    }
    override fun showLoading(isShowing: Boolean) {
        binding.progress.root.isVisible = isShowing
        enabledBack = !isShowing
        if (isShowing) {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
            )
        } else {
            window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        }
    }
    override fun onBackPressed() {
        if (enabledBack){
            super.onBackPressedDispatcher.onBackPressed()
        }
    }
}