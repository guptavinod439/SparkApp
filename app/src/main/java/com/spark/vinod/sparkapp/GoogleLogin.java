package com.spark.vinod.sparkapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.view.View.GONE;

public class GoogleLogin extends AppCompatActivity implements View.OnClickListener ,GoogleApiClient.OnConnectionFailedListener{


    private RelativeLayout layout;
    //    private ImageView img;
    private CircleImageView img;
    private TextView name  , email;
    private Button signoutbtn;

    private GoogleApiClient googleApiClient;
    private static final int REQ_CODE =9001;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_login);


        layout=(RelativeLayout) findViewById(R.id.layout1);
        name=(TextView) findViewById(R.id.user_profile_name);
        email=(TextView) findViewById(R.id.user_gmail_address);
        img =(CircleImageView) findViewById(R.id.user_profile_photo);
        signoutbtn=(Button)findViewById(R.id.signoutbtn) ;
        signoutbtn.setOnClickListener(this);
        layout.setVisibility(GONE);
        GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleApiClient =new GoogleApiClient.Builder(this).enableAutoManage(this, this).addApi(Auth.GOOGLE_SIGN_IN_API, signInOptions).build();
        signIn();

    }

    @Override
    public void onClick(View view) {
        if(view == signoutbtn ){
            signout();


        }
    }

    private void signIn(){
        Intent intent =Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(intent, REQ_CODE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==REQ_CODE){
            GoogleSignInResult result=Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleResult(result);
        }
    }
    private void signout(){
        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>()

        {


            @Override
            public void onResult(@NonNull Status status) {

                finish();
                updateUI(false);

            }
        });
    }
    private void updateUI(boolean isLogin){
        if (isLogin){
            layout.setVisibility(View.VISIBLE);
        }

    }
    private void handleResult(GoogleSignInResult result){
        if (result.isSuccess()){
            GoogleSignInAccount account=result.getSignInAccount();
            String usrname=account.getGivenName();
            String usremail=account.getEmail();
            String usrimgurl=account.getPhotoUrl().toString();

            name.setText(usrname);
            email.setText(usremail);
            Glide.with(getApplicationContext()).load(account.getPhotoUrl()).into(img);
            updateUI(true);
        }
        else{
            updateUI(false);
        }
    }
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
