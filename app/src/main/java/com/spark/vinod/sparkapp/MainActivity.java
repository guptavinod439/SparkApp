package com.spark.vinod.sparkapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button google;
    TwitterLoginButton twitter;
    private Button fb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Twitter.initialize(this);
        setContentView(R.layout.activity_main);
        twitter = (TwitterLoginButton) findViewById(R.id.sign_in_btn_Twitter);
        twitter.setOnClickListener(this);
        google = (Button) findViewById(R.id.sign_in_btn_google);
        google.setOnClickListener(this);
        fb = (Button) findViewById(R.id.sign_in_btn_fb);
        fb.setOnClickListener(this);

        twitter.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                /*
                  This provides TwitterSession as a result
                  This will execute when the authentication is successful
                 */
                TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();
                TwitterAuthToken authToken = session.getAuthToken();
                String token = authToken.token;
                String secret = authToken.secret;

                //Calling login method and passing twitter session
                login(session);



            }

            @Override
            public void failure(TwitterException exception) {
                //Displaying Toast message
                Toast.makeText(MainActivity.this, "Authentication failed!", Toast.LENGTH_LONG).show();
            }
        });

    }
    public void login(TwitterSession session)
    {
        String username = session.getUserName();

        Intent intent = new Intent(MainActivity.this, Home.class);
        intent.putExtra("username", username);
        startActivity(intent);
    }

    /**
     * @param requestCode - we'll set it to REQUEST_CAMERA
     * @param resultCode - this will store the result code
     * @param data - data will store an intent
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result to the login button.
        twitter.onActivityResult(requestCode, resultCode, data);
    }
    @Override
    public void onClick(View view) {
        if (view == google) {

            Intent i = new Intent(this, GoogleLogin.class);
            startActivity(i);
            // finish();
        } else
        {
            if (view==fb)
            {
                Intent i=new Intent(this,Fb.class);
                startActivity(i);
            }
        }

    }
}
