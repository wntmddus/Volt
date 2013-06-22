package com.example.volt;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

//import com.enspirit.data.toggle.MobileDataToggler;


public class TurnOnOff3GReceiver extends BroadcastReceiver {
  
	@Override
	public void onReceive(Context context, Intent intent) {
		//MobileDataToggler Toggler = new MobileDataToggler(context);
		if (intent.getAction() == "turn_off_3g") {
			// turn off 3g
			//Toggler.toggleMobileData(true);
		} else if (intent.getAction() == "turn_on_3g" ){
			// turn on 3g
			//Toggler.toggleMobileData(false);
		}	
	}

}
