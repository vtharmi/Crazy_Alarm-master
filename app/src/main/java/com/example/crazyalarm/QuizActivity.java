 /** *************************************************************** **/
 /** QuizActivity
 /**
 /** activity for generating quize, when notification comes.
 /*******************************************************************/
package com.example.crazyalarm;

import android.app.NotificationManager;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.Random;


public class QuizActivity extends AppCompatActivity {

    Button stop;
    RadioButton rBtn1, rBtn2, rBtn3, rBtn4;
    RadioGroup radioGroupQstn;
    TextView result, qustn, textViewNum;
    String ans;
    int count;
    int click = 1;
    String quize;

    DatabaseReference myRef;
    DatabaseReference myChildRef;
    DatabaseReference selectQuize;
    DatabaseReference question;
    DatabaseReference answer;
    DatabaseReference a1;
    DatabaseReference a2;
    DatabaseReference a3;
    DatabaseReference a4;


    /** on create methode for quize activity **/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        stop = (Button) findViewById(R.id.btnStop);
        radioGroupQstn = (RadioGroup) findViewById(R.id.radioGroupQstn);
        result = (TextView) findViewById(R.id.tvResult);
        qustn = (TextView) findViewById(R.id.tvQstn);
        textViewNum = (TextView) findViewById(R.id.textViewNum);
        rBtn1 = (RadioButton) findViewById(R.id.a1);
        rBtn2 = (RadioButton) findViewById(R.id.a2);
        rBtn3 = (RadioButton) findViewById(R.id.a3);
        rBtn4 = (RadioButton) findViewById(R.id.a4);

        /** Reading quiz data from fire base **/ /** there are no insertions from app, you have to insert data(string) from fire base itself **/
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        myChildRef = myRef.child("Quize");

        myChildRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                /** getting children count, to get how many quiz are there in fire base **/
                count = (int) dataSnapshot.getChildrenCount();
                Log.e("dbCount", String.valueOf(count));

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        /** calling generateQuize() method **/
        generateQuize();

        /** getting quiz count from the intent, which passed from alarm notification **/
        final String quizCount = getIntent().getExtras().getString("count");
        Log.e("quize count1",quizCount);

        if(Integer.parseInt(quizCount)>1){
            stop.setText("Answer"); /** change button text if count >1 **/
        }

        /** answer button click event call **/
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                result.setText("");
                /** get selected radio button from radioGroup **/
                int selectedId = radioGroupQstn.getCheckedRadioButtonId();

                /** find the radiobutton by returned id **/
                RadioButton radioButton = (RadioButton) findViewById(selectedId);

                /** calling generateQuize() according to quiz count **/
                if(radioButton.getText().equals(ans)|| radioButton.getText().equals("n")){

                    if(Integer.parseInt(quizCount)>click){
                        Log.e("clicks",String.valueOf(click));
                        click++;
                        textViewNum.setText(String.valueOf(click));
                        radioGroupQstn.clearCheck();
                        generateQuize();
                    }
                    else {
                        /** stopping alarm **/
                        if(AudioPlay.isplayingAudio){
                            AudioPlay.stopAudio();
                        }
                        result.setText("Correct Answer. Alarm Stopped !!!");
                        /** closing the quiz view **/
                        finish();
                        /** clear notification from notification bar after alarm stopped **/
                        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
                        notificationManager.cancel(01);
                    }
                }
                else {
                    /** display incorrect answer **/
                    result.setText("Incorrect Answer");
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    /** generateQuize() method implementation **/
    public void generateQuize(){
        /** generate a random number from quize count in database, and getting a random quiz **/
        final int random = new Random().nextInt(count+1) + 1;
        quize = "qz"+random;

        selectQuize = myChildRef.child(quize);
        question = selectQuize.child("question");
        answer = selectQuize.child("answer");
        a1 = selectQuize.child("a1");
        a2 = selectQuize.child("a2");
        a3 = selectQuize.child("a3");
        a4 = selectQuize.child("a4");


        question.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String question = dataSnapshot.getValue(String.class);
                qustn.setText(question);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        a1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String a1 = dataSnapshot.getValue(String.class);
                rBtn1.setText(a1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        a2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String a2 = dataSnapshot.getValue(String.class);
                rBtn2.setText(a2);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        a3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String a3 = dataSnapshot.getValue(String.class);
                rBtn3.setText(a3);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        a4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String a4 = dataSnapshot.getValue(String.class);
                rBtn4.setText(a4);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        answer.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String answer = dataSnapshot.getValue(String.class);
                ans = answer;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
