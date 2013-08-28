package com.example.volt;

import android.content.Context;
import android.content.SharedPreferences;

public class DataStore {
        public static SharedPreferences prefForToggle, 
        prefForSeekBar1, 
        prefForSeekBar2, 
        prefForSeekBar3,
        prefForCheckBox1,
        prefForCheckBox2;
        
        public static final String DATA_STORE_NAME = "volt_data_store"; 
        public static final String BATTERY_SAVING_MODE_KEY = "BATTERY_SAVING_MODE_KEY";
        
        public static final String DATA_STORE_SETTING_NAME1 = "volt_setting_data_store1";
        public static final String SETTING_SAVE_MODE_KEY1 = "SETTING_SAVE_MODE_KEY1";

        public static final String DATA_STORE_SETTING_NAME2 = "volt_setting_data_store2";
        public static final String SETTING_SAVE_MODE_KEY2 = "SETTING_SAVE_MODE_KEY2";
        
        public static final String DATA_STORE_SETTING_NAME3 = "volt_setting_data_store3";
        public static final String SETTING_SAVE_MODE_KEY3 = "SETTING_SAVE_MODE_KEY3";
        
        public static final String DATA_STORE_SETTING_NAME4 = "volt_setting_data_store4";
        public static final String SETTING_SAVE_MODE_KEY4 = "SETTING_SAVE_MODE_KEY4";
        
        public static final String DATA_STORE_SETTING_NAME5 = "volt_setting_data_store5";
        public static final String SETTING_SAVE_MODE_KEY5 = "SETTING_SAVE_MODE_KEY5";
        
        public DataStore(Context context) {
                //This preference is for toggle function
                prefForToggle = context.getSharedPreferences(DATA_STORE_NAME, 0);
                prefForSeekBar1 = context.getSharedPreferences(DATA_STORE_SETTING_NAME1, 0);
                prefForSeekBar2 = context.getSharedPreferences(DATA_STORE_SETTING_NAME2, 0);
                prefForSeekBar3 = context.getSharedPreferences(DATA_STORE_SETTING_NAME3, 0);
                prefForCheckBox1 = context.getSharedPreferences(DATA_STORE_SETTING_NAME4, 0);
                prefForCheckBox2 = context.getSharedPreferences(DATA_STORE_SETTING_NAME5, 0);
                
        }
        
        public void setBatterySavingModeEnabled(boolean on) {
                SharedPreferences.Editor editor = prefForToggle.edit();
                editor.putBoolean(BATTERY_SAVING_MODE_KEY, on);
                editor.commit();
        }
        
        public boolean getBatterySavingModeEnabled() {
                return prefForToggle.getBoolean(BATTERY_SAVING_MODE_KEY, false);
        }
        
        public void setSeekBarSetting1(int time) {
                SharedPreferences.Editor editor = prefForSeekBar1.edit();
                editor.putInt(SETTING_SAVE_MODE_KEY1, time);
                editor.commit();
        }
        public int getSeekBarSetting1() {
                return prefForSeekBar1.getInt(SETTING_SAVE_MODE_KEY1, 20);
        }
        
        public void setSeekBarSetting2(int time) {
                SharedPreferences.Editor editor = prefForSeekBar2.edit();
                editor.putInt(SETTING_SAVE_MODE_KEY2, time);
                editor.commit();                
        }
        public int getSeekBarSetting2() {
                return prefForSeekBar2.getInt(SETTING_SAVE_MODE_KEY2, 20);
        }
        
        public void setSeekBarSetting3(int time) {
                SharedPreferences.Editor editor = prefForSeekBar3.edit();
                editor.putInt(SETTING_SAVE_MODE_KEY3, time);
                editor.commit();                
        }
        public int getSeekBarSetting3() {
                return prefForSeekBar3.getInt(SETTING_SAVE_MODE_KEY3, 20);
        }
        
        public void setCheckBoxSetting1(boolean check) {
                SharedPreferences.Editor editor = prefForCheckBox1.edit();
                editor.putBoolean(SETTING_SAVE_MODE_KEY4, check);
                editor.commit();
        }
        
        public boolean getCheckBoxSetting1() {
                return prefForCheckBox1.getBoolean(SETTING_SAVE_MODE_KEY4, false);
        }
        
        public void setCheckBoxSetting2(boolean check) {
                SharedPreferences.Editor editor = prefForCheckBox2.edit();
                editor.putBoolean(SETTING_SAVE_MODE_KEY5, check);
                editor.commit();
        }
        
        public boolean getCheckBoxSetting2() {
                return prefForCheckBox2.getBoolean(SETTING_SAVE_MODE_KEY5, false);
        }

}
