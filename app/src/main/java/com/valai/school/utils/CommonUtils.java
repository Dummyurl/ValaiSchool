/*
 * Copyright (C) 2017 MINDORKS NEXTGEN PRIVATE LIMITED
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://mindorks.com/license/apache-v2
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */

package com.valai.school.utils;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.text.format.Formatter;
import android.util.Log;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.valai.school.R;
import com.valai.school.interfaces.FragmentListner;
import com.valai.school.interfaces.FragmentListnerAdmin;
import com.valai.school.interfaces.FragmentListnerStaff;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import retrofit2.Response;

import static android.content.Context.WIFI_SERVICE;
import static com.valai.school.utils.AppConstants.MULTIPART_FORM_DATA;

@SuppressWarnings({"deprecation", "ResultOfMethodCallIgnored"})
public final class CommonUtils {

    private static final String TAG = CommonUtils.class.getSimpleName();


    private CommonUtils() {
        // This utility class is not publicly instantiable
    }

    public static ProgressDialog showLoadingDialog(Context context) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.show();
        if (progressDialog.getWindow() != null) {
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        return progressDialog;
    }

    @SuppressLint("all")
    public static String getDeviceId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    public static boolean isEmailValid(String email) {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN =
                "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static String loadJSONFromAsset(Context context, String jsonFileName)
            throws IOException {

        AssetManager manager = context.getAssets();
        InputStream is = manager.open(jsonFileName);

        int size = is.available();
        byte[] buffer = new byte[size];
        is.read(buffer);
        is.close();

        return new String(buffer, "UTF-8");
    }

    public static AnimationItem[] getAnimationItems() {
        return new AnimationItem[]{
                new AnimationItem("Fall down", R.anim.layout_animation_fall_down),
                new AnimationItem("Slide from right", R.anim.layout_animation_from_right),
                new AnimationItem("Slide from bottom", R.anim.layout_animation_from_bottom)
        };
    }

    public static void runLayoutAnimation(final RecyclerView recyclerView, final AnimationItem item) {
        final Context context = recyclerView.getContext();

        final LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(context, item.getResourceId());

        recyclerView.setLayoutAnimation(controller);
        if (recyclerView.getAdapter() != null) {
            recyclerView.getAdapter().notifyDataSetChanged();
        }
        recyclerView.scheduleLayoutAnimation();
    }

    @NonNull
    public static String getStringResponseBody(Response<ResponseBody> response) {
        StringBuilder sbAppend = new StringBuilder();
        BufferedSource source = response.body().source();
        try {
            source.request(Long.MAX_VALUE); // Buffer the entire body.
        } catch (IOException e) {
            e.printStackTrace();
        }
        Buffer buffer = source.buffer();
        String responseString = buffer.clone().readString(Charset.forName("UTF-8"));
        return sbAppend.append("\"data\":").append(responseString).append("}").toString();
    }

    @SuppressLint("SimpleDateFormat")
    public static String convertDateStringFormat(String date) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat spf = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
        Date newDate = null;
        try {
            newDate = spf.parse(date);
            spf = new SimpleDateFormat("dd-MMM-yyyy");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return spf.format(newDate);
    }

    @SuppressLint("SimpleDateFormat")
    public static String convertDateStringFormat2(String date) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat spf = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
        Date newDate = null;
        try {
            newDate = spf.parse(date);
            spf = new SimpleDateFormat("dd/MM/yyyy");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return spf.format(newDate);
    }

    @SuppressLint("SimpleDateFormat")
    public static String convertDateStringFormat3(String date) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat spf = new SimpleDateFormat("dd-MM-yyyy");
        Date newDate = null;
        try {
            newDate = spf.parse(date);
            spf = new SimpleDateFormat("dd MMM");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return spf.format(newDate);
    }

    @SuppressLint("SimpleDateFormat")
    public static String convertDateStringFormat4(String date) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat spf = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
        Date newDate = null;
        try {
            newDate = spf.parse(date);
            spf = new SimpleDateFormat("dd EEE");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return splitDate(spf.format(newDate));
    }

    @SuppressLint("SimpleDateFormat")
    public static String convertDateStringFormat5(String date) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat spf = new SimpleDateFormat("dd-MM-yyyy");
        Date newDate = null;
        try {
            newDate = spf.parse(date);
            spf = new SimpleDateFormat("yyyy-MM-dd");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return spf.format(newDate);
    }

    public static String splitTime(String dateTime) {
        String[] parts = dateTime.split("T");
        String part1 = parts[0];
        String part2 = parts[1];

        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdfs = new SimpleDateFormat("hh:mm a");
        Date dt = null;
        try {
            dt = sdf.parse(part2);
            System.out.println("Time Display: " + sdfs.format(dt)); // <-- I got result here
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return sdfs.format(dt);
    }

    private static String splitDate(String date) {
        String[] parts = date.split(" ");
        String part1 = parts[0];
        String part2 = parts[1];
        return part1 + "\n" + part2;
    }

    @NonNull
    public static Spanned fromHtml(@NonNull String source) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(source, Html.FROM_HTML_MODE_LEGACY);
        } else {
            return Html.fromHtml(source);
        }
    }

    public static boolean writeResponseBodyToDisk(Context ctx, ResponseBody body, String directoryName, String filename, FragmentListner fragmentListner) {

        File myDir;
        if (!directoryName.equals("")) {
            myDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
                    + File.separator
                    + ctx.getString(R.string.app_name) + File.separator + directoryName + File.separator);
        } else {
            myDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
                    + File.separator
                    + ctx.getString(R.string.app_name) + File.separator);
        }

            /* if specified not exist create new */
        if (!myDir.exists()) {
            myDir.mkdirs();
        }
        File outputFile = new File(myDir, filename);
        fragmentListner.setOutputFilePath(outputFile);

        try {
            FileOutputStream fileOutput = new FileOutputStream(outputFile);
            InputStream inputStream = body.byteStream();
            long totalSize = body.contentLength();
            byte[] buffer = new byte[1024 * 1024];
            int bufferLength;
            int downloadedSize = 0;
            while ((bufferLength = inputStream.read(buffer)) > 0) {
                fileOutput.write(buffer, 0, bufferLength);
                downloadedSize += bufferLength;
                fragmentListner.setFileSizeDownloaded(((long) downloadedSize / totalSize) * 100, totalSize);
            }

            // close the output stream when complete //
            fileOutput.flush();
            if (fileOutput != null) {
                fileOutput.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
            return true;
        } catch (final MalformedURLException e) {
            e.printStackTrace();
            Log.e("MalformedURLException", "MalformedURLException>>" + e.getMessage());
            return false;
        } catch (ProtocolException e) {
            e.printStackTrace();
            Log.e("ProtocolException", "ProtocolException>>" + e.getMessage());
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("IOException", "IOException>>" + e.getMessage());
            return false;
        }
    }

    public static boolean writeAdminResponseBodyToDisk(Context ctx, ResponseBody body, String directoryName, String filename, FragmentListnerAdmin fragmentListner) {
        File myDir;
        if (!directoryName.equals("")) {
            myDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
                    + File.separator
                    + ctx.getString(R.string.app_name) + File.separator + directoryName + File.separator);
        } else {
            myDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
                    + File.separator
                    + ctx.getString(R.string.app_name) + File.separator);
        }

            /* if specified not exist create new */
        if (!myDir.exists()) {
            myDir.mkdirs();
        }

        File outputFile = new File(myDir, filename);
        fragmentListner.setOutputFilePath(outputFile);

        try {

            FileOutputStream fileOutput = new FileOutputStream(outputFile);
            InputStream inputStream = body.byteStream();
            long totalSize = body.contentLength();

            byte[] buffer = new byte[1024 * 1024];
            int bufferLength;
            int downloadedSize = 0;
            while ((bufferLength = inputStream.read(buffer)) > 0) {
                fileOutput.write(buffer, 0, bufferLength);
                downloadedSize += bufferLength;
                fragmentListner.setFileSizeDownloaded(((long) downloadedSize / totalSize) * 100, totalSize);
            }

            // close the output stream when complete //
            fileOutput.flush();
            if (fileOutput != null) {
                fileOutput.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
            return true;
        } catch (final MalformedURLException e) {
            e.printStackTrace();
            Log.e("MalformedURLException", "MalformedURLException>>" + e.getMessage());
            return false;
        } catch (ProtocolException e) {
            e.printStackTrace();
            Log.e("ProtocolException", "ProtocolException>>" + e.getMessage());
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("IOException", "IOException>>" + e.getMessage());
            return false;
        }
    }

    public static boolean writeStaffResponseBodyToDisk(Context ctx, ResponseBody body, String directoryName, String filename, FragmentListnerStaff fragmentListner) {
        File myDir;
        if (!directoryName.equals("")) {
            myDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
                    + File.separator
                    + ctx.getString(R.string.app_name) + File.separator + directoryName + File.separator);
        } else {
            myDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
                    + File.separator
                    + ctx.getString(R.string.app_name) + File.separator);
        }

            /* if specified not exist create new */
        if (!myDir.exists()) {
            myDir.mkdirs();
        }

        File outputFile = new File(myDir, filename);
        fragmentListner.setOutputFilePath(outputFile);

        try {

            FileOutputStream fileOutput = new FileOutputStream(outputFile);
            InputStream inputStream = body.byteStream();
            long totalSize = body.contentLength();

            byte[] buffer = new byte[1024 * 1024];
            int bufferLength;
            int downloadedSize = 0;
            while ((bufferLength = inputStream.read(buffer)) > 0) {
                fileOutput.write(buffer, 0, bufferLength);
                downloadedSize += bufferLength;
                fragmentListner.setFileSizeDownloaded(((long) downloadedSize / totalSize) * 100, totalSize);
            }

            // close the output stream when complete //
            fileOutput.flush();
            if (fileOutput != null) {
                fileOutput.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
            return true;
        } catch (final MalformedURLException e) {
            e.printStackTrace();
            Log.e("MalformedURLException", "MalformedURLException>>" + e.getMessage());
            return false;
        } catch (ProtocolException e) {
            e.printStackTrace();
            Log.e("ProtocolException", "ProtocolException>>" + e.getMessage());
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("IOException", "IOException>>" + e.getMessage());
            return false;
        }
    }

    public static void openFile(Context ctx, File url) {
        // Refer This Link For Open Files
        //https://medium.com/@ali.muzaffar/what-is-android-os-fileuriexposedexception-and-what-you-can-do-about-it-70b9eb17c6d0
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            Uri uri = FileProvider.getUriForFile(ctx,
                    ctx.getApplicationContext()
                            .getPackageName() + ".provider", url);

            if (url.toString().contains(".doc") || url.toString().contains(".docx")) {
                // Word document
                intent.setDataAndType(uri, "application/msword");

            } else if (url.toString().contains(".pdf")) {
                // PDF file
                intent.setDataAndType(uri, "application/pdf");

            } else if (url.toString().contains(".ppt") || url.toString().contains(".pptx")) {
                // Powerpoint file
                intent.setDataAndType(uri, "application/vnd.ms-powerpoint");

            } else if (url.toString().contains(".xls") || url.toString().contains(".xlsx")) {
                // Excel file
                intent.setDataAndType(uri, "application/vnd.ms-excel");

            } else if (url.toString().contains(".zip") || url.toString().contains(".rar")) {
                // WAV audio file
                intent.setDataAndType(uri, "application/x-wav");

            } else if (url.toString().contains(".rtf")) {
                // RTF file
                intent.setDataAndType(uri, "application/rtf");

            } else if (url.toString().contains(".wav") || url.toString().contains(".mp3")) {
                // WAV audio file
                intent.setDataAndType(uri, "audio/x-wav");

            } else if (url.toString().contains(".gif")) {
                // GIF file
                intent.setDataAndType(uri, "image/gif");

            } else if (url.toString().contains(".jpg")) {
                // JPG file
                intent.setDataAndType(uri, "image/jpg");

            } else if (url.toString().contains(".jpeg")) {
                // JPEG file
                intent.setDataAndType(uri, "image/jpeg");

            } else if (url.toString().contains(".png")) {
                // PNG file
                intent.setDataAndType(uri, "image/png");

            } else if (url.toString().contains(".txt")) {
                // Text file
                intent.setDataAndType(uri, "text/plain");

            } else if (url.toString().contains(".3gp") || url.toString().contains(".mpg") ||
                    url.toString().contains(".mpeg") || url.toString().contains(".mpe") || url.toString().contains(".mp4") || url.toString().contains(".avi")) {
                // Video files
                intent.setDataAndType(uri, "video/*");

            } else {
                intent.setDataAndType(uri, "*/*");
            }

            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            ctx.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static String getCurrentDate() {
        Date c = Calendar.getInstance().getTime();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(c);
    }

    public static String getCurrentDateForAttendance() {
        Date c = Calendar.getInstance().getTime();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        return df.format(c);
    }

    @SuppressLint("SimpleDateFormat")
    public static String getTimeStamp() {
        return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
    }

    // Prepare FilePart
    @NonNull
    public static MultipartBody.Part prepareFilePart(String name, File file) {
        RequestBody requestFile = RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), file);
        return MultipartBody.Part.createFormData(name, file.getName(), requestFile);
    }

    // Prepare StringPart
    @NonNull
    public static MultipartBody.Part prepareStringPart(String name, String imagePath) {
        return MultipartBody.Part.createFormData(name, imagePath);
    }

    public static File copyOrMoveFile(Context ctx, File sourceLocation, String directoryName, String fileName) {
        File myDir;
        if (!directoryName.equals("")) {
            myDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
                    + File.separator
                    + ctx.getString(R.string.app_name) + File.separator + directoryName + File.separator);
        } else {
            myDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
                    + File.separator
                    + ctx.getString(R.string.app_name) + File.separator);
        }

              /* if specified not exist create new */
        if (!myDir.exists()) {
            myDir.mkdirs();
        }

        File targetLocation = new File(myDir, fileName);
        // just to take note of the location sources
        Log.e(TAG, "sourceLocation: " + sourceLocation);
        Log.e(TAG, "targetLocation: " + targetLocation);

        try {
            // 1 = move the file, 2 = copy the file
            int actionChoice = 2;

            // moving the file to another directory
            if (actionChoice == 1) {

                if (sourceLocation.renameTo(targetLocation)) {
                    Log.e(TAG, "Move file successful.");
                } else {
                    Log.e(TAG, "Move file failed.");
                }
            }

            // we will copy the file
            else {

                // make sure the target file exists

                if (sourceLocation.exists()) {

                    InputStream in = new FileInputStream(sourceLocation);
                    OutputStream out = new FileOutputStream(targetLocation);

                    // Copy the bits from inPutStream to outPutStream
                    byte[] buf = new byte[1024];
                    int len;

                    while ((len = in.read(buf)) > 0) {
                        out.write(buf, 0, len);
                    }

                    in.close();
                    out.close();

                    Log.e(TAG, "Copy file successful.");

                } else {
                    Log.e(TAG, "Copy file failed. Source file missing.");
                }
            }

        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return targetLocation;
    }

    public static String getIpAddress(Context ctx) {
        WifiManager wm = (WifiManager) ctx.getApplicationContext().getSystemService(WIFI_SERVICE);
        return Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());
    }
}