package in.ernet.arkadeepiitg.the_gettogether_app;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class PlaceAddActivity extends AppCompatActivity {
	
	String name, address, phone;
	EditText n, a, p;
	CheckBox pi, sw, bu;
	RadioGroup rg;
	RadioButton ra;
	
	/** Called when the activity is first created. */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_place);
		
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
	        
	        n = (EditText)findViewById(R.id.txtName);   
			a = (EditText)findViewById(R.id.txtAddress);   
			p = (EditText)findViewById(R.id.txtPhone);
			pi = (CheckBox) findViewById(R.id.chkBoxPizza);
			bu = (CheckBox) findViewById(R.id.chkBoxBurger);
			sw = (CheckBox) findViewById(R.id.chkBoxSandwich);
			rg = (RadioGroup) findViewById(R.id.rg1);
			
			
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

		
	
	/** Called when the Add Friend button is clicked. */
	public void onClick(View view)
	{
		DBAdapter db = new DBAdapter(this);

		  
		name = n.getText().toString();
		address = a.getText().toString(); 
		phone = p.getText().toString();
		String popFood = "";
		if(pi.isChecked()) popFood += pi.getText();
		if(bu.isChecked()) popFood += bu.getText();
		if(sw.isChecked()) popFood += sw.getText();
		int selectedId = rg.getCheckedRadioButtonId();
		 
		// find the radiobutton by returned id
	        ra = (RadioButton) findViewById(selectedId);
	        String rat = (String) ra.getText();
    //---add a friend to the database---
    db.open();
    long id = db.insertPlace(name, address,phone, popFood, rat);
    Log.d("From App","insert="+id);
    db.close();
    
		Toast.makeText(getBaseContext(), "Place Added", Toast.LENGTH_SHORT).show();
		Intent i = new Intent("in.ernet.arkadeepiitg.the_gettogether_app.PlacesActivity");
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
    	 
    	 case 1:
    		 	Intent intent1 = new Intent(PlaceAddActivity.this, HomeActivity.class);
				startActivity(intent1);
	            return true;
        case 2:
        	Intent intent = new Intent(Intent.ACTION_MAIN);
        	intent.addCategory(Intent.CATEGORY_HOME);
        	intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        	startActivity(intent);
            return true;
        
        
    }
    	return false;
    }
    	
	
	

}
