package edu.udem.feriaint.Activities;

import android.os.Bundle;
import android.util.Log;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import edu.udem.feriaint.R;

/**
 * Created by Andrea Arroyo on 26/11/2016.
 */

public class PlayerActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {

    private YouTubePlayerView playerView;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        setContentView(R.layout.activity_player);

        playerView = (YouTubePlayerView)findViewById(R.id.player_view2);

        playerView.initialize(MainActivity.API_KEY, this);
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                        YouTubeInitializationResult result) {
        Log.e("PLAYACTIVITY", "Fail");
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player,
                                        boolean restored) {
        if(!restored){
            //player.setFullscreen(true);

            player.loadVideo(getIntent().getStringExtra("VIDEO_ID"));
        }
    }
}