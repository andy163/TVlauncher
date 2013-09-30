package com.fonsview.dbprogramming;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

public class InternalStorageExample extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_internal_storage_example);
		
		// THE NAME OF THE FILE
		String fileName = "my_file.txt";
		// STRING TO BE WRITTEN TO FILE
		String msg = "Hello World.";
		try {
		// CREATE THE FILE AND WRITE
		FileOutputStream fos = openFileOutput(fileName,
		Context.MODE_PRIVATE);
		fos.write(msg.getBytes());
		fos.close();
		} catch (IOException e) {
		e.printStackTrace();
		}
		
		readFile();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.internal_storage_example, menu);
		return true;
	}

	public void readFile(){
		// THE NAME OF THE FILE
		String fileName = "my_file.txt";
		try {
			// OPEN FILE INPUT STREAM THIS TIME
			FileInputStream fis = openFileInput(fileName);
			InputStreamReader isr = new InputStreamReader(fis);
			// READ STRING OF UNKNOWN LENGTH
			StringBuilder sb = new StringBuilder();
			char[] inputBuffer = new char[2048];
			int l;
			// FILL BUFFER WITH DATA
			while ((l = isr.read(inputBuffer)) != -1) {
				sb.append(inputBuffer, 0, l);
			}
			// CONVERT BYTES TO STRING
			String readString = sb.toString();
			Log.i("LOG_TAG", "Read string: " + readString);
			// CAN ALSO DELETE THE FILE
			deleteFile(fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
