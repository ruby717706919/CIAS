package com.example.testit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import com.example.testit.util.DateUtil;

import android.os.Handler;
import android.os.Message;
import android.widget.TextView;


public class UserActivity extends AppCompatActivity {

    private TextView tv1;
    private TextView tv2;//实时显示时间
    //private DateUtil dateUtil=new DateUtil();
   // private Timer timer = new Timer();
    private int what=1;
    public void setTime(){
        DateUtil dateUtil=new DateUtil();
        tv1.setText(dateUtil.getDate());
        tv2.setText(dateUtil.getNowTime());
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        tv1 = this.<TextView>findViewById(R.id.date);
        tv2 = this.<TextView>findViewById(R.id.time);
        timeRefresh.start();

        /*tv1.setText(dateUtil.getDate());
        tv2.setText(dateUtil.getNowTime());
        refresh rf = new refresh();*/
        //timer.schedule(timeRefresh,0,1000);
    }
    private Handler handler=new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what){
                case 1:
                    setTime();
                    break;
            }
            super.handleMessage(msg);

        }
    };
    private Thread timeRefresh=new Thread(new Runnable() {
        @Override
        public void run() {
            while (true) {
                handler.sendEmptyMessage(what);
                try {
                    Thread.sleep(1000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }
    });




}
