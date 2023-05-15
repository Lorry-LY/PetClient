package com.lorry.petclient.activity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.lorry.petclient.R;

public class PostVideoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_video);

        VideoView mVv = (VideoView) findViewById(R.id.videoView);

        mVv.setMediaController(new MediaController(this));


        //设置视频源播放res/raw中的文件,文件名小写字母,格式: 3gp,mp4等,flv的不一定支持;
        Uri rawUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.video);
        mVv.setVideoURI(rawUri);

        mVv.start();
        mVv.requestFocus();

    }
}