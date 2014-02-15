package com.teamviperstrike.desde;



import android.app.Activity;

import android.content.Intent;
import android.graphics.Typeface;
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

public class MyMilestones extends Activity{
	
	
	

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mymilestones_menu);
		final String MSTag = "MSTag";
		
		//Intents
		final Intent m1Intent = new Intent(this, MilestoneDetail.class);
		
		//Instantiate objects, typefaces
		final MSSQLiteHelper milestonesDB = new MSSQLiteHelper(this);
		final Button milestoneOne = (Button) findViewById(R.id.buttonMS1);
		final Button milestoneTwo = (Button) findViewById(R.id.buttonMS2);
		final Button milestoneThree = (Button) findViewById(R.id.buttonMS3);
		Typeface sItalic = Typeface.createFromAsset(getAssets(),"Sanchez-Italic.ttf");
		
		
		// Object params
		milestoneOne.setTag(milestonesDB.getID(1));
		milestoneOne.setTypeface(sItalic);
		milestoneTwo.setTag(milestonesDB.getID(2));
		milestoneTwo.setTypeface(sItalic);
		milestoneThree.setTag(milestonesDB.getID(3));
		milestoneThree.setTypeface(sItalic);
		milestoneOne.setText(milestonesDB.getName(1));
		milestoneOne.setBackgroundResource((R.drawable.buttonbgs));
		milestoneTwo.setText(milestonesDB.getName(2));
		milestoneTwo.setBackgroundResource((R.drawable.buttonbgs));
		milestoneThree.setText(milestonesDB.getName(3));
		milestoneThree.setBackgroundResource((R.drawable.buttonbgs));
		
		/***************************************/
		/*			Add New Button. Not included in v1.0.1
		final Button addNew = (Button) findViewById(R.id.handle);
		addNew.setTypeface(sItalic);

		final EditText textTitle = (EditText) findViewById(R.id.editTextTitle);
		textTitle.setOnEditorActionListener(new OnEditorActionListener()  
		 {  
			 @SuppressWarnings("deprecation")
			@Override  
			 public boolean onEditorAction(TextView arg0, int arg1, KeyEvent arg2)  
			 {  
			       SlidingDrawer slider = (SlidingDrawer) findViewById(R.id.slidingDrawer1);
			       slider.close();
			      //TODO Implement the following:
			       		// Fragments hinge off number of database records
			       		// See PennySaved for Fragment Implementation, adding a new tab per database record
			       		// new SQL method getCount() for number of records in DB
			       		// show fragments based on that number. swipe fragment implementation using same layout
			      if (textTitle.getText().toString() != null && !textTitle.getText().toString().isEmpty()) {  
			           milestonesDB.insertRecord(textTitle.getText().toString());
			      }  
			       return false;  
			   }  
			 });*/

	
		// Set onclick listeners
		milestoneOne.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
		    	
		    	m1Intent.putExtra(MSTag, milestoneOne.getTag().toString());
		    	
		    	startActivity(m1Intent);
		    	finish();
		    	
		    }

		});
		
		milestoneTwo.setOnClickListener(new Button.OnClickListener() {
		    public void onClick(View v) {
		    	
		    	m1Intent.putExtra(MSTag, milestoneTwo.getTag().toString());
		    	startActivity(m1Intent);
		    	finish();
		    	
		    }

		});
		
		milestoneThree.setOnClickListener(new Button.OnClickListener() {
		    public void onClick(View v) {
		    	
		    	m1Intent.putExtra(MSTag, milestoneThree.getTag().toString());
		    	startActivity(m1Intent);
		    	finish();
		    	
		    }

		});

		
		
	}
	
	

	 // Back button push
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

	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_menu, menu);
		
		
		return true;
	}

}
