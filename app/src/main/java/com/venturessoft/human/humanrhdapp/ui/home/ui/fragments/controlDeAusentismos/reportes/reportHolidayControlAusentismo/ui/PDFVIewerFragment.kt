package com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.reportes.reportHolidayControlAusentismo.ui

import android.Manifest
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.StrictMode
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.venturessoft.human.humanrhdapp.R
import com.venturessoft.human.humanrhdapp.databinding.FragmentPDFViewerBinding
import com.venturessoft.human.humanrhdapp.ui.home.MainInterface
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.listEmployee.ui.ListEmployeeAdapter
import com.venturessoft.human.humanrhdapp.utilis.complements.Constants.Companion.PDF_KEY
import com.venturessoft.human.humanrhdapp.utilis.complements.Constants.Companion.TITULO_KEY
import com.venturessoft.human.humanrhdapp.utilis.complements.Utilities
import es.dmoral.toasty.Toasty
import org.apache.commons.io.FileUtils
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


class PDFVIewerFragment : Fragment() {
    private lateinit var binding: FragmentPDFViewerBinding
    private var pdf: String = ""
    private var title: String = ""
    private var pdfFile: File? = null
    private var mainInterface: MainInterface? = null
    private lateinit var decodeBytes: ByteArray
    private var namePDF: String = ""

    companion object {
        private const val STORAGE_PERMISSION_REQUEST_CODE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { bundle ->
            pdf = bundle.getString(PDF_KEY, "")
            title = bundle.getString(TITULO_KEY, "")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPDFViewerBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        renderPdf(pdf)
        binding.descargar.setOnClickListener { downloadPdf() }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MainInterface) {
            mainInterface = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        ListEmployeeAdapter.listEmployeSelect.value = listOf()
        mainInterface = null
    }

    private fun renderPdf(base64EncodedString: String) = try {
        val decodedString = Base64.decode(base64EncodedString, Base64.DEFAULT)
        binding.pdfView.fromBytes(decodedString).load()
        pdfFile = convertBase64ToPdf(base64EncodedString, requireContext())
        decodeBytes = Base64.decode(base64EncodedString, Base64.DEFAULT)
    } catch (e: Exception) {
        // handle error
    }

    private fun convertBase64ToPdf(base64String: String, context: Context): File {
        val decodedBytes = Base64.decode(base64String, Base64.DEFAULT)
        val file = File(context.filesDir, "${getString(R.string.reporte)} $title.pdf")

        FileOutputStream(file).use { fileOutputStream ->
            fileOutputStream.write(decodedBytes)
            fileOutputStream.flush()
        }

        return file
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    @SuppressLint("ServiceCast", "UseCompatLoadingForDrawables")
    private fun downloadPdf() {
        if (isStoragePermissionGranted()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val downloadsDir =
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                namePDF = "${getString(R.string.reporte)} $title ${System.currentTimeMillis()}.pdf"
                val destFile = File(
                    downloadsDir,
                    namePDF
                )
                try {
                    FileUtils.copyFile(pdfFile, destFile)
                    Toasty.success(
                        requireContext(),
                        getString(R.string.pdf_descargado),
                        Toast.LENGTH_SHORT,
                        true
                    ).show()
                    Toasty.custom(
                        requireContext(),
                        "${getString(R.string.descargas)}/$namePDF",
                        requireContext().getDrawable(R.drawable.archive),
                        requireContext().getColor(R.color.descargas),
                        Color.WHITE,
                        Toast.LENGTH_SHORT,
                        true,
                        true
                    ).show()
                    showNotificationSimple()

                } catch (e: java.nio.file.FileAlreadyExistsException) {
                    e.printStackTrace()
                    Toasty.warning(
                        requireContext(),
                        "El archivo ya existe",
                        Toast.LENGTH_LONG,
                        true
                    )
                        .show()
                } catch (e: IOException) {
                    e.printStackTrace()
                    println("CAUSA" + e.cause)
                    println("Message" + e.message)
                    Toasty.error(
                        requireContext(),
                        getString(R.string.error_descargar_PDF),
                        Toast.LENGTH_SHORT,
                        true
                    ).show()
                }
            } else {
                Toasty.success(
                    requireContext(),
                    getString(R.string.pdf_descargado),
                    Toast.LENGTH_SHORT,
                    true
                ).show()
                Toasty.custom(
                    requireContext(),
                    "${getString(R.string.almacenamiento_interno)}/$namePDF",
                    requireContext().getDrawable(R.drawable.archive),
                    requireContext().getColor(R.color.descargas),
                    Color.WHITE,
                    Toast.LENGTH_LONG,
                    true,
                    true
                ).show()
                val downloadDir = Environment.getExternalStorageDirectory()
                namePDF = "${getString(R.string.reporte)} $title ${System.currentTimeMillis()}.pdf"
                val fileName = namePDF
                val file = File(downloadDir, fileName)
                val outputStream = FileOutputStream(file)
                outputStream.write(decodeBytes)
                outputStream.close()
                showNotificationSimple()
            }
            Utilities.vibrate(requireContext())
        } else {
            requestStoragePermission()
        }
    }

    private fun isStoragePermissionGranted(): Boolean {
        return if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
            checkSelfPermission(
                requireContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        } else {
            true
        }
    }

    private fun requestStoragePermission() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
            STORAGE_PERMISSION_REQUEST_CODE
        )
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun showNotificationSimple() {
        val channelId = "pdf_download_channel"
        val channelName = "PDF Downloads"
        val importance = NotificationManager.IMPORTANCE_DEFAULT

        if (checkSelfPermission(
                requireContext(),
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                STORAGE_PERMISSION_REQUEST_CODE
            )
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel =
                NotificationChannel(channelId, channelName, importance).apply {
                    lightColor = Color.BLUE
                    enableLights(true)
                }
            val notificationManager = requireContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(notificationChannel)
        }

        val downloadsDir: File = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        } else {
            Environment.getExternalStorageDirectory()
        }

        val pdfFile = File(downloadsDir, namePDF)

        val packageName = requireContext().packageName
        val authority = "${packageName}.fileprovider"
        val pdfUri: Uri = FileProvider.getUriForFile(requireContext(), authority, pdfFile)

        val intent = Intent(Intent.ACTION_VIEW)
        intent.setDataAndType(pdfUri, "application/pdf")
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val builder = StrictMode.VmPolicy.Builder()
            StrictMode.setVmPolicy(builder.build())
        }
        val pendingIntent =
            PendingIntent.getActivity(requireContext(), 0, intent, PendingIntent.FLAG_IMMUTABLE)
        val notificationBuilder = NotificationCompat.Builder(requireContext(), channelId)
            .setContentIntent(pendingIntent)
            .setSmallIcon(R.drawable.success)
            .setContentTitle(getString(R.string.pdf_descargado))
            .setContentText(namePDF)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        val progressMax = 100
        val progressCurrent = 0
        NotificationManagerCompat.from(requireContext()).apply {
            notificationBuilder.setProgress(progressMax, progressCurrent, false)
            notify(1, notificationBuilder.build())
            notificationBuilder.setContentText(namePDF)
                .setProgress(0, 0, false)
            notify(1, notificationBuilder.build())
        }
    }
}