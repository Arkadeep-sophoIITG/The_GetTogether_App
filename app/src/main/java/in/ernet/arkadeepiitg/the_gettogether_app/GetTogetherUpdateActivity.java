package in.ernet.arkadeepiitg.the_gettogether_app;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class GetTogetherUpdateActivity extends AppCompatActivity{
	
	

	TimePicker timePicker;
	int hour, minute, year, month, day;
	DatePicker datePicker;
	String strDate,strTime;
	
	boolean placeClicked, dateClicked, timeClicked;
	String name, desc, place,friends, selectedFriends, datetime, selectedPlace, currSelectedFriends;
	EditText n, d;
	TextView sf,sp,sdt;
	DBAdapter db;
	int id;
	
	ListView friendsListView;
	ArrayAdapter<String> listAdapter1;
	List friendsList, friendIdList;
	/* String[] friends = {
	            "Sakhi",
	            "Mili",
	            "Ananya",
	            "Aditya"
	    };
	    */
	 
	 ListView placesListView;
		ArrayAdapter<String> listAdapter2;
		List placesList, placeIdList;
	/*	 String[] places = {
		            "Pizza Hut",
		            "Dominoes",
		            "Mc Donalds",
		            "Smokin Joes"
		    };
		    */
	
	/** Called when the activity is first created. */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update_gettogether);

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
		d = (EditText)findViewById(R.id.txtDescription);  
		sf = (TextView)findViewById(R.id.txtSelectedFriends);  
		sp = (TextView)findViewById(R.id.txtSelectedPlace);
		sdt =  (TextView)findViewById(R.id.txtSelectedDateTime);
		
		db = new DBAdapter(this);
        db.open();
        
        Cursor c = db.getAllFriends();
        friendsList = new ArrayList();
        friendIdList = new ArrayList();
        
        if (c.moveToFirst())
        {
            do {
            	int id = c.getInt(0);
            	String nm = c.getString(1);
            	Log.d("From App","Name="+nm);
                friendsList.add(nm);
                friendIdList.add(id);
               
            } while (c.moveToNext());
        }
        
        db.close();
        
        db.open();
        Intent intent = this.getIntent();
        int idx = intent.getIntExtra("id", 0);
        Cursor c4 = db.getGetTogether(idx);
        int ct = c4.getCount();
        Log.d("from app","ct="+ct);
        if (c4.moveToFirst())
        {
            do {
            	int i = c4.getInt(1);
            	currSelectedFriends = c4.getString(3);
            	
               
            } while (c4.moveToNext());
        }
        Log.d("from app","curreselectedfriends="+currSelectedFriends);
        db.close();
		friendsListView = (ListView) findViewById(R.id.lstFriends);
		friendsListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		listAdapter1 = new ArrayAdapter<String>(GetTogetherUpdateActivity.this, android.R.layout.simple_list_item_multiple_choice, friendsList);        
		friendsListView.setAdapter(listAdapter1); 	
		int count = listAdapter1.getCount();
        for (int i = 0; i < count; i++) {
		                String currentItem = (String) listAdapter1.getItem(i);
		                Log.d("from app","currentitem="+currentItem);
		                Log.d("from app","indexof="+currSelectedFriends.indexOf(currentItem));
		                if ((currSelectedFriends.indexOf(currentItem))!=-1) {
		                	friendsListView.setItemChecked(i, true);
		                }
        }

        
		
		
		
		db = new DBAdapter(this);
        db.open();
        
        Cursor c1 = db.getAllPlaces();
        placesList = new ArrayList();
        placeIdList = new ArrayList();
        
        if (c1.moveToFirst())
        {
            do {
            	int id = c1.getInt(0);
            	String nm = c1.getString(1);
            	Log.d("From App","Name="+nm);
                placesList.add(nm);
                placeIdList.add(id);
               
            } while (c1.moveToNext());
        }
        db.close();		
		placesListView = (ListView) findViewById(R.id.lstPlaces);
		listAdapter2 = new ArrayAdapter<String>(GetTogetherUpdateActivity.this, android.R.layout.simple_list_item_1, placesList);        
		placesListView.setAdapter(listAdapter2); 
		
		placesListView.setOnItemClickListener(new OnItemClickListener() {
	        
			public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
				 
                    placeClicked = true;
                    selectedPlace = listAdapter2.getItem(position);
              
		        
		    }					
					
			
	        });
		
		/*if(listAdapter2.getCount() > 2){
	        View item = listAdapter2.getView(0, null, placesListView);
	        item.measure(0, 0);         
	        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, (int) (5.5 * item.getMeasuredHeight()));
	        placesListView.setLayoutParams(params);
	}
	*/
		
		db = new DBAdapter(this);
		db.open();
		 intent = this.getIntent();
        id = intent.getIntExtra("id", 0);
        
        Cursor c3 = db.getGetTogether(id);
        if (c3.moveToFirst())
        {
            do {
            	
            	name = c3.getString(1);
            	desc = c3.getString(2);
                friends = c3.getString(3);
                place = c3.getString(4);
                datetime = c3.getString(5);
               
            } while (c3.moveToNext());
        }
        
       
           
        db.close();
        
        n.setText(name);
        d.setText(desc);
        sf.setText(friends);
        sp.setText(place);
        sdt.setText(datetime);
        
		
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
    		 	Intent intent1 = new Intent(GetTogetherUpdateActivity.this, HomeActivity.class);
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
    	
	
	
	@Override
	protected Dialog onCreateDialog(int id)
	{
		switch (id) {
		case 0:
			return new TimePickerDialog(
					this, mTimeSetListener, hour, minute, false);
			
		case 1:
			return new DatePickerDialog(
					this, mDateSetListener, year, month, day);	
		
		}
		return null;
	}
	
	private TimePickerDialog.OnTimeSetListener mTimeSetListener =
			new TimePickerDialog.OnTimeSetListener()
	{
		public void onTimeSet(
				TimePicker view, int hourOfDay, int minuteOfHour)
		{
			timeClicked = true;
			hour = hourOfDay;
			minute = minuteOfHour;

			SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm aa");			
			Date date = new Date(0,0,0, hour, minute);
			strTime = timeFormat.format(date);

			Toast.makeText(getBaseContext(),
					"You have selected " + strTime,
					Toast.LENGTH_SHORT).show();			
		}
	};
	
	private DatePickerDialog.OnDateSetListener mDateSetListener =
			new DatePickerDialog.OnDateSetListener()
	{
		public void onDateSet(
				DatePicker view, int y, int m, int d)
		{
			dateClicked = true;
			year = y;
			month = m;
			day = d;

			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");			
			Date date = new Date(year, month, day);
			strDate = dateFormat.format(date);

			Toast.makeText(getBaseContext(),
					"You have selected " + strDate,
					Toast.LENGTH_SHORT).show();			
		}
	};
	
	
	public void onClickTime(View view) {
		showDialog(0);
	}

	public void onClickDate(View view) {
		showDialog(1);
	}

	
	/** Called when the Add Friend button is clicked. */
	public void onClick(View view)
	{
		
		DBAdapter db = new DBAdapter(this);

		  
		name = n.getText().toString();
		desc = d.getText().toString(); 
		//String newdatetime = day + "/" + (month +1) + "/" + year +" " + hour +":" + minute;
		String newdatetime = strDate + " " + strTime;
		  SparseBooleanArray checked = friendsListView.getCheckedItemPositions();
	       String selectedFriends = "";
	        for (int i = 0; i < checked.size(); i++) {
	            // Item position in adapter
	            int position = checked.keyAt(i);
	            // Add sport if it is checked i.e.) == TRUE!
	            if (checked.valueAt(i))
	                selectedFriends += listAdapter1.getItem(position) +",";
	        }
	        
	        
    //---add a get together to the database---
  db.open();
  if(placeClicked) place = selectedPlace;
  if(dateClicked || timeClicked) datetime = newdatetime;
    
    if (db.updateGetTogether(id, name, desc,selectedFriends,place,datetime))
        Toast.makeText(GetTogetherUpdateActivity.this, "Update successful.", Toast.LENGTH_LONG).show();
    else
        Toast.makeText(GetTogetherUpdateActivity.this, "Update failed.", Toast.LENGTH_LONG).show();
    db.close();  

		Toast.makeText(getBaseContext(), "Get Together Updated", Toast.LENGTH_SHORT).show();
		Intent i = new Intent(".GetTogethersActivity");
		startActivity(i);
		
	}
	

}
