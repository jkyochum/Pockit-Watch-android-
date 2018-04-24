package www.jkyochum.com.chronometer2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
//import android.widget.Chronometer;
import www.jkyochum.com.chronometer2.Chronometer;

import android.widget.ScrollView;
import android.widget.TextView;
import java.lang.Runnable;
import java.lang.Thread;

public class MainActivity extends AppCompatActivity{


    public TextView mTvTime;
    private Button mBtnStart;
    private Button mBtnLap;
    private Button mBtnStop;
    private TextView mTvLaps;
    private int mLaps = 1;
    private ScrollView mSvLaps;
    private Button mBtnLogData;

    private Context mContext;
    private Chronometer mChronometer;
    private Thread mThreadChrono;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;

        mTvTime = findViewById(R.id.tvTimer);
        mBtnStart = findViewById(R.id.btnStart);
        mBtnLap = findViewById(R.id.btnLap);
        mBtnStop = findViewById(R.id.btnStop);
        mTvLaps = findViewById(R.id.tvLaps);
        mSvLaps = findViewById(R.id.svLap);
        mBtnLogData = findViewById(R.id.btnLogData);

        mBtnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mChronometer == null){
                    mChronometer = new Chronometer(mContext);
                    mThreadChrono = new Thread(mChronometer);
                    mThreadChrono.start();
                    mChronometer.start();

                    mLaps = 1;
                    mTvLaps.setText("");
                }
            }
        });

        mBtnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mChronometer!= null){
                    mChronometer.stop();
                    mThreadChrono.interrupt();
                    mThreadChrono = null;

                    mChronometer = null;
                }
            }
        });

        mBtnLap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mChronometer == null){
                    return;
                }

                mTvLaps.append("LAP " + String.valueOf(mLaps) + " " + String.valueOf(mTvTime.getText()) + "\n");
                mLaps++;

                mSvLaps.smoothScrollTo(0, mTvLaps.getBottom());
            }
        });

        mBtnLogData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Database.class);

                //SENDING THE TIME BY INTENT TO DATABASE CLASS
                intent.putExtra("timeText", mTvTime.getText().toString());
                startActivity(intent);

            }
        });
    }

    public void updateTimerText(final String time){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mTvTime.setText(time);
            }
        });
    }


}
