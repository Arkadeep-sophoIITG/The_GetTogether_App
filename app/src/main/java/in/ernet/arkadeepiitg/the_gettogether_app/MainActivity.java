package in.ernet.arkadeepiitg.the_gettogether_app;



import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity{

String msg = "GetTogetherActivityLC";
	
	/** Called when the activity is first created. */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Log.d(msg, "OnCreate called");
	}

	/** Called when the Launch button is clicked. */
	public void onClick(View view)
	{
		Intent i = new Intent("in.ernet.arkadeepiitg.the_gettogether_app.HomeActivity");
		startActivity(i);
		
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_about) {
			Intent intent = new Intent(this, About.class);
			startActivity(intent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	/** Called when the activity is about to become visible. */
	protected void onStart()
	{
		super.onStart();
		Log.d(msg, "OnStart called");
		
	}
	
	/** Called when the activity has become visible. */
	protected void onResume()
	{
		super.onResume();
		Log.d(msg, "OnResume called");
		
	}	
	
	/** Called when the another activity is getting focus. */
	protected void onPause()
	{
		super.onPause();
		Log.d(msg, "OnPause called");
		
	}
	
	/** Called when the activity is no longer visible. */
	protected void onStop()
	{
		super.onStop();
		Log.d(msg, "OnStop called");
		
	}
	
	/** Called just before the activity is destroyed. */
	protected void onDestroy()
	{
		super.onDestroy();
		Log.d(msg, "OnDestroy called");
		
	}
	
	
    
}
