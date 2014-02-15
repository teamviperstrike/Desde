package com.teamviperstrike.desde;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;

/********************************************************
 * 
 * @author teamviperstrike
 * @date 02/15/2014
 * @version 1.0.1
 * @see be a decent human. don't steal code, credit the author.
 *
 ********************************************************/

public class ConnectFacebook extends FragmentActivity {

	private MainFragment mainFragment;

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);

	    if (savedInstanceState == null) {
	        // Add the fragment on initial activity setup
	        mainFragment = new MainFragment();
	        getSupportFragmentManager()
	        .beginTransaction()
	        .add(android.R.id.content, mainFragment)
	        .commit();

	    } else {
	        // Or set the fragment from restored state info
	        mainFragment = (MainFragment) getSupportFragmentManager()
	        .findFragmentById(android.R.id.content);
	    }
	    
	    
	    
	}
	
	
	// Destroy activity and initialize MainMenu
	 @Override
	 public boolean onKeyDown(int keyCode, KeyEvent event)
	 {
	     if ((keyCode == KeyEvent.KEYCODE_BACK))
	     {
	    	 final Intent mIntent = new Intent(this, MainMenu.class);
	    	 startActivity(mIntent);
	         finish();
	     }
	     return super.onKeyDown(keyCode, event);
	 }

	
}
