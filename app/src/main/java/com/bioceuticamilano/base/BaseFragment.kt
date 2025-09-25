package app.carrental.base

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.Dialog
import android.content.Context
import android.database.Cursor
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.OpenableColumns
import android.text.format.DateFormat
import android.transition.Fade
import android.transition.TransitionInflater
import android.transition.TransitionSet
import android.view.Window
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.bioceuticamilano.Constants
import com.bioceuticamilano.MainActivity
import com.bioceuticamilano.R
import com.bioceuticamilano.base.ActivityBase
import com.bioceuticamilano.utils.Preferences
import com.bioceuticamilano.utils.Utility
import com.google.android.gms.common.util.IOUtils.copyStream
import org.json.JSONObject
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.net.MalformedURLException
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*


open class BaseFragment : Fragment() {
    var loading = false
    var progressDialog: Dialog? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    fun isContentNull(content: String): Boolean {
        return content.isEmpty()
    }

    fun isEmailValid(email: String): Boolean {
        return email.contains("@")
    }

    open fun onFailure(message: String) {
        dismissWaitingDialog()
        Utility.showDialog(
            requireActivity(),
            message, false
        )
    }

    open fun handleErrorMessage(`object`: JSONObject) {
        try {
            if (`object`.has("error") && `object`.getString("message") == getString(R.string.auth_token_not_correct)) {
                logoutUser()
            } else {
                val msg = `object`.getString("message")
                Utility.showDialog(activity, msg, false)
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    open fun logoutUser() {
        Preferences.logoutDefaults(requireContext())
        Utility.startActivity(activity, MainActivity::class.java, Constants.CLEAR_BACK_STACK)
    }

    fun changeLanguage(local_language: String) {
        val res = resources
        val dm = res.displayMetrics
        val conf = res.configuration
        conf.locale = Locale(local_language)
        res.updateConfiguration(conf, dm)
    }

    open fun showWaitingDialog() {
        if (!loading) {
            try {
                val activity = requireActivity()
                activity.runOnUiThread {
                    try {
                        progressDialog = Dialog(activity)
                        progressDialog?.window?.let { window ->
                            window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                        }
                        progressDialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
                        progressDialog?.setCancelable(false)
                        progressDialog?.setContentView(R.layout.layout_lottie_loading)
                        progressDialog?.show()
                        loading = true
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    open fun dismissWaitingDialog() {
        try {
            val activity = requireActivity()
            activity.runOnUiThread {
                try {
                    progressDialog?.let { dialog ->
                        dialog.dismiss()
                        dialog.cancel()
                    }
                    progressDialog = null
                    loading = false
                } catch (ex: Exception) {
                    ex.printStackTrace()
                    progressDialog = null
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @Throws(IOException::class)
    open fun getFilePathFromUri(uri: Uri): Uri? {
        try {
            val fileName: String = getFileName(uri)
            val context = requireContext()
            val cacheDir = context.getExternalCacheDir() ?: return null
            val file = File(cacheDir, fileName)
            file.createNewFile()
            FileOutputStream(file).use { outputStream ->
                context.contentResolver.openInputStream(uri)?.use { inputStream ->
                    copyStream(inputStream, outputStream)
                    outputStream.flush()
                }
            }
            return Uri.fromFile(file)
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

    @SuppressLint("Range")
    fun getFileName(uri: Uri): String {
        var result: String? = null
        if (uri.scheme == "content") {
            val cursor: Cursor? =
                requireActivity().contentResolver.query(uri, null, null, null, null)
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                }
            } finally {
                cursor?.close()
            }
        }
        if (result == null) {
            result = uri.path
            val cut = result?.lastIndexOf('/') ?: -1
            if (cut != -1) {
                result = result?.substring(cut + 1)
            }
        }
        return result ?: "unknown_file"
    }


//    protected fun callFragmentWithAddBackStack(containerId: Int, fragment: Fragment, tag: String) {
//        ActivityBase.activity!!.supportFragmentManager.beginTransaction()
//            .replace(containerId, fragment, tag).setCustomAnimations(
//                R.anim.slide_in_enter,
//                R.anim.slide_in_exit,
//                R.anim.slide_pop_enter,
//                R.anim.slide_pop_exit
//            ).addToBackStack(tag).commit()
//    }
//
//    fun callFragmentWithBackStack(containerId: Int, fragment: Fragment, tag: String) {
//        ActivityBase.activity!!.supportFragmentManager.beginTransaction()
//            .replace(containerId, fragment, tag).setCustomAnimations(
//                R.anim.slide_in_enter,
//                R.anim.slide_in_exit,
//                R.anim.slide_pop_enter,
//                R.anim.slide_pop_exit
//            ).addToBackStack(tag).commit()
//    }
//
//
//    fun addFragment(containerId: Int, fragment: Fragment, tag: String?) {
//        ActivityBase.activity!!.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
//        val transaction = ActivityBase.activity!!.supportFragmentManager.beginTransaction()
//            .setCustomAnimations(
//                R.anim.slide_in_enter,
//                R.anim.slide_in_exit,
//                R.anim.slide_pop_enter,
//                R.anim.slide_pop_exit
//            ).add(containerId, fragment, tag)
//
//        if (tag != null) transaction.addToBackStack(tag).commit()
//        else transaction.commit()
//    }
//
//    fun openSideMenu(containerId: Int, fragment: Fragment, tag: String?) {
//
//        ActivityBase.activity.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
//        val transaction = ActivityBase.activity.supportFragmentManager.beginTransaction()
//            .setCustomAnimations(
//                R.anim.slide_in_left,
//                R.anim.slide_in_left_exit,
//                R.anim.slide_in_left,
//                R.anim.slide_in_left_exit
//            ).add(containerId, fragment, tag)
//
//
//        if (tag != null) transaction.addToBackStack(tag).commit()
//        else transaction.commit()
//    }
//
//    fun addFragmentChild(
//        containerId: Int,
//        fragment: Fragment,
//        tag: String?,
//        manager: androidx.fragment.app.FragmentManager
//    ) {
//
//        ActivityBase.activity.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
//        val transaction = manager.beginTransaction().add(containerId, fragment, tag)
//            .setCustomAnimations(
//                R.anim.slide_in_enter,
//                R.anim.slide_in_exit,
//                R.anim.slide_pop_enter,
//                R.anim.slide_pop_exit
//            )
//
//        if (tag != null) transaction.addToBackStack(tag).commit()
//        else transaction.commit()
//    }
//
//
//    fun callFragmentWithReplace(containerId: Int, fragment: Fragment, tag: String?) {
//        Utility.hideKeypad(activity)
//        ActivityBase.activity.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
//        val transaction = ActivityBase.activity.supportFragmentManager
//            .beginTransaction().replace(containerId, fragment, tag).setCustomAnimations(
//                R.anim.slide_in_enter,
//                R.anim.slide_in_exit,
//                R.anim.slide_pop_enter,
//                R.anim.slide_pop_exit
//            )
//        if (tag != null) transaction.addToBackStack(tag).commit()
//        else transaction.commit()
//    }

    open fun openFragmentWithoutBackStack(
        contentId: Int?,
        fragment: Fragment?,
        activity: AppCompatActivity
    ) {
        try {
            Utility.hideKeypad(activity)
            val fm = activity.supportFragmentManager
            val fragmentTransaction = fm.beginTransaction()
            fragmentTransaction.replace(contentId!!, fragment!!)
            fragmentTransaction.commit()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

//    fun callFragmentWithReplaceAndShareElement(
//        containerId: Int,
//        fragment: Fragment,
//        shareElement: View,
//        transitionName: String,
//        tag: String?
//    ) {
//        val transaction = requireActivity()!!.supportFragmentManager.beginTransaction()
//            .addSharedElement(shareElement, transitionName).replace(containerId, fragment, tag)
//            .setCustomAnimations(
//                R.anim.slide_in_enter,
//                R.anim.slide_in_exit,
//                R.anim.slide_pop_enter,
//                R.anim.slide_pop_exit
//            )
//        if (tag != null) transaction.addToBackStack(tag).commit()
//        else transaction.commit()
//    }


    fun clearBackStack() {
        requireActivity()!!.supportFragmentManager.popBackStack(
            null,
            androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE
        )
    }

    open fun clearBackStack(activity: AppCompatActivity) {
        try {
            val fm = activity.supportFragmentManager
            fm.popBackStackImmediate(
                null,
                androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE
            )
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    fun DateFormatter(date: String): String {
        val a = date.replace("\\D+".toRegex(), "")
        val timeInMillis = java.lang.Long.parseLong(a)
        if (timeInMillis < 0) return ""
        val calendar = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault())
        calendar.timeInMillis = timeInMillis
        val _date = calendar.time
        val mYear = calendar.get(Calendar.YEAR)
        val mMonth = calendar.get(Calendar.MONTH) + 1
        val mDay = calendar.get(Calendar.DAY_OF_MONTH)
        return mDay.toString() + "/" + mMonth + "/" + mYear
    }

    fun DateFormatter1(date: String): String {
        val a = date.replace("\\D+".toRegex(), "")
        val timeInMillis = java.lang.Long.parseLong(a)
        if (timeInMillis < 0) return ""
        val calendar = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault())
        calendar.timeInMillis = timeInMillis
        val _date = calendar.time
        val mYear = calendar.get(Calendar.YEAR)
        val mMonth = calendar.get(Calendar.MONTH) + 1
        val mDay = calendar.get(Calendar.DAY_OF_MONTH)
        return mYear.toString() + "-" + mMonth + "-" + mDay
    }

    fun getUriFromUrl(thisUrl: String): Uri {
        val builder: Uri.Builder
        var url: URL? = null
        try {
            url = URL(thisUrl)
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        }

        builder = Uri.Builder().scheme(url!!.protocol).authority(url.authority).appendPath(url.path)
        return builder.build()
    }

    fun DateHeader(date: String): Long {
        val a = date.replace("\\D+".toRegex(), "")
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = java.lang.Long.parseLong(a)
        return calendar.get(Calendar.DAY_OF_MONTH).toLong()
    }

    fun GetDateTime(): String {
        val calendar = Calendar.getInstance()
        val mYear = calendar.get(Calendar.YEAR)
        val mMonth = calendar.get(Calendar.MONTH)
        val mDay = calendar.get(Calendar.DAY_OF_MONTH)

        val month_date = SimpleDateFormat("MMM")
        val month_name = month_date.format(calendar.time)
        val delegate = "hh:mm aaa"
        val time = DateFormat.format(delegate, Calendar.getInstance().time) as String

        return "$month_name $mDay, $mYear $time"
    }


    fun Get24FormatTime(date: String): String {
        val a = date.replace("\\D+".toRegex(), "")
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = java.lang.Long.parseLong(a)

        val delegate = "hh:mm"

        return DateFormat.format(delegate, calendar.time) as String
    }

    fun GetDateTimeComment(milli: String): String {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = java.lang.Long.parseLong(DateMilli(milli))
        val mYear = calendar.get(Calendar.YEAR)
        val mDay = calendar.get(Calendar.DAY_OF_MONTH)

        val month_date = SimpleDateFormat("MMM")
        val month_name = month_date.format(calendar.time)
        val delegate = "hh:mm aaa"
        val time = DateFormat.format(delegate, calendar.time) as String

        return "$month_name $mDay, $mYear $time"
    }

    fun DateMilli(date: String): String {
        return date.replace("\\D+".toRegex(), "")
    }


    fun ClearAllFragments() {
        try {
            val activity = requireActivity()
            for (loop in 0 until activity.supportFragmentManager.backStackEntryCount) {
                activity.supportFragmentManager.popBackStackImmediate()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    companion object {
        var searchView: SearchView? = null

        fun getDataColumn(
            context: Context,
            uri: Uri,
            selection: String,
            selectionArgs: Array<String>
        ): String? {
            var cursor: Cursor? = null
            val column = "_data"
            val projection = arrayOf(column)
            try {
                cursor =
                    context.contentResolver.query(uri, projection, selection, selectionArgs, null)
                if (cursor != null && cursor.moveToFirst()) {
                    val column_index = cursor.getColumnIndexOrThrow(column)
                    return cursor.getString(column_index)
                }
            } finally {
                cursor?.close()
            }
            return null
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    fun performTransition(containerId: Int, fragment: Fragment, tag: String?) {
        try {
            val activity = ActivityBase.activity ?: return
            val MOVE_DEFAULT_TIME: Long = 0
            val FADE_DEFAULT_TIME: Long = 300

            val previousFragment = activity.supportFragmentManager.findFragmentById(containerId)
            val nextFragment = fragment
            val fragmentTransaction = activity.supportFragmentManager.beginTransaction()

            // 1. Exit for Previous Fragment
            val exitFade = Fade()
            exitFade.duration = FADE_DEFAULT_TIME
            previousFragment?.exitTransition = exitFade

            // 2. Shared Elements Transition
            val enterTransitionSet = TransitionSet()
            enterTransitionSet.addTransition(
                TransitionInflater.from(activity)
                    .inflateTransition(android.R.transition.explode)
            )
            enterTransitionSet.duration = MOVE_DEFAULT_TIME
            enterTransitionSet.startDelay = FADE_DEFAULT_TIME
            nextFragment.sharedElementEnterTransition = enterTransitionSet

            // 3. Enter Transition for New Fragment
            val enterFade = Fade()
            enterFade.duration = FADE_DEFAULT_TIME
            nextFragment.enterTransition = enterFade

            fragmentTransaction.add(containerId, nextFragment)
            if (tag != null) fragmentTransaction.addToBackStack(tag)
            fragmentTransaction.commitAllowingStateLoss()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Clean up any resources if needed
    }
}