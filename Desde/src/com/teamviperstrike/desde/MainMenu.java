package com.teamviperstrike.desde;



import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;


/********************************************************
 * 
 * @author teamviperstrike
 * @date 02/15/2014
 * @version 1.0.1
 * @see be a decent human. don't steal code, credit the author.
 *
 ********************************************************/

public class MainMenu extends Activity {
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_menu);
		
		
		// Intents
		final Intent myMIntent = new Intent(this, MyMilestones.class);
		final Intent sIntent = new Intent(this, SettingsMenu.class);
		
		// Animation
		final Animation animationFadeIn = AnimationUtils.loadAnimation(this,R.anim.fadin);
		
		// Instantiate objects, typefaces end
		TextView appName = (TextView) findViewById(R.id.textView1);
		Button myMilestones = (Button) findViewById(R.id.button1);
		Button settingsButton = (Button) findViewById(R.id.button3);
		Typeface jsLight = Typeface.createFromAsset(getAssets(),"JosefinSlab-Light.ttf"); 
		Typeface sItalic = Typeface.createFromAsset(getAssets(),"Sanchez-Italic.ttf");
		
		// Set typefaces
		appName.setTypeface(jsLight);
		myMilestones.setTypeface(sItalic);
		settingsButton.setTypeface(sItalic);
		settingsButton.setBackgroundResource((R.drawable.buttonbgs));
		myMilestones.setBackgroundResource((R.drawable.buttonbgs));
		
		// Set animation
		myMilestones.startAnimation(animationFadeIn);
		settingsButton.startAnimation(animationFadeIn);
		
		
		// set OnClick Listeners
		myMilestones.setOnClickListener(new Button.OnClickListener() {
		    public void onClick(View v) {
		    	startActivity(myMIntent);
		    	finish();
		    }

		});
		
		settingsButton.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v){
				startActivity(sIntent);
				finish();
			}
		});
		
		
	}

	
	 // Destroy activity
	 @Override
	 public boolean onKeyDown(int keyCode, KeyEvent event)
	 {
	     if ((keyCode == KeyEvent.KEYCODE_BACK))
	     {
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
