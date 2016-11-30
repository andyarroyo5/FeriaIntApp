package edu.udem.feriaint;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.LoginEvent;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInApi;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.plus.Plus;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.twitter.sdk.android.core.models.User;
import com.twitter.sdk.android.core.models.UserValue;
import com.twitter.sdk.android.core.services.AccountService;

import edu.udem.feriaint.Activities.ActivityInicial;
import edu.udem.feriaint.Activities.MainActivity;

public class TwitterInicioSesion extends AppCompatActivity implements  View.OnClickListener,GoogleApiClient.OnConnectionFailedListener {

    private static final int RC_SIGN_IN =55 ;
    TwitterLoginButton loginButtonTwitter;
    SignInButton loginButtonGoogle;


    Button cerrarSesion;

    GoogleApiClient mGoogleApiClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twitter);
        mGoogleApiClient=buildGoogleAPIClient();

        final Bundle intent=getIntent().getExtras();

        if (intent.getBoolean("cerrarS"))
        {
            cerrarS();
        }

        loginButtonGoogle=(SignInButton) findViewById(R.id.sign_in_button_google);
        loginButtonGoogle.setOnClickListener(this);
        loginButtonTwitter = (TwitterLoginButton) findViewById(R.id.twitter_login_button);
        loginButtonTwitter.setOnClickListener(this);
        loginButtonTwitter.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                // The TwitterSession is also available through:
                // Twitter.getInstance().core.getSessionManager().getActiveSession()
                TwitterSession session = result.data;
                // TODO: Remove toast and use the TwitterSession's userID
                // with your app's user model
                String msg = "@" + session.getUserName() + " logged in! (#" + session.getUserId() + ")";

                 Intent  main= new Intent(getApplicationContext(), MainActivity.class);

                final String[] usuarioInfo = new String[4];

                /*

                Twitter.getApiClient(session).getAccountService().
                        .verifyCredentials(null,null,new Callback<User>() {

                    @Override
                    public void success(Result<User> userResult) {
                        Bundle b=new Bundle();
                        Intent intent=new Intent();
                        intent.putExtra("user",userResult.data.name);
                        Intent user = intent.putExtra("user", userResult.data.email);


                        // b.putExtra("img",userResult.data.profileImageUrlHttps);



                    }

                    @Override
                    public void failure(TwitterException exception) {

                    }
                });
                */


                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                main.putExtra("user",session.getUserName());
                main.putExtra("token",session.getAuthToken());
                main.putExtra("id",session.getUserId());
                main.putExtra("session",session.getClass());
                main.putExtra("tipo","twitter");

                startActivity(main);
                finish();
            }
            @Override
            public void failure(TwitterException exception) {
                Log.d("TwitterKit", "Login with Twitter failure", exception);
            }


        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Make sure that the loginButtonTwitter hears the result from any
        // Activity that it triggered.

        switch (requestCode)
        {
            case RC_SIGN_IN:  GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                //Calling a new function to handle signin
                handleSignInResult(result);
                break;

            default:
                loginButtonTwitter.onActivityResult(requestCode, resultCode, data);
                break;
        }



    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void handleSignInResult(GoogleSignInResult result) {
        //If the login succeed
        if (result.isSuccess()) {
            //Getting google account
            GoogleSignInAccount acct = result.getSignInAccount();
            Log.e("Inicio",String.valueOf(result.getStatus())+result.toString());
            Log.e("InicioSesion", "display name: " + acct.getDisplayName());

            //Displaying name and email

            //Initializing image loader


            Intent main=new Intent(getApplicationContext(), MainActivity.class);
            main.putExtra("user ",acct.getDisplayName());
            main.putExtra("correo", acct.getEmail());
            main.putExtra("img", acct.getPhotoUrl());


           startActivity(main);

            //Loading image
           // profilePhoto.setImageUrl(acct.getPhotoUrl().toString(), imageLoader);


            finish();
        } else {
            //If login fails
            Log.e("ERROR",String.valueOf(result.getStatus())+result.toString());
           Log.e("Google", "Login Googled Failed");
        }
    }

    private void googleInicioSesion() {
       Toast.makeText(this, "Iniciar Sesion", Toast.LENGTH_LONG).show();
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
       startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d("GoogleLogIn", "Login with Google failure");
    }

    private GoogleApiClient buildGoogleAPIClient() {


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        return mGoogleApiClient;
    }

    private void cerrarS() {

            if(mGoogleApiClient.isConnected()) {

                Toast.makeText(this, "Conectado Google",
                        Toast.LENGTH_SHORT).show();
                Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                        new ResultCallback<Status>() {
                            @Override
                            public void onResult(Status status) {
                                //updateUI(false);
                                Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
                                mGoogleApiClient.disconnect();

                            }
                        });
            }
            else
            {
                Twitter.getSessionManager().clearActiveSession();

                SessionRecorder.recordSessionInactive("About: accounts deactivated");
                Answers.getInstance().logLogin(new LoginEvent().putMethod("Twitter").putSuccess(false));


                Toast.makeText(this, "Haz cerrado sesión",
                        Toast.LENGTH_SHORT).show();
            }


        }


    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.sign_in_button_google:
                googleInicioSesion();
                break;

            case R.id.btnCerrarSesion:
                cerrarS();
                break;
        }
    }


}
