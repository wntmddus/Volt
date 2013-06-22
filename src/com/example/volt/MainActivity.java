package com.example.volt;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

public class MainActivity extends Activity {
  
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	MenuListAdapter mMenuAdapter;
	DataStore DS;

	

	String[] title;
	boolean on;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DS = new DataStore(this);
        setButtonClickListener();
        callImageButton();
        title = new String[] { " Wait X minutes before turning off data", 
        		"Turn on data every X minutes",
        		"Turn on data for X seconds",
        		"Don't interfere with messaging apps",
        		"Don't interfere with streaming apps"};
        
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        //mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        //Pass results to MenuListAdapter Class
        mMenuAdapter = new MenuListAdapter(this, title);
        
        mDrawerList.setAdapter(mMenuAdapter);
        
        mDrawerToggle = new ActionBarDrawerToggle(this, 
        		mDrawerLayout,
        		R.drawable.ic_drawer,
        		R.string.drawer_open,
        		R.string.drawer_close) {
        	public void onDrawerClosed(View view) {
        		super.onDrawerClosed(view);
        		//invalidateOptionsMenu();
        	}
        	public void onDrawerOpened(View drawerView) {
        		super.onDrawerOpened(drawerView);
        		//invalidateOptionsMenu();
        	}
        };
        
        mDrawerLayout.setDrawerListener(mDrawerToggle);
          

    }

	private void togglePictures() {
		ImageView imageView1 = (ImageView)findViewById(R.id.imageView2);
		ImageView imageView2 = (ImageView)findViewById(R.id.imageView1);
		ImageView imageView3 = (ImageView)findViewById(R.id.imageView3);
		
		Drawable phoneImage1;
		Drawable phoneImage2;
		Drawable phoneImage3;
		if(on) {
			phoneImage1 = getResources().getDrawable(R.drawable.ic_launcher);
			phoneImage2 = getResources().getDrawable(R.drawable.ic_launcher);
			phoneImage3 = getResources().getDrawable(R.drawable.ic_launcher);
		} else {
			phoneImage1 = getResources().getDrawable(R.drawable.ic_drawer);
			phoneImage2 = getResources().getDrawable(R.drawable.ic_drawer);
			phoneImage3 = getResources().getDrawable(R.drawable.ic_drawer);
		}
		imageView1.setImageDrawable(phoneImage1);
		imageView2.setImageDrawable(phoneImage2);
		imageView3.setImageDrawable(phoneImage3);
	}

    
    private void setButtonClickListener() {

		//first setBatterySavingModeEnabled to false in the memory
		Button threeGonOff = (Button)findViewById(R.id.button1);
		threeGonOff.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				on = DS.getBatterySavingModeEnabled();
				//3G turn on and off feature will be included in here
				if(on == false) {
					//usually when user hit toggle button for the very first time,
					//it will receive false from getBatterySavingModeEnabled();
					on = true; // change boolean on to true
					//toggleBatterySavingMode(on); // true value turns on broadcast receiver
					DS.setBatterySavingModeEnabled(on); // on value saved as true
					Intent intent = new Intent(MainActivity.this, AppWatcherService.class);
			    	startService(intent);
				} else {
					//Toggle.toggleMobileData(on);
					on = false; // when true is received from on, on is switched to false
					//toggleBatterySavingMode(on); // false value received from on will 
												// turns off broadcast receiver
					DS.setBatterySavingModeEnabled(on); // on value saved as false by saving false 
														// in the memory
					Intent intent = new Intent(MainActivity.this, AppWatcherService.class);
			    	AppWatcherService.list_val.clear();
			    	SystemClock.setCurrentTimeMillis(0);
			    	stopService(intent);
				}
				togglePictures(); // toggle turn off and turn on picture
								// in order to see current status
			}
		});
	}
    private void callImageButton() {
		ImageButton explanation = (ImageButton) findViewById(R.id.imageView3);
		explanation.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				startNewActivity();
			}
		});
    }
    
	private void startNewActivity() {
		Intent intent = new Intent(this, Explain.class);
		this.startActivity(intent);
	}
    


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
    	MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
 
        if (item.getItemId() == android.R.id.home) {
 
            if (mDrawerLayout.isDrawerOpen(mDrawerList)) {
                mDrawerLayout.closeDrawer(mDrawerList);
            } else {
                mDrawerLayout.openDrawer(mDrawerList);
            }
        }
 
        return super.onOptionsItemSelected(item);
    }
    
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }
 
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
}
