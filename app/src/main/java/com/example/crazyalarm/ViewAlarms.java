/** *************************************************************** **/
/** ViewAlarms
 /**
 /** view alarm from recycler views.
 /*******************************************************************/
package com.example.crazyalarm;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

public class ViewAlarms extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseHelper myDB;
    AlarmViewAdapter.OnItemClickListener listener;

    /** on create method **/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDB = new DatabaseHelper(this);
        recyclerView = (RecyclerView) findViewById(R.id.alarmRecycler);

        /** read all alarm data from db **/
        Cursor data = myDB.getAllData();
        List<Alarm> alarmList = new ArrayList<>();
        int i = 0;
        if(data.getCount() != 0){
            /** for all the row in the database **/
            while(data.moveToNext()){
                Alarm alarms = new Alarm(data.getString(2),data.getString(1),data.getString(0),data.getString(3),data.getString(4),data.getString(5));
                /** adding to alarm list **/
                alarmList.add(i,alarms);
                i++;
            }
            Log.d("list", alarmList.toString());
            /** passing alarm list to AlarmViewAdapter **/
            AlarmViewAdapter adapter = new AlarmViewAdapter(alarmList, listener);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager((new LinearLayoutManager(this)));
        }else{
            /** if there is no alarms to show **/
            Toast.makeText(ViewAlarms.this, "There is no alarms to show!",Toast.LENGTH_LONG).show();
        }
    }
}