package com.example.volt;

import java.util.ArrayList;
import java.util.List;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;

import com.enspirit.data.toggle.MobileDataToggler;

public class AppWatcherService extends IntentService {
        
        public static final String AWS_OUT_MSG = "omsg";
        private List<ActivityManager.RunningTaskInfo> list;
        public static ArrayList<String> list_val = new ArrayList<String>();
        public static boolean isScreenOn = true; //to notify AppWathcerService from ScreenWatchReceiver if the screen is off or on
        public static boolean isSwitchOff = false;
        public static boolean first_screen_on_off = false;//when user turns on the screen it will reset the first_screen_on_off boolean to false to notify the first time the screen is off
        public static boolean was_data_off = false;//to see if the user has already turned off the data before turning off the screen
        final static private int ONE_SECOND = 1000;
        final static private int ONE_MINUTE = ONE_SECOND * 60;
        private int first_wait_min;
        private int delay;
        private int delay2;
        

        public static MobileDataToggler mdt;
        AlarmManager alm;
        ActivityManager am;
        PendingIntent pi_1, pi_on, pi_off;
        
        public AppWatcherService() {
                super("AppWatcherService");
        }

        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {
                super.onStartCommand(intent, flags, startId);
                
                return START_STICKY;
        }
        
        @SuppressWarnings({ "deprecation" })
        @Override
    protected void onHandleIntent(Intent workIntent) {
                
                //Get a list of running tasks and extract out the current top activity.
                am = (ActivityManager)this.getSystemService(Context.ACTIVITY_SERVICE);
                alm = (AlarmManager)this.getSystemService(Context.ALARM_SERVICE);
                DataToggleReceiver.turn_switch = false;
                DataToggleReceiver.wait_is_in_progress = false;
                
                try {
                        mdt = new MobileDataToggler(this);
                } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                } catch (SecurityException e) {
                        e.printStackTrace();
                } catch (IllegalAccessException e) {
                        e.printStackTrace();
                } catch (NoSuchFieldException e) {
                        e.printStackTrace();
                } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                }
                
                
                pi_1 = PendingIntent.getBroadcast(this, 0, new Intent(DataToggleReceiver.ACTION_FIRST_WAIT), 0);
                pi_on = PendingIntent.getBroadcast(this, 0, new Intent(DataToggleReceiver.ACTION_TURN_ON), 0);
                pi_off = PendingIntent.getBroadcast(this, 0, new Intent(DataToggleReceiver.ACTION_TURN_OFF), 0);

                String top_act_str;
                String temp = "1234"; //random string initialized
                //isSwitchOff = false;
                Log.d("AppWatcherService","onHandleIntentStart");
                //Foreground Service Initiation
                Notification note=new Notification(R.drawable.simple_icon,"Tap to turn off",System.currentTimeMillis());
                Intent i=new Intent(this, MainActivity.class);
                PendingIntent pi=PendingIntent.getActivity(this, 0,
                        i, 0);
                note.setLatestEventInfo(this, "Volt","Tap to turn off",pi);
                note.flags|=Notification.FLAG_NO_CLEAR;

                startForeground(1337, note);
                //
                
                while(true)
                {
                        if(isSwitchOff)
                        {
                                Log.d("AppWatcherService", "received isSwitchOff");
                                break;
                        }
                        else if(isScreenOn)
                        {
                                if(first_screen_on_off)
                                {
                                        Log.d("AppWatcherService", "Inside else if(isScreenOn) and first_screen_on_off");
                                        alm.cancel(pi_off);
                                        alm.cancel(pi_on);
                                        alm.cancel(pi_1);
                                        
                                        sendBroadcast(new Intent(DataToggleReceiver.ACTION_TURN_ON));
                                        first_screen_on_off = false;
                                }
                                
                        list = am.getRunningTasks(1);
                        ComponentName top_activity = list.get(0).topActivity;
                        top_act_str = top_activity.toString();
                        top_act_str = makeItPretty(top_act_str);
                       
                        if(!(temp.compareTo(top_act_str) == 0))
                        {
                                list_val.add(attachTimeStamp(top_act_str));
                                temp = top_act_str;
                        }
                        
                        //Log.d("AppWatcherService", list_val.get(list_val.size()-1));
                        SystemClock.sleep(ONE_SECOND * 3);
                        }
                        else if(!isScreenOn)
                        {
                                //Log.d("AppWatcherService", "received isScreenOn = false from AppWatchReceiver");
                                //Log.d("AppWatcherService", "first_screen_on_off status:" + first_screen_on_off);
                                if(!was_data_off)
                                {
                                        
                                        first_wait_min = MainActivity.DS.getSeekBarSetting1();
                                        delay  = MainActivity.DS.getSeekBarSetting2();
                                        delay2  = MainActivity.DS.getSeekBarSetting3();
                                        
                                        if(!DataToggleReceiver.wait_is_in_progress && !DataToggleReceiver.turn_switch)  
                                        {
                                                Log.d("AppWatcherService", "first_wait_min:" + first_wait_min);
                                                Log.d("AppWatcherService", "delay:" + delay);
                                                Log.d("AppWatcherService", "delay2:" + delay2);
                                                if(first_screen_on_off)
                                                {
                                                        Log.d("AppWatcherService", "if(!first_screen_on_off)");
                                                        alm.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + (ONE_MINUTE * first_wait_min), pi_off);
                                                        first_screen_on_off = false;
                                                }
                                                else
                                                {
                                                        Log.d("AppWatcherService", "else");
                                                        alm.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + (ONE_SECOND * delay2), pi_off);
                                                }
                                                DataToggleReceiver.wait_is_in_progress = true;
                                                DataToggleReceiver.turn_switch = true;
                                        }
                                        //}
                                        /*
                                        labelOfInnerLoop: while(!(!DataToggleReceiver.RECEIVED_SIGNAL_OFF && DataToggleReceiver.RECEIVED_SIGNAL_ON))
                                        {
                                                Log.d("AppWatcherService", "I am inside InnerLoop");
                                                SystemClock.sleep(ONE_SECOND*2);
                                            if(isScreenOn)
                                            {
                                                break labelOfInnerLoop;
                                            }
                                        }
                                        */
                                        
                                        //if(!isScreenOn)
                                        //{
                                        if(!DataToggleReceiver.wait_is_in_progress && DataToggleReceiver.turn_switch)
                                        {
                                                Log.d("AppWatcherService", "I am inside alm.set(pi_on)");
                                                alm.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + (ONE_MINUTE * delay), pi_on);
                                                DataToggleReceiver.wait_is_in_progress = true;
                                                DataToggleReceiver.turn_switch = false;
                                        }
                                        //}
                                        
                                        /*
                                        labelOfInnerLoop2: while(!(DataToggleReceiver.RECEIVED_SIGNAL_OFF && !DataToggleReceiver.RECEIVED_SIGNAL_ON))
                                        {
                                                Log.d("AppWatcherService", "I am inside InnerLoop3");
                                                SystemClock.sleep(ONE_SECOND*2);
                                            if(isScreenOn)
                                            {
                                                break labelOfInnerLoop2;
                                            }
                                        }
                                        */
                                }
                                SystemClock.sleep(ONE_SECOND * 3);                      
                        }
                }
                Log.d("AppWatcherService","Broke Out THE LOOP");
                stopForeground(true);
    }
        
        //takes an input of componentname.toString() and makes it look prettier
        private String makeItPretty(String s)
        {
                int index = s.lastIndexOf('.');
                s = s.substring(index+1,s.length()-1);
                return s;
        }
        private String attachTimeStamp(String s)
        {
                s = s + " Time: " + SystemClock.currentThreadTimeMillis();
                return s;
        }
}
