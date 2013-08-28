package com.example.volt;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.RemoteViews;
import android.widget.TextView;


public class MainActivity extends Activity {

        private DrawerLayout mDrawerLayout;
        private ListView mDrawerList;
        private ActionBarDrawerToggle mDrawerToggle;
        MenuListAdapter mMenuAdapter;
        public static DataStore DS;
        String[] title;
        boolean on;
        RemoteViews remoteViews;
        ScreenWatchReceiver swr;
        DataToggleReceiver dtr;
        AnimationDrawable onOffAnimation;
        AnimationDrawable offOnAnimation;
        ImageView imageView1;
        RelativeLayout layout;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_main);
                DS = new DataStore(this);
                setup();
                initializeCurrentStatus();
                setButtonClickListener();       
                callImageButton();

                title = new String[] { " Wait X minutes before turning off data", 
                                "Turn on data every X minutes",
                                "Turn on data for X seconds"};

                mDrawerList = (ListView) findViewById(R.id.left_drawer);
                mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
                mMenuAdapter = new MenuListAdapter(this, title);

                mDrawerList.setAdapter(mMenuAdapter);

                mDrawerToggle = new ActionBarDrawerToggle(this, 
                                mDrawerLayout,
                                R.drawable.setting,
                                R.string.drawer_open,
                                R.string.drawer_close) {
                        public void onDrawerClosed(View view) {
                                super.onDrawerClosed(view);
                        }
                        public void onDrawerOpened(View drawerView) {
                                super.onDrawerOpened(drawerView);
                        }
                };

                getActionBar().setDisplayHomeAsUpEnabled(true);
                getActionBar().setHomeButtonEnabled(true);
                getActionBar().setDisplayShowHomeEnabled(true);

                mDrawerLayout.setDrawerListener(mDrawerToggle);
        }

        private void setup()
        {
                //Register ScreenWatchReceiver
                IntentFilter filter_screen_on = new IntentFilter(Intent.ACTION_SCREEN_ON);
                filter_screen_on.addAction(Intent.ACTION_SCREEN_OFF);
                swr = new ScreenWatchReceiver();
                registerReceiver(swr, filter_screen_on);
                //Register DataToggleReceiver
                IntentFilter dtr_filter = new IntentFilter(DataToggleReceiver.ACTION_FIRST_WAIT);
                dtr_filter.addAction(DataToggleReceiver.ACTION_TURN_OFF);
                dtr_filter.addAction(DataToggleReceiver.ACTION_TURN_ON);
                dtr = new DataToggleReceiver();
                registerReceiver(dtr,dtr_filter);
                
                imageView1 = (ImageView)findViewById(R.id.button1);
                layout = (RelativeLayout) findViewById(R.id.content_frame);
                
                
                on = false;
                DS.setBatterySavingModeEnabled(on);
                Log.d("MainActivity","Status of on:" + on);

        }

        @SuppressWarnings("deprecation")
        private void togglePictures() {
                Drawable image0;
                Drawable image1;
                if(on) {
                        image0 = getResources().getDrawable(R.drawable.on_1);
                        image1 = getResources().getDrawable(R.drawable.background_on);
                        
                        
                } else {
                        image0 = getResources().getDrawable(R.drawable.on_8);
                        image1 = getResources().getDrawable(R.drawable.background_off);
                        
                }
                imageView1.setBackgroundDrawable(image0);
                layout.setBackgroundDrawable(image1);
        }

        @SuppressWarnings("deprecation")
        private void initializeCurrentStatus() {
                
                Drawable image0;
                Drawable image1;
                if(DS.getBatterySavingModeEnabled() == true) {
                        image0 = getResources().getDrawable(R.drawable.on_1);
                        image1 = getResources().getDrawable(R.drawable.background_on);

                                
                } else {
                        image0 = getResources().getDrawable(R.drawable.on_8);
                        image1 = getResources().getDrawable(R.drawable.background_off);
                        
                }
                imageView1.setBackgroundDrawable(image0);
                layout.setBackgroundDrawable(image1);
        }

        private void setButtonClickListener() {
                final ImageButton threeGonOff = (ImageButton)findViewById(R.id.button1);
                threeGonOff.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                                on = DS.getBatterySavingModeEnabled();
                                if(on == false) {
                                        on = true; // change boolean on to true
                                        DS.setBatterySavingModeEnabled(on); // on value saved as true
                                        Intent intent = new Intent(MainActivity.this, AppWatcherService.class);
                                        startService(intent);
                                        AppWatcherService.isSwitchOff = false; //turns on the switch
                                        Log.d("MainActivity", "firstClick when on == false");

                                } else if(on == true) {
                                        on = false; // when true is received from on, on is switched to false
                                        Log.d("MainActivity", "secondClick when on == true");
                                        // turns off broadcast receiver
                                        DS.setBatterySavingModeEnabled(on); // on value saved as false by saving false 
                                        // in the memory
                                        AppWatcherService.list_val.clear();
                                        AppWatcherService.isSwitchOff = true; //turns off the switch

                                }
                                togglePictures(); // toggle turn off and turn on picture
                        }
                });
        }
        private void callImageButton() {
                TextView explanation = (TextView) findViewById(R.id.gototutorial);
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

                switch(item.getItemId()) {

                case android.R.id.home:
                        if(mDrawerLayout.isDrawerOpen(mDrawerList)) {
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
        protected void onRestart() {
                super.onRestart();
                initializeCurrentStatus();
        }

        protected void onResume(Bundle savedInstanceState) {
                super.onResume();
                initializeCurrentStatus();
        }

        protected void onDestroy() {
                super.onDestroy();
                AppWatcherService.list_val.clear();
                AppWatcherService.isSwitchOff = true; //turns off the switch
                DS.setBatterySavingModeEnabled(false);          
                unregisterReceiver(swr);
                unregisterReceiver(dtr);
        }
        

        @Override
        public void onConfigurationChanged(Configuration newConfig) {
                super.onConfigurationChanged(newConfig);
                // Pass any configuration change to the drawer toggles
                mDrawerToggle.onConfigurationChanged(newConfig);
        }
}
