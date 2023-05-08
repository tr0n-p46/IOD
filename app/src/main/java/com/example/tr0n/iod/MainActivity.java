package com.example.tr0n.iod;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.MediaType;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    OkHttpClient client = new OkHttpClient();
    String crypto[] = {"BTC", "ETH", "XRP", "LTC", "BCH"};

    TextView tv;
    EditText et;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button) findViewById(R.id.button);

        et = (EditText) findViewById(R.id.editText);

        tv = (TextView) findViewById(R.id.textView);
        button.setOnClickListener(this);
    }



    @Override
    public void onClick(View v) {
        getData();
    }

    public void getData()  {


        new Thread(new Runnable() {
            @Override
            public void run() {
                String code = et.getText().toString();
                int val = 1;
                for(int i=0;i<crypto.length;i++) {
                    if(code.equalsIgnoreCase(crypto[i])) {
                        val = i + 1;
                        break;
                    }
                }

                String url = "https://api.coinmarketcap.com/v2/ticker/" + val;
                //RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), data);

                Request request = new Request.Builder()
                        .url(url)
                        .get()
                        .build();
                try {
                    final Response response = client.newCall(request).execute();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                tv.setText(response.body().string());
                            }
                            catch(IOException e) {}
                        }
                    });

                }
                catch(IOException e) {}
            }
        }).start();



    }
}
