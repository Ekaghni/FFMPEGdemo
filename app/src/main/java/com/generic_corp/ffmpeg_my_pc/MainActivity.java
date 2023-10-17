package com.generic_corp.ffmpeg_my_pc;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.VideoView;

import com.github.hiteshsondhi88.libffmpeg.ExecuteBinaryResponseHandler;
import com.github.hiteshsondhi88.libffmpeg.FFmpeg;
import com.github.hiteshsondhi88.libffmpeg.LoadBinaryResponseHandler;
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegCommandAlreadyRunningException;
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegNotSupportedException;

public class MainActivity extends AppCompatActivity {
    VideoView view;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        view = (VideoView)findViewById(R.id.videoView);
        btn = findViewById(R.id.saveBtn);
        String path = "android.resource://" + getPackageName() + "/" + R.raw.video;
        view.setVideoURI(Uri.parse(path));
        view.start();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startRec();
            }
        });



    }

    public void startRec(){
        FFmpeg ffmpeg = FFmpeg.getInstance(this);
        try {
            ffmpeg.loadBinary(new LoadBinaryResponseHandler() {
                @Override
                public void onFailure() {
                    // Handle failure
                }

                @Override
                public void onSuccess() {
                    // FFmpeg is ready to use
                }
            });
        } catch (FFmpegNotSupportedException e) {
            // Handle FFmpeg not supported
        }


        String videoFileName = "video.mp4"; // Replace with your video file name
        String outputFileName = "output_video.mp4"; // Replace with your desired output file name




        String[] command = {"-i", "file:///android_asset/video.mp4", "-vf", "drawtext=text='Your Text':x=10:y=10:fontsize=24:fontcolor=white", "-c:v", "copy", "-c:a", "copy", "file:///android_asset/video_2.mp4"};


        try {
            ffmpeg.execute(command, new ExecuteBinaryResponseHandler() {
                @Override
                public void onSuccess(String message) {
                    Toast.makeText(MainActivity.this, "Saved Successfully!", Toast.LENGTH_SHORT).show();
                    // Encoding completed successfully
                }

                @Override
                public void onFailure(String message) {
                    // Encoding failed
                    Toast.makeText(MainActivity.this, "Failed!!", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onProgress(String message) {
                    // Encoding progress
                }

                @Override
                public void onStart() {
                    // Encoding started
                }

                @Override
                public void onFinish() {
                    // Encoding finished
                }
            });
        } catch (FFmpegCommandAlreadyRunningException e) {
            // Handle FFmpeg already running
        }




    }
}