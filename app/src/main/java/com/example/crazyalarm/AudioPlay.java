/** *************************************************************** **/
/** AudioPlay
 /**
 /** Audio play class for playing and stopping alarms.
 /*******************************************************************/
package com.example.crazyalarm;

        import android.content.Context;
        import android.media.MediaPlayer;

public class AudioPlay {

    public static MediaPlayer mediaPlayer;
    public static boolean isplayingAudio = false;

    /** play audio method implementation **/
    public static void playAudio(Context c, int id){

        mediaPlayer = MediaPlayer.create(c,id);
        mediaPlayer.start();
        mediaPlayer.setLooping(true);
        isplayingAudio=true;
    }

    /** stop audio method implementation **/
    public static void stopAudio(){
        mediaPlayer.stop();
        isplayingAudio=false;
    }
}
