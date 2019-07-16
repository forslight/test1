package com.fishstar.mytodolist;

import android.Manifest;
import android.content.pm.PackageManager;
import android.icu.text.IDNA;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class DebugActivity extends AppCompatActivity {

    private static int REQUEST_CODE_STORAGE_PERMISSION = 1001;
    private Button pathPrintBtn;
    private TextView info;
    private TextView fileInfo;
    private Button RequestPermissionBtn;
    private Button WBtn;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_debug);
        setTitle("Debug");

        pathPrintBtn = findViewById(R.id.PathPrintBtn);
        info = findViewById(R.id.pathInfo);
        RequestPermissionBtn = findViewById(R.id.getPerssion);
        WBtn = findViewById(R.id.writeFile);
        fileInfo = findViewById(R.id.fileInfo);

        pathPrintBtn.setOnClickListener((v) -> {
            StringBuilder Infos = new StringBuilder();
            Infos.append("===== Internal Private =====\n").append(getInternalPath())
                    .append("===== External Private =====\n").append(getExternalPriavtePath())
                    .append("===== External Public =====\n").append(getExternalPublicPath());
            info.setText(Infos);
        });

        RequestPermissionBtn.setOnClickListener((v) -> {
            int state = ActivityCompat.checkSelfPermission(DebugActivity.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (state == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(DebugActivity.this,
                        "permission is already granted",
                        Toast.LENGTH_SHORT)
                        .show();
                return;
            }
            ActivityCompat.requestPermissions(DebugActivity.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_STORAGE_PERMISSION);

        });
        WBtn.setOnClickListener((v) -> {
            String fileName = "info";
            String content = "姓名：xxx；学号：3170101xxx";

            try {
                FileOutputStream fos = this.openFileOutput(fileName, MODE_PRIVATE);//获得FileOutputStream
                //将要写入的字符串转换为byte数组
                byte[] bytes = content.getBytes();
                fos.write(bytes);//将byte数组写入文件
                fos.close();//关闭文件输出流
            } catch (Exception e) {
                e.printStackTrace();
            }

            String result="";

            try{
                FileInputStream fis = this.openFileInput(fileName);

                //获取文件长度
                int lenght = fis.available();

                byte[] buffer = new byte[lenght];

                fis.read(buffer);

                //将byte数组转换成指定格式的字符串
                result = new String(buffer, "UTF-8");

            } catch (Exception e) {
                e.printStackTrace();
            }

            fileInfo.setText(result);


        });
    }

    private String getInternalPath() {
        Map<String, File> dirMap = new LinkedHashMap<>();
        dirMap.put("cachDir", getCacheDir());
        dirMap.put("filesDir", getFilesDir());
        dirMap.put("customDir", getDir("custom", MODE_PRIVATE));
        return getCanonicalPath(dirMap);
    }

    private String getExternalPriavtePath() {
        Map<String, File> dirMap = new LinkedHashMap<>();
        dirMap.put("cacheDir", getExternalCacheDir());
        dirMap.put("filesDir", getExternalFilesDir(null));
        dirMap.put("pictureDir", getExternalFilesDir(Environment.DIRECTORY_PICTURES));
        return getCanonicalPath(dirMap);
    }

    private String getExternalPublicPath() {
        Map<String, File> dirMap = new LinkedHashMap<>();
        dirMap.put("rootDir", Environment.getExternalStorageDirectory());
        dirMap.put("picturesDir",
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES));
        return getCanonicalPath(dirMap);
    }

    private static String getCanonicalPath(Map<String, File> dirMap) {
        StringBuilder sb = new StringBuilder();
        try {
            for (String name : dirMap.keySet()) {
                sb.append(name)
                        .append(": ")
                        .append(dirMap.get(name).getCanonicalPath())
                        .append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
