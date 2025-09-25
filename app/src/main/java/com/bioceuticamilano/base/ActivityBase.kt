package com.bioceuticamilano.base

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.drawable.ColorDrawable
import android.location.Address
import android.location.Geocoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment
import com.bioceuticamilano.R
import com.bioceuticamilano.utils.Preferences
import com.bioceuticamilano.utils.Utility
import com.google.android.gms.common.util.IOUtils
import com.google.gson.GsonBuilder
import org.json.JSONObject
import java.io.*
import java.lang.reflect.Type
import java.util.*


open class ActivityBase : AppCompatActivity() {
    var loading = false
    var progressDialog: Dialog? = null
    /**
     * @return the lifecycleState
     */
    /**
     * @param lifecycleState the lifecycleState to set
     */
    var lifecycleState: State? = null

    /**
     * Take care of popping the fragment back stack or finishing the activity
     * as appropriate.
     */


    /**
     * It saves the current state of the application.
     */
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }

    @Throws(IOException::class)
    open fun getAddress(latitude: Double, longitude: Double): String? {
        val geocoder = Geocoder(this, Locale.getDefault())
        val addresses: List<Address> =
            geocoder.getFromLocation(latitude, longitude, 1) as List<Address>
        return addresses[0].getAddressLine(0)
    }

    open fun getImageUri(inContext: Context, inImage: Bitmap): Uri? {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(
            inContext.contentResolver,
            inImage,
            "Title",
            null
        )
        return Uri.parse(path)
    }

    fun setWindowFlag(bits: Int, on: Boolean) {
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        window.statusBarColor = Color.TRANSPARENT
        val win = window
        val winParams = win.attributes
        if (on) {
            winParams.flags = winParams.flags or bits
        } else {
            winParams.flags = winParams.flags and bits.inv()
        }
        win.attributes = winParams
    }

    open fun logoutUser(activity: Activity) {
        Preferences.logoutDefaults(activity)
//        Utility.startActivity(activity, MainActivity::class.java, Constants.CLEAR_BACK_STACK)

//        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build()
//
//        val googleSignInClient = GoogleSignIn.getClient(activity, gso)
//        googleSignInClient.signOut()
    }

    fun rotateImage(source: Bitmap, angle: Int): Bitmap? {
        val matrix = Matrix()
        matrix.postRotate(angle.toFloat())
        return Bitmap.createBitmap(
            source, 0, 0, source.width, source.height,
            matrix, true
        )
    }

    inline fun <reified T> parseArray(json: String, typeToken: Type): T {
        val gson = GsonBuilder().create()
        return gson.fromJson<T>(json, typeToken)
    }

    open fun onFailure(message: String, activity: Activity) {
        dismissWaitingDialog(activity)
        Utility.showDialog(
            activity,
            message, false
        )
    }


    @Throws(IOException::class)
    open fun getFilePathFromUri(uri: Uri, activity: Activity): Uri? {
        val fileName: String = getFileName(uri, activity)
        val file = File(activity.externalCacheDir, fileName)
        file.createNewFile()
        FileOutputStream(file).use { outputStream ->
            activity.contentResolver.openInputStream(uri).use { inputStream ->
                IOUtils.copyStream(
                    inputStream!!,
                    outputStream
                ) //Simply reads input to output stream
                outputStream.flush()
            }
        }
        return Uri.fromFile(file)
    }

    @SuppressLint("Range")
    fun getFileName(uri: Uri, activity: Activity): String {
        var result: String? = null
        if (uri.scheme == "content") {
            val cursor: Cursor? =
                activity.contentResolver.query(uri, null, null, null, null)
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                }
            } finally {
                cursor!!.close()
            }
        }
        if (result == null) {
            result = uri.path
            val cut = result!!.lastIndexOf('/')
            if (cut != -1) {
                result = result.substring(cut + 1)
            }
        }
        return result
    }

    open fun handleErrorMessage(`object`: JSONObject, activity: Activity) {
        try {
            if (`object`.has("error") && `object`.getString("message") == getString(R.string.auth_token_not_correct)) {
                logoutUser(activity)
            } else {
                val msg = `object`.getString("message")
                Utility.showDialog(activity, msg, false)
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    open fun showWaitingDialog(activity: Activity) {
        if (!loading) {
            activity.runOnUiThread {
                try {
                    progressDialog =
                        Dialog(activity)
                    if (progressDialog!!.window != null) {
                        progressDialog!!.window!!
                            .setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                    }
                    progressDialog!!.requestWindowFeature(
                        Window.FEATURE_NO_TITLE
                    )
                    progressDialog!!.setCancelable(false)
                    progressDialog!!.setContentView(R.layout.layout_lottie_loading)
                    progressDialog!!.show()
                    loading = true
                } catch (e: java.lang.Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    open fun dismissWaitingDialog(activity: Activity) {
        activity.runOnUiThread {
            try {
                if (progressDialog != null) {
                    progressDialog!!.dismiss()
                    progressDialog!!.cancel()
                    loading = false
                }
            } catch (ex: java.lang.Exception) {
                ex.printStackTrace()
                progressDialog = null
            }
        }
    }

    open fun onFailure(activity: Activity, message: String) {
        dismissWaitingDialog(activity)
        Utility.showDialog(
            activity,
            message, false
        )
    }

    /**
     * Restores the saved application state, in case of be needed, and does default settings for the action bar.
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity = this
        // Ensure a consistent default status bar across activities that extend ActivityBase
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                // white background with dark icons
                setStatusBarColor(ContextCompat.getColor(this, R.color.white), View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
            } else {
                // older devices: just set a dark status bar color so light icons remain visible
                setStatusBarColor(ContextCompat.getColor(this, R.color.black), 0)
            }
        } catch (e: Exception) {
            // ignore
        }
//        BlurKit.init(activity)
//        if (Util.LOG_ENABLED) {
//            Log.v(TAG, "protected void onCreate(Bundle savedInstanceState)")
 
    }

    @SuppressLint("NewApi")
    fun setApplicationLanguage(newLanguage: String) {
        val newLocale = Locale(newLanguage)
        val activityRes = resources
        val activityConf = activityRes.configuration
        activityConf.setLocale(newLocale)
        activityRes.updateConfiguration(activityConf, activityRes.displayMetrics)

        val applicationRes = activity.applicationContext.resources
        val applicationConf = applicationRes.configuration
        applicationConf.setLocale(newLocale)
        applicationRes.updateConfiguration(
            applicationConf,
            applicationRes.displayMetrics
        )
    }

    fun callFragmentWithAddBackStack(containerId: Int, fragment: Fragment, tag: String) {
        Utility.hideKeypad(activity)
//        supportFragmentManager
//            .beginTransaction()
//            .replace(containerId, fragment, tag)
//            .setCustomAnimations(
//                R.anim.slide_in_enter,
//                R.anim.slide_in_exit,
//                R.anim.slide_pop_enter,
//                R.anim.slide_pop_exit
//            )
//            .addToBackStack(tag)
//            .commit()
    }

    fun callFragmentWithReplace(containerId: Int, fragment: Fragment, tag: String?) {
        Utility.hideKeypad(activity)
//      bn
    }

    /**
     * if you will pass tag as title of fragment it will add that
     * fragment to stack otherwise will not add on stack.
     *
     * @param containerId
     * @param fragment
     * @param tag
     */
    fun callFragment(containerId: Int, fragment: Fragment, tag: String?) {
//        Utility.hideKeypad(activity)
//        activity.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
//        val transaction = supportFragmentManager
//            .beginTransaction()
//            .replace(containerId, fragment, tag)
//            .setCustomAnimations(
//                R.anim.slide_in_enter, R.anim.slide_in_exit,
//                R.anim.slide_pop_enter, R.anim.slide_pop_exit
//            )
//        if (tag != null)
//            transaction.addToBackStack(tag)
//                .commit()
//        else
//            transaction
//                .commit()
//
//        Utility.hideKeypad(activity)
    }

    fun callFragmentLossState(containerId: Int, fragment: Fragment, tag: String?) {
//        val transaction = supportFragmentManager
//            .beginTransaction()
//            .replace(containerId, fragment, tag)
//            .setCustomAnimations(
//                R.anim.slide_in_enter, R.anim.slide_in_exit,
//                R.anim.slide_pop_enter, R.anim.slide_pop_exit
//            )
//        if (tag != null)
//            transaction.addToBackStack(tag)
//                .commitAllowingStateLoss()
//        else
//            transaction
//                .commitAllowingStateLoss()
    }

    /**
     * if you will pass tag as title of fragment it will add that
     * fragment to stack otherwise will not add on stack.
     *
     * @param containerId
     * @param fragment
     * @param tag
     */
    fun addFragment(containerId: Int, fragment: Fragment, tag: String?) {
//        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
//        val transaction = supportFragmentManager
//            .beginTransaction()
//            .add(containerId, fragment, tag)
//            .setCustomAnimations(
//                R.anim.slide_in_enter, R.anim.slide_in_exit,
//                R.anim.slide_pop_enter, R.anim.slide_pop_exit
//            )
//        if (tag != null)
//            transaction.addToBackStack(tag)
//                .commit()
//        else
//            transaction
//                .commit()
    }

    /**
     * if you will pass tag as title of fragment it will add that
     * fragment to stack otherwise will not add on stack.
     *
     * @param fragment
     */
    fun removeFragment(fragment: Fragment) {
//        window.setSoftInputMode(
//            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
//        )
//        val transaction = supportFragmentManager
//            .beginTransaction()
//            .remove(fragment)
//            .setCustomAnimations(
//                R.anim.slide_in_enter, R.anim.slide_in_exit,
//                R.anim.slide_pop_enter, R.anim.slide_pop_exit
//            )
//        transaction
//            .commit()
    }

    /**
     * Pass Exact Uri of image to the method, and it will provide you the real path of image.
     *
     * @param contentUri
     * @return
     */
    fun getRealPathFromURI(contentUri: Uri): String {
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = contentResolver.query(contentUri, projection, null, null, null)
        val column_index = cursor!!.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor!!.moveToFirst()
        return cursor!!.getString(column_index)
    }

    override fun onStart() {
        super.onStart()
        lifecycleState = State.STARTED
    }

    override fun onResume() {
        super.onResume()
        lifecycleState = State.RESUMED
    }

    override fun onPause() {
        super.onPause()
        lifecycleState = State.PAUSED
    }


    override fun onStop() {
        super.onStop()
        lifecycleState = State.STOPPED
    }

    override fun onDestroy() {
        super.onDestroy()
        lifecycleState = State.DESTROYED
    }

    /**
     * The possibles states of an activity lifecycle.
     */
    enum class State {
        CREATED, STARTED, RESUMED, PAUSED, STOPPED, DESTROYED
    }


    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }

    @SuppressLint("ServiceCast")
    fun showKeyboard() {
        (getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager)
            .toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0)
    }

    fun hideKeyboard() {
        (getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager)
            .toggleSoftInput(0, InputMethodManager.HIDE_IMPLICIT_ONLY)
    }


    @Throws(Exception::class)
    private fun RGB565toARGB888(img: Bitmap): Bitmap {
        val numPixels = img.width * img.height
        val pixels = IntArray(numPixels)
        img.getPixels(pixels, 0, img.width, 0, 0, img.width, img.height)
        val result = Bitmap.createBitmap(img.width, img.height, Bitmap.Config.ARGB_8888)
        result.setPixels(pixels, 0, result.width, 0, 0, result.width, result.height)
        return result
    }

    fun noTrailingwhiteLines(text: CharSequence): CharSequence {
        var text = text

        while (text[text.length - 1] == '\n') {
            text = text.subSequence(0, text.length - 1)
        }

        while (text[text.length - 2] == '\n') {
            text = text.subSequence(0, text.length - 2)
        }
        return text
    }


    fun withSuffix(count: Long): String {
        if (count < 1000) return "" + count
        val exp = (Math.log(count.toDouble()) / Math.log(1000.0)).toInt()
        return String.format(
            "%.1f %c",
            count / Math.pow(1000.0, exp.toDouble()),
            "kMGTPE"[exp - 1]
        )
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    fun setStatusBarGradiant(flag: Int) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                val window = activity.window
//                val background = activity.resources.getDrawable(R.drawable.bg_tour_header)
//                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
//                window.statusBarColor = activity.resources.getColor(android.R.color.transparent)
//                //window.setNavigationBarColor(activity.getResources().getColor(android.R.color.transparent));
//                activity.window.decorView.systemUiVisibility = flag
//                window.setBackgroundDrawable(background)
//            }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    fun setStatusBarColor(color: Int, flag: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = activity.window
            // Ensure we draw system bar backgrounds and have no translucent status
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = color

            try {
                // Modern approach: use WindowInsetsControllerCompat to set light/dark icons
                val isLight = (flag and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR) != 0
                WindowCompat.setDecorFitsSystemWindows(window, true)
                WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = isLight
            } catch (e: Exception) {
                // Fallback for older devices: use legacy systemUiVisibility
                activity.window.decorView.systemUiVisibility = flag
            }
        }
    }

    fun BitmapToFile(mAct: Activity, bitmap: Bitmap): File {
        val f = File(mAct.cacheDir, "pic.png")
        try {
            f.createNewFile()

            //Convert bitmap to byte array
            val bos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100 /*ignored for PNG*/, bos)
            val bitmapdata = bos.toByteArray()

//write the bytes in file
            val fos = FileOutputStream(f)
            fos.write(bitmapdata)
            fos.flush()
            fos.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return f
    }

    companion object {
        lateinit var activity: AppCompatActivity
    }

    override fun onBackPressed() {
        Utility.hideKeypad(activity)
        Utility.finishActivity(activity)
    }


}