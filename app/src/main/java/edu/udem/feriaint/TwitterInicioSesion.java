package edu.udem.feriaint;

import android.accounts.Account;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.TwitterAuthProvider;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Session;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import edu.udem.feriaint.Activities.MainActivity;

public class TwitterInicioSesion extends AppCompatActivity implements  View.OnClickListener,GoogleApiClient.OnConnectionFailedListener {

    private String TAG;
    private static final int RC_SIGN_IN =55 ;
    TwitterLoginButton loginButtonTwitter;
    SignInButton loginButtonGoogle;

    private final String TWITTER="twitter";
    private final String GOOGLE="google";


    Button cerrarSesion;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;


    GoogleApiClient mGoogleApiClient;
    AuthCredential credentialGoogle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TAG=getClass().getSimpleName();
        mAuth = FirebaseAuth.getInstance();
        mGoogleApiClient=buildGoogleAPIClient();


        setContentView(R.layout.activity_twitter);


        // Configure Google Sign In


        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                  // empezaAplicacionFB(user);
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };



        final Bundle intent=getIntent().getExtras();



        if (intent!=null && intent.getBoolean("cerrarS"))
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
                handleTwitterSession(result.data);
               empezarAplicacionTwitter(TWITTER,session);



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
            case RC_SIGN_IN:


                //Calling a new function to handle signin


                GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                if (result.isSuccess()) {
                    // Google Sign In was successful, authenticate with Firebase
                    GoogleSignInAccount account = result.getSignInAccount();
                    handleSignInResult(result);

                } else {
                    // Google Sign In failed, update UI appropriately
                    // ...
                }
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
            String personName = acct.getDisplayName();
            String personEmail = acct.getEmail();
            String personId = acct.getId();
            Uri personPhoto = acct.getPhotoUrl();
            String personPhoneURL = acct.getPhotoUrl().toString();

           // empezarAplicacionGoogle(acct);

            //Displaying name and email

            //Initializing image loader

            firebaseAuthWithGoogle(acct);

           /* Intent main=new Intent(getApplicationContext(), MainActivity.class);
           main.putExtra("user ",acct.getDisplayName());
           main.putExtra("correo", acct.getEmail());
           main.putExtra("img", acct.getPhotoUrl());


           startActivity(main);
           finish();*/
        } else {
            //If login fails
            Log.e("ERROR",String.valueOf(result.getStatus())+result.toString());
           Log.e("Google", "Login Googled Failed");
        }
    }

    private void googleInicioSesion() {
       Toast.makeText(this, "Iniciar Sesion", Toast.LENGTH_SHORT).show();
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
       startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d("GoogleLogIn", "Login with Google failure");
    }

    private GoogleApiClient buildGoogleAPIClient() {


        /*GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();*/

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        return mGoogleApiClient;
    }



    private void empezarAplicacionTwitter(String tipo, Session activeSession)
    {
        Intent main= new Intent(this, MainActivity.class);

        TwitterSession session= Twitter.getSessionManager().getSession(activeSession.getId());
        main.putExtra("user",session.getUserName());
        main.putExtra("token",session.getAuthToken());
        main.putExtra("id",session.getUserId());
        main.putExtra("session",session.getClass());
        main.putExtra("tipo","twitter");

        startActivity(main);
        finish();
    }

    public void empezarAplicacionGoogle(GoogleSignInAccount account)
    {
        Intent main= new Intent(this, MainActivity.class);
        main.putExtra("user", account.getDisplayName());
        main.putExtra("correo", account.getEmail());
        main.putExtra("img", account.getPhotoUrl());
        main.putExtra("token", account.getIdToken());
        main.putExtra("tipo","google");
        main.putExtra("id",account.getId());
        startActivity(main);
        finish();

    }



    public void empezaAplicacionFB(FirebaseUser user)
    {
        Intent main= new Intent(this, MainActivity.class);
        main.putExtra("user", user.getDisplayName());
        main.putExtra("correo", user.getEmail());
        main.putExtra("img", user.getPhotoUrl());
        main.putExtra("provider", user.getProviderId());
        main.putExtra("tipo","firebase");
        main.putExtra("id",user.getUid());
        startActivity(main);
        finish();

    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void cerrarS() {

        FirebaseAuth.getInstance().signOut();

        if (mGoogleApiClient.isConnected()) {
            // Google sign out
            Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                    new ResultCallback<Status>() {
                        @Override
                        public void onResult(Status status) {
                            Log.e(TAG, "salir google");
                        }
                    });
        }


    }


    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.sign_in_button_google:
                //googleInicioSesion();
                signIn();
                break;

            case R.id.btnCerrarSesion:
                cerrarS();
                break;
        }
    }




    private void firebaseAuthWithGoogle(final GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithCredential", task.getException());
                            Toast.makeText(getApplicationContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();


                            empezarAplicacionGoogle(acct);

                        }
                        // ...
                    }
                });
    }
    private void handleTwitterSession(TwitterSession session) {
        Log.d(TAG, "handleTwitterSession:" + session);

        AuthCredential credential = TwitterAuthProvider.getCredential(
                session.getAuthToken().token,
                session.getAuthToken().secret);

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithCredential", task.getException());
                            Toast.makeText(getApplicationContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }


}
