package com.fonsview.dbprogramming;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Menu;

public class SharedPreferencesExample extends Activity {

	private static final String MY_DB = "my_db";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shared_preferences_example);
		
		SharedPreferences sp = getSharedPreferences(MY_DB,
		Context.MODE_PRIVATE);
		/**
		* CHECK IF THIS IS USER'S FIRST VISIT
		*/
		boolean hasVisited = sp.getBoolean("hasVisited",false);
		if (!hasVisited) {
			// ...
			// SHOW SPLASH ACTIVITY, LOGIN ACTIVITY, ETC
			// ...
			// DON'T FORGET TO COMMIT THE CHANGE!
			Editor e = sp.edit();
			e.putBoolean("hasVisited", true);
			e.commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.shared_preferences_example, menu);
		return true;
	}

}
