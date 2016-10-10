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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class PlaceUpdateActivity extends AppCompatActivity {
	String name, address, phone;
	EditText n, a, p;
	int ph;
	CheckBox pi, sw, bu;
	RadioGroup rg;
	RadioButton ra, ex,mo,go;
	DBAdapter db;
	int id;
	String po,rat;
	
	
	/** Called when the activity is first created. */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update_place);
		
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
			ex = (RadioButton) findViewById(R.id.optionExcellent);
			mo = (RadioButton) findViewById(R.id.optionModerate);
			go = (RadioButton) findViewById(R.id.optionGood);
			
			db = new DBAdapter(this);
			db.open();
			Intent intent = this.getIntent();
	        id = intent.getIntExtra("id", 0);
	        
	        Cursor c = db.getPlace(id);
	        if (c.moveToFirst())
	        {
	            do {
	            	
	            	name = c.getString(1);
	            	address = c.getString(2);
	                ph = c.getInt(3);
	                po = c.getString(4);
	                rat = c.getString(5);
	               
	            } while (c.moveToNext());
	        }
	           
	        db.close();
	        
	        n.setText(name);
	        a.setText(address);
	        p.setText(new Integer(ph).toString());
	        if((po.indexOf("Pizza"))!=-1) pi.setChecked(true);
	        if((po.indexOf("Burger"))!=-1) bu.setChecked(true);
	        if((po.indexOf("Sandwich"))!=-1) sw.setChecked(true);
	        if((rat.indexOf("Excellent"))!=-1) ex.setChecked(true);
	        if((rat.indexOf("Moderate"))!=-1) mo.setChecked(true);
	        if((rat.indexOf("Good"))!=-1) go.setChecked(true);
	        
	        
			
			
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
   // int id = db.updatePlace(id, name, address,phone, popFood, rat);
    if (db.updatePlace(id, name, address,phone, popFood, rat))
        Toast.makeText(PlaceUpdateActivity.this, "Update successful.", Toast.LENGTH_LONG).show();
    else
        Toast.makeText(PlaceUpdateActivity.this, "Update failed.", Toast.LENGTH_LONG).show();
    db.close(); 
    Log.d("From App","insert="+id);
    db.close();
		Toast.makeText(getBaseContext(), "Place Updated", Toast.LENGTH_SHORT).show();
		Intent i = new Intent("in.ernet.arkadeepiitg.the_gettogether_app;.PlacesActivity");
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
    		 	Intent intent1 = new Intent(PlaceUpdateActivity.this, HomeActivity.class);
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
