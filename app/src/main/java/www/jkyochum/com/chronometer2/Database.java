package www.jkyochum.com.chronometer2;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Message;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.lang.Integer;


import static www.jkyochum.com.chronometer2.R.id.tvTime;
import static www.jkyochum.com.chronometer2.R.id.tvTimer;

public class Database extends AppCompatActivity {

    DatabaseHelper myDb;
    EditText mEtEvent;
    TextView mTvTime;
    Button mBtnAddEvent, mBtnView, mBtnGoBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);

        myDb = new DatabaseHelper(this);

        mEtEvent = findViewById(R.id.etEvent);
        mTvTime = findViewById(tvTime);
        mBtnAddEvent = findViewById(R.id.btnAddEvent);
        mBtnView = findViewById(R.id.btnView);
        mBtnGoBack = findViewById(R.id.btnGoToMain);


        //GETTING TIME FROM MAIN ACTIVITY TEXT VIEW
        mTvTime.setText(getIntent().getExtras().getString("timeText"));


        mBtnAddEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newEntry = mEtEvent.getText().toString();
                String time = mTvTime.getText().toString();
                if(mTvTime.length() != 0){
                    if(mEtEvent.length()!=0){
                        AddData(newEntry, time);
                        mTvTime.setText("");
                        mEtEvent.setText("");
                    }
                    else{
                        Toast.makeText(Database.this, "Enter an event", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(Database.this, "No data to insert", Toast.LENGTH_SHORT).show();

                }
            }
        });

       mBtnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = myDb.showData();
                if(res.getCount() == 0){
                    showMessage("Error", "Nothing Found");
                    return;
                }

                StringBuffer buffer = new StringBuffer();
                while(res.moveToNext()){
                    buffer.append("ID: " + res.getString(0) + "\n");
                    buffer.append("Event: " + res.getString(1) + "\n");
                    buffer.append("Time: " + res.getString(2) + "\n\n");

                }
                showMessage("Data", buffer.toString());
            }
        });


        mBtnGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Database.this, MainActivity.class);
                startActivity(intent);

            }
        });

    }


    public void AddData (String newEntry, String time){

            boolean insertData = myDb.addData(newEntry, time);
            if(insertData == true){
                Toast.makeText(Database.this, "Data Entered", Toast.LENGTH_SHORT).show();

            }
            else{
                Toast.makeText(Database.this, "Not Working", Toast.LENGTH_SHORT).show();

            }
    }

    public void showMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}
