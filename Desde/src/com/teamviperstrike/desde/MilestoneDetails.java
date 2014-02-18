package com.teamviperstrike.desde;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;




public class MilestoneDetails extends FragmentActivity {
	
	 MSSQLiteHelper milestonesDB = new MSSQLiteHelper(this);
	 
	 
	
	/**
     * The number of pages (wizard steps) to show in this demo.
     */
	
	
	
	int count = milestonesDB.getCount();
    private final int NUM_PAGES = count;
    
   

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private ViewPager mPager;
    
    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private PagerAdapter mPagerAdapter;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_slide);
        
       

        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setPageTransformer(true, new ZoomOutPageTransformer());
        mPager.setTag(savedInstanceState);
        mPager.setAdapter(mPagerAdapter);
        
    }
    
    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
        	

            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
          
        } else {
            // Otherwise, select the previous step.
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }
    
    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
        	switch(position){
			case 0:
				MilestoneFragment fragment0 = new MilestoneFragment();
				Bundle bundle0 = new Bundle();
				bundle0.putInt("MSTag", 1);
				fragment0.setArguments(bundle0);
				return fragment0;
				
			case 1:
				MilestoneFragment fragment1 = new MilestoneFragment();
				Bundle bundle = new Bundle();
				bundle.putInt("MSTag", 2);
				fragment1.setArguments(bundle);
				return fragment1;
			case 2:
				MilestoneFragment fragment2 = new MilestoneFragment();
				Bundle bundle2 = new Bundle();
				bundle2.putInt("MSTag", 3);
				fragment2.setArguments(bundle2);
				return fragment2;
			default:
				return null;
			}
           
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }

}


