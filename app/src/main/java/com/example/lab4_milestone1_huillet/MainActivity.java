package com.example.lab4_milestone1_huillet;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Main Activity";

    Button startButton;
    TextView downloadProgressText;

    private volatile boolean stopThread = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startButton = findViewById(R.id.start_button);
        downloadProgressText = findViewById(R.id.download_progress);
    }

    public void mockFileDownloader()
    {
        runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                startButton.setText("Downloading...");
            }
        });

        for (int downloadProgress = 0; downloadProgress <= 100; downloadProgress += 10)
        {
            if (stopThread)
            {
                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        startButton.setText("Start");
                        downloadProgressText.setText("");
                    }
                });
                return;
            }

            int finalDownloadProgress = downloadProgress;
            runOnUiThread(new Runnable()
            {
                @Override
                public void run()
                {
                    downloadProgressText.setText("Download Progress: " + finalDownloadProgress + "%");
                }
            });

            try
            {
                Thread.sleep(1000);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }

        runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                startButton.setText("Start");
                downloadProgressText.setText("");
            }
        });
    }

    public void startDownload(View view)
    {
        stopThread = false;
        ExampleRunnable runnable = new ExampleRunnable();
        new Thread(runnable).start();
    }

    public void stopDownload(View view)
    {
        stopThread = true;
    }

    public class ExampleRunnable implements Runnable {
        @Override
        public void run() {
            mockFileDownloader();
        }
    }
}