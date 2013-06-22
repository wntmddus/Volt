package com.example.volt;

import java.util.ArrayList;
import java.util.List;

import android.app.ActivityManager;
import android.app.IntentService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;

public class AppWatcherService extends IntentService {
  
	public static final String AWS_OUT_MSG = "omsg";
	private List<ActivityManager.RunningTaskInfo> list;
	public static ArrayList<String> list_val;
	
	public AppWatcherService() {
		super("AppWatcherService");
	}

	@Override
    protected void onHandleIntent(Intent workIntent) {
		
		//Get a list of running tasks and extract out the current top activity.
		ActivityManager am = (ActivityManager)this.getSystemService(Context.ACTIVITY_SERVICE);
		list_val = new ArrayList<String>();
		String top_act_str;
		String temp = "1234"; //random string initialized 

		while(true)
		{
	        list = am.getRunningTasks(1);
	        ComponentName top_activity = list.get(0).topActivity;
	        top_act_str = top_activity.toString();
	        top_act_str = makeItPretty(top_act_str);
	        Log.d("STUPID_DANNY","onHandleIntentStartWhileLoop");
	        if(!(temp.compareTo(top_act_str) == 0))
	        {
	        	list_val.add(attachTimeStamp(top_act_str));
	        	temp = top_act_str;
	        }
	        SystemClock.sleep(5000);
		}
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
