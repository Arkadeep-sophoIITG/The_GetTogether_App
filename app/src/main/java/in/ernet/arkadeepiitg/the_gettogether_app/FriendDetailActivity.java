package in.ernet.arkadeepiitg.the_gettogether_app;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FriendDetailActivity extends AppCompatActivity {
	
	TextView n, e, p;
	String name, email;
	long phone;
	DBAdapter db;
	/** Called when the activity is first created. */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_friend_detail);
		ImageButton startBtn = (ImageButton) findViewById(R.id.sms);
		startBtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				sendSMS();
			}
		});

		try {
            String destPath = "/data/data/" + getPackageName() +
                "/databases";
            File f = new File(destPath);
            if (!f.exists()) {            	
            	f.mkdirs();
                f.createNewFile();
            	
            	//---copy the db from the assets folder into 
            	// the databases folder---
                CopyDB(getBaseContext().getAssets().open("gtapp"),
                    new FileOutputStream(destPath + "/GTApp"));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        n = (TextView)findViewById(R.id.txtName);   
		e = (TextView)findViewById(R.id.txtEmail);   
		p = (TextView)findViewById(R.id.txtPhone); 
		db = new DBAdapter(this);
		db.open();
		Intent intent = this.getIntent();
        int id = intent.getIntExtra("id", 0);
        
        Cursor c = db.getFriend(id);
        if (c.moveToFirst())
        {
            do {
            	
            	name = c.getString(1);
            	email = c.getString(2);
                phone = c.getLong(3);
               
            } while (c.moveToNext());
        }
           
        db.close();
        
        n.setText(name);
        e.setText(email);
        p.setText(new Long(phone).toString());

	}
	public void CopyDB(InputStream inputStream,
					   OutputStream outputStream) throws IOException {
		//---copy 1K bytes at a time---
		byte[] buffer = new byte[1024];
		int length;
		while ((length = inputStream.read(buffer)) > 0) {
			outputStream.write(buffer, 0, length);
		}
		inputStream.close();
		outputStream.close();
	}

	public void sendSMS() {
		Log.i("Send SMS", "");
		Intent smsIntent = new Intent(Intent.ACTION_VIEW);

		smsIntent.setData(Uri.parse("smsto:"));
		smsIntent.setType("vnd.android-dir/mms-sms");
		smsIntent.putExtra("address",phone);
		smsIntent.putExtra("sms_body"  , "Reminder");

		try {
			startActivity(smsIntent);
			finish();
			Log.i("Finished sending SMS...", "");
		}
		catch (android.content.ActivityNotFoundException ex) {
			Toast.makeText(FriendDetailActivity.this,
					"SMS failed, please try again later.", Toast.LENGTH_SHORT).show();
		}
	}

	

}
