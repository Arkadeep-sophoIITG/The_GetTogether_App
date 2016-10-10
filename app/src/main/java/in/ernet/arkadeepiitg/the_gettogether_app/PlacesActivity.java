package in.ernet.arkadeepiitg.the_gettogether_app;



import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class PlacesActivity extends AppCompatActivity {
	
	ListView placesListView;
	ArrayAdapter<String> listAdapter;
	List placesList, placeIdList;
	/* String[] places = {
	            "Pizza Hut",
	            "Dominoes",
	            "Mc Donalds",
	            "Smokin Joes"
	    };
	    */
	 DBAdapter db;
	 
	 


	
	/** Called when the activity is first created. */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_places);
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
		
		 //---get all contacts---
		db = new DBAdapter(this);
        db.open();
        
        Cursor c = db.getAllPlaces();
        placesList = new ArrayList();
        placeIdList = new ArrayList();
        
        if (c.moveToFirst())
        {
            do {
            	int id = c.getInt(0);
            	String nm = c.getString(1);
            	Log.d("From App","Name="+nm);
                placesList.add(nm);
                placeIdList.add(id);
               
            } while (c.moveToNext());
        }
        db.close();		
		placesListView = (ListView) findViewById(R.id.lstPlaces);
		listAdapter = new ArrayAdapter<String>(PlacesActivity.this, android.R.layout.simple_list_item_1, placesList);        
		placesListView.setAdapter(listAdapter); 
		registerForContextMenu(placesListView);
		placesListView.setOnItemClickListener(new OnItemClickListener() {
		        
				public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
					 db.open();
	                    
	                    int idnew = (Integer)placeIdList.get(position);
	                        
	                    db.close();  	
					Intent intent = new Intent(PlacesActivity.this, PlaceDetailActivity.class);
					intent.putExtra("id", idnew); 
				      startActivity(intent);
			        
			    }					
						
				
		        });
		
		
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
	        MenuItem mnu0 = menu.add(0, 0, 0, "Add");
	        {
	            mnu0.setIcon(R.drawable.add_icon);
	            mnu0.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
	        }
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
	    		 	Intent intent0 = new Intent(PlacesActivity.this, PlaceAddActivity.class);
					startActivity(intent0);
		            return true;
	    	 case 1:
	    		 	Intent intent1 = new Intent(PlacesActivity.this, HomeActivity.class);
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
	    public void onCreateContextMenu(ContextMenu menu, View view,
	    ContextMenuInfo menuInfo)
	    {
	         super.onCreateContextMenu(menu, view, menuInfo);
	         MenuItem mnu0 = menu.add(0, 0, 0, "Update");
		        {
		            mnu0.setIcon(R.drawable.add_icon);
		            
		        }
		        MenuItem mnu1 = menu.add(0, 1, 1, "Delete");
		        {
		            mnu1.setIcon(R.drawable.home_icon);
		            
		        }
	    }
	    
	    @Override
	    public boolean onContextItemSelected(MenuItem item)
	    {
	    	AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
            final int pos = info.position;
	    	
	    	switch (item.getItemId()) {
	    	 case 0:
	    		 db.open();
                 int idnew = (Integer)placeIdList.get(pos);
                 db.close(); 
	    		 	Intent intent0 = new Intent(PlacesActivity.this, PlaceUpdateActivity.class);
	    		 	intent0.putExtra("id", idnew);
					startActivity(intent0);
		            return true;
	    	 case 1:
	    		 new AlertDialog.Builder(PlacesActivity.this).setTitle("Delete Place").setMessage("Are you sure you want to delete this place?")
                 .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface d, final int i) {
                    	AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(PlacesActivity.this); 
                   	dlgAlert.setMessage("Place Deleted"); 
                   	dlgAlert.setTitle("Delete Place"); 
                   	dlgAlert.setPositiveButton("OK", new DialogInterface.OnClickListener() { 
                           public void onClick(DialogInterface dialog, int which) { 
                        	   db.open();
                               
                               if (db.deletePlace((Integer)placeIdList.get(pos)))
                                   Toast.makeText(PlacesActivity.this, "Delete successful.", Toast.LENGTH_LONG).show();
                               else
                                   Toast.makeText(PlacesActivity.this, "Delete failed.", Toast.LENGTH_LONG).show();
                               db.close();  	   
                           	startActivity(new Intent(PlacesActivity.this, PlacesActivity.class));   
                             } 
                         });  
                   	dlgAlert.setCancelable(true); 
                   	dlgAlert.create().show(); 
                   	

                    }
                 }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface d, final int i) {
                    }
                 }).show();
	        
	        
	        
	    }
	    	return false;
	    }
	
    
}
