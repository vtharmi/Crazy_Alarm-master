/** *************************************************************** **/
/** MainActivity
/**
/** Main Activity of the application, most of the important implementation comes here.
/*******************************************************************/
package com.example.crazyalarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    DatabaseHelper myDb;

    ImageButton addAlarm;
    AlertDialog.Builder mBuilder, tBuilder;
    View mView, tView;
    TimePicker mTimePicker;
    NumberPicker mNumberPicker;
    AlertDialog mDialog, tDialog;
    EditText mName;
    TextView alarmTime, mhead, alarmName, testTxt;
    Button mTone, mAdd, mCancel, tSelect, tCancel;
    RadioGroup radioGroup1;
    RecyclerView recyclerView;

    List<Alarm> alarmList = new ArrayList<>();
    AlarmViewAdapter.OnItemClickListener listener;
    private  String sid ;
    private String stime ;
    private String sname;
    private String stone ;
    private String scount;

    AlarmManager alarmManager;

    /** oncreate method **/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDb = new DatabaseHelper(this);

        addAlarm = (ImageButton) findViewById(R.id.btnAddAlarm);

        /** creating alert dialog popup for adding alarm **/
        mBuilder = new AlertDialog.Builder(MainActivity.this);
        mView = getLayoutInflater().inflate(R.layout.add_alarm, null);
        mBuilder.setView(mView);
        mDialog = mBuilder.create();

        /** creating alert dialog popup for selecting tone **/
        tBuilder = new AlertDialog.Builder(MainActivity.this);
        tView = getLayoutInflater().inflate(R.layout.select_tone, null);
        tBuilder.setView(tView);
        tDialog = tBuilder.create();

        mTimePicker = (TimePicker) mView.findViewById(R.id.timePicker);
        mNumberPicker = (NumberPicker) mView.findViewById(R.id.numberPicker);
        mNumberPicker.setMinValue(1);
        mNumberPicker.setMaxValue(10);
        mNumberPicker.setWrapSelectorWheel(true);
        mName = (EditText) mView.findViewById(R.id.etName);
        mTone = (Button) mView.findViewById(R.id.btnTone);
        mAdd = (Button) mView.findViewById(R.id.btnAdd);
        mCancel = (Button) mView.findViewById(R.id.btnCancel);
        mhead = (TextView) mView.findViewById(R.id.textView2);

        radioGroup1 = (RadioGroup) tView.findViewById(R.id.radGrp1);
        tSelect = (Button) tView.findViewById(R.id.btnSelect);
        tCancel = (Button) tView.findViewById(R.id.btnCancel);

        alarmTime = (TextView) findViewById(R.id.alarmTimeView);
        alarmTime = (TextView) findViewById(R.id.alarmNameView);
        recyclerView = (RecyclerView) findViewById(R.id.alarmRecycler);

        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        /** registering a broadcast intent from alarm view adaptor for switch status **/
        registerReceiver(broadcastReceiver, new IntentFilter("Alarm_Status_Intent"));

        /** calling set alarm method **/
        setAlarm();
        /** calling show alarm method **/
        showAlarms();


        /** + button click action to add new alarm **/
        addAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mhead.setText("NEW ALARM");
                mAdd.setText("ADD");
                mDialog.show();

                /** button click to open tone selection popup **/
                mTone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        tDialog.show();

                        /** listening for the tone selection **/
                        radioGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                                /** switching playing tone for the selection **/
                                switch (checkedId) {
                                    case R.id.btnRad1:
                                        if(AudioPlay.isplayingAudio){
                                            AudioPlay.stopAudio();
                                        }
                                        AudioPlay.playAudio(MainActivity.this, R.raw.tone_chennai_funny);
                                        return;
                                    case R.id.btnRad2:
                                        if(AudioPlay.isplayingAudio){
                                            AudioPlay.stopAudio();
                                        }
                                        AudioPlay.playAudio(MainActivity.this, R.raw.tone_energetic);
                                        return;
                                    case R.id.btnRad3:
                                        if(AudioPlay.isplayingAudio){
                                            AudioPlay.stopAudio();
                                        }
                                        AudioPlay.playAudio(MainActivity.this, R.raw.tone_iphone);
                                        return;
                                    case R.id.btnRad4:
                                        if(AudioPlay.isplayingAudio){
                                            AudioPlay.stopAudio();
                                        }
                                        AudioPlay.playAudio(MainActivity.this, R.raw.tone_kabali);
                                        return;
                                    case R.id.btnRad5:
                                        if(AudioPlay.isplayingAudio){
                                            AudioPlay.stopAudio();
                                        }
                                        AudioPlay.playAudio(MainActivity.this, R.raw.tone_kadhale_kadhale);
                                        return;
                                    case R.id.btnRad6:
                                        if(AudioPlay.isplayingAudio){
                                            AudioPlay.stopAudio();
                                        }
                                        AudioPlay.playAudio(MainActivity.this, R.raw.tone_morning_96);
                                        return;
                                    case R.id.btnRad7:
                                        if(AudioPlay.isplayingAudio){
                                            AudioPlay.stopAudio();
                                        }
                                        AudioPlay.playAudio(MainActivity.this, R.raw.tone_neethanae);
                                        return;
                                    case R.id.btnRad8:
                                        if(AudioPlay.isplayingAudio){
                                            AudioPlay.stopAudio();
                                        }
                                        AudioPlay.playAudio(MainActivity.this, R.raw.tone_pachainiramae);
                                        return;
                                    default:
                                        return;
                                }
                            }
                        });

                        /** confirming the selected tone **/
                        tSelect.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                /** get selected radio button from radioGroup **/
                                int selectedId = radioGroup1.getCheckedRadioButtonId();

                                /** find the radiobutton by returned id **/
                                RadioButton radioButton = (RadioButton) tView.findViewById(selectedId);

                                Button tone = (Button) mView.findViewById(R.id.btnTone);
                                tone.setText(radioButton.getText());
                                if(AudioPlay.isplayingAudio){
                                    AudioPlay.stopAudio();
                                }
                                tDialog.dismiss(); /** dismissal of tone selection interface **/
                            }
                        });

                        /** cancelling the tone selection **/
                        tCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if(AudioPlay.isplayingAudio){
                                    AudioPlay.stopAudio();
                                }
                                tDialog.dismiss();
                            }
                        });
                    }
                });

                /** adding the inputted data for new alarm **/
                mAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.e("timePick",mTimePicker.getCurrentHour().toString());
                        int TpHour = mTimePicker.getCurrentHour(); // hourOfDay
                        int TpMinute =  mTimePicker.getCurrentMinute(); // Minute
                        String getAMPMValue = "AM";
                        if(TpHour>11){
                            getAMPMValue="PM";
                            if(TpHour!=12) {
                                TpHour = TpHour-12;
                            }
                        }
                        /** Display the 12 hour format time in TextView **/
                        Log.e("timePicks",""+TpHour+":"+ TpMinute+ " "+ getAMPMValue);

                        /** inserting the data to DB **/
                        boolean isInserted = myDb.insertData(mName.getText().toString(),mTimePicker.getCurrentHour().toString()+":"+mTimePicker.getCurrentMinute().toString(),mTone.getText().toString(), String.valueOf(mNumberPicker.getValue()), "1");
                        /** showing success message and calling methods after success insertion **/
                        if(isInserted == true){
                            Toast.makeText(MainActivity.this, "Alarm added Successfully", Toast.LENGTH_LONG).show();

                            DecimalFormat formatter = new DecimalFormat("00");
                            String HourString = formatter.format(TpHour);
                            String MinuteString = formatter.format(TpMinute);
                            String message = "Alarm set for "+""+HourString+":"+ MinuteString+ " "+ getAMPMValue;
                            Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();

                            showAlarms();
                            setAlarm();
                            mDialog.dismiss(); /** dismissal of add alarm interface **/
                        }
                        /** show alert when failure insertion **/
                        else {
                            Toast.makeText(MainActivity.this, "Failed to add Alarm", Toast.LENGTH_LONG).show();
                        }
                    }
                });

                mCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mDialog.dismiss(); /** dismiss the add alarm interface **/
                    }
                });


            }
        });

        /** deleting alarm when swiping listed alarm **/
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                sid = alarmList.get(viewHolder.getAdapterPosition()).getId();
                stime = alarmList.get(viewHolder.getAdapterPosition()).getTime();
                sname = alarmList.get(viewHolder.getAdapterPosition()).getName();
                stone = alarmList.get(viewHolder.getAdapterPosition()).getTone();
                scount = alarmList.get(viewHolder.getAdapterPosition()).getCount();
                /** deleting alarm data from db **/
                myDb.deleteData(sid);
                /** deleting created alarm pending intent **/
                Intent BroadcastIntent = new Intent(MainActivity.this,MyBroadcastReceiver.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, Integer.parseInt(sid),BroadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                pendingIntent.cancel();
                alarmManager.cancel(pendingIntent);

                showAlarms(); /** update list soon after delete **/
                Toast.makeText(MainActivity.this, "Alarm deleted", Toast.LENGTH_SHORT).show();

                /** Snack bar for undo deleted item **/
                Snackbar.make(recyclerView, "Alarm deleted.",Snackbar.LENGTH_LONG).setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String[] separated = stime.split(":");

                        String hr = separated[0].replaceAll("^0+","").replaceAll(" ","");
                        String min = separated[1].replaceAll("^0+","").replaceAll(" ","").replaceAll("[^0-9]", "");
                        String getAMPM = stime.replaceAll("[^A-Z]","");
                        if(getAMPM.equals("PM"))
                        {
                            if(Integer.valueOf(hr)!=12) {
                                hr = String.valueOf(Integer.valueOf(hr)+12);
                            }
                        }
                        Log.e("hour",hr);
                        stime = hr+":"+min;
                        myDb.insertData(sname,stime,stone,scount,"0");

                        showAlarms(); /** show alarm list after undo **/
                        setAlarm(); /** set alarm for the undone alarm **/
                    }
                }).show();

            }

        }).attachToRecyclerView(recyclerView);

        /** getting intent from alarm click and getting extras for Update Alarm **/
        Intent intent = getIntent();
        final String method = intent.getStringExtra("methode");
        final String name = intent.getStringExtra("name");
        final String id = intent.getStringExtra("id");
        final String time = intent.getStringExtra("time");
        final String tone = intent.getStringExtra("tone");
        final String count = intent.getStringExtra("count");


        if(method != null && method.equals("show")){
            String[] separated = time.split(":");

            /** adding previous values to updating interface **/
            mhead.setText("EDIT ALARM");
            mAdd.setText("UPDATE");
            mName.setText(name);
            /** separating time data **/
            String hr = separated[0].replaceAll("^0+","").replaceAll(" ","");
            String min = separated[1].replaceAll("^0+","").replaceAll(" ","").replaceAll("[^0-9]", "");
            String getAmPm = time.replaceAll("[^A-Z]","");
            if(getAmPm.equals("Pm"))
            {
                if(Integer.valueOf(hr)!=12) {
                    hr = String.valueOf(Integer.valueOf(hr)+12);
                }
            }
            Log.e("hour",hr);
            mTimePicker.setIs24HourView(false);
            mTimePicker.setCurrentHour(Integer.valueOf(hr));
            mTimePicker.setCurrentMinute(Integer.valueOf(min));
            mTone.setText(tone);
            mNumberPicker.setValue(Integer.parseInt(count));

            /** showing the update interface popup **/
            mDialog.show();

            /** tone selection for update alarm **/
            mTone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    tDialog.show();

                    radioGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                            switch (checkedId) {
                                case R.id.btnRad1:
                                    if(AudioPlay.isplayingAudio){
                                        AudioPlay.stopAudio();
                                    }
                                    AudioPlay.playAudio(MainActivity.this, R.raw.tone_chennai_funny);
                                    return;
                                case R.id.btnRad2:
                                    if(AudioPlay.isplayingAudio){
                                        AudioPlay.stopAudio();
                                    }
                                    AudioPlay.playAudio(MainActivity.this, R.raw.tone_energetic);
                                    return;
                                case R.id.btnRad3:
                                    if(AudioPlay.isplayingAudio){
                                        AudioPlay.stopAudio();
                                    }
                                    AudioPlay.playAudio(MainActivity.this, R.raw.tone_iphone);
                                    return;
                                case R.id.btnRad4:
                                    if(AudioPlay.isplayingAudio){
                                        AudioPlay.stopAudio();
                                    }
                                    AudioPlay.playAudio(MainActivity.this, R.raw.tone_kabali);
                                    return;
                                case R.id.btnRad5:
                                    if(AudioPlay.isplayingAudio){
                                        AudioPlay.stopAudio();
                                    }
                                    AudioPlay.playAudio(MainActivity.this, R.raw.tone_kadhale_kadhale);
                                    return;
                                case R.id.btnRad6:
                                    if(AudioPlay.isplayingAudio){
                                        AudioPlay.stopAudio();
                                    }
                                    AudioPlay.playAudio(MainActivity.this, R.raw.tone_morning_96);
                                    return;
                                case R.id.btnRad7:
                                    if(AudioPlay.isplayingAudio){
                                        AudioPlay.stopAudio();
                                    }
                                    AudioPlay.playAudio(MainActivity.this, R.raw.tone_neethanae);
                                    return;
                                case R.id.btnRad8:
                                    if(AudioPlay.isplayingAudio){
                                        AudioPlay.stopAudio();
                                    }
                                    AudioPlay.playAudio(MainActivity.this, R.raw.tone_pachainiramae);
                                    return;
                                default:
                                    return;
                            }
                        }
                    });

                    tSelect.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            /** get selected radio button from radioGroup **/
                            int selectedId = radioGroup1.getCheckedRadioButtonId();

                            /** find the radiobutton by returned id **/
                            RadioButton radioButton = (RadioButton) tView.findViewById(selectedId);

                            Button tone = (Button) mView.findViewById(R.id.btnTone);
                            tone.setText(radioButton.getText());
                            if(AudioPlay.isplayingAudio){
                                AudioPlay.stopAudio();
                            }
                            tDialog.dismiss(); /** dismiss the select tone interface **/
                        }
                    });

                    tCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(AudioPlay.isplayingAudio){
                                AudioPlay.stopAudio();
                            }
                            tDialog.dismiss(); /** dismiss the select tone interface **/
                        }
                    });
                }
            });

            /** adding the updated alarm **/
            mAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int TpHour = mTimePicker.getCurrentHour(); // hourOfDay
                    int TpMinute =  mTimePicker.getCurrentMinute(); // Minute
                    String getAMPMValue = "AM";
                    if(TpHour>11){
                        getAMPMValue="PM";
                        if(TpHour!=12) {
                            TpHour = TpHour-12;
                        }
                    }
                    // Display the 12 hour format time in TextView
                    Log.e("timePicks",""+TpHour+":"+ TpMinute+ " "+ getAMPMValue);

                    boolean isInserted = myDb.updateAlarm(id,mName.getText().toString(),mTimePicker.getCurrentHour().toString()+":"+mTimePicker.getCurrentMinute().toString(),mTone.getText().toString(), String.valueOf(mNumberPicker.getValue()),"1");
                    if(isInserted == true){
                        Toast.makeText(MainActivity.this,  "Alarm Updated Successfully", Toast.LENGTH_LONG).show();

                        DecimalFormat formatter = new DecimalFormat("00");
                        String HourString = formatter.format(TpHour);
                        String MinuteString = formatter.format(TpMinute);
                        String message = "Alarm set for "+""+HourString+":"+ MinuteString+ " "+ getAMPMValue;
                        Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();

                        showAlarms();
                        setAlarm();
                        mDialog.dismiss(); /** dismiss the update alarm interface **/
                    }
                    else {
                        Toast.makeText(MainActivity.this, "Failed to update Alarm", Toast.LENGTH_LONG).show();
                    }
                }
            });

            mCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mDialog.dismiss(); /** dismiss the update alarm interface **/
                    Toast.makeText(MainActivity.this, "cancel clicked", Toast.LENGTH_SHORT).show();
                }
            });

        }

    }

    /** Method for setting Alarm **/
    public void setAlarm(){
        /** reading data from db **/
        Cursor res = myDb.getAllData();
        if(res.getCount() == 0){
            return;
        }

        String id;
        String name;
        String time;
        String tone;
        String count;
        String status;
        String[] separatedTime = new String[10];

        Date date = new Date();

        /** for each row of alarm in database **/
        while (res.moveToNext()){
            id = res.getString(0);
            name = res.getString(1);
            time = res.getString(2);
            tone = res.getString(3);
            count = res.getString(4);
            status = res.getString(5);

            separatedTime = time.split(":");
            Log.e("Check 1 data", separatedTime[0]+":"+separatedTime[1]);

            Calendar cal_alarm = Calendar.getInstance();
            Calendar cal_now = Calendar.getInstance();

            cal_alarm.setTime(date);
            cal_now.setTime(date);

            cal_alarm.set(Calendar.HOUR_OF_DAY,Integer.parseInt(separatedTime[0]));
            cal_alarm.set(Calendar.MINUTE, Integer.parseInt(separatedTime[1]));
            cal_alarm.set(Calendar.SECOND,0);

            if(cal_alarm.before(cal_now)){
                cal_alarm.add(Calendar.DATE,1);
            }

            Log.e("Check status onnnn", status);

            if (Integer.parseInt(status) == 1){
                Intent BroadcastIntent = new Intent(MainActivity.this,MyBroadcastReceiver.class);
                BroadcastIntent.putExtra("name", name);
                BroadcastIntent.putExtra("time", time);
                BroadcastIntent.putExtra("tone", tone);
                BroadcastIntent.putExtra("count", count);

                ArrayList<PendingIntent> intentArray = new ArrayList<PendingIntent>();
                PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, Integer.parseInt(id),BroadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                alarmManager.set(AlarmManager.RTC_WAKEUP, cal_alarm.getTimeInMillis(),pendingIntent); //setRepeating**************************

                intentArray.add(pendingIntent);
            }

        }

    }

    /** Method for Show Alarm on recycle list view **/
    public void showAlarms(){

        String[] separatedDbTime = new String[10];

        /** reading data from database **/
        Cursor data = myDb.getAllData();
        /** clearing existing alarms in the list**/
        alarmList.clear();

        int i = 0;
        if(data.getCount() != 0){
            /** for each row of alarm in database **/
            while(data.moveToNext()){

                separatedDbTime = data.getString(2).split(":");

                int dbHour = Integer.parseInt(separatedDbTime[0]);
                int dbMinute =  Integer.parseInt(separatedDbTime[1]);

                /** converting 24h to 12h format **/
                String getAMPMValue = "Am";
                if(dbHour>11){
                    getAMPMValue="Pm";
                    if(dbHour!=12) {
                        dbHour = dbHour-12;
                    }
                }
                else if(dbHour==0){
                    dbHour = 12;
                }

                /** decimal formatting to display clearly **/
                DecimalFormat formatter = new DecimalFormat("00");
                String dbHourString = formatter.format(dbHour);
                String dbMinuteString = formatter.format(dbMinute);
                String time = dbHourString+" : "+dbMinuteString+" "+getAMPMValue;

                Alarm alarms = new Alarm(time,data.getString(1),data.getString(0),data.getString(3),data.getString(4),data.getString(5));
                alarmList.add(i,alarms); /** adding to alarm list **/
                i++;


            }
            Log.d("list", alarmList.toString());
            /** binding alarm list with adapter **/
            AlarmViewAdapter adapter = new AlarmViewAdapter(alarmList,listener);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager((new LinearLayoutManager(this)));
        }else{
            Toast.makeText(this, "There is no alarms to show!",Toast.LENGTH_LONG).show();
        }
    }

    /** getting a broadcast from AlarmViewAdapter for status change **/
    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String ListId = intent.getExtras().getString("listId");
            String Status = intent.getExtras().getString("status");

            Log.e("Check switch id", ListId);
            Log.e("Check switch status", Status);

            boolean isUpdated = myDb.updateAlarmStatus(ListId,Status);
            if(isUpdated && Status.equals("1")){
                setAlarm(); /** setting alarm when status ON **/
            }
            else {
                /** removing the created pending intent if status is OFF **/
                Intent BroadcastIntent = new Intent(MainActivity.this,MyBroadcastReceiver.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, Integer.parseInt(ListId),BroadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                pendingIntent.cancel();
                alarmManager.cancel(pendingIntent);
            }

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        /** unregister the receiver when view destroyed **/
        unregisterReceiver(broadcastReceiver);
    }

}
