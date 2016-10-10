package in.ernet.arkadeepiitg.the_gettogether_app;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class HomeActivity extends AppCompatActivity {
	
	ListView homeOptionsList;
	ArrayAdapter<String> listAdapter;
	List listOfHomeOptions;
	 String[] homeOptions = {
	            "Manage Friends",
	            "Manage Get Togethers",
	            "Manage Places",
	            "Search Places","Translate for free"
	    };
	 
	 


	
	/** Called when the activity is first created. */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		homeOptionsList = (ListView) findViewById(R.id.lstHomeOptions);
		listAdapter = new ArrayAdapter<String>(HomeActivity.this, android.R.layout.simple_list_item_1, homeOptions);        
		homeOptionsList.setAdapter(listAdapter); 
		homeOptionsList.setOnItemClickListener(new OnItemClickListener() {
		        
				public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
					switch (position) {
			        case 0:
			        	Intent intent0 = new Intent(HomeActivity.this, FriendsActivity.class);
						startActivity(intent0);
			            break;
			        case 1:
			        	Intent intent1 = new Intent(HomeActivity.this, GetTogethersActivity.class);
						startActivity(intent1);
			            break;
			        case 2:
			        	Intent intent2 = new Intent(HomeActivity.this, PlacesActivity.class);
						startActivity(intent2);
			            break;
			        case 3:
			        	Intent intent3 = new Intent(HomeActivity.this, SearchPlacesActivity.class);
						startActivity(intent3);
			            break;

						case 4:
							Intent intent4 = new Intent(HomeActivity.this, BingTranslator.class);
							startActivity(intent4);
							break;
			        }
			        
			    }					
						
				
		        });
		
		
	}
	
	 @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
		 
	        super.onCreateOptionsMenu(menu);
	        menu.add(0, 0, 0, "Exit").setIcon(R.drawable.exit_icon).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
	        return true;
	    }
	    
	    @Override
	    public boolean onOptionsItemSelected(MenuItem item)
	    {
	    	switch (item.getItemId()) {
	        case 0:
	        	Intent intent = new Intent(Intent.ACTION_MAIN);
	        	intent.addCategory(Intent.CATEGORY_HOME);
	        	intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	        	startActivity(intent);
	            return true;
	        
	       
	    }
	    	 return false;
	    }
	    

	
    
}
