package edu.udem.feriaint;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Environment;
import android.preference.PreferenceManager;

import com.crashlytics.android.Crashlytics;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import java.io.File;

import io.fabric.sdk.android.Fabric;

import android.app.Application;

/**
 * Created by Andrea Arroyo on 03/11/2016.
 */

public class App extends Application {
    public final static String BROADCAST_POEM_CREATION_RESULT = "POEM_CREATION_RESULT";
    public final static String BROADCAST_POEM_DELETION_RESULT = "POEM_DELETION_RESULT";
    public final static String BROADCAST_POEM_CREATION = "POEM_CREATION";
    public final static String BROADCAST_POEM_DELETION = "POEM_DELETION";

    public final static String CRASHLYTICS_KEY_THEME = "theme";
    public final static String CRASHLYTICS_KEY_SESSION_ACTIVATED = "session_activated";
    public final static String CRASHLYTICS_KEY_SEARCH_COUNT = "last_twitter_search_result_count";
    public final static String CRASHLYTICS_KEY_COUNTDOWN = "countdown_timer_remaining_sec";
    public final static String CRASHLYTICS_KEY_WORDBANK_COUNT = "word_bank_count_loaded";
    public final static String CRASHLYTICS_KEY_POEM_TEXT = "saving_poem_text";
    public final static String CRASHLYTICS_KEY_POEM_IMAGE = "saving_poem_image";
    public final static String CRASHLYTICS_KEY_CRASHES = "are_crashes_enabled";
    public final static String POEM_PIC_DIR = "cannonball";

    private static App singleton;
    private TwitterAuthConfig authConfig;
    private Typeface avenirFont;
    private String CONSUMER_KEY="554133054-F09oYfIaq3hFcSibiEovIoojyKhiLHO8TZ4eNwYv";
    private String CONSUMER_SECRET="YAWQf0B9mfD9KOI5iVo2q2q5aWXQkoPo4efnBlCgfTSXI";



    public static App getInstance() {
        return singleton;
    }

    /* Checks if external storage is available for read and write */
    public static boolean isExternalStorageWritable() {
        final String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /* Checks if external storage is available to at least read */
    public static boolean isExternalStorageReadable() {
        final String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    public static File getPoemFile(String fileName) {
        // Get the directory for the user's public pictures directory.
        final File picsDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        final File poemPicsDir = new File(picsDir, POEM_PIC_DIR);
        poemPicsDir.mkdirs();
        return new File(poemPicsDir, fileName);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        singleton = this;
        extractAvenir();
        authConfig
                = new TwitterAuthConfig(CONSUMER_KEY, CONSUMER_SECRET);
        Fabric.with(this, new Crashlytics(), new Twitter(authConfig));

        Crashlytics.setBool(CRASHLYTICS_KEY_CRASHES, areCrashesEnabled());
    }

    private void extractAvenir() {
        avenirFont = Typeface.createFromAsset(getAssets(), "fonts/Avenir.ttc");
    }

    public Typeface getTypeface() {
        if (avenirFont == null) {
            extractAvenir();
        }
        return avenirFont;
    }

    public boolean areCrashesEnabled() {
        SharedPreferences preferences;
        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        return preferences.getBoolean("are_crashes_enabled", false);
    }

    public void setCrashesStatus(boolean status) {
        SharedPreferences preferences;
        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        final SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("are_crashes_enabled", status);
        editor.apply();
    }
}
