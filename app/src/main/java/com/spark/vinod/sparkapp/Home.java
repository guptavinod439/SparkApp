package com.spark.vinod.sparkapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author Priyanka
 */

public class Home extends Activity {
    TextView n;
    CircleImageView pic;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        /* fetching the username from LoginActivity */
        String un = getIntent().getStringExtra("username");
        TextView n = findViewById(R.id.TV_username);
        pic=(CircleImageView)findViewById(R.id.twitterpic);
String s="https://twitter.com/"+un+"/profile_image?size=original";
        Glide.with(getApplicationContext()).load(s).into(pic);

        n.setText(un);
        //String ab=getIntent().getAction();
    }
}