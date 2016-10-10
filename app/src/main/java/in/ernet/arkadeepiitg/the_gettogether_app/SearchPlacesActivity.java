package in.ernet.arkadeepiitg.the_gettogether_app;



import java.util.List;








import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class SearchPlacesActivity extends AppCompatActivity {
	
	private EditText searchKeyword;
	
	/** Called when the activity is first created. */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_places);
		searchKeyword = (EditText) findViewById(R.id.search_keyword);
		
	}
	
	/** Called when the Search button is clicked. */
	public void onClick(View view)
	{
		try {
   		 Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
            String keyword = searchKeyword.getText().toString();
            intent.putExtra(SearchManager.QUERY, keyword);
            startActivity(intent);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}	

	
    
}
