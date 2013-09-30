package com.example.mysecondeapp;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.example.mysecondeapp.FeedReaderContract.FeedEntry;
import com.example.mysecondeapp.FeedReaderContract.FeedEntryBean;

public class MainActivity extends Activity {

	 private TextView mTableSize;
	 private int tableSize;
	 private TextView mTableDataList;
	 private List<FeedEntryBean> tableDataList;
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//printData();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void saveSharedPreferencesdata(){
		Context context = getApplicationContext();
		SharedPreferences sharedPref = context.getSharedPreferences(
		        getString(R.string.preference_file_key), Context.MODE_PRIVATE);
		
		SharedPreferences.Editor editor = sharedPref.edit();
		editor.putInt(getString(R.string.saved_high_score), 60);
		editor.commit();
		
		int defaultValue = getResources().getInteger(R.string.saved_high_score_default);
		long highScore = sharedPref.getInt(getString(R.string.saved_high_score), defaultValue);
		mTableSize = (TextView)findViewById(R.id.table_size);
		mTableSize.setText(highScore+"");
	}
	
	public void addData(View v){
		Context context = getApplicationContext();
		FeedReaderDbHelper mDbHelper = new FeedReaderDbHelper(context);
		
		// Gets the data repository in write mode
		SQLiteDatabase db = mDbHelper.getWritableDatabase();
		// Create a new map of values, where column names are the keys
		ContentValues values = new ContentValues();
		values.put(FeedEntry.COLUMN_NAME_ENTRY_ID, queryTableSize()+1);
		values.put(FeedEntry.COLUMN_NAME_TITLE, queryTableSize()+1+"'title");
		values.put(FeedEntry.COLUMN_NAME_CONTENT, queryTableSize()+1+"'content");
		
		// Insert the new row, returning the primary key value of the new row
		db.insert(
		         FeedEntry.TABLE_NAME,
		         null,
		         values);
		printData();
	}
	
	public void queryData(View v){
		printData();
	}
	
	public List<FeedEntryBean> queryData(){
		Context context = getApplicationContext();
		FeedReaderDbHelper mDbHelper = new FeedReaderDbHelper(context);
		
		// Gets the data repository in write mode
		SQLiteDatabase db = mDbHelper.getWritableDatabase();
		
		// Define a projection that specifies which columns from the database
		// you will actually use after this query.
		String[] projection = {
		    FeedEntry.COLUMN_NAME_ENTRY_ID,
		    FeedEntry.COLUMN_NAME_TITLE,
		    FeedEntry.COLUMN_NAME_CONTENT
		    };
		
		
		// How you want the results sorted in the resulting Cursor
		String sortOrder =
		    FeedEntry.COLUMN_NAME_TITLE + " DESC";
		String selection = "";
		String[] selectionArgs = {};
		Cursor cursor = db.query(
		    FeedEntry.TABLE_NAME,  // The table to query
		    projection,                               // The columns to return
		    selection,                                // The columns for the WHERE clause
		    selectionArgs,                            // The values for the WHERE clause
		    null,                                     // don't group the rows
		    null,                                     // don't filter by row groups
		    sortOrder                                 // The sort order
		    );
		
		cursor.moveToFirst();
		List<FeedEntryBean> list = new ArrayList<FeedEntryBean>();
		while (!cursor.isAfterLast()) {
			FeedReaderContract f = new FeedReaderContract();
			FeedEntryBean ent =  f.new FeedEntryBean();
			String entryId = cursor.getString(
				    cursor.getColumnIndexOrThrow(FeedEntry.COLUMN_NAME_ENTRY_ID)
				);
			ent.setEntryId(entryId);
			String itemTitle = cursor.getString(
				    cursor.getColumnIndexOrThrow(FeedEntry.COLUMN_NAME_TITLE)
				);
			ent.setTitle(itemTitle);
			String itemContent = cursor.getString(
				    cursor.getColumnIndexOrThrow(FeedEntry.COLUMN_NAME_CONTENT)
				);	
			ent.setContent(itemContent);
			list.add(ent);
			cursor.moveToNext();
		}
		return list;   
	}
	
	public void updateData(View v){
		Context context = getApplicationContext();
		FeedReaderDbHelper mDbHelper = new FeedReaderDbHelper(context);
		
		// Gets the data repository in write mode
		SQLiteDatabase db = mDbHelper.getWritableDatabase();
		
		// New value for one column
		ContentValues values = new ContentValues();
		values.put(FeedEntry.COLUMN_NAME_TITLE, "updated title");

		// Which row to update, based on the ID
		String selection = FeedEntry.COLUMN_NAME_ENTRY_ID + " LIKE ?";
		int rowId = 1;
		String[] selectionArgs = { String.valueOf(rowId) };

		db.update(
		    FeedEntry.TABLE_NAME,
		    values,
		    selection,
		    selectionArgs);
		printData();
	}
	
	public void delData(View v){
		Context context = getApplicationContext();
		FeedReaderDbHelper mDbHelper = new FeedReaderDbHelper(context);
		
		// Gets the data repository in write mode
		SQLiteDatabase db = mDbHelper.getWritableDatabase();
		
		// Define 'where' part of query.
		String selection = FeedEntry.COLUMN_NAME_ENTRY_ID + " LIKE ?";
		// Specify arguments in placeholder order.
		int rowId = 1;
		String[] selectionArgs = { String.valueOf(rowId) };
		// Issue SQL statement.
		db.delete(FeedEntry.TABLE_NAME, selection, selectionArgs);
		printData();
	}
	
	public int queryTableSize(){
		Context context = getApplicationContext();
		FeedReaderDbHelper mDbHelper = new FeedReaderDbHelper(context);
		
		// Gets the data repository in write mode
		SQLiteDatabase db = mDbHelper.getWritableDatabase();
		
		String[] projection = {
		    FeedEntry.COLUMN_NAME_ENTRY_ID,
		    FeedEntry.COLUMN_NAME_TITLE,
		    FeedEntry.COLUMN_NAME_CONTENT
		    };
		
		
		// How you want the results sorted in the resulting Cursor
		Cursor cursor = db.query(
		    FeedEntry.TABLE_NAME,  // The table to query
		    projection,                               // The columns to return
		    null,                                // The columns for the WHERE clause
		    null,                            // The values for the WHERE clause
		    null,                                     // don't group the rows
		    null,                                     // don't filter by row groups
		    null                                 // The sort order
		    );
		
		return cursor.getCount();
		
	}
	
	public void printData() {
		tableSize = queryTableSize();
		tableDataList = queryData();
		
		StringBuilder sb = new StringBuilder();
		String s = "id \t| title \t| content\t"; 
		sb.insert(0, s + "\r\n");
        for (FeedEntryBean ent : tableDataList) {
            String sa = ent.getEntryId()+" \t "+ent.getTitle()+" \t"+ent.getContent()+"\t"; 
        	sb.insert(0, sa + "\r\n");
        }
        
        mTableSize = (TextView)findViewById(R.id.table_size_value);
        mTableSize.setText(String.valueOf(tableSize));
        mTableDataList = (TextView)findViewById(R.id.table_data_list_value);
        mTableDataList.setText(sb.toString());
	}
}
