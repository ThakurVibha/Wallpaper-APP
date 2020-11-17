package com.example.wallpaperapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;

import java.io.IOException;

import br.com.bloder.magic.view.MagicButton;

public class FullScreenWallpaper extends AppCompatActivity {
    String originalUrl = "";
    PhotoView photoView;
    MagicButton magicButton, downloadButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_wallpaper);
        getSupportActionBar().hide();
        Intent intent = getIntent();
        originalUrl = intent.getStringExtra("originalUrl");
        photoView = findViewById(R.id.photoView);


        Glide.with(this).load(originalUrl).into(photoView);

        //to download wallpaper
        downloadButton = findViewById(R.id.btnDownloadSetWallpaper);
        downloadButton.setMagicButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                Uri uri = Uri.parse(originalUrl);
                DownloadManager.Request request = new DownloadManager.Request(uri);
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                downloadManager.enqueue(request);
                Toast.makeText(FullScreenWallpaper.this, "Download is started..", Toast.LENGTH_SHORT).show();
            }
        });

        //to set the wallpaper
        magicButton = findViewById(R.id.btnSetWallpaper);
        magicButton.setMagicButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WallpaperManager wallpaperManager = WallpaperManager.getInstance(FullScreenWallpaper.this);
                Bitmap bitmap = ((BitmapDrawable) photoView.getDrawable()).getBitmap();

                try {
                    wallpaperManager.setBitmap(bitmap);
                    Toast.makeText(FullScreenWallpaper.this, "Wallpaper is Set", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        });

    }


}


