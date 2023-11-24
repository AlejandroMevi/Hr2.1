package com.venturessoft.human.humanrhdapp.utilis.complements

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Context.VIBRATOR_SERVICE
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.Rect
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.text.Editable
import android.text.TextWatcher
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.auth0.android.jwt.JWT
import com.venturessoft.human.humanrhdapp.R
import com.venturessoft.human.humanrhdapp.core.AnimationExpanded
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.permisos.ui.Permisos
import com.venturessoft.human.humanrhdapp.jwt.JWTUtils
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.preauthorizationTimes.data.models.TiempoExtra
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.scheduleNegotiation.data.models.ScheduleNegotiation
import com.venturessoft.human.humanrhdapp.utilis.complements.Constants.Companion.COUNTRYES
import com.venturessoft.human.humanrhdapp.utilis.complements.Constants.Companion.LENGUAGEes
import es.dmoral.toasty.Toasty
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class Utilities {
    companion object {
        fun showDialog(
            title: String,
            message: String,
            positiveButtonText: String,
            context: Context,
            listener: DialogListener?
        ) {
            val dialog = GenericDialog(title, message, positiveButtonText, context, listener)
            dialog.setCancelable(false)
            dialog.show()
        }
        fun showDialog(
            title: String,
            message: String,
            positiveButtonText: String,
            negativeButtonText: String,
            context: Context,
            listener: DialogListener?
        ) {
            val dialog = GenericDialog(
                title,
                message,
                positiveButtonText,
                negativeButtonText,
                context,
                listener
            )
            dialog.show()
        }
        fun showErrorDialog(message: String, context: Context) {
            GenericDialog(
                title = context.getString(R.string.dialog_title),
                message = message,
                positiveButtonText = context.getString(R.string.dialog_positive),
                context = context,
                listener = null
            ).show()
        }
        fun loadMessageError(codigo: String, activity: Activity) {
            val contextoPaquete: String = activity.packageName
            val identificadorMensaje = activity.applicationContext.resources.getIdentifier(
                codigo,
                "string",
                contextoPaquete
            )
            if (identificadorMensaje > 0) {
                showErrorDialog(activity.getString(identificadorMensaje), activity)
            } else {
                showErrorDialog(codigo, activity)
            }
        }
        @SuppressLint("DiscouragedApi")
        fun textcode(codigo: String, context: Context): String {
            val textvalue: String = try {
                val contextoPaquete: String = context.packageName
                val indentificadorMensaje =
                    context.resources.getIdentifier(codigo, "string", contextoPaquete)
                if (indentificadorMensaje > 0) {
                    context.getString(indentificadorMensaje)
                } else {
                    "Error en el servicio"
                }
            } catch (exception: java.lang.Exception) {
                codigo
            }
            return textvalue
        }
        fun jwt() {
            val token = User.token.replace("Bearer ", "")
            val jwt = JWT(token)
            val isExpired = jwt.isExpired(10)
            if (isExpired) {
                Log.i(ContentValues.TAG, "El token expiro: true")
            } else {
                Log.i(ContentValues.TAG, "El token NO expiro")

            }
        }
        fun validateAndRefreshJWT() {
            val jwtUtils = JWTUtils(User.token)
            if (jwtUtils.isExpired(10)) {
                Log.i(ContentValues.TAG, "JWTutils El token expiro")
            } else {
                Log.i(ContentValues.TAG, "JWTutils El token no expiro")
            }
        }
        fun <T> LiveData<T>.observeOnce(lifecycleOwner: LifecycleOwner, observer: Observer<T>) {
            observe(lifecycleOwner, object : Observer<T> {
                override fun onChanged(value: T) {
                    observer.onChanged(value)
                    if (value != null) {
                        removeObserver(this)
                    }
                }
            })
        }
        fun toggleLayout(isExpanded: Boolean, v: View, layoutExpand: View): Boolean {
            AnimationExpanded().toggleArrow(v, isExpanded)
            if (isExpanded) {
                AnimationExpanded().expand(layoutExpand)
            } else {
                AnimationExpanded().collapse(layoutExpand)
            }
            return isExpanded
        }
        fun comentEdit(comentarios: EditText) {
            comentarios.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    val a: String = comentarios.text.toString()
                    Permisos.comentarios = a
                }

                override fun afterTextChanged(s: Editable?) {
                }
            })
        }
        fun formatYearMonthDay(strDate: String): String? {
            return try {
                val sourceSdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale(LENGUAGEes, COUNTRYES))
                val requiredSdf = SimpleDateFormat("dd/MM/yyyy", Locale(LENGUAGEes, COUNTRYES))
                return sourceSdf.parse(strDate)?.let { requiredSdf.format(it) }
            } catch (_: Exception) {
                try {
                    val sourceSdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm", Locale(LENGUAGEes, COUNTRYES))
                    val requiredSdf = SimpleDateFormat("dd/MM/yyyy", Locale(LENGUAGEes, COUNTRYES))
                    return sourceSdf.parse(strDate)?.let { requiredSdf.format(it) }
                } catch (_: Exception) {
                    ""
                }
            }
        }
        fun formatYearMonth(strDate: String): String? {
            return try {
                val sourceSdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale(LENGUAGEes, COUNTRYES))
                val requiredSdf = SimpleDateFormat("yyyy-MM", Locale(LENGUAGEes, COUNTRYES))
                return sourceSdf.parse(strDate)?.let { requiredSdf.format(it) }
            } catch (_: Exception) {
                try {
                    val sourceSdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm", Locale(LENGUAGEes, COUNTRYES))
                    val requiredSdf = SimpleDateFormat("yyyy-MM", Locale(LENGUAGEes, COUNTRYES))
                    return sourceSdf.parse(strDate)?.let { requiredSdf.format(it) }
                } catch (_: Exception) {
                    ""
                }
            }
        }
        fun formatYear(strDate: String): String? {
            return try {
                val sourceSdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale(LENGUAGEes, COUNTRYES))
                val requiredSdf = SimpleDateFormat("yyyy", Locale(LENGUAGEes, COUNTRYES))
                return sourceSdf.parse(strDate)?.let { requiredSdf.format(it) }
            } catch (_: Exception) {
                try {
                    val sourceSdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm", Locale(LENGUAGEes, COUNTRYES))
                    val requiredSdf = SimpleDateFormat("yyyy", Locale(LENGUAGEes, COUNTRYES))
                    return sourceSdf.parse(strDate)?.let { requiredSdf.format(it) }
                } catch (_: Exception) {
                    ""
                }
            }
        }
        fun formatMonth(strDate: String): String? {
            return try {
                val sourceSdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale(LENGUAGEes, COUNTRYES))
                val requiredSdf = SimpleDateFormat("M", Locale(LENGUAGEes, COUNTRYES))
                return sourceSdf.parse(strDate)?.let { requiredSdf.format(it) }
            } catch (_: Exception) {
                try {
                    val sourceSdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm", Locale(LENGUAGEes, COUNTRYES))
                    val requiredSdf = SimpleDateFormat("M", Locale(LENGUAGEes, COUNTRYES))
                    return sourceSdf.parse(strDate)?.let { requiredSdf.format(it) }
                } catch (_: Exception) {
                    ""
                }
            }
        }
        fun formatHourMin(strDate: String): String? {
            return try {
                val sourceSdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale(LENGUAGEes, COUNTRYES))
                val requiredSdf = SimpleDateFormat("HH:mm", Locale(LENGUAGEes, COUNTRYES))
                sourceSdf.parse(strDate)?.let { requiredSdf.format(it) }
            } catch (_: Exception) {
                ""
            }
        }
        @SuppressLint("SimpleDateFormat")
        fun setformatYearMonthDay(date: Date): String? {
            return try {
                val format1 = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                val sourceSdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale(LENGUAGEes, COUNTRYES))
                val requiredSdf = SimpleDateFormat("yyyy", Locale(LENGUAGEes, COUNTRYES))
                return sourceSdf.parse(format1.format(date))?.let { requiredSdf.format(it) }
            } catch (_: Exception) {
                ""
            }
        }
        fun dayOfTheWeek(): Int {
            return try {
                val tz = TimeZone.getTimeZone("America/Mexico_City")
                val calendar: Calendar = Calendar.getInstance(tz)
                return calendar.get(Calendar.DAY_OF_WEEK) - 1
            } catch (_: Exception) {
                0
            }
        }
        fun getDateFromMiliseconds(timestamp: Long, format: String): String {
            val timeZoneUTC = TimeZone.getDefault()
            val offsetFromUTC = timeZoneUTC.getOffset(Date().time) * -1
            val simpleFormat = SimpleDateFormat(format, Locale(LENGUAGEes, COUNTRYES))
            val date = Date(timestamp + offsetFromUTC)
            return simpleFormat.format(date)
        }
        @SuppressLint("SimpleDateFormat")
        fun getDateLocal(): String? {
            return try {
                val calendar = Calendar.getInstance(Locale(LENGUAGEes, COUNTRYES))
                val format1 = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                val sourceSdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale(LENGUAGEes, COUNTRYES))
                val requiredSdf = SimpleDateFormat("dd/MM/yyyy", Locale(LENGUAGEes, COUNTRYES))
                return sourceSdf.parse(format1.format(calendar.time))
                    ?.let { requiredSdf.format(it) }
            } catch (_: Exception) {
                ""
            }
        }
        fun getTimeLocal():String{
            return try {
                val tz = TimeZone.getTimeZone("America/Mexico_City")
                val calendar: Calendar = Calendar.getInstance(tz)
                val newHour = calendar.get(Calendar.HOUR_OF_DAY)
                val newMinute = calendar.get(Calendar.MINUTE)
                var hora = ""
                if (newHour < 10) {
                    hora = "0$newHour:$newMinute"
                }
                if (newMinute < 10) {
                    hora = "$newHour:0$newMinute"
                }
                if (newMinute < 10 && newHour < 10) {
                    hora = "0$newHour:0$newMinute"
                }
                if (newMinute >= 10 && newHour >= 10) {
                    hora = "$newHour:$newMinute"
                }
                hora
            }catch (_:Exception){
                ""
            }
        }
        fun cambiarFormatoFecha(fechaString: String, diagonal: Boolean): String {
            var fecha: LocalDate? = null
            var formatoSalida: DateTimeFormatter? = null
            when (diagonal) {
                true -> {
                    val formatoEntrada = DateTimeFormatter.ofPattern("yyyy/MM/dd")
                    fecha = LocalDate.parse(fechaString, formatoEntrada)
                    formatoSalida = DateTimeFormatter.ofPattern("dd/MM/yyyy")
                }

                false -> {
                    val formatoEntrada = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                    fecha = LocalDate.parse(fechaString, formatoEntrada)
                    formatoSalida = DateTimeFormatter.ofPattern("dd/MM/yyyy")
                }
            }
            return fecha.format(formatoSalida)
        }
        fun changeDateFormat(fechaString: String, inputformat: String, outformat: String): String {
            return try {
                val fecha: LocalDate?
                val formatoEntrada = DateTimeFormatter.ofPattern(inputformat)
                fecha = LocalDate.parse(fechaString, formatoEntrada)
                val formatoSalida: DateTimeFormatter? = DateTimeFormatter.ofPattern(outformat)
                fecha.format(formatoSalida)
            }catch (_:Exception){
                ""
            }
        }
        fun orderDates(arrayList: ArrayList<ScheduleNegotiation>): List<ScheduleNegotiation> {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale(LENGUAGEes, COUNTRYES))
            arrayList.sortByDescending {
                it.fechaInicio.let { it1 -> dateFormat.parse(it1) }
            }
            return arrayList
        }
        fun showToastyGeneral(context: Context, text: String) {
            Toasty.custom(
                context, text, ContextCompat.getDrawable(context, R.drawable.warning),
                context.getColor(R.color.principal),
                Color.WHITE,
                Toast.LENGTH_SHORT,
                true,
                true
            ).show()
        }
        fun orderDatesPreauthorization(stiempoExtra: ArrayList<TiempoExtra>, formato: String): List<TiempoExtra> {
            val dateFormat = SimpleDateFormat(formato, Locale(LENGUAGEes, COUNTRYES))
            stiempoExtra.sortByDescending {
                it.fecha.let { it1 -> dateFormat.parse(it1) }
            }
            return stiempoExtra
        }

        fun getRoundedDrawable(bitmap: Bitmap, activity: Activity): Drawable {
            val output = Bitmap.createBitmap(bitmap.width, bitmap.height, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(output)
            val paint = Paint()
            val rect = Rect(0, 0, bitmap.width, bitmap.height)
            paint.isAntiAlias = true
            canvas.drawARGB(0, 0, 0, 0)
            paint.color = Color.parseColor("#FF5722")
            canvas.drawCircle(
                (bitmap.width / 2).toFloat(),
                (bitmap.height / 2).toFloat(),
                (bitmap.width / 2).toFloat(),
                paint
            )
            paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
            canvas.drawBitmap(bitmap, rect, rect, paint)
            return BitmapDrawable(activity.resources, output)
        }

        fun base64StringToBitmap(base64: String): Bitmap {
            val imageBytes = Base64.decode(base64, 0)
            return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
        }
        fun vibrate(context: Context){
            val v = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                val vibratorManager =
                    context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
                vibratorManager.defaultVibrator
            } else {
                @Suppress("DEPRECATION")
                context.getSystemService(VIBRATOR_SERVICE) as Vibrator
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val time = longArrayOf(0, 200, 0, 200, 0, 200)
                val amplitude = intArrayOf(0, 50, 0, 100, 0, 150)
                val vibrationEffect =
                    VibrationEffect.createWaveform(time, amplitude, -1 /*-1 No repeat*/)
                v.vibrate(vibrationEffect)
            } else {
                v.vibrate(1500)
            }
        }
    }
}