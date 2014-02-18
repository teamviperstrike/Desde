package com.teamviperstrike.desde;


import java.util.Arrays;

import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;

import com.facebook.widget.LoginButton;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/********************************************************
 * 
 * @author teamviperstrike
 * @date 02/15/2014
 * @version 1.0.2
 * @see be a decent human. don't steal code, credit the author.
 *
 ********************************************************/

public class MainFragment extends Fragment {
	private UiLifecycleHelper uiHelper;
	
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, 
	        ViewGroup container, 
	        Bundle savedInstanceState) {
		// Setup view, add textView, set typeface, add Login Button, set permissions
	    View view = inflater.inflate(R.layout.activity_fbconnect_menu, container, false);
	    TextView goSocial = (TextView) view.findViewById(R.id.textView1);
	    Typeface jsLight = Typeface.createFromAsset(getActivity().getAssets(),"JosefinSlab-Light.ttf"); 
	    goSocial.setTypeface(jsLight);
	    LoginButton authButton = (LoginButton) view.findViewById(R.id.authButton);
	    authButton.setPublishPermissions(Arrays.asList("publish_actions", "publish_stream"));
	    authButton.setFragment(this);

	    return view;
	}
	
	
	// Unused. Available for layout changes based on authentication.
	private void onSessionStateChange(Session session, SessionState state, Exception exception) {
	    if (state.isOpened()) {	
	        
	    } else if (state.isClosed()) {
	    	
	    }
	}
	

	private Session.StatusCallback callback = new Session.StatusCallback() {
	    @Override
	    public void call(Session session, SessionState state, Exception exception) {
	        onSessionStateChange(session, state, exception);
	    }
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    uiHelper = new UiLifecycleHelper(getActivity(), callback);
	    uiHelper.onCreate(savedInstanceState);
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

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	    super.onActivityResult(requestCode, resultCode, data);
	    uiHelper.onActivityResult(requestCode, resultCode, data);
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

}
