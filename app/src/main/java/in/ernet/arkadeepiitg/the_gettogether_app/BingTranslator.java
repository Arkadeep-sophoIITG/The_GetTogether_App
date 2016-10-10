package in.ernet.arkadeepiitg.the_gettogether_app;

/**
 * Created by arkadeep on 9/10/16.
 */

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.memetix.mst.language.Language;
import com.memetix.mst.translate.Translate;

import java.util.Locale;
public class BingTranslator extends Activity implements TextToSpeech.OnInitListener {

    private TextToSpeech tts;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translate);

        tts = new TextToSpeech(this, this);
        ((Button) findViewById(R.id.bSpeak)).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                speakOut(((TextView) findViewById(R.id.etUserText)).getText().toString());
            }
        });

        ((Button) findViewById(R.id.bTranslate)).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                class bgStuff extends AsyncTask<Void, Void, Void> {
                    String text = ((EditText) findViewById(R.id.etUserText)).getText().toString();
                    String translatedText = "";
                    @Override
                    protected Void doInBackground(Void... params) {
                        // TODO Auto-generated method stub
                        try {

                            translatedText = translate(text);
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                            translatedText = e.toString();
                        }

                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void result) {
                        // TODO Auto-generated method stub
                        ((TextView) findViewById(R.id.tvTranslatedText)).setText(translatedText);
                        super.onPostExecute(result);
                    }

                }

                new bgStuff().execute();
            }
        });
    }
    Language x=Language.ENGLISH;
    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();
        switch (view.getId()) {
            case R.id.L1:x=Language.HINDI;
                break;
            case R.id.L3:x=Language.SPANISH;
                break;
            case R.id.L4:x=Language.ARABIC;
                break;
            case R.id.L5:x= Language.PORTUGUESE;
                break;
            case R.id.L7:x=Language.CHINESE_SIMPLIFIED;
                break;
            case R.id.L8:x=Language.RUSSIAN;
                break;
            default:
                Toast.makeText(getBaseContext(), "Please select one of the options", Toast.LENGTH_LONG).show();
        }

    }
    public String translate(String text) throws Exception{


        // Set the Client ID / Client Secret once per JVM. It is set statically and applies to all services
        Translate.setClientId("absgibson123"); //Change this
        Translate.setClientSecret("rddCjRR/TA+n/GzCA8pG1Xa4Zx9vVaYuhoB9e3zMfDOk="); //change


        String translatedText = "";

        translatedText = Translate.execute(text,x);

        return translatedText;
    }

    @Override
    public void onInit(int status) {
        // TODO Auto-generated method stub
        if (status == TextToSpeech.SUCCESS) {

            int result = tts.setLanguage(Locale.ENGLISH);

            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported");
            } else {

                //speakOut("Ich");
            }

        } else {
            Log.e("TTS", "Initilization Failed!");
        }
    }

    private void speakOut(String text) {
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }

}