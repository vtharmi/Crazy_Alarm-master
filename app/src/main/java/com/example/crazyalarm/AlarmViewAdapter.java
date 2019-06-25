/** *************************************************************** **/
/** AlarmViewAdapter
 /**
 /** Alarm view adapter handler, handling view alarm interface actions
 /*******************************************************************/
package com.example.crazyalarm;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;
import java.util.List;


public class AlarmViewAdapter extends RecyclerView.Adapter<AlarmViewAdapter.ViewHolder> {

    private OnItemClickListener mlistener;

    private List<Alarm> mAlarm;
    private static DatabaseHelper myDb;
    private Context context;

    public interface OnItemClickListener{
        void onItemUpdate(Alarm alarm);
        void itemClicked(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mlistener = listener;
    }

    /** binding view holder for alarm adapter **/
    @Override
    public void onBindViewHolder(AlarmViewAdapter.ViewHolder viewHolder, int position) {
        Alarm alarm = mAlarm.get(position);

        TextView mTime = viewHolder.time;
        mTime.setText(alarm.getTime());

        TextView mName = viewHolder.name;
        mName.setText(alarm.getName());

        Switch mStatus = viewHolder.status;
        if(alarm.getStatus().equals("1")){
            mStatus.setChecked(true);
        }else {
            mStatus.setChecked(false);
        }

        viewHolder.bind(alarm,mlistener,context);
    }

    /** view holder class **/
    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView time;
        public TextView name;
        public Switch status;
        MainActivity main;

        /** view holder constructor **/
        public ViewHolder(final View itemView, final Context context) {
            super(itemView);
            main = new MainActivity();
            time = (TextView) itemView.findViewById(R.id.alarmTimeView);
            name = (TextView) itemView.findViewById(R.id.alarmNameView);
            status = (Switch) itemView.findViewById(R.id.alram_switch);
            myDb = new DatabaseHelper(context);
        }

        /** binding alarm to view holder **/
        public void bind(final Alarm alarm , final OnItemClickListener listener , final Context context){
            status.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();

                    if(position != RecyclerView.NO_POSITION){

                        int statusNum;
                        if(status.isChecked()){
                            statusNum = 1;

                            Log.e("switch","checked on ");

                        }else {
                            statusNum = 0;

                            Log.e("switch","checked off");

                        }
                        Intent statusIntent = new Intent("Alarm_Status_Intent");
                        statusIntent.putExtra("listId",alarm.getId());
                        statusIntent.putExtra("status",String.valueOf(statusNum));
                        context.sendBroadcast(statusIntent);
                    }
                }
            });

            /** alarm list click event, for updating alarm **/
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("cardview clicked","clicked");
                    String id  = alarm.getId();
                    String time = alarm.getTime();
                    String name = alarm.getName();
                    String tone = alarm.getTone();
                    String count = alarm.getCount();
                    Intent intent = new Intent(context,MainActivity.class);
                    intent.putExtra("methode","show");
                    intent.putExtra("id",id);
                    intent.putExtra("time",time);
                    intent.putExtra("name",name);
                    intent.putExtra("tone",tone);
                    intent.putExtra("count",count);
                    context.startActivity(intent);

                }
            });

        }

    }

    /** alarm view adapter **/
    public AlarmViewAdapter(List<Alarm> alarms, OnItemClickListener listener) {
        mAlarm = alarms;
        mlistener = listener;
    }

    /** view holder on crete **/
    @Override
    public AlarmViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.single_alarm_card, parent, false);
        ViewHolder viewHolder = new ViewHolder(contactView,context);
        return viewHolder;
    }

    /** get adapter item count **/
    @Override
    public int getItemCount() {
        return mAlarm.size();
    }
}
