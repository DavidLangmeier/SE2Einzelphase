package com.example.se2einzelphase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class MainActivity extends AppCompatActivity {
    Socket socket;
    final String hostname = "se2-isys.aau.at";
    final int portNr = 53212;
    TextView tvServerResult;
    EditText editText;
    TextView primeNumbers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = (EditText) findViewById(R.id.editText);
        tvServerResult = (TextView)findViewById(R.id.serverResult);
        primeNumbers = (TextView)findViewById(R.id.primeNumbers);
    }

    public void sendMessage(View view) {
        String matrNr = editText.getText().toString();
        Log.d("MATRIKELNUMMER = ", (matrNr));
        ConnectionTask task = new ConnectionTask();
        task.execute(matrNr);
    }

    public void showOnlyPrimeNumbers(View view) {
        int lengthOfNr = editText.getText().toString().length();
        int matrNr = (Integer.parseInt(editText.getText().toString()));
        Log.d("SOPN-MATRIKELNR: ", String.valueOf(matrNr));
        String result = "";
        int mod = 10;

        for (int i = 0; i < lengthOfNr; i++) {
            int currentDigit = matrNr % mod;
            Log.d("SOPN-CURRENT_DIGIT = ", String.valueOf(currentDigit));

            if ((currentDigit != 0) && (currentDigit == 2 || currentDigit == 3 || currentDigit == 5 || currentDigit == 7)) {
                result += currentDigit;
            }
            matrNr /= 10;
        }
        Log.d("PRIMEONLY = ", result);

        String reverse = "";
        for(int i = result.length() - 1; i >= 0; i--)
        {
            reverse = reverse + result.charAt(i);
        }

        primeNumbers.setText(reverse);
    }


    private class ConnectionTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String result = "";

            try {
                socket = new Socket(hostname, portNr);
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                Log.d("SENDING MESSAGE: ", strings[0]);
                out.writeBytes(strings[0] +'\n');
                result = in.readLine();
                Log.d("SERVER ANSWERED: ", result);
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            tvServerResult.setText(s);
        }
    }

}
