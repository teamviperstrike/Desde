package com.teamviperstrike.desde;


/********************************************************
 * 
 * @author teamviperstrike
 * @date 02/15/2014
 * @version 1.0.1
 * @see be a decent human. don't steal code, credit the author.
 *
 ********************************************************/


import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphObject;
import com.facebook.model.OpenGraphAction;
import com.facebook.model.OpenGraphObject;
import com.facebook.widget.FacebookDialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Scroller;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class MilestoneFragment extends Fragment implements OnClickListener {
	
	MSSQLiteHelper milestonesDB = new MSSQLiteHelper(getActivity());
	

	
	
	
	
    String milestoneTag = null;
    final String MSTag = "MSTag";
    private UiLifecycleHelper uiHelper;
    
    Session.StatusCallback callback = new Session.StatusCallback(){
    	@Override
    	public void call(Session session, SessionState state, Exception exception){
    		onSessionStateChange(session, state, exception);
    	}
    };
	


	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState){
		ViewGroup rootView = (ViewGroup) inflater.inflate(
				R.layout.activity_milestone_menu, container, false);
		
		Bundle bundle = this.getArguments();
		int currentTag = bundle.getInt(MSTag);
		
		uiHelper = new UiLifecycleHelper(getActivity(), callback);
		uiHelper.onCreate(savedInstanceState);
		

		
		// Instantiate objects and typefaces
		Typeface jsLight = Typeface.createFromAsset(getActivity().getAssets(),"JosefinSlab-SemiBold.ttf"); 
		Typeface sItalic = Typeface.createFromAsset(getActivity().getAssets(),"Sanchez-Italic.ttf");
		final TextView displayText = (TextView) rootView.findViewById(R.id.textViewTime);
		Button startButton = (Button) rootView.findViewById(R.id.buttonStart);
		EditText mNotes = (EditText) rootView.findViewById(R.id.editTextNotes);
		Button resetButton = (Button) rootView.findViewById(R.id.buttonReset);
		final Switch notifications = (Switch) rootView.findViewById(R.id.switch1);
		final Switch facebook = (Switch) rootView.findViewById(R.id.switch2);
		EditText titleText = (EditText) rootView.findViewById(R.id.editTextTitle);
		
		// Set typefaces
		titleText.setTypeface(jsLight);
		displayText.setTypeface(jsLight);
		startButton.setTypeface(sItalic);
		resetButton.setTypeface(sItalic);
		notifications.setTypeface(sItalic);
		
		// Set savedDate = getDate(mID)
				// Update view every second to count up.
				// If  mStart is NULL, set to 0s
				final String savedDate = milestonesDB.getDate(currentTag);
				if(milestonesDB.checkDate(currentTag)){
					Thread t = new Thread() {
					@Override
					 public void run(){
						try{
							while(!isInterrupted()){
								Thread.sleep(1000);
								getActivity().runOnUiThread(new Runnable() {
									@Override
									public void run(){
										displayText.setText(milestonesDB.getTimeLength(savedDate));
										Typeface jsLight = Typeface.createFromAsset(getActivity().getAssets(),"JosefinSlab-Light.ttf");
										displayText.setTypeface(jsLight);
									}
								});
							}
						}catch (InterruptedException e){
						}
					}
					};
					
					t.start();
				
			
				}else{
					displayText.setText("0 days : 0 hours : 0 minutes : 0 seconds");
					displayText.setTypeface(jsLight);
				}
				
				// Set object params
				titleText.setText(milestonesDB.getName(currentTag));
		        mNotes.setTypeface(sItalic);
		        mNotes.setScroller(new Scroller(getActivity())); 
		        mNotes.setMaxLines(4); 
		        mNotes.setVerticalScrollBarEnabled(true); 
		        mNotes.setMovementMethod(new ScrollingMovementMethod());
		        facebook.setVisibility(View.INVISIBLE);
		        notifications.setChecked(milestonesDB.checkNotify(currentTag));
		        notifications.setVisibility(View.INVISIBLE);
		        if(milestonesDB.checkNotes(currentTag)){
		        	mNotes.setText(milestonesDB.getNotes(currentTag));
		        }else if(!milestonesDB.checkNotes(currentTag)){
		        	mNotes.setText("");
		        	mNotes.setHint("Tap To Add Note");
		        }
		        
		        // Set OnClick listeners
		        startButton.setOnClickListener(this);
		        resetButton.setOnClickListener(this);
		
		
		
		
		
		
				
		
				return rootView;

	}
	
	 // Setup session check, only show Facebook switch if the user is logged in	
	  private void onSessionStateChange(Session session, SessionState state, Exception exception){
		  Bundle bundle = this.getArguments();
			final int currentTag = bundle.getInt(MSTag);
		  if(state.isOpened()){
			  
			  final Switch facebook = (Switch) getActivity().findViewById(R.id.switch2);
			  facebook.setVisibility(View.VISIBLE);
			  Typeface sItalic = Typeface.createFromAsset(getActivity().getAssets(),"Sanchez-Italic.ttf");
			  facebook.setTypeface(sItalic);
				facebook.setChecked(milestonesDB.checkFacebook(currentTag));
				facebook.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				       if(!isChecked){
				    	   
				    	   milestonesDB.updateFacebook(currentTag, false);
				    	   
				       }else{
				    	   
				    	   
				    	   shareDialogMethod();
				    	   milestonesDB.updateFacebook(currentTag, true);
				    	   
				       }
				    }
				});
			  
		  }else if(state.isClosed()){
			  final Switch facebook = (Switch) getActivity().findViewById(R.id.switch2);
			  facebook.setVisibility(View.INVISIBLE);
		  }	
		
	}
	  
	  // Setup facebook share dialog, sending only the Title
	  private void shareDialogMethod(){
		  Bundle bundle = this.getArguments();
			int currentTag = bundle.getInt(MSTag);
			OpenGraphObject milestone = OpenGraphObject.Factory.createForPost("viperstrike_desde:reach");
			milestone.setProperty("title", milestonesDB.getName(currentTag));
					
			
			OpenGraphAction reach = GraphObject.Factory.create(OpenGraphAction.class);
			reach.setProperty("milestone",milestone);
			
			@SuppressWarnings("deprecation")
			FacebookDialog shareDialog = new FacebookDialog.OpenGraphActionDialogBuilder(getActivity(), reach, "viperstrike_desde:reach", "milestone")
											.build();
			
			uiHelper.trackPendingDialogCall(shareDialog.present());
			
		}
	  
	  @Override
	  public void onResume() {
	      super.onResume();
	      Session session = Session.getActiveSession();
	      if (session != null &&
	             (session.isOpened() || session.isClosed()) ) {
	          onSessionStateChange(session, session.getState(), null);
	      }
	      uiHelper.onResume();
	  }
	  
	  // If user post fails, reset Facebook switch to 0, 'Off'
	  @Override
	  public void onActivityResult(int requestCode, int resultCode, Intent data) {
		  Bundle bundle = this.getArguments();
			final int currentTag = bundle.getInt(MSTag);
	      super.onActivityResult(requestCode, resultCode, data);
	      uiHelper.onActivityResult(requestCode, resultCode, data);
	      uiHelper.onActivityResult(requestCode, resultCode, data, new FacebookDialog.Callback() {
		        @Override
		        public void onError(FacebookDialog.PendingCall pendingCall, Exception error, Bundle data) {
		            
		            milestonesDB.updateFacebook(currentTag, false);
		        }

		        @Override
		        public void onComplete(FacebookDialog.PendingCall pendingCall, Bundle data) {
		            
		            String postId = FacebookDialog.getNativeDialogPostId(data);
		            
		            Context context = getActivity().getApplicationContext();
		            Toast toast = Toast.makeText(context, "Succes! ID: " + postId, Toast.LENGTH_SHORT);
		            toast.show();
		        }
		    });
	  }
	  
	  @Override
	  public void onPause() {
	      super.onPause();
	      uiHelper.onPause();
	  }

	  @Override
	  public void onDestroy() {
	      super.onDestroy();
	      uiHelper.onDestroy();
	  }

	  @Override
	  public void onSaveInstanceState(Bundle outState) {
	      super.onSaveInstanceState(outState);
	      uiHelper.onSaveInstanceState(outState);
	  }
	  
	  // Reset onClick Listener. Updates database. Restarts activity.
	public void onClick(View v) {
		Bundle bundle = this.getArguments();
		final int currentTag = bundle.getInt(MSTag);
		
		final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setMessage("This will erase all Milestone data!").setTitle("Reset Milestone");
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener(){
			public void onClick(DialogInterface dialog, int id){
				
				milestonesDB.updateNotify(currentTag, false);
				milestonesDB.updateFacebook(currentTag, false);
	    	    milestonesDB.updateClearStartTime(currentTag);
	    	    milestonesDB.resetNames(currentTag);
	    	    milestonesDB.updateNotes(currentTag, "");
	    	    getActivity().finish();
	    	    startActivity(getActivity().getIntent());

				
			}
		});
		
		// On 'Cancel', do nothing. Close dialog.
				builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
					public void onClick(DialogInterface dialog, int id){
						
						dialog.dismiss();
					}
				});
				
				builder.create();
				

		        switch(v.getId()) {
		        case R.id.buttonStart:
		        		milestonesDB.updateStartTime(currentTag);
		        		getActivity().finish();
		        		startActivity(getActivity().getIntent());
		            break;
		       case R.id.buttonReset:
		    	   builder.show();
		    	   break;
		 

		       }

	}
	
	 public boolean onKeyDown(int keyCode, KeyEvent event)
	 {
	     if ((keyCode == KeyEvent.KEYCODE_BACK))
	     {
	    	 Bundle bundle = this.getArguments();
	 		int currentTag = bundle.getInt(MSTag);
	    	 EditText titleText = (EditText) getActivity().findViewById(R.id.editTextTitle);
	    	 EditText mNotes = (EditText) getActivity().findViewById(R.id.editTextNotes);
	    	 Intent menuIntent = new Intent(getActivity(), MyMilestones.class);
	    	 if (titleText.getText().toString() != null && !titleText.getText().toString().isEmpty()) {
		    		milestonesDB.updateName(currentTag,titleText.getText().toString());
	    	 }
	    	 milestonesDB.updateNotes(currentTag, mNotes.getText().toString());
	    	   startActivity(menuIntent);
	    	   getActivity().finish();
	     }
	     return super.getActivity().onKeyDown(keyCode, event);
	 }

}
