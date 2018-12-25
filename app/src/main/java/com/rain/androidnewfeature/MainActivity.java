package com.rain.androidnewfeature;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.rain.androidnewfeature.ui.ButtonClickEffectActivity;
import com.rain.androidnewfeature.util.ToastUtil;

import java.io.File;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String downLoadUrl = Environment.getExternalStorageDirectory() + "/app-debug.apk";
    private static final String TAG = "MainActivity";
    private static final int GET_UNKNOWN_APP_SOURCES = 217;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_install).setOnClickListener(this);
        findViewById(R.id.btn_click_effect).setOnClickListener(this);
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void checkSdCardPermission() {
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            installApk();
        } else {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100 & grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            installApk();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_install:
                checkInstallPermissionAndInstall();
                break;

            case R.id.btn_click_effect:
                startActivity(new Intent(this,ButtonClickEffectActivity.class));
                break;

            default:

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case GET_UNKNOWN_APP_SOURCES:
                // 用户授予了未知来源应用的权限
                if (resultCode == RESULT_OK) {
                    checkSdCardPermission();
                }
                break;
            default:
                break;
        }
    }

    public void checkInstallPermissionAndInstall() {
        // 如果是8.0系统
        if (Build.VERSION.SDK_INT >= 26) {
            boolean b = getPackageManager().canRequestPackageInstalls();
            // 如果已经打开了安装未知来源的开关
            if (b) {
                checkSdCardPermission();
            } else {
                // 请求打开安装未知应用来源的界面,非运行时权限
                Uri packageURI = Uri.parse("package:" + getPackageName());
                Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, packageURI);
                startActivityForResult(intent, GET_UNKNOWN_APP_SOURCES);
            }
        } else {
            checkSdCardPermission();
        }
    }

    private void installApk() {
        Uri uri = null;
        File file = new File(downLoadUrl);
        if (!file.exists()) {
            ToastUtil.showToast("文件不存在！");
            return;
        }
        // 安卓7.0及以上
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            // Android7.0之后获取uri要用contentProvider
            uri = FileProvider.getUriForFile(this, getApplicationInfo().packageName + ".FileProvider", file);
        } else {
            uri = Uri.fromFile(file);
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(uri, "application/vnd.android.package-archive");
        startActivity(intent);
    }
}
