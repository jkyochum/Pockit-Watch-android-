package www.jkyochum.com.chronometer2;


import android.app.ActionBar;
import android.content.Context;
import android.os.Bundle;

public class Chronometer implements Runnable {


    public static final long MILLIS_TO_MINUTES = 60000;//how many milliseconds in a minute
    public static final long MILLIS_TO_HOURS = 3600000;

    private Context mContext;
    private long mStartTime;

    private boolean mIsRunning;

    ActionBar bar;


    //context constructor
    public Chronometer(Context context) {
        mContext = context;
    }

    public void start(){
        mStartTime = System.currentTimeMillis(); //capturing current time on system
        mIsRunning = true;
    }

    public void stop(){

        mIsRunning = false;

    }

    @Override
    public void run() {

        while(mIsRunning){
            long since = System.currentTimeMillis() - mStartTime; //how many milliseconds have passed sine start

            int seconds = (int) ((since/1000) % 60);
            int minutes = (int) (((since/MILLIS_TO_MINUTES)) % 60);
            int hours = (int) ((since/(MILLIS_TO_HOURS))%24);
            int millis = (int) since%1000;

            ((MainActivity)mContext).updateTimerText(String.format(
                    "%02d:%02d:%02d:%03d", hours, minutes, seconds, millis
            ));
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
