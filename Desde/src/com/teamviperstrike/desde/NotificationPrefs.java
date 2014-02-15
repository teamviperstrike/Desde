package com.teamviperstrike.desde;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

/********************************************************
 * 
 * @author teamviperstrike
 * @date 02/15/2014
 * @version 1.0.1
 * @see be a decent human. don't steal code, credit the author.
 * Class Not Used in current version
 *
 ********************************************************/

public class NotificationPrefs extends Activity {

	//DB helper, tag setup
	MSSQLiteHelper milestonesDB = new MSSQLiteHelper(this);
    String currentTag = null;
    final String MSTag = "MSTag";
    
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notify_menu);
		
		
		//Get Intent
		Bundle intent = getIntent().getExtras();
		currentTag = intent.getString("MSTag");
		
		//Instantiate Object
		Button saveButton = (Button) findViewById(R.id.button2);
		Button cancelButton = (Button) findViewById(R.id.button3);
		final RadioButton rDaily = (RadioButton) findViewById(R.id.radio0);
		final RadioButton rWeekly = (RadioButton) findViewById(R.id.radio1);
		final RadioButton rMonthly = (RadioButton) findViewById(R.id.radio2);
		final RadioButton rYearly = (RadioButton) findViewById(R.id.radio3);

		//Intents
		final Intent mIntent = new Intent(this, MilestoneDetail.class);
		
		
		//Save Button. This will change.
		saveButton.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v){
				
				if(rDaily.isChecked()){
					milestonesDB.updateInterval(Integer.parseInt(currentTag), 0);
					mIntent.putExtra(MSTag, currentTag);
					startActivity(mIntent);
					finish();
				}else if(rWeekly.isChecked()){
					milestonesDB.updateInterval(Integer.parseInt(currentTag), 1);
					mIntent.putExtra(MSTag, currentTag);
					startActivity(mIntent);
					finish();
				}else if(rMonthly.isChecked()){
					milestonesDB.updateInterval(Integer.parseInt(currentTag), 2);
					mIntent.putExtra(MSTag, currentTag);
					startActivity(mIntent);
					finish();
				}else if(rYearly.isChecked()){
					milestonesDB.updateInterval(Integer.parseInt(currentTag), 3);
					mIntent.putExtra(MSTag, currentTag);
					startActivity(mIntent);
					finish();
				}
				
			}
		});
		
		cancelButton.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v){
				
				milestonesDB.updateNotify(Integer.parseInt(currentTag), false);
				mIntent.putExtra(MSTag, currentTag);
				startActivity(mIntent);
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

}
