package com.teamviperstrike.desde;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

/********************************************************
 * 
 * @author teamviperstrike
 * @date 02/15/2014
 * @version 1.0.1
 * @see be a decent human. don't steal code, credit the author.
 *
 ********************************************************/
public class SettingsMenu extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settingsmenu_menu);
		
		
		//Intents
		final Intent sIntent = new Intent(this, ConnectFacebook.class);
		
		//Instantiate Objects, set params
		Button settingsButton = (Button) findViewById(R.id.buttonNotify);
		settingsButton.setBackgroundResource((R.drawable.buttonbgs));
		
		// Onclick Listener
		settingsButton.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v){
				startActivity(sIntent);
				finish();
			}
		});
		
		
	}

	 
	 @Override
	 public boolean onKeyDown(int keyCode, KeyEvent event)
	 {
	     if ((keyCode == KeyEvent.KEYCODE_BACK))
	     {
	    	 Intent mIntent = new Intent(this, MainMenu.class);
	    	 startActivity(mIntent);
	         finish();
	     }
	     return super.onKeyDown(keyCode, event);
	 }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_menu, menu);
		
		
		return true;
	}
		
}


