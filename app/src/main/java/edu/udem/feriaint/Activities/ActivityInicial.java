package edu.udem.feriaint.Activities;


    import android.app.Activity;
    import android.content.Intent;
    import android.os.Bundle;
    import android.os.Parcelable;
    import android.support.annotation.NonNull;
    import android.support.annotation.Nullable;
    import android.support.v7.app.AppCompatActivity;
    import android.util.Log;

    import com.google.android.gms.auth.api.Auth;
    import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
    import com.google.android.gms.common.ConnectionResult;
    import com.google.android.gms.common.api.GoogleApiClient;
    import com.twitter.sdk.android.Twitter;
    import edu.udem.feriaint.TwitterInicioSesion;



    import edu.udem.feriaint.SessionRecorder;
    import com.twitter.sdk.android.core.Session;
    import com.twitter.sdk.android.core.TwitterSession;

/**
 * Created by laboratorio on 11/3/16.
 */

public class ActivityInicial extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {


    private GoogleApiClient mGoogleApiClient;
    boolean conectadoGoogle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        conectadoGoogle=false;


        final Session activeSession = SessionRecorder.recordInitialSessionState(
                Twitter.getSessionManager().getActiveSession()
        );



        Log.e("IA",String.valueOf(activeSession));
        mGoogleApiClient = buildGoogleAPIClient();
        mGoogleApiClient.connect();
       // Log.e("IA",String.valueOf(mGoogleApiClient.getConnectionResult(result)));

        if (activeSession != null || mGoogleApiClient.isConnected()) {
            startThemeActivity(activeSession);
            Log.e("IA","active session");
        } else {
            startLoginActivity();
            Log.e("IA","log in ");
        }


    }

    private void startThemeActivity(Session activeSession) {

       //if twitter
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

    private void startLoginActivity() {
        Intent hacerLogIn=new Intent(this, TwitterInicioSesion.class);

        hacerLogIn.putExtra("cerrarS",false);
       // hacerLogIn.putExtra("apiClient", (Parcelable) mGoogleApiClient);
        startActivity(hacerLogIn);

    }

    private GoogleApiClient buildGoogleAPIClient() {


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this).addConnectionCallbacks(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        return mGoogleApiClient;
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

        conectadoGoogle=false;
        Log.e("IA","onconnectionFailed"+conectadoGoogle);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        //conectadoGoogle=true;
        Log.e("IA","onconnected"+conectadoGoogle);
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.e("IA","onconnectionsuspended");

    }
}
