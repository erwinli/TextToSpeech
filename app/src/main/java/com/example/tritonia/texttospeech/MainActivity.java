package com.example.tritonia.texttospeech;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;

import java.util.Locale;


public class MainActivity extends ActionBarActivity {

    public static int TTS_DATA_CHECK = 1;
    public static int VOICE_RECOGNITION = 2;
    private TextToSpeech tts, ttsagain;
    private boolean ttsIsInit, ttsagainIsInit = false;
    public String demotext = "This is a test of the text-to-speech engine in Android.";
    public EditText txtinput;
    public SeekBar seekBar;
    public SeekBar speedSeekBar;
    float pitchValue = 0;
    int speedValue = 0;
    int lang = 0;
    int test;


    public void testTTS(View v) {
        demotext = txtinput.getText().toString();

        if (demotext.isEmpty()){
            Log.i("SpeechDemo", "## ERROR 02: Field is Empty");
            demotext = "The field is empty. Type something first!";
        }

        Intent intent = new Intent(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        startActivityForResult(intent, TTS_DATA_CHECK);
        Toast.makeText(getBaseContext(), "Testing Text to Speech", Toast.LENGTH_SHORT).show();
    }

    public void setUS(View v) {
        lang = 0;

    }

    public void setFrench(View v){
        lang = 1;

    }

    public void setGerman(View v){
        lang = 2;

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (pitchValue == 0)
        {
            pitchValue = 100;
        }


        if (requestCode == TTS_DATA_CHECK) {
            if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
                Log.i("SpeechDemo", "## INFO 03: CHECK_VOICE_DATA_PASS");
                tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {

                    @Override
                    public void onInit(int arg0) {
                        ttsIsInit = true;
                        if (tts.isLanguageAvailable(Locale.US) >= 0) {

                            //lang select
                            if(lang == 0){
                                tts.setLanguage(Locale.US);
                            }
                            if(lang == 1){
                                tts.setLanguage(Locale.CANADA_FRENCH);
                            }
                            if(lang == 2){
                                tts.setLanguage(Locale.GERMAN);
                            }
                            tts.setPitch(pitchValue);
                            tts.setSpeechRate(speedValue);
                            tts.speak(demotext, TextToSpeech.QUEUE_ADD, null);

                        }
                    }

                });
            } else {
                Log.i("SpeechDemo", "## INFO 04: CHECK_VOICE_DATA_FAILED, resultCode = " + resultCode);
                Intent installVoice = new Intent(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
                startActivity(installVoice);

            }

        } else if (requestCode == VOICE_RECOGNITION) {
            Log.i("SpeechDemo", "## INFO 02: RequestCode VOICE_RECOGNITION = " + requestCode);
        } else {
            Log.i("SpeechDemo", "## ERROR 01: Unexpected RequestCode = " + requestCode);
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtinput = (EditText)findViewById(R.id.editText1);
        seekBar = (SeekBar)findViewById(R.id.seekBar1);
        speedSeekBar = (SeekBar)findViewById(R.id.seekBarSpeed);

        seekBar.setMax(10);
        seekBar.incrementProgressBy(1);
        seekBar.setProgress(1);

        speedSeekBar.setMax(10);
        speedSeekBar.incrementProgressBy(1);
        speedSeekBar.setProgress(1);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {


            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                pitchValue = (float)(((double)progress+1)/10);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Toast.makeText(getApplicationContext(), "Pitch: " + pitchValue, Toast.LENGTH_SHORT).show();
            }
        });

        speedSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {


            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                speedValue = (int)((double)progress+1);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Toast.makeText(getApplicationContext(), "Speech Speed: " + speedValue, Toast.LENGTH_SHORT).show();
            }
        });

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
