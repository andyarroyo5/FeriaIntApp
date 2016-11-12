package edu.udem.feriaint.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import edu.udem.feriaint.R;

public class TriviaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //if 2 o 4
        setContentView(R.layout.activity_trivia2);
        //setContentView(R.layout.activity_trivia4);
    }
}
