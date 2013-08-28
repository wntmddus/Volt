package com.example.volt;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class MenuListAdapter extends BaseAdapter {

        Context context;
        String[] mTitle;
        LayoutInflater inflater;
        DataStore DS;
        int currentProgress, newProgressValue;

        public MenuListAdapter(Context context, String[] title) {
                this.context = context;
                this.mTitle = title;
        }
        @Override
        public int getCount() {
                // TODO Auto-generated method stub
                return mTitle.length;
        }

        @Override
        public Object getItem(int position) {
                // TODO Auto-generated method stub
                return mTitle[position];
        }

        @Override
        public long getItemId(int position) {
                // TODO Auto-generated method stub
                return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
                // TODO Auto-generated method stub
                final TextView txtTitle;
                DS = new DataStore(context);
                SeekBar seekbar;
                View itemView = null;
                if(position == 0) {
                        String x = "Wait " + Integer.toString(DS.getSeekBarSetting1()) + " minutes before turning off data";
                        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        itemView = inflater.inflate(R.layout.drawer_list_item0, parent, false);
                        txtTitle = (TextView) itemView.findViewById(R.id.title1);
                        txtTitle.setText(x);
                        txtTitle.setTextColor(0xff000000);
                        seekbar = (SeekBar) itemView.findViewById(R.id.seekbar1);
                        if(DS.getSeekBarSetting1() == 0)
                                currentProgress = 5;
                        else
                                currentProgress = DS.getSeekBarSetting1();

                        seekbar.setProgress(currentProgress);
                        seekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener()
                        {
                                public void onProgressChanged(SeekBar seekbar1, int progress, boolean fromUser) {
                                        // seekbar has a range between 0 to 100
                                        String x = "Wait " + Integer.toString(seekbar1.getProgress() + 1) + " minutes before turning off data";
                                        txtTitle.setText(x);
                                        txtTitle.setTextColor(0xff000000);

                                }
                                public void onStartTrackingTouch(SeekBar seekbar1) {

                                }
                                public void onStopTrackingTouch(SeekBar seekbar1) {

                                        newProgressValue = seekbar1.getProgress();
                                        currentProgress = newProgressValue;
                                        DS.setSeekBarSetting1(seekbar1.getProgress() + 1);
                                }
                        });
                } else if(position == 1) {
                        String x = "Turn on data every " + Integer.toString(DS.getSeekBarSetting2()) + " minutes";
                        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        itemView = inflater.inflate(R.layout.drawer_list_item1, parent, false);
                        txtTitle = (TextView) itemView.findViewById(R.id.title2);
                        txtTitle.setText(x);
                        txtTitle.setTextColor(0xff000000);
                        if(DS.getSeekBarSetting2() == 0)
                                currentProgress = 10;
                        else
                                currentProgress = DS.getSeekBarSetting2();

                        seekbar = (SeekBar) itemView.findViewById(R.id.seekbar2);
                        seekbar.setProgress(currentProgress);
                        seekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener()
                        {
                                public void onProgressChanged(SeekBar seekbar1, int progress, boolean fromUser) {
                                        // seekbar has a range between 0 to 100
                                        //long time = progress * 60000 ;

                                        //DS.setSeekBarSetting1(progress);
                                        String x = "Turn on data every " + Integer.toString(seekbar1.getProgress()+ 1) + " minutes";
                                        txtTitle.setText(x);
                                        txtTitle.setTextColor(0xff000000);
                                }
                                public void onStartTrackingTouch(SeekBar seekbar1) {

                                }
                                public void onStopTrackingTouch(SeekBar seekbar1) {
                                        newProgressValue = seekbar1.getProgress();
                                        currentProgress = newProgressValue;
                                        DS.setSeekBarSetting2(seekbar1.getProgress() + 1);
                                }
                        });
                } else if(position == 2) {
                        String x = "Turn on data for " + Integer.toString(DS.getSeekBarSetting3()) + " seconds";
                        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        itemView = inflater.inflate(R.layout.drawer_list_item2, parent, false);
                        txtTitle = (TextView) itemView.findViewById(R.id.title3);
                        txtTitle.setText(x);
                        txtTitle.setTextColor(0xff000000);
                        if(DS.getSeekBarSetting3() == 0)
                                currentProgress = 10;
                        else
                                currentProgress = DS.getSeekBarSetting3();
                        seekbar = (SeekBar) itemView.findViewById(R.id.seekbar3);
                        seekbar.setProgress(currentProgress);
                        seekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener()
                        {
                                public void onProgressChanged(SeekBar seekbar1, int progress, boolean fromUser) {
                                        // seekbar has a range between 0 to 100
                                        //long time = progress * 60000 ;
                                        //DS.setSeekBarSetting1(progress);
                                        String x = "Turn on data for " + Integer.toString(seekbar1.getProgress() + 3) + " seconds";
                                        txtTitle.setText(x);
                                        txtTitle.setTextColor(0xff000000);
                                }
                                public void onStartTrackingTouch(SeekBar seekbar1) {

                                }
                                public void onStopTrackingTouch(SeekBar seekbar1) {
                                        newProgressValue = seekbar1.getProgress();
                                        currentProgress = newProgressValue;
                                        DS.setSeekBarSetting3(seekbar1.getProgress() + 3);
                                }
                        });
                }
                return itemView;
        }

}
