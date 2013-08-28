package com.example.volt;

import java.lang.reflect.InvocationTargetException;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class ScreenWatchReceiver extends BroadcastReceiver {
        
        @Override
        public void onReceive(Context context, Intent intent) {
                if (Intent.ACTION_SCREEN_OFF == intent.getAction()) {
                        
                        Log.d("SCREENWATCHER_RECEIVER","Received ACTION_SCREEN_OFF");
                        AppWatcherService.isScreenOn = false;
                        
                        try {
                                if(!AppWatcherService.mdt.isMobileDataEnabled())
                                {
                                        Log.d("SCREENWATCHER_RECEIVER","Received isMobileDataEnabled = false");
                                        AppWatcherService.was_data_off = true;
                                }
                        } catch (SecurityException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                        } catch (IllegalArgumentException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                        } catch (NoSuchMethodException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                        } catch (IllegalAccessException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                        } catch (InvocationTargetException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                        }
                        
                        AppWatcherService.first_screen_on_off = true;
                
                        
                } else if (Intent.ACTION_SCREEN_ON == intent.getAction()) {
                        Log.d("SCREENWATCHER_RECEIVER","Received ACTION_SCREEN_ON");
                        AppWatcherService.isScreenOn = true;
                        AppWatcherService.first_screen_on_off = true; //when user turns on the screen it will reset the first_screen_on_off boolean to false to notify the first time the screen is off
                }
                
        }
}
