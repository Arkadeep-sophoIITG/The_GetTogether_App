package in.ernet.arkadeepiitg.the_gettogether_app;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class GetTogetherDetailActivity extends AppCompatActivity{
	
	TextView n, d, f, p, dt;
	String name, desc, friends, place, datetime;
	
	DBAdapter db;
	
	/** Called when the activity is first created. */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gettogether_detail);
		
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
		d = (TextView)findViewById(R.id.txtDescription);   
		f = (TextView)findViewById(R.id.txtFriend); 
		p = (TextView)findViewById(R.id.txtPlace);
		dt = (TextView)findViewById(R.id.txtDateTime);
		
		db = new DBAdapter(this);
		db.open();
		Intent intent = this.getIntent();
        int id = intent.getIntExtra("id", 0);
        
        Cursor c = db.getGetTogether(id);
        if (c.moveToFirst())
        {
            do {
            	
            	name = c.getString(1);
            	desc = c.getString(2);
                friends = c.getString(3);
                place = c.getString(4);
                datetime = c.getString(5);
                
               
            } while (c.moveToNext());
        }
           
        db.close();
        
        n.setText(name);
        d.setText(desc);
        f.setText(friends);
        p.setText(place);
        dt.setText(datetime);
        
        
		
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
	
	
	
	

}
