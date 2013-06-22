package com.example.volt;

import java.util.ArrayList;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

//import com.enspirit.data.toggle.MobileDataToggler;

/**
 * 
 * BroadcastReceiver that receives screen off and screen on events.
 * 
 * @author Seung Yeon Joo
 *
 */
public class ScreenWatchReceiver extends BroadcastReceiver {

  final public static String ONE_TIME = "onetime";
	//MobileDataToggler dataToggler;
	DataStore DS;
	AlarmManager am;
	PendingIntent pi1, pi2, pi3;
	Intent intent1, intent2, intent3;
	ArrayList<PendingIntent> intentArray;
	
	@Override
	public void onReceive(Context context, Intent intent) {
		//DS = new DataStore(context);
		//dataToggler = new MobileDataToggler(context);
		if (Intent.ACTION_SCREEN_OFF == intent.getAction()) {
			
			//dataToggler.toggleMobileData(true);
			// when screen off is received from intent, 3g is turned on
			TurnOnOff3GForXmins(context);
		} else if (Intent.ACTION_SCREEN_ON == intent.getAction()) {
			am.cancel(pi1);
			am.cancel(pi2);
			am.cancel(pi3);
			//dataToggler.toggleMobileData(true);
		}
		
	}
	public void TurnOnOff3GForXmins(Context context)
	{
		long i, i1;
		DS = new DataStore(context);
		//Turn off 3g after 10 mins
		am=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
		intentArray = new ArrayList<PendingIntent>();
		intent1 = new Intent(context, TurnOnOff3GReceiver.class);
		intent1.setAction("turn_off_3g");
		pi1 = PendingIntent.getBroadcast(context, 0, intent1, 0);
		i = System.currentTimeMillis() + 10 * 60 * 1000;
		am.set(AlarmManager.RTC_WAKEUP, i, pi1);
		intentArray.add(pi1);
		
		//Turn on 3G back on after 30 mins
		intent2 = new Intent(context, TurnOnOff3GReceiver.class);
		intent2.setAction("turn_on_3g");
		pi2 = PendingIntent.getBroadcast(context, 1, intent2, 0);
		am.set((int) i, i + (3 * i), pi2);
		intentArray.add(pi2);
		i1 = i + (3 * i);
		
		//Turn off 3G after 20 seconds
		intent3 = new Intent(context, TurnOnOff3GReceiver.class);
		intent3.setAction("turn_off_3g");
		pi3 = PendingIntent.getBroadcast(context, 2, intent3, 0);
		am.set((int) i1, i1 + 20000, pi3);
		intentArray.add(pi3);
		
		//End of alarm cycle
	}
}
