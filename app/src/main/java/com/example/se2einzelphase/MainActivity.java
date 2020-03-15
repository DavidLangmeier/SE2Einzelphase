package com.example.se2einzelphase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // called when user taps the "Send" button
    public void sendMessage(View view) {
        EditText editText = (EditText) findViewById(R.id.editText);
        int matrNr = (Integer.parseInt(editText.getText().toString()));
        Log.d("Matrikelnummer = ", String.valueOf(matrNr));
        sendToServer(matrNr);
    }

    public int sendToServer(int nr) {
        // TODO: 15.03.2020 matrNr an Server senden
        return 0;
    }
}
