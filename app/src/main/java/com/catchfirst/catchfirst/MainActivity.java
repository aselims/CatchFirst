package com.catchfirst.catchfirst;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.os.Bundle;
import android.os.ParcelUuid;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements Contract.view {

    private TextView distanceTV;
    private TextView msgTV;



    private TextToSpeech textToSpeech;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    textToSpeech.setLanguage(Locale.UK);
                }
            }
        });

        ScanPresenter scanPresenter = new ScanPresenter();
        scanPresenter.start(this);





        distanceTV = (TextView) findViewById(R.id.tv_distance);
        msgTV = (TextView) findViewById(R.id.tv_msg);


       /* if (distance > 4) {
            if (!textToSpeech.isSpeaking()) {
                textToSpeech.speak("Don't forget your belongings!", TextToSpeech.QUEUE_FLUSH, null, null);
            }
        }*/


      /*  findViewById(R.id.btn_stop_scan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bluetoothLeScanner.stopScan(scanCallback);
            }
        });
*/

    }


    @Override
    public void showDistance(double distance) {
        distanceTV.setText(String.valueOf(distance));
    }

    @Override
    public void showSafe() {
        msgTV.setText("Safe!");
        distanceTV.setText("");

    }

    @Override
    public void showDetectionMode() {
        msgTV.setText("");

    }

    @Override
    public void showBoom() {
        msgTV.setText("Boom!");

    }

    @Override
    public void showDeactivated() {

        msgTV.setText("Deactivated!");
    }

    @Override
    public boolean isButtonPressed() {
        return false;
    }
}
