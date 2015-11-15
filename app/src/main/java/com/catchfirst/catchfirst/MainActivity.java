package com.catchfirst.catchfirst;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements Contract.view {

	private State state = State.safe;

	private enum State {
		safe,
		searching,
		lost,
		inDanger,
		deactivated
	}

	private static final String TAG = "Main activity";

	private TextView distanceTV;
	private TextView msgTV;


	private TextToSpeech textToSpeech;
	private boolean buttonPressed = false;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		final ScanPresenter scanPresenter = new ScanPresenter();

		textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
			@Override
			public void onInit(int status) {
				if (status != TextToSpeech.ERROR) {
					textToSpeech.setLanguage(Locale.UK);
				}
			}
		});

		findViewById(R.id.btn_restart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                state = State.safe;
                scanPresenter.restart();
                findViewById(R.id.layout).setBackgroundColor(Color.BLACK);


            }
        });

		findViewById(R.id.btn_deactivate).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int action = motionEvent.getAction();
                if (action == MotionEvent.ACTION_DOWN)
                    buttonPressed = true;
                if (action == MotionEvent.ACTION_UP)
                    buttonPressed = false;
                Log.i(TAG, "buttonPressed: " + buttonPressed);
                return false;
            }
        });

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
        ProgressBar progress = ((ProgressBar)findViewById(R.id.progressBar));
        progress.getProgressDrawable().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN);

        progress.setProgress((int)(100 - (100 * distance / 20)));

		distanceTV.setText(String.valueOf(distance));
	}

	@Override
	public void showSafe() {
		if (state != State.safe)
			textToSpeech.speak("You are now safe!", TextToSpeech.QUEUE_FLUSH, null, null);
		state = State.safe;

		msgTV.setText("Safe!");
		showEmptyDistance();
	}

	private void showEmptyDistance() {
		distanceTV.setText("");
	}

	@Override
	public void showDetectionMode(boolean buttonPressed) {
		if (buttonPressed) {
			if (state != State.searching)
				textToSpeech.speak("Searching", TextToSpeech.QUEUE_FLUSH, null, null);
			state = State.searching;
		} else {
			if (state != State.inDanger)
				textToSpeech.speak("Attention please!", TextToSpeech.QUEUE_FLUSH, null, null);
			state = State.inDanger;
		}
		msgTV.setText("");
	}

	@Override
	public void showBoom() {
		if (state != State.lost)
			textToSpeech.speak("You lost!", TextToSpeech.QUEUE_FLUSH, null, null);
		state = State.lost;
		msgTV.setText("Boom!");
		showEmptyDistance();
        findViewById(R.id.layout).setBackgroundColor(Color.RED);

    }

	@Override
	public void showDeactivated() {
		if (state != State.deactivated)
			textToSpeech.speak("Deactivated!", TextToSpeech.QUEUE_FLUSH, null, null);
		state = State.deactivated;
		msgTV.setText("Deactivated!");
	}

	@Override
	public boolean isButtonPressed() {
		return buttonPressed;
	}
}
