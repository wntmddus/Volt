package com.example.volt;

import java.lang.reflect.InvocationTargetException;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class DataToggleReceiver extends BroadcastReceiver{

        public final static String ACTION_FIRST_WAIT = "com.example.volt.intent.action.ACTION_FIRST_WAIT";
        public final static String ACTION_TURN_OFF = "com.example.volt.intent.action.TURN_OFF";
        public final static String ACTION_TURN_ON = "com.example.volt.intent.action.TURN_ON";
        //public static boolean RECEIVED_SIGNAL_ON = false;
        //public static boolean RECEIVED_SIGNAL_OFF = false;
        public static boolean turn_switch = false; //false means its a turn to turn off; true means its a turn to turn on
        public static boolean wait_is_in_progress = false;
        @Override
        public void onReceive(Context arg0, Intent arg1) {
                if(arg1.getAction() == ACTION_TURN_ON)
                {
                        //RECEIVED_SIGNAL_ON = true;
                        //RECEIVED_SIGNAL_OFF = false;
                        Log.d("DataToggleReceiver", "I am inside ACTION_TURN_ON");
                        try {
                                //if(AppWatcherService.mdt.isMobileDataEnabled())
                                //{
                                        AppWatcherService.mdt.toggleMobileData(true);
                                //}
                        } catch (SecurityException e) {
                                e.printStackTrace();
                        } catch (IllegalArgumentException e) {
                                e.printStackTrace();
                        } catch (NoSuchMethodException e) {
                                e.printStackTrace();
                        } catch (IllegalAccessException e) {
                                e.printStackTrace();
                        } catch (InvocationTargetException e) {
                                e.printStackTrace();
                        } catch (ClassNotFoundException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                        } catch (NoSuchFieldException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                        }
                        wait_is_in_progress = false;
                        Log.d("DataToggleReceiver", "I am inside ACTION_TURN_ON:EXECUTED");
                }
                else if(arg1.getAction() == ACTION_TURN_OFF)
                {
                        //RECEIVED_SIGNAL_ON = false;
                        //RECEIVED_SIGNAL_OFF = true;
                        Log.d("DataToggleReceiver", "I am inside ACTION_TURN_OFF");
                        try {
                                if(AppWatcherService.mdt.isMobileDataEnabled())
                                {
                                        AppWatcherService.mdt.toggleMobileData(false);
                                }
                        } catch (SecurityException e) {
                                e.printStackTrace();
                        } catch (IllegalArgumentException e) {
                                e.printStackTrace();
                        } catch (NoSuchMethodException e) {
                                e.printStackTrace();
                        } catch (IllegalAccessException e) {
                                e.printStackTrace();
                        } catch (InvocationTargetException e) {
                                e.printStackTrace();
                        } catch (ClassNotFoundException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                        } catch (NoSuchFieldException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                        }
                        wait_is_in_progress = false;
                        Log.d("DataToggleReceiver", "I am inside ACTION_TURN_OFF:EXECUTED");
                }
        }
}
