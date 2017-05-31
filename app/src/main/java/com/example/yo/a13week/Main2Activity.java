package com.example.yo.a13week;

import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {
    TextView tv, tv2;
    EditText et;
    Button btn;
    ImageView imageView2,imageView3;
    myTask task;
    int[] menulist = {R.drawable.pizza, R.drawable.spa, R.drawable.chick};
    String[] menuname = {"피자", "스파게티", "치킨"};
    int timer = 1;
    int num = 0;
    int endtime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        tv = (TextView) findViewById(R.id.tv);
        tv2 = (TextView) findViewById(R.id.tv2);
        et = (EditText) findViewById(R.id.et);
        imageView2 = (ImageView)findViewById(R.id.imageView2);
        imageView3 = (ImageView)findViewById(R.id.imageView3);

    }

    public void onClick(View v) {

        task = new myTask();
        if(v.getId() == R.id.imageView2){
            if(et.length() == 0){
                Toast.makeText(getApplicationContext(), "초 간격을 입력해주세요", Toast.LENGTH_SHORT).show();
            }
            else {
                timer = Integer.parseInt(et.getText().toString());
                imageView2.setVisibility(View.GONE);
                imageView3.setVisibility(View.VISIBLE);
                task.execute(0);
            }

        }
        if (v.getId() == R.id.btn) {
            task = null;
            et.setText(null);
            tv2.setText(null);
            imageView2.setVisibility(View.VISIBLE);
            imageView3.setVisibility(View.GONE);
        }
    }


    class myTask extends AsyncTask<Integer, Integer, Void> {
        @Override
        protected Void doInBackground(Integer... params) {
            for (int i = 1; i < 101; i++) {
                if (isCancelled() == true) {
                    return null;
                }
                try {
                    Thread.sleep(1000);
                    publishProgress(i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            imageView3.setImageResource(menulist[num]);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

            tv2.setText("시작부터 " + values[0] + "초");

            if (values[0] % timer == 0) {
                num++;
                if(num == 3){
                    num = 0;
                }
                imageView3.setImageResource(menulist[num]);
                imageView3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        task.cancel(true);
                        task = null;
                    }
                });
            }

            endtime = values[0];
        }
        @Override
        protected void onCancelled() {
            tv2.setText(menuname[num] + "선택" + "(" + endtime + "초)");
            endtime = 0;
            num = 0;
            super.onCancelled();
        }
    }
}