package in.ernet.arkadeepiitg.the_gettogether_app;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FriendUpdateActivity extends AppCompatActivity {
	EditText n, e, p;
	String name, email, phone;
	long ph;
	DBAdapter db;
	int id;
	
	/** Called when the activity is first created. */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update_friend);
		

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
        
        n = (EditText)findViewById(R.id.txtName1);   
		e = (EditText)findViewById(R.id.txtEmail1);   
		p = (EditText)findViewById(R.id.txtPhone1); 
		db = new DBAdapter(this);
		db.open();
		Intent intent = this.getIntent();
        id = intent.getIntExtra("id", 0);
        
        Cursor c = db.getFriend(id);
        if (c.moveToFirst())
        {
            do {
            	
            	name = c.getString(1);
            	email = c.getString(2);
                ph = c.getLong(3);
               
            } while (c.moveToNext());
        }
           
        db.close();
        
        n.setText(name);
        e.setText(email);
        p.setText(new Long(ph).toString());
        
        
		
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

	
	/** Called when the Update Friend button is clicked. */
	public void onClick(View view)
	{
		

		name = n.getText().toString();
		email = e.getText().toString(); 
		phone = p.getText().toString();;
		//---delete a contact---
        db.open();
        
        if (db.updateFriend(id,name,email,phone))
            Toast.makeText(FriendUpdateActivity.this, "Update successful.", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(FriendUpdateActivity.this, "Update failed.", Toast.LENGTH_LONG).show();
        db.close();  
		Toast.makeText(getBaseContext(), "Friend Updated", Toast.LENGTH_SHORT).show();
		Intent i = new Intent("in.ernet.arkadeepiitg.the_gettogether_app.FriendsActivity");
		startActivity(i);
		
	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        
        MenuItem mnu1 = menu.add(0, 1, 1, "Home");
        {
            mnu1.setIcon(R.drawable.home_icon);
            mnu1.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        }
        MenuItem mnu2 = menu.add(0, 2, 2, "Exit");
        {
            mnu2.setIcon(R.drawable.exit_icon);
            mnu2.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        }
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
    	switch (item.getItemId()) {
    	 
    	 case 0:
    		 	Intent intent1 = new Intent(FriendUpdateActivity.this, HomeActivity.class);
				startActivity(intent1);
	            return true;
        case 1:
        	Intent intent = new Intent(Intent.ACTION_MAIN);
        	intent.addCategory(Intent.CATEGORY_HOME);
        	intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        	startActivity(intent);
            return true;
        
        
    }
    	return false;
    }
    	

	

}
