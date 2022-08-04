package com.appointment.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.multidex.BuildConfig;

import com.google.android.material.snackbar.Snackbar;
import com.appointment.R;
import com.appointment.extra.SystemUtility;
import com.appointment.extra.permission.PermissionCaller;

import java.io.File;
import java.util.List;

import dmax.dialog.SpotsDialog;

public class BaseActivity extends AppCompatActivity {

    SpotsDialog mProgressDialog;
    public static final int REQUEST_CAPTURE = 1001;
    public static final int REQUEST_GALLERY = 1002;
    public Uri captureMediaFile = null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    public void toast(final String message) {
        Toast.makeText(BaseActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    public void loadProgressBar(Context context, String message, boolean cancellable) {

        try {
            if (mProgressDialog == null)
                mProgressDialog = new SpotsDialog(this, message, R.style.SpotCustomDialog);
            if ((context != null && !((Activity) context).isFinishing()) && !mProgressDialog.isShowing())
                mProgressDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dismissProgressBar(Context context) {
        try {
            if (mProgressDialog != null) {
                if ((context != null && !((Activity) context).isFinishing()) && mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }
                mProgressDialog = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public AlertDialog.Builder getAlertDialogBuilder(String title, String message, boolean cancellable) {
        return new AlertDialog.Builder(this, R.style.AppTheme_AlertDialog)
                .setTitle(title)
                .setMessage(message)
                .setCancelable(cancellable);
    }

    public void enableLoadingBar(Context context, boolean enable, String s) {
        if (enable) {
            loadProgressBar(context, s, false);
        } else {
            dismissProgressBar(context);
        }
    }

    public void onError(String reason) {
        onError(reason, false);
    }

    public void onError(String reason, final boolean finishOnOk) {
        try {
            showPopupDialog(getString(R.string.default_error), getString(R.string.oops), false);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void showPopupDialog(String reason, String titletext, final Boolean finishOnOk) {
        /*Dialog pauseDialog = new Dialog(BaseActivity.this);
        pauseDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        pauseDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        pauseDialog.setContentView(R.layout.dialog_popup);

        WindowManager manager = (WindowManager) getSystemService(Activity.WINDOW_SERVICE);
        int width;
        width = manager.getDefaultDisplay().getWidth();



        pauseDialog.getWindow().setBackgroundDrawable(null);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(pauseDialog.getWindow().getAttributes());
        lp.width = width - 70;
        lp.gravity = Gravity.CENTER;
        pauseDialog.getWindow().setAttributes(lp);

        if (pauseDialog.isShowing()) {
            pauseDialog.dismiss();
        }

        final LinearLayout mainContent = pauseDialog.findViewById(R.id.main_content);
        // AnimationSet mModalInAnim = (AnimationSet) OptAnimationLoader.loadAnimation(BaseActivity.this, R.anim.modal_in);
        // mainContent.startAnimation(mModalInAnim);

        TextView title = pauseDialog.findViewById(R.id.popup_title);
        title.setText(titletext);

        TextView message = pauseDialog.findViewById(R.id.popup_message);
        message.setText(reason);

        AppCardView cvButton = pauseDialog.findViewById(R.id.cv_button);

        cvButton.setCornerRadius(100);
        cvButton.BackMethod(getResources().getColor(R.color.light_blue),getResources().getColor(R.color.dark_blue));


        TextView positiveButton = pauseDialog.findViewById(R.id.positive_button);
        positiveButton.setText(getString(R.string.ok));
        ImageView cancel = pauseDialog.findViewById(R.id.cancel);


        final Dialog finalPauseDialog = pauseDialog;
        cvButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (finishOnOk) {
                    finish();
                } else {
                    if (finalPauseDialog.isShowing())
                        finalPauseDialog.dismiss();
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (finalPauseDialog.isShowing())
                    finalPauseDialog.dismiss();
            }
        });

        try {
            if (!pauseDialog.isShowing())
                pauseDialog.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
*/

    }

    public void snackBar(View view, String message) {
        Snackbar snackbar = Snackbar
                .make(view, message, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    public void pickImageFromGallery() {
        if (!PermissionCaller.getInstance(this).isListOfPermission(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA}, REQUEST_GALLERY))
            return;
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, getString(R.string.select_image)), REQUEST_GALLERY);
    }

    public void pickImageFromCamera() {
        if (!PermissionCaller.getInstance(this).isListOfPermission(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA}, REQUEST_CAPTURE))
            return;

        Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, captureMediaFile);
        /*captureMediaFile = getOutputMediaFileUri(1);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, captureMediaFile);*/

        try {
            File tempFile = File.createTempFile("image", ".png", new File(SystemUtility.getTempMediaDirectory(this)));
            captureMediaFile = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".provider", tempFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, captureMediaFile);

            List<ResolveInfo> resInfoList = getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
            for (ResolveInfo resolveInfo : resInfoList) {
                String packageName = resolveInfo.activityInfo.packageName;
                grantUriPermission(packageName, captureMediaFile, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        startActivityForResult(intent, REQUEST_CAPTURE);
    }




}