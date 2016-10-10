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
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

public class FriendAddActivity extends AppCompatActivity {

	String name, email, phone;
	EditText n, e, p;
	/**
	 * ATTENTION: This was auto-generated to implement the App Indexing API.
	 * See https://g.co/AppIndexing/AndroidStudio for more information.
	 */
	private GoogleApiClient client;

	/**
	 * Called when the activity is first created.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(in.ernet.arkadeepiitg.the_gettogether_app.R.layout.activity_add_friend);


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

		n = (EditText) findViewById(in.ernet.arkadeepiitg.the_gettogether_app.R.id.txtName);
		e = (EditText) findViewById(in.ernet.arkadeepiitg.the_gettogether_app.R.id.txtEmail);
		p = (EditText) findViewById(R.id.txtPhone);

		// ATTENTION: This was auto-generated to implement the App Indexing API.
		// See https://g.co/AppIndexing/AndroidStudio for more information.
		client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
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
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {

			case 1:
				Intent intent1 = new Intent(FriendAddActivity.this, HomeActivity.class);
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


	/**
	 * Called when the Add Friend button is clicked.
	 */
	public void onClick(View view) {
		DBAdapter db = new DBAdapter(this);


		name = n.getText().toString();
		email = e.getText().toString();
		phone = p.getText().toString();
		//---add a friend to the database---
		db.open();
		long id = db.insertFriend(name, email, phone);
		Log.d("From App", "insert=" + id);
		db.close();


		Toast.makeText(getBaseContext(), "Friend Added", Toast.LENGTH_SHORT).show();
		Intent i = new Intent("in.ernet.arkadeepiitg.the_gettogether_app.FriendsActivity");
		startActivity(i);

	}


	@Override
	public void onStart() {
		super.onStart();

		// ATTENTION: This was auto-generated to implement the App Indexing API.
		// See https://g.co/AppIndexing/AndroidStudio for more information.
		client.connect();
		Action viewAction = Action.newAction(
				Action.TYPE_VIEW, // TODO: choose an action type.
				"FriendAdd Page", // TODO: Define a title for the content shown.
				// TODO: If you have web page content that matches this app activity's content,
				// make sure this auto-generated web page URL is correct.
				// Otherwise, set the URL to null.
				Uri.parse("http://host/path"),
				// TODO: Make sure this auto-generated app URL is correct.
				Uri.parse("android-app://in.ernet.arkadeepiitg.the_gettogether_app/http/host/path")
		);
		AppIndex.AppIndexApi.start(client, viewAction);
	}

	@Override
	public void onStop() {
		super.onStop();

		// ATTENTION: This was auto-generated to implement the App Indexing API.
		// See https://g.co/AppIndexing/AndroidStudio for more information.
		Action viewAction = Action.newAction(
				Action.TYPE_VIEW, // TODO: choose an action type.
				"FriendAdd Page", // TODO: Define a title for the content shown.
				// TODO: If you have web page content that matches this app activity's content,
				// make sure this auto-generated web page URL is correct.
				// Otherwise, set the URL to null.
				Uri.parse("http://host/path"),
				// TODO: Make sure this auto-generated app URL is correct.
				Uri.parse("android-app://in.ernet.arkadeepiitg.the_gettogether_app/http/host/path")
		);
		AppIndex.AppIndexApi.end(client, viewAction);
		client.disconnect();
	}
}
