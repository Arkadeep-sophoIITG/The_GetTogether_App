package in.ernet.arkadeepiitg.the_gettogether_app;

/**
 * Created by arkadeep on 10/10/16.
 */

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

public class About extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);
        TextView txtView = (TextView) findViewById(R.id.about_text);
        txtView.setMovementMethod(LinkMovementMethod.getInstance());
    }
}

