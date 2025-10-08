package com.bioceuticamilano.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.nfc.Tag;
import android.nfc.tech.MifareClassic;
import android.nfc.tech.MifareUltralight;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import com.bioceuticamilano.ui.Constants;
import com.bumptech.glide.Glide;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.bioceuticamilano.R;
import okhttp3.MediaType;
import okhttp3.RequestBody;


/**
 * Created by DeviceBee on 9/3/2018.
 */

public class Utility {


    public static Boolean LeftOrientation = true;

    public static void openWhatsapp(Activity activity, String number) {
        String url = "https://api.whatsapp.com/send?phone=" + number;
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        activity.startActivity(i);
    }

    public static void setWhiteStatusBarICons(Activity activity) {
        try {
            Window window = activity.getWindow();
            // Ensure we draw system bar backgrounds
            WindowCompat.setDecorFitsSystemWindows(window, true);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.WHITE);
            // Set dark icons (appearance light) on light background
            new WindowInsetsControllerCompat(window, window.getDecorView()).setAppearanceLightStatusBars(true);
        } catch (Exception e) {
            // Fallback
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            }
        }
    }

    public static void request_location(final Activity activity) {
        LocationRequest mLocationRequest = LocationRequest.create();
        mLocationRequest.setInterval(60000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationCallback mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    if (location != null) {
                        //TODO: UI updates.
                    }
                }
            }
        };
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationServices.getFusedLocationProviderClient(activity).requestLocationUpdates(mLocationRequest, mLocationCallback, null);
    }

    public static void setWhiteStatusBar(Activity activity) {
        try {
            Window window = activity.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.WHITE);
            new WindowInsetsControllerCompat(window, window.getDecorView()).setAppearanceLightStatusBars(true);
        } catch (Exception e) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = activity.getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.WHITE);
                activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        }
    }

//    public static Location Complete_location_process(final Activity activity) {
//
//        //Checking Location permission
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            if (Utility.Check_USer_Location_PERMSIISSION(activity)) {
//                //Checking GPS
//                Utility.request_location(activity);
//
//                LocationRequest locationRequest = LocationRequest.create();
//                locationRequest.setInterval(10000);
//                locationRequest.setFastestInterval(5000);
//                locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//
//                LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
//                        .addLocationRequest(locationRequest);
//
//                SettingsClient client = LocationServices.getSettingsClient(activity);
//                Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());
//
//
//                task.addOnSuccessListener(activity, new OnSuccessListener<LocationSettingsResponse>() {
//                    @Override
//                    public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
//                        //This means that the GPS was already opened
//                        FusedLocationProviderClient mfused_location;
//                        mfused_location = LocationServices.getFusedLocationProviderClient(activity);
//
//                        try {
//                            if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 100);
//                                return;
//                            }
//                            Task location = mfused_location.getLastLocation();
//                            location.addOnCompleteListener(new OnCompleteListener() {
//                                @Override
//                                public void onComplete(@NonNull Task task) {
//                                    if (task.getResult() != null) {
//                                        Location MY_LOCATION = (Location) task.getResult();
////                                        return MY_LOCATION;
//                                    } else {
////                                        GET_POPULAR_CATEGORIES();
//                                        Toast.makeText(activity, "Could not get location", Toast.LENGTH_SHORT).show();
//                                    }
//                                }
//                            });
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                });
//
//                task.addOnFailureListener(activity, new OnFailureListener() {
//
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        if (e instanceof ResolvableApiException) {
//                            // Location settings are not satisfied, but this can be fixed
//                            // by showing the user a dialog.
//                            try {
//                                // Show the dialog by calling startResolutionForResult(),
//                                // and check the result in onActivityResult().
//                                ResolvableApiException resolvable = (ResolvableApiException) e;
//                                resolvable.startResolutionForResult(activity,
//                                        100);
//                            } catch (IntentSender.SendIntentException sendEx) {
//                                // Ignore the error.
//                            }
//                        }
//                    }
//                });
//            }
//        }
//
//        @Override
//        public void onRequestPermissionsResult(int requestCode,
//        String permissions[], int[] grantResults) {
//            if (requestCode == LOCATION_PERMISSION) {
//                if (grantResults.length > 0
//                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    // permission was granted, yay!
//                    createLocationRequest();
//                } else {
//                    GET_POPULAR_CATEGORIES();
//                    // permission denied, boo! Disable the
//                    // functionality that depends on this permission.
//                    Utility.showToast(activity, "Please grant location permission for better results");
//                }
//            }
//        }
//
//    }


    public static boolean Check_USer_Location_PERMSIISSION(final Activity activity) {
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.ACCESS_FINE_LOCATION)) {
            } else {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 100);
            }
            return false;
        } else {
            return true;
        }
    }

    public static void statusbarlight(final Activity activity) {
        try {
            Window window = activity.getWindow();
            new WindowInsetsControllerCompat(window, window.getDecorView()).setAppearanceLightStatusBars(true);
        } catch (Exception e) {
            activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }


    public static void makeTextViewResizable(final TextView tv, final int maxLine, final String expandText, final boolean viewMore) {

        if (tv.getTag() == null) {
            tv.setTag(tv.getText());
        }
        ViewTreeObserver vto = tv.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @SuppressWarnings("deprecation")
            @Override
            public void onGlobalLayout() {
                String text;
                int lineEndIndex;
                ViewTreeObserver obs = tv.getViewTreeObserver();
                obs.removeGlobalOnLayoutListener(this);
                if (maxLine == 0) {
                    lineEndIndex = tv.getLayout().getLineEnd(0);
                    text = tv.getText().subSequence(0, lineEndIndex - expandText.length() + 1) + " " + expandText;
                } else if (maxLine > 0 && tv.getLineCount() >= maxLine) {
                    lineEndIndex = tv.getLayout().getLineEnd(maxLine - 1);
                    text = tv.getText().subSequence(0, lineEndIndex - expandText.length() + 1) + " " + expandText;
                } else {
                    lineEndIndex = tv.getLayout().getLineEnd(tv.getLayout().getLineCount() - 1);
                    text = tv.getText().subSequence(0, lineEndIndex) + " " + expandText;
                }
                tv.setText(text);
                tv.setMovementMethod(LinkMovementMethod.getInstance());
                tv.setText(
                        addClickablePartTextViewResizable(Html.fromHtml(tv.getText().toString()), tv, lineEndIndex, expandText,
                                viewMore), TextView.BufferType.SPANNABLE);
            }
        });
    }

    public static SpannableStringBuilder addClickablePartTextViewResizable(final Spanned strSpanned, final TextView tv,
                                                                           final int maxLine, final String spanableText, final boolean viewMore) {
        String str = strSpanned.toString();
        SpannableStringBuilder ssb = new SpannableStringBuilder(strSpanned);

        if (str.contains(spanableText)) {
            ssb.setSpan(new ClickableSpan() {

                @Override
                public void onClick(View widget) {
                    tv.setLayoutParams(tv.getLayoutParams());
                    tv.setText(tv.getTag().toString(), TextView.BufferType.SPANNABLE);
                    tv.invalidate();
                    if (viewMore) {
                        makeTextViewResizable(tv, -1, "View Less", false);
                    } else {
                        makeTextViewResizable(tv, 2, "View More", true);
                    }

                }
            }, str.indexOf(spanableText), str.indexOf(spanableText) + spanableText.length(), 0);

        }
        return ssb;
    }

    public static boolean isNetworkAvailable(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (context.checkSelfPermission(Manifest.permission.ACCESS_NETWORK_STATE)
                    == PackageManager.PERMISSION_GRANTED) {

                ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                @SuppressLint("MissingPermission") NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                boolean isAvailable = activeNetworkInfo != null && activeNetworkInfo.isConnected();
                if (!isAvailable)
                    Toast.makeText(context,
                            R.string.please_check_your_internet,
                            Toast.LENGTH_SHORT).show();
                return isAvailable;

            }
        }

        return false;
    }

    public static int getProgressPercentage(long currentDuration, long totalDuration) {
        double percentage = 0;

        long currentSeconds = (int) (currentDuration / 1000);
        long totalSeconds = (int) (totalDuration / 1000);

        // calculating percentage
        percentage = (((double) currentSeconds) / totalSeconds) * 100;

        // return percentage
        return (int) percentage;
    }

    public static Uri getImageContentUri(Context context, File imageFile) {
        String filePath = imageFile.getAbsolutePath();
        Cursor cursor = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.Media._ID},
                MediaStore.Images.Media.DATA + "=? ",
                new String[]{filePath}, null);
        if (cursor != null && cursor.moveToFirst()) {
            @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns._ID));
            cursor.close();
            return Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "" + id);
        } else {
            if (imageFile.exists()) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, filePath);
                return context.getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                return null;
            }
        }
    }

    public static Bitmap convert(String base64Str) throws IllegalArgumentException {
        byte[] decodedBytes = Base64.decode(
                base64Str.substring(base64Str.indexOf(",") + 1),
                Base64.DEFAULT
        );

        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }

    public static String convert(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);

        return Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
    }

    public static Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public static int roundPercent(double val) {
        try {
            int returnVal;
            returnVal = (int) Math.round(val);
            return returnVal;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static int roundPercent(String val) {
        try {
            int returnVal;
            returnVal = (int) Math.round(Double.parseDouble(val));
            return returnVal;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static boolean validateDateFormat(String dateToValdate, String format) {

        Date date = null;

        String dateArray[] = dateToValdate.split("/");
        String secondPart = "20" + dateArray[1];

        try {
            DateFormat df = new SimpleDateFormat(format);
            df.setLenient(false);
            date = df.parse(dateArray[0] + "/" + secondPart);

            Calendar c = Calendar.getInstance();
            String formattedDate = df.format(c.getTime());
            Date date1 = df.parse(formattedDate);

            if (date.after(date1))
                return true;
            else
                return false;
        } catch (ParseException e) {
            return false;
        }
    }

    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean isValidMobile(String phone) {
        if (!Pattern.matches("[a-zA-Z]+", phone)) {
            return phone.length() > 8 && phone.length() <= 16;
        }
        return false;
    }

    public static String getMimeType(String url) {
        String extension = url.substring(url.lastIndexOf("."));
        String mimeTypeMap = MimeTypeMap.getFileExtensionFromUrl(extension);
        String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(mimeTypeMap);
        return mimeType;
    }

    public static String convert24to12(Date date) {
        String convertedTime = "";

        SimpleDateFormat displayFormat = new SimpleDateFormat("hh:mm a");
        convertedTime = displayFormat.format(date);
        System.out.println("convertedTime : " + convertedTime);

        return convertedTime;
        //Output will be 10:23 PM
    }

    public static boolean isValidPassword(final String password) {
        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }

    public static String convertDecimal(final double amount) {
        return new DecimalFormat("#######.##").format(amount);
    }


    public static int spToPx(final Context context, final float sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, context.getResources().getDisplayMetrics());
    }


    public static void dismissProgressDialog(Dialog progressDialog) {
        try {
            if (progressDialog != null) {
                progressDialog.dismiss();
                progressDialog.cancel();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            progressDialog = null;
        }


    }

    public static String getFormattedDateFromCalendarPicker(String date) {
        if (date != null) {

            DateFormat dateFormat = null;
            Date requestDate = null;
            try {
                dateFormat = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());
                requestDate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).parse(date);
//            requestDate = gmttoLocalDate(requestDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (requestDate != null) {
//            String timeZone = Calendar.getInstance().getTimeZone().getID();
                dateFormat.setTimeZone(Calendar.getInstance().getTimeZone());
                return dateFormat.format(requestDate);
            } else {
                return null;
            }
        } else {
            return "";
        }
    }

    public static String getFormattedDate(String dateTime) {
        if (dateTime != null) {
            String date = dateTime;
//            String time = dateTime.substring(11, 19);
//            String dt = date + " " + time;
            DateFormat dateFormat = null;
            Date requestDate = null;
            try {
                dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                requestDate = new SimpleDateFormat("MM-dd-yyyy", Locale.getDefault()).parse(date);
//            requestDate = gmttoLocalDate(requestDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (requestDate != null) {
//            String timeZone = Calendar.getInstance().getTimeZone().getID();
                dateFormat.setTimeZone(Calendar.getInstance().getTimeZone());
                return dateFormat.format(requestDate);
            } else {
                return null;
            }
        } else {
            return "";
        }
    }

    public static String getDate_toLetters_From_digits(String dateTime) {
        if (dateTime != null) {
            String date = dateTime;
            String dt = date;
            DateFormat dateFormat = null;
            Date requestDate = null;
            try {
                dateFormat = new SimpleDateFormat("dd MMMM yyyy ", Locale.getDefault());
                requestDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(dt);
//            requestDate = gmttoLocalDate(requestDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (requestDate != null) {
//            String timeZone = Calendar.getInstance().getTimeZone().getID();
                dateFormat.setTimeZone(Calendar.getInstance().getTimeZone());
                return dateFormat.format(requestDate);
            } else {
                return null;
            }
        } else {
            return "";
        }
    }

    public static String getFormattedDateGeneric(String date, String from, String to) {
        DateFormat dateFormat = null;
        Date requestDate = null;
        try {
            dateFormat = new SimpleDateFormat(from, Locale.getDefault());
            requestDate = new SimpleDateFormat(to, Locale.getDefault()).parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (requestDate != null) {
            dateFormat.setTimeZone(Calendar.getInstance().getTimeZone());
            return dateFormat.format(requestDate);
        } else {
            return null;
        }
    }

    public static String calculateTimeAgo(String dateTime) {
        String time = dateTime;
        String dt = time;
        DateFormat dateFormat = null;
        Date requestDate = null;
        try {
            requestDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).parse(dt);
//            requestDate = gmttoLocalDate(requestDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (requestDate != null) {
//            String timeZone = Calendar.getInstance().getTimeZone().getID();
            dateFormat.setTimeZone(Calendar.getInstance().getTimeZone());
            return dateFormat.format(requestDate);
        } else {
            return null;
        }
    }

    public static String getFormattedTime_toAm(String dateTime) {
        String time = dateTime;
        String dt = time;
        DateFormat dateFormat = null;
        Date requestDate = null;
        try {
            dateFormat = new SimpleDateFormat("hh:mmaa", Locale.getDefault());
            requestDate = new SimpleDateFormat("HH:mm", Locale.getDefault()).parse(dt);
//            requestDate = gmttoLocalDate(requestDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (requestDate != null) {
//            String timeZone = Calendar.getInstance().getTimeZone().getID();
            dateFormat.setTimeZone(Calendar.getInstance().getTimeZone());
            return dateFormat.format(requestDate);
        } else {
            return null;
        }
    }

    public static String getFormattedTimeOnly(String dateTime) {
        DateFormat dateFormat = null;
        Date requestDate = null;
        try {
            dateFormat = new SimpleDateFormat("hh:mmaa", Locale.getDefault());
            requestDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.getDefault()).parse(dateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (requestDate != null) {
            dateFormat.setTimeZone(Calendar.getInstance().getTimeZone());
            return dateFormat.format(requestDate);
        } else {
            return null;
        }
    }

    public static String getFormattedDateandTime(String date) {
        DateFormat dateFormat = null;
        DateFormat timeFormat = null;
        Date requestDate = null;
        try {
            timeFormat = new SimpleDateFormat("hh:mmaa", Locale.getDefault());
            dateFormat = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());
            requestDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.getDefault()).parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (requestDate != null) {
            dateFormat.setTimeZone(Calendar.getInstance().getTimeZone());
            timeFormat.setTimeZone(Calendar.getInstance().getTimeZone());
            return dateFormat.format(requestDate) + " at " + timeFormat.format(requestDate);
        } else {
            return null;
        }
    }

    public static String getFormattedDateOnly(String date) {
        DateFormat dateFormat = null;
        Date requestDate = null;
        try {
            dateFormat = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());
            requestDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.getDefault()).parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (requestDate != null) {
            dateFormat.setTimeZone(Calendar.getInstance().getTimeZone());
            return dateFormat.format(requestDate);
        } else {
            return null;
        }
    }


    public static String localToUTC(String dateTime) {
        return dateTime;
        // if needed un comment below

//        Date requestDate = null;
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault());
//        try {
//            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
//            requestDate = sdf.parse(dateTime);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//
//        if (requestDate != null) {
//            return sdf.format(requestDate);
//        } else {
//            return null;
//        }

    }


    public static Date gmttoLocalDate(Date date) {
        String timeZone = Calendar.getInstance().getTimeZone().getID();
        return new Date(date.getTime() + TimeZone.getTimeZone(timeZone).getOffset(date.getTime()));
    }

    public static void showKeypad(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    public static void hideKeypad(Activity activity) {
        try {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            //Find the currently focused view, so we can grab the correct window token from it.
            View view = activity.getCurrentFocus();
            //If no view currently has focus, create a new one, just so we can grab a window token from it
            if (view == null) {
                view = new View(activity);
            }
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        } catch (Exception e) {
        }
    }

    public static void showDialog(final Context activity, String message) {
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.error_dialogue, null);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity);
        dialogBuilder.setView(dialogView);

        TextView tvMessage = dialogView.findViewById(R.id.tvMessage);
        tvMessage.setText(message);

        Button btYes = dialogView.findViewById(R.id.btYes);

        AlertDialog alertDialog = dialogBuilder.create();
        Objects.requireNonNull(alertDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
        Window window = alertDialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        alertDialog.show();

        btYes.setOnClickListener(v -> {
            alertDialog.dismiss();
        });
    }

    public static void showDialog(final Activity activity, String message, final boolean isFinish) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.error_dialogue, null);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity);
        dialogBuilder.setView(dialogView);

        TextView tvMessage = dialogView.findViewById(R.id.tvMessage);
        tvMessage.setText(message);

        Button btYes = dialogView.findViewById(R.id.btYes);

        AlertDialog alertDialog = dialogBuilder.create();
        Objects.requireNonNull(alertDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
        Window window = alertDialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        alertDialog.show();

        btYes.setOnClickListener(v -> {
            if (isFinish)
                activity.finish();
            alertDialog.dismiss();
        });
    }

    public static void showsDialogOnclick(final Activity activity, String message, View.OnClickListener clickListener) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.error_dialogue, null);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity);
        dialogBuilder.setView(dialogView);

        TextView tvMessage = dialogView.findViewById(R.id.tvMessage);
        tvMessage.setText(message);

        Button btYes = dialogView.findViewById(R.id.btYes);

        AlertDialog alertDialog = dialogBuilder.create();
        Objects.requireNonNull(alertDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
        Window window = alertDialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        alertDialog.show();

        btYes.setOnClickListener(clickListener);
    }

    public static void showSuccessDialogOnclick(final Activity activity, String message, View.OnClickListener clickListener) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.success_dialogue, null);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity);
        dialogBuilder.setView(dialogView);

        TextView tvMessage = dialogView.findViewById(R.id.tvMessage);
        tvMessage.setText(message);

        Button btYes = dialogView.findViewById(R.id.btYes);

        AlertDialog alertDialog = dialogBuilder.create();
        Objects.requireNonNull(alertDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
        Window window = alertDialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        alertDialog.show();

        btYes.setOnClickListener(clickListener);
    }

    public static void showSuccessDialog(final Activity activity, String message, final boolean isFinish) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.success_dialogue, null);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity);
        dialogBuilder.setView(dialogView);

        TextView tvMessage = dialogView.findViewById(R.id.tvMessage);
        tvMessage.setText(message);

        Button btYes = dialogView.findViewById(R.id.btYes);

        AlertDialog alertDialog = dialogBuilder.create();
        Objects.requireNonNull(alertDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
        Window window = alertDialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        alertDialog.show();

        btYes.setOnClickListener(v -> {
            if (isFinish) {
                alertDialog.dismiss();
                Utility.finishActivity(activity);
            } else {
                alertDialog.dismiss();
            }

        });
    }

    public static String getDeviceName() {
        String deviceName = "";
        deviceName = Build.MANUFACTURER + Build.MODEL;
        return deviceName;
    }

    public static String getOSVersion() {
        return Build.VERSION.RELEASE;
    }

    public static String getAppVersion(Activity activity) {
        try {
            PackageInfo pInfo = activity.getPackageManager().getPackageInfo(activity.getPackageName(), 0);
            return pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "1.0.0";

    }

    public static String getDeviceOS() {
        String deviceOs = "";

        StringBuilder builder = new StringBuilder();
        builder.append("version=").append(Build.VERSION.RELEASE);

        Field[] fields = Build.VERSION_CODES.class.getFields();
        for (Field field : fields) {
            String fieldName = field.getName();
            int fieldValue = -1;

            try {
                fieldValue = field.getInt(new Object());
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }

            if (fieldValue == Build.VERSION.SDK_INT) {
                builder.append(":").append("name=").append(fieldName).append(":");
                builder.append("sdk=").append(fieldValue);
                deviceOs = builder.toString();
            }
        }

        Log.e("sss", builder.toString());

        return deviceOs;
    }

    public static File BitmapToFile(Activity mAct, Bitmap bitmap) {
        File f = new File(mAct.getCacheDir(), "pic.png");
        try {
            f.createNewFile();

            //Convert bitmap to byte array
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100 /*ignored for PNG*/, bos);
            byte[] bitmapdata = bos.toByteArray();

//write the bytes in file
            FileOutputStream fos = new FileOutputStream(f);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return f;
    }

    public static Bitmap getBitmapFromFile(String url, int size) {
        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        BitmapFactory.decodeFile(url, options);
        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, size, size);
        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        options.inMutable = true;
        Bitmap bitmap = BitmapFactory.decodeFile(url, options);
        if (bitmap == null || bitmap.isRecycled()) {
            return null;
        } else
            return bitmap;
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            // Calculate ratios of height and width to requested height and width
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);

            // Choose the smallest ratio as inSampleSize value, this will guarantee
            // a final image with both dimensions larger than or equal to the
            // requested height and width.
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }

        return inSampleSize;
    }

    public static void showToast(Activity activity, String msg) {
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
    }


    public static String getDecimalValue(int value) {
        try {
            return String.format(Locale.getDefault(), "%.2f", (double) value);
        } catch (Exception ex) {
            return String.valueOf(value);
        }
    }

    public static String getDecimalValue(String value) {
        try {
            return String.format(Locale.getDefault(), "%.2f", Double.parseDouble(value));
        } catch (Exception ex) {
            return value;
        }
    }

    public static String getDecimalValue(Double value) {
        try {
            return String.format(Locale.getDefault(), "%.2f", value);
        } catch (Exception ex) {
            return String.valueOf(value);
        }
    }





    // used for facebook login
    public static void printHashKey(Context pContext) {
        try {
            PackageInfo info = pContext.getPackageManager().getPackageInfo(pContext.getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String hashKey = new String(Base64.encode(md.digest(), 0));
                String a = hashKey;
//                Toast.makeText(pContext, "" + hashKey, Toast.LENGTH_LONG).show();
                Log.i("tag", "printHashKey() Hash Key: " + hashKey);
            }
        } catch (NoSuchAlgorithmException e) {
            Log.e("tag", "printHashKey()", e);
        } catch (Exception e) {

            Log.e("tag", "printHashKey()", e);
        }
    }

    public static RequestBody getRequestParam(String content) {
        return RequestBody.create(MediaType.parse("text/plain"), content);
    }


    public static List<String> displayTimeSlots(String startTime, String endTime) {

        List<String> timeSlotList = new ArrayList<>();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);
        try {
            Calendar startCalendar = Calendar.getInstance();
            startCalendar.setTime(sdf.parse(startTime));

            Calendar endCalendar = Calendar.getInstance();
            endCalendar.setTime(sdf.parse(endTime));


            SimpleDateFormat slotTime = new SimpleDateFormat("hh:mma", Locale.ENGLISH);
            SimpleDateFormat slotDate = new SimpleDateFormat(", dd/MM/yy", Locale.ENGLISH);
            while (endCalendar.after(startCalendar)) {
                String slotStartTime = slotTime.format(startCalendar.getTime());
                String slotStartDate = slotDate.format(startCalendar.getTime());

                startCalendar.add(Calendar.MINUTE, 30);
                String slotEndTime = slotTime.format(startCalendar.getTime());
                timeSlotList.add(slotStartTime);
                Log.d("DATE", slotStartTime + " - " + slotEndTime + slotStartDate);
            }

        } catch (ParseException e) {
            // date in wrong format
        }

        return timeSlotList;
    }


    public static String getCityAddress(Activity activity, Location location) {
        String address = null;

        try {
            Geocoder geo = new Geocoder(activity, Locale.getDefault());
            List<Address> addresses = geo.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            if (addresses.isEmpty()) {
            } else {
                if (addresses.size() > 0) {
                    address = addresses.get(0).getSubLocality() + ", " + addresses.get(0).getLocality();
                }
            }
        } catch (Exception e) {
            if (e.getMessage() != null)
                Log.e("TAG", e.getMessage());
        }

        return address;
    }

    public static String getCompleteAddress(Activity activity, Location location) {
        String address = null;

        try {
            Geocoder geo = new Geocoder(activity, Locale.getDefault());
            List<Address> addresses = geo.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            if (addresses.isEmpty()) {
            } else {
                if (addresses.size() > 0) {
                    address = addresses.get(0).getFeatureName() + ", " + addresses.get(0).getSubLocality() + ", " + addresses.get(0).getLocality() + ", " + addresses.get(0).getAdminArea() + ", " + addresses.get(0).getCountryName();
                }
            }
        } catch (Exception e) {
            if (e.getMessage() != null)
                Log.e("TAG", e.getMessage());
        }

        return address;
    }

//    public static Bitmap getMarkerBitmapFromView(Activity activity, String url) {
//
//        View customMarkerView = ((LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.custom_marker, null);
//        CircleImageView markerImageView = customMarkerView.findViewById(R.id.image);
//        ImageUtils.loadImage(url, markerImageView);
//        customMarkerView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
//        customMarkerView.layout(0, 0, customMarkerView.getMeasuredWidth(), customMarkerView.getMeasuredHeight());
//        customMarkerView.buildDrawingCache();
//        Bitmap returnedBitmap = Bitmap.createBitmap(customMarkerView.getMeasuredWidth(), customMarkerView.getMeasuredHeight(),
//                Bitmap.Config.ARGB_8888);
//        Canvas canvas = new Canvas(returnedBitmap);
//        canvas.drawColor(Color.WHITE, PorterDuff.Mode.SRC_IN);
//        Drawable drawable = customMarkerView.getBackground();
//        if (drawable != null)
//            drawable.draw(canvas);
//        customMarkerView.draw(canvas);
//        return returnedBitmap;
//    }

//    public static void startActivityFromBottom(Activity context, Class<?> serviceClass, int status) {
//        Intent intent = new Intent(context, serviceClass);
//
//        if (status == Constants.FINISH_ACTIVITY) {
//            context.startActivity(intent);
//            context.finish();
//        } else if (status == Constants.START_ACTIVITY) {
//            context.startActivity(intent);
//        } else if (status == Constants.CLEAR_BACK_STACK) {
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            context.startActivity(intent);
//        }
//        context.overridePendingTransition(R.anim.bottom_top_in, R.anim.bottom_top_out);
//
//    }

    public static void startActivityForResult(Activity context, Class<?> serviceClass, ActivityResultLauncher<Intent> activityResultLauncher, int status, Bundle bundle) {

        Intent intent = new Intent(context, serviceClass);
        intent.putExtras(bundle);

        if (status == Constants.FINISH_ACTIVITY) {
            activityResultLauncher.launch(intent);
            context.finish();
        } else if (status == Constants.START_ACTIVITY) {
            activityResultLauncher.launch(intent);
        } else if (status == Constants.CLEAR_BACK_STACK) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            activityResultLauncher.launch(intent);
        }

        if (LeftOrientation) {
            context.overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
        } else {
            context.overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
        }
    }

    public static void startActivityForResult(Activity context, Class<?> serviceClass, ActivityResultLauncher<Intent> activityResultLauncher, int status) {

        Intent intent = new Intent(context, serviceClass);


        if (status == Constants.FINISH_ACTIVITY) {
            activityResultLauncher.launch(intent);
            context.finish();
        } else if (status == Constants.START_ACTIVITY) {
            activityResultLauncher.launch(intent);
        } else if (status == Constants.CLEAR_BACK_STACK) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            activityResultLauncher.launch(intent);
        }

        if (LeftOrientation) {
            context.overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
        } else {
            context.overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
        }
    }

    public static void startActivityFromLeft(Activity context, Class<?> serviceClass, int status) {

        Intent intent = new Intent(context, serviceClass);

        if (status == Constants.FINISH_ACTIVITY) {
            context.startActivity(intent);
            context.finish();
        } else if (status == Constants.START_ACTIVITY) {
            context.startActivity(intent);
        } else if (status == Constants.CLEAR_BACK_STACK) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(intent);
        }

//        if (LeftOrientation) {
//        context.overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
//        } else {
        context.overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
//        }
    }

    public static void startActivity(Activity context, Class<?> serviceClass, int status) {

        Intent intent = new Intent(context, serviceClass);

        if (status == Constants.FINISH_ACTIVITY) {
            context.startActivity(intent);
            context.finish();
        } else if (status == Constants.START_ACTIVITY) {
            context.startActivity(intent);
        } else if (status == Constants.CLEAR_BACK_STACK) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(intent);
        }

//        if (LeftOrientation) {
        context.overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
//        } else {
//            context.overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
//        }
    }


    public static void startActivity(Activity context, Class<?> serviceClass, int status, Bundle bundle) {

        Intent intent = new Intent(context, serviceClass);
        intent.putExtras(bundle);

        if (status == Constants.FINISH_ACTIVITY) {
            context.startActivity(intent);
            context.finish();
        } else if (status == Constants.START_ACTIVITY) {
            context.startActivity(intent);
        } else if (status == Constants.CLEAR_BACK_STACK) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(intent);
        }

        if (LeftOrientation) {
            context.overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
        } else {
            context.overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
        }
    }

//    public static void finishActivityFromTop(Activity context) {
//        context.finish();
//        context.overridePendingTransition(R.anim.top_bottom_in, R.anim.top_bottom_out);
//
//    }

    public static Boolean isLastActivity(Activity activity) {
        ActivityManager activityManager = (ActivityManager) activity.getSystemService(activity.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> taskList = activityManager.getRunningTasks(10);
        if (taskList.get(0).numActivities == 1 &&
                taskList.get(0).topActivity.getClassName().equals(activity.getClass().getName())) {
            return true;
        } else {
            return false;
        }
    }

    public static void finishActivityFromRight(Activity context) {
        context.finish();
//        if (LeftOrientation) {
//            context.overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
//        } else {
        context.overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
//        }
    }

    public static void finishActivity(Activity context) {
        context.finish();
        if (LeftOrientation) {
            context.overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
        } else {
            context.overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
        }
    }

    public static Object convertObject(String objString, Class<?> serviceClass) {
        Gson gson = new Gson();
        return gson.fromJson(objString, serviceClass);
    }

//    public static List<StatusTrackModel> convertToList(String objString) {
//        Gson gson = new Gson();
//        Type type = new TypeToken<List<Object>>() {
//        }.getType();
//        return gson.fromJson(objString, type);
//    }

    public static String convertToString(Object obj) {
        return new Gson().toJson(obj);
    }

    public static int convertPixelsToDp(int px, Context context) {
        return px / ((int) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    public static void openFacebookIntent(Activity activity, String urlOrId, boolean forProfile) {
        try {
            activity.getPackageManager().getPackageInfo("com.facebook.katana", 0);
            if (forProfile) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://profile/" + urlOrId));
                activity.startActivity(intent);
            } else {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/" + urlOrId));
                activity.startActivity(intent);
            }

        } catch (Exception e) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlOrId));
            activity.startActivity(intent);
        }
    }

    public static void openBrowser(Activity activity, String url) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        activity.startActivity(browserIntent);
    }

    public static void openInstagramPage(Activity activity, String url) {

        Uri uri = Uri.parse(url);
        Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);
        likeIng.setPackage("com.instagram.android");

        try {
            activity.startActivity(likeIng);
        } catch (ActivityNotFoundException e) {
            activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
        }
    }

    public static void openGmaps(Activity activity, double lat, double lng) {
        String uri = "http://maps.google.com/maps?q=loc:" + lat + "," + lng;
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        activity.startActivity(intent);
    }


    public static void openDialer(Activity activity, String phone) {
        try {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + phone));
            activity.startActivity(intent);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void openEmail(Activity activity, String email, String subject, String message) {
        try {
            Intent emailIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:" + email));
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
            emailIntent.putExtra(Intent.EXTRA_TEXT, message);
            activity.startActivity(emailIntent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //To display the UID
    public static String bin2hex(byte[] data) {
        return String.format("%0" + (data.length * 2) + "X", new BigInteger(1, data));
    }

    // nfc
    public static String dumpTagData(Tag tag) {
        StringBuilder sb = new StringBuilder();
        byte[] id = tag.getId();
        sb.append("ID (hex): ").append(toHex(id)).append('\n');
        sb.append("ID (reversed hex): ").append(toReversedHex(id)).append('\n');
        sb.append("ID (dec): ").append(toDec(id)).append('\n');
        sb.append("ID (reversed dec): ").append(toReversedDec(id)).append('\n');

        String prefix = "android.nfc.tech.";
        sb.append("Technologies: ");
        for (String tech : tag.getTechList()) {
            sb.append(tech.substring(prefix.length()));
            sb.append(", ");
        }

        sb.delete(sb.length() - 2, sb.length());

        for (String tech : tag.getTechList()) {
            if (tech.equals(MifareClassic.class.getName())) {
                sb.append('\n');
                String type = "Unknown";

                try {
                    MifareClassic mifareTag = MifareClassic.get(tag);

                    switch (mifareTag.getType()) {
                        case MifareClassic.TYPE_CLASSIC:
                            type = "Classic";
                            break;
                        case MifareClassic.TYPE_PLUS:
                            type = "Plus";
                            break;
                        case MifareClassic.TYPE_PRO:
                            type = "Pro";
                            break;
                    }
                    sb.append("Mifare Classic type: ");
                    sb.append(type);
                    sb.append('\n');

                    sb.append("Mifare size: ");
                    sb.append(mifareTag.getSize() + " bytes");
                    sb.append('\n');

                    sb.append("Mifare sectors: ");
                    sb.append(mifareTag.getSectorCount());
                    sb.append('\n');

                    sb.append("Mifare blocks: ");
                    sb.append(mifareTag.getBlockCount());
                } catch (Exception e) {
                    sb.append("Mifare classic error: " + e.getMessage());
                }
            }

            if (tech.equals(MifareUltralight.class.getName())) {
                sb.append('\n');
                MifareUltralight mifareUlTag = MifareUltralight.get(tag);
                String type = "Unknown";
                switch (mifareUlTag.getType()) {
                    case MifareUltralight.TYPE_ULTRALIGHT:
                        type = "Ultralight";
                        break;
                    case MifareUltralight.TYPE_ULTRALIGHT_C:
                        type = "Ultralight C";
                        break;
                }
                sb.append("Mifare Ultralight type: ");
                sb.append(type);
            }
        }

        return sb.toString();
    }

    public static String toHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = bytes.length - 1; i >= 0; --i) {
            int b = bytes[i] & 0xff;
            if (b < 0x10)
                sb.append('0');
            sb.append(Integer.toHexString(b));
            if (i > 0) {
                sb.append(" ");
            }
        }
        return sb.toString();
    }

    public static String toReversedHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; ++i) {
            if (i > 0) {
                sb.append(" ");
            }
            int b = bytes[i] & 0xff;
            if (b < 0x10)
                sb.append('0');
            sb.append(Integer.toHexString(b));
        }
        return sb.toString();
    }

    public static long toDec(byte[] bytes) {
        long result = 0;
        long factor = 1;
        for (int i = 0; i < bytes.length; ++i) {
            long value = bytes[i] & 0xffl;
            result += value * factor;
            factor *= 256l;
        }
        return result;
    }

    public static long toReversedDec(byte[] bytes) {
        long result = 0;
        long factor = 1;
        for (int i = bytes.length - 1; i >= 0; --i) {
            long value = bytes[i] & 0xffl;
            result += value * factor;
            factor *= 256l;
        }
        return result;
    }

    public static float dpFromPx(final Context context, final float px) {
        float density = context.getResources().getDisplayMetrics().density;
        return px / density;
    }


    public static float pxFromDp(final Context context, final float dp) {
        float density = context.getResources().getDisplayMetrics().density;
        return dp * density;
    }

    public static void openInformationDialog(Activity activity, String title, String content) {
        Dialog dialog = new Dialog(activity);
//        dialog.setContentView(R.layout.terms_dialog);
//
//        TextView btn_ok = dialog.findViewById(R.id.btn_ok);
//        TextView tv_title = dialog.findViewById(R.id.tv_title);
//        TextView tv_content = dialog.findViewById(R.id.tv_content);
//
//        tv_content.setText(content);
//        tv_title.setText(title);
//
//        btn_ok.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });
//
//        dialog.show();
//
//        if (dialog.getWindow() != null) {
//            dialog.getWindow().setBackgroundDrawableResource(R.drawable.bg_dailog);
//        }

    }

    public static void buildAlertMessageNoGps(Activity activity) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        activity.startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    public static Boolean requestLocationPermission(Activity activity) {
        String permission = Manifest.permission.ACCESS_FINE_LOCATION;
        int grant = ContextCompat.checkSelfPermission(activity, permission);
        if (grant != PackageManager.PERMISSION_GRANTED) {
            String[] permission_list = new String[1];
            permission_list[0] = permission;
            ActivityCompat.requestPermissions(activity, permission_list, 1);
            return false;
        } else
            return true;
    }

    public static void requestSmsPermission(Activity activity) {
        String permission = Manifest.permission.RECEIVE_SMS;
        int grant = ContextCompat.checkSelfPermission(activity, permission);
        if (grant != PackageManager.PERMISSION_GRANTED) {
            String[] permission_list = new String[1];
            permission_list[0] = permission;
            ActivityCompat.requestPermissions(activity, permission_list, 1);
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public static void loadImageForCar(Activity activity, String url, ImageView imageView) {
        Glide.with(activity).load(url).into(imageView);
        Log.e("imageLoaded", url);
    }
    @SuppressLint("UseCompatLoadingForDrawables")
    public static void loadProfileImage(Activity activity, String url, ImageView imageView) {
        Glide.with(activity).load(url).placeholder(R.drawable.profile_image).into(imageView);
        Log.e("imageLoaded", url);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public static void loadImage(Activity activity, String url, ImageView imageView) {
        Glide.with(activity).load(url).into(imageView);
        Log.e("imageLoaded", url);
    }

    public static String calculateEstimatedDeliveryTime(Activity mContext, String deliveryTimee) {
        int deliveryTime = Integer.parseInt(deliveryTimee);

        if (deliveryTime == 60) {
            return "1 hr";
        } else if (deliveryTime % 60 == 0) {
            return deliveryTime / 60 + " hrs";
        } else if (deliveryTime < 60) {
            return deliveryTimee + " mins";
        } else {
            int hours = deliveryTime / 60;
            int minutes = deliveryTime % 60;
            return hours + "." + minutes + " hrs";
        }
    }

    public static void showProgressDialog(Dialog progressDialog) {
        try {
            if (progressDialog.getWindow() != null) {
                progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            }
            progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            progressDialog.setCancelable(false);
            progressDialog.setContentView(R.layout.layout_lottie_loading);
            progressDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void showCarUnavailableToast(final Activity activity) {
        if (activity == null) return;
        Toast.makeText(activity, "We're sorry, but the selected vehicle is currently unavailable at that location. Please try a different location or vehicle.", Toast.LENGTH_LONG).show();
    }
}
