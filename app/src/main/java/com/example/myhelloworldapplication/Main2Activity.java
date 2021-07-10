package com.example.myhelloworldapplication;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myhelloworldapplication.bean.ChannelBean;
import com.example.myhelloworldapplication.bean.FeedBean;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.SyncFailedException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;



public class Main2Activity extends AppCompatActivity {

    private TextView a, b, c, d, e;

    Button bt2;
    List<List<Double>> a1 = new ArrayList<>();
    ArrayList<String> a2;


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        a = findViewById(R.id.a);
        b = findViewById(R.id.b);
        c = findViewById(R.id.c);
        d = findViewById(R.id.d);
        e = findViewById(R.id.e);

        bt2 = findViewById(R.id.get1);

        StringBuilder sb = new StringBuilder();

        // 读取 assets/指定的json文件内容
        try {
            //选择assets文件夹下的json文件
            InputStream is = getResources().getAssets().open("total_data.json");
            InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
            BufferedReader br = new BufferedReader(isr);
            String str = "";

            while ((str = br.readLine()) != null) {
                sb.append(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }



        //建立两个数组
        List<ChannelBean> channels = new ArrayList<ChannelBean>();

        List<FeedBean> feed = new ArrayList<FeedBean>();

        try {

            //将sb中的数据传给jsonObject
            JSONObject jsonObject = new JSONObject(sb.toString());

            //在jsonObject中寻找名为“channel”的JSON对象
            JSONObject jsonObject1 = jsonObject.getJSONObject("channel");

                //实例化ChannelBean()
                ChannelBean channelBean = new ChannelBean();

                //取出channel里的个部分值
                channelBean.setId(jsonObject1.getInt("id"));
                channelBean.setName(jsonObject1.getString("name"));
                channelBean.setDescription(jsonObject1.getString(("description")));
                channelBean.setLatitude(jsonObject1.getString("latitude"));
                channelBean.setLongitude(jsonObject1.getString("longitude"));
                channelBean.setField1(jsonObject1.getString("field1"));
                channelBean.setField2(jsonObject1.getString("field2"));
                channelBean.setField3(jsonObject1.getString("field3"));
                channelBean.setField4(jsonObject1.getString("field4"));
                channelBean.setField5(jsonObject1.getString("field5"));
                channelBean.setCreated_at(jsonObject1.getString("created_at"));
                channelBean.setUpdated_at(jsonObject1.getString("updated_at"));
                channelBean.setElevation(jsonObject1.getString("elevation"));
                channelBean.setLast_entry_id(jsonObject1.getInt("last_entry_id"));
                channels.add(channelBean);




            //在jsonObject中寻找名为“feeds”的JSON数组
            JSONArray jsonArray = jsonObject.getJSONArray("feed");

            //遍历数组
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject2 = (JSONObject) jsonArray.get(i);

                ////实例化ChannelBean()
                FeedBean feedBean = new FeedBean();

                //取出feeds里的个部分值
                feedBean.setCreated_at(jsonObject2.getString("created_at"));
                feedBean.setId(jsonObject2.getInt("id"));

                JSONArray ja = jsonObject2.getJSONArray("field1");

                List<Double> cc = new ArrayList<Double>();

                for (int j = 0; j < ja.length(); j++){
                    cc.add(ja.getDouble(j));
                }
                feedBean.setField1(cc);

                feed.add(feedBean);
            }

        }catch (Exception e) {
            e.printStackTrace();
        }

        //初始化变量sb的值(重用sb)
        sb = new StringBuilder();


        //调用channel里的数据
        for ( ChannelBean lang : channels){
            //以下循环添加你需要的数据内容

            sb.append(lang.getName()+lang.getDescription()+"\n");

        }

        //调用feeds里的数据
        for ( FeedBean lang : feed){
            //以下循环添加你需要的数据内容

            sb.append("Time" + lang.getField1().toString()+"\n");

            System.out.println(sb);

            // 将所有数据加入进一个列表。结构为列表（数组）
            a1.add(lang.getField1());

            a.setText("数组"+sb);
            a.setText("数组"+sb);

        }
        System.out.println(a1);
        //输出数据至屏幕
        Toast.makeText(this, sb.toString(),Toast.LENGTH_LONG).show();
//
        //      bt2 = findViewById(R.id.get1);
        // bt2.setOnClickListener(new View.OnClickListener() {
        // @Override
        //  public void onClick(View v) {
        //      Intent intent =new Intent(Testjson7.this,Testtransform.class);
        //      Transform tr = new Transform();
        //      tr.setField1(a1);
        //      tr.setCreated_at(a2);
        //      Intent intent1 = intent.putExtra("product",
        //              "tr");
        //      startActivity(intent, );
//
//





        //   }
    // });


    }
}
