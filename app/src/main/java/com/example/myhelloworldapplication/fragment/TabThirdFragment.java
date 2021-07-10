package com.example.myhelloworldapplication.fragment;

import com.example.myhelloworldapplication.MyLineChart;
import com.example.myhelloworldapplication.OrderActivity;
import com.example.myhelloworldapplication.R;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myhelloworldapplication.WeatherActivity;
import com.example.myhelloworldapplication.bean.ChannelBean;
import com.example.myhelloworldapplication.bean.FeedBean;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.BarLineChartTouchListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import com.example.myhelloworldapplication.marker.DetailsMarkerView;
import com.example.myhelloworldapplication.marker.PositionMarker;
import com.example.myhelloworldapplication.marker.RoundMarker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;

import org.json.JSONArray;
import org.json.JSONObject;

public class TabThirdFragment extends Fragment {
    private static final String TAG = "TabThirdFragment";
    private static int id = 1200;
    protected View mView; // 声明一个视图对象
    protected Context mContext; // 声明一个上下文对象
    MyLineChart mLineChart;
    RadarChart mRadarChart;

//    List<RadarEntry> list_radar = new ArrayList<>();
    // 所有数据
    List<List<Double>> list_radar = new ArrayList<>();
    List<Double> list_radar_change = new ArrayList<>();
    List<Double> list_radar_temp = new ArrayList<>();
    // 当前的一组数据，当前天气
    List<RadarEntry> list_radar_item = new ArrayList<>();
    List<RadarEntry> list1 = new ArrayList<>();
    List<RadarEntry> list2 = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity(); // 获取活动页面的上下文
        mView = inflater.inflate(R.layout.activity_weather, null); // 获取界面
        mRadarChart = mView.findViewById(R.id.radar_test);
        mLineChart = mView.findViewById(R.id.chart); // 获取折线统计图的界面

        // 加载json数据
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
            list_radar.add(lang.getField1());


        }
        System.out.println(list_radar);


        // 每次更新图表的时候都要删除上次的数据
        if (list_radar_item.size() != 0){
            list_radar_item.clear();
            list1.clear();
            list2.clear();
        }
        // Add data to main radar chart
        list_radar_change = list_radar.get((int) (Math.random() * 71));
//            for (int j = 0; j < 5; j++){
//                list_radar_item.add(new RadarEntry((float) (list_radar_change.get(j)).doubleValue()));
//            }
        list_radar_item.add(new RadarEntry((float) ((list_radar_change.get(0)).doubleValue() * 1.7)));
        list_radar_item.add(new RadarEntry((float) ((list_radar_change.get(1)).doubleValue() * 0.6)));
        list_radar_item.add(new RadarEntry((float) (list_radar_change.get(2)).doubleValue() * 10));
        list_radar_item.add(new RadarEntry((float) ((float) (list_radar_change.get(3)).doubleValue() * 0.05)));
        list_radar_item.add(new RadarEntry((float) ( (list_radar_change.get(4)).doubleValue() * 5.5)));

        // 5 variables for notification
        double current_temp = list_radar_change.get(0).doubleValue();
        double current_humidity = list_radar_change.get(1).doubleValue();
        double current_uv = list_radar_change.get(2).doubleValue();
        double current_atmos = list_radar_change.get(3).doubleValue();
        double current_wind = list_radar_change.get(4).doubleValue();



        // Add fixed data to red region
        for (int i = 0; i < 5; i++) list2.add(new RadarEntry(100));
        // Add fixed data to red region
        for (int i = 0; i < 5; i++) list1.add(new RadarEntry(60));

        // 最上层，主要图
        RadarDataSet radarDataSet = new RadarDataSet(list_radar_item,"current weather");
        radarDataSet.setColor(Color.RED);
        radarDataSet.setLineWidth(1.2f);
        // safe 部分
        RadarDataSet radarDataSet1 = new RadarDataSet(list1, "safe");
        radarDataSet1.setColor(Color.GREEN);
        radarDataSet1.setFillColor(Color.GREEN);
        radarDataSet1.setDrawFilled(true);
        // red 部分
        RadarDataSet radarDataSet2 = new RadarDataSet(list2, "risk");
        radarDataSet2.setColor(Color.YELLOW);
        radarDataSet2.setFillColor(Color.YELLOW);
        radarDataSet2.setDrawFilled(true);
        // 数据打包
        RadarData radarData = new RadarData(radarDataSet);
        radarData.addDataSet(radarDataSet1);
        radarData.addDataSet(radarDataSet2);
        mRadarChart.setData(radarData);

        //Y轴最小值不设置会导致数据中最小值默认成为Y轴最小值
        mRadarChart.getYAxis().setAxisMinimum(0);

        //大字的颜色（中心点和各顶点的连线）
        mRadarChart.setWebColor(Color.BLACK);
        mRadarChart.setWebLineWidthInner(0.8f);
        //所有五边形的颜色,以及加粗
        mRadarChart.setWebColorInner(Color.BLACK);
        mRadarChart.setWebLineWidth(0.8f);
        //整个控件的背景颜色
        mRadarChart.setBackgroundColor(Color.WHITE);

        XAxis rxAxis = mRadarChart.getXAxis();
        rxAxis.setTextColor(Color.BLUE);//X轴字体颜色
        rxAxis.setTextSize(10);     //X轴字体大小
        //自定义X轴坐标描述（也就是五个顶点上的文字,默认是0、1、2、3、4）
        rxAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float v, AxisBase axisBase) {
                if (v==0){
                    return "temperature";
                }
                if (v==1){
                    return "humidity";
                }
                if (v==2){
                    return "UV";
                }
                if (v==3){
                    return "atmos";
                }
                if (v==4){
                    return "wind velocity";
                }
                return "";
            }
        });


        //是否绘制雷达框上对每个点的数据的标注    和Y轴坐标点一般不同时存在 否则显得很挤  默认为true
        radarDataSet.setDrawValues(true);
        radarDataSet.setValueTextSize(12);  //数据值得字体大小（这里只是写在这）
        radarDataSet.setValueTextColor(Color.BLACK);//数据值得字体颜色（这里只是写在这）

        YAxis yAxis = mRadarChart.getYAxis();
        //是否绘制Y轴坐标点  和雷达框数据一般不同时存在 否则显得很挤 默认为true
        yAxis.setDrawLabels(false);
        yAxis.setTextColor(Color.GRAY);//Y轴坐标数据的颜色
        yAxis.setAxisMaximum(80);   //Y轴最大数值
        yAxis.setAxisMinimum(0);   //Y轴最小数值
        //Y轴坐标的个数    第二个参数一般填false     true表示强制设置标签数 可能会导致X轴坐标显示不全等问题
        yAxis.setLabelCount(5,false);


        //对于右下角一串字母的操作
        mRadarChart.getDescription().setEnabled(false);                  //是否显示右下角描述
        mRadarChart.getDescription().setText("这是修改那串英文的方法");    //修改右下角字母的显示
        mRadarChart.getDescription().setTextSize(16);                    //字体大小
        mRadarChart.getDescription().setTextColor(Color.CYAN);             //字体颜色

        //图例
        Legend rlegend = mRadarChart.getLegend();
        rlegend.setEnabled(true);    //是否显示图例
        rlegend.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);    //图例的位置
        // 以上雷达图创建完成


        mView.findViewById(R.id.btn_show).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<ILineDataSet> dataSets = mLineChart.getLineData().getDataSets();
                for (ILineDataSet set : dataSets)
                    set.setVisible(!set.isVisible());
                mLineChart.animateXY(500, 500);
                mLineChart.invalidate();
            }
        });

        mView.findViewById(R.id.btn_get_data).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog = new AlertDialog.Builder(getContext())
                        .setTitle("Current weather data")//设置对话框的标题
                        .setMessage("Temperature:  " + current_temp  + "℃" + "\n Humidity:  " + current_humidity +"%"
                        + "\n UV Index:  " + current_uv + "\n atmosphere pressure: " + current_atmos +"h/Pa"
                        + "\n wind speed:  " + current_wind + "m/s")//设置对话框的内容
                        //设置对话框的按钮
                        .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).create();
                dialog.show();


            }
        });

//        mView.findViewById(R.id.btn_delete).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //清空数据
//                mLineChart.getLineData().clearValues();
//                mLineChart.highlightValues(null);
//                mLineChart.invalidate();
//            }
//        });


        mView.findViewById(R.id.btn_slide).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                float scaleX = mLineChart.getScaleX();
                if (scaleX == 1)
                    mLineChart.zoomToCenter(5, 1f);
                else {
                    BarLineChartTouchListener barLineChartTouchListener = (BarLineChartTouchListener) mLineChart.getOnTouchListener();
                    barLineChartTouchListener.stopDeceleration();
                    mLineChart.fitScreen();
                }

                mLineChart.invalidate();
            }
        });

        //1.设置x轴和y轴的点
        for (int i = 0; i < list_radar.size(); i++){
            list_radar_temp.add(list_radar.get(i).get(0));
        }

        List<Entry> entries = new ArrayList<>();
        for (int i = 0; i < 24; i++)
            entries.add(new Entry(i, (float) (list_radar_temp.get(i).doubleValue())));

        //2.把数据赋值到你的线条
        LineDataSet dataSet = new LineDataSet(entries, "Label"); // add entries to dataset
        dataSet.setDrawCircles(false);
        dataSet.setColor(Color.parseColor("#7d7d7d"));//线条颜色
        dataSet.setCircleColor(Color.parseColor("#7d7d7d"));//圆点颜色
        dataSet.setLineWidth(1.5f);//线条宽度
        mLineChart.setScaleEnabled(false);

        //mLineChart.getLineData().getDataSets().get(0).setVisible(true);
        //设置样式
        YAxis rightAxis = mLineChart.getAxisRight();
        //设置图表右边的y轴禁用
        rightAxis.setEnabled(false);
        YAxis leftAxis = mLineChart.getAxisLeft();
        //设置图表左边的y轴
        leftAxis.setEnabled(true);
        rightAxis.setAxisMaximum(dataSet.getYMax() + 10);
        leftAxis.setAxisMaximum(dataSet.getYMax() + 10);
        //设置x轴
        XAxis xAxis = mLineChart.getXAxis();
        xAxis.setTextColor(Color.parseColor("#333333"));
        xAxis.setTextSize(11f);
        xAxis.setAxisMinimum(0f);
        xAxis.setDrawAxisLine(true);//是否绘制轴线
        xAxis.setDrawGridLines(false);//设置x轴上每个点对应的线
        xAxis.setDrawLabels(true);//绘制标签  指x轴上的对应数值
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//设置x轴的显示位置
        xAxis.setGranularity(1f);//禁止放大x轴标签重绘
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            list.add(String.valueOf(i + 1).concat(":05"));
        }
        xAxis.setValueFormatter(new IndexAxisValueFormatter(list));

        //透明化图例
        Legend legend = mLineChart.getLegend();
        legend.setForm(Legend.LegendForm.NONE);
        legend.setTextColor(Color.WHITE);
        //legend.setYOffset(-2);

        //点击图表坐标监听
        mLineChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                //查看覆盖物是否被回收
                if (mLineChart.isMarkerAllNull()) {
                    //重新绑定覆盖物
                    createMakerView();
                    //并且手动高亮覆盖物
                    mLineChart.highlightValue(h);
                }
            }

            @Override
            public void onNothingSelected() {

            }
        });

        //隐藏x轴描述
        Description description = new Description();
        description.setEnabled(false);
        mLineChart.setDescription(description);

        //创建覆盖物
        createMakerView();

        //3.chart设置数据
        LineData lineData = new LineData(dataSet);
        //是否绘制线条上的文字
        lineData.setDrawValues(false);
        mLineChart.setData(lineData);
        mLineChart.invalidate(); // refresh



//        if (current_temp >= 60){
//            //启动Notification
//            Intent intent = new Intent(getContext(), WeatherActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//            PendingIntent pendingIntent = PendingIntent.getActivity(getContext(), 0, intent, 0);
//            String channelId = createNotificationChannel("my_channel_id", "my_channel_NAME", NotificationManager.IMPORTANCE_HIGH);
//            NotificationCompat.Builder notification = new NotificationCompat.Builder(getContext(), channelId)
//                    .setContentTitle("Note")
//                    .setContentText("Receive a message")
//                    .setContentIntent(pendingIntent)
//                    .setSmallIcon(R.mipmap.ic_launcher)
//                    .setPriority(NotificationCompat.PRIORITY_HIGH)
//                    .setStyle(new NotificationCompat.BigTextStyle()
//                            .bigText("尊敬的用户您好，由于当前海滩气候异常，请您合理安排时间，避免高温中暑。谢谢您的配合！"))
//                    .setAutoCancel(true);
//            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getContext());
//            notificationManager.notify(100, notification.build());
//        }
//        if (current_humidity >=60 ){
//            //启动Notification
//            Intent intent = new Intent(getContext(), OrderActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//            PendingIntent pendingIntent = PendingIntent.getActivity(getContext(), 0, intent, 0);
//            String channelId = createNotificationChannel("my_channel_id", "my_channel_NAME", NotificationManager.IMPORTANCE_HIGH);
//            NotificationCompat.Builder notification = new NotificationCompat.Builder(getContext(), channelId)
//                    .setContentTitle("Note")
//                    .setContentText("Receive a new message")
//                    .setContentIntent(pendingIntent)
//                    .setSmallIcon(R.mipmap.ic_launcher)
//                    .setPriority(NotificationCompat.PRIORITY_HIGH)
//                    .setStyle(new NotificationCompat.BigTextStyle()
//                            .bigText("尊敬的用户您好，由于当前海滩气候异常，请您合理安排时间，避免高温中暑。谢谢您的配合！"))
//                    .setAutoCancel(true);
//            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getContext());
//            notificationManager.notify(100, notification.build());
//        }
//        if (current_uv >= 60){
//            //启动Notification
//            Intent intent = new Intent(getContext(), OrderActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//            PendingIntent pendingIntent = PendingIntent.getActivity(getContext(), 0, intent, 0);
//            String channelId = createNotificationChannel("my_channel_id", "my_channel_NAME", NotificationManager.IMPORTANCE_HIGH);
//            NotificationCompat.Builder notification = new NotificationCompat.Builder(getContext(), channelId)
//                    .setContentTitle("Note")
//                    .setContentText("Receive a new message")
//                    .setContentIntent(pendingIntent)
//                    .setSmallIcon(R.mipmap.ic_launcher)
//                    .setPriority(NotificationCompat.PRIORITY_HIGH)
//                    .setStyle(new NotificationCompat.BigTextStyle()
//                            .bigText("尊敬的用户您好，由于当前海滩气候异常，请您合理安排时间，避免高温中暑。谢谢您的配合！"))
//                    .setAutoCancel(true);
//            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getContext());
//            notificationManager.notify(100, notification.build());
//        }
//        if (current_atmos >= 60){
//            //启动Notification
//            Intent intent = new Intent(getContext(), OrderActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//            PendingIntent pendingIntent = PendingIntent.getActivity(getContext(), 0, intent, 0);
//            String channelId = createNotificationChannel("my_channel_id", "my_channel_NAME", NotificationManager.IMPORTANCE_HIGH);
//            NotificationCompat.Builder notification = new NotificationCompat.Builder(getContext(), channelId)
//                    .setContentTitle("Note")
//                    .setContentText("Receive a new message")
//                    .setContentIntent(pendingIntent)
//                    .setSmallIcon(R.mipmap.ic_launcher)
//                    .setPriority(NotificationCompat.PRIORITY_HIGH)
//                    .setStyle(new NotificationCompat.BigTextStyle()
//                            .bigText("尊敬的用户您好，由于当前海滩气候异常，请您合理安排时间，避免高温中暑。谢谢您的配合！"))
//                    .setAutoCancel(true);
//            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getContext());
//            notificationManager.notify(100, notification.build());
//        }
//        if (current_wind >= 60){
//            //启动Notification
//            Intent intent = new Intent(getContext(), OrderActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//            PendingIntent pendingIntent = PendingIntent.getActivity(getContext(), 0, intent, 0);
//            String channelId = createNotificationChannel("my_channel_id", "my_channel_NAME", NotificationManager.IMPORTANCE_HIGH);
//            NotificationCompat.Builder notification = new NotificationCompat.Builder(getContext(), channelId)
//                    .setContentTitle("Note")
//                    .setContentText("Receive a new message")
//                    .setContentIntent(pendingIntent)
//                    .setSmallIcon(R.mipmap.ic_launcher)
//                    .setPriority(NotificationCompat.PRIORITY_HIGH)
//                    .setStyle(new NotificationCompat.BigTextStyle()
//                            .bigText("尊敬的用户您好，由于当前海滩气候异常，请您合理安排时间，避免高温中暑。谢谢您的配合！"))
//                    .setAutoCancel(true);
//            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getContext());
//            notificationManager.notify(100, notification.build());
//        }


        return mView;
    }

    public void createMakerView() {
        DetailsMarkerView detailsMarkerView = new DetailsMarkerView(mContext);
        detailsMarkerView.setChartView(mLineChart);
        mLineChart.setDetailsMarkerView(detailsMarkerView);
        mLineChart.setPositionMarker(new PositionMarker(mContext));
        mLineChart.setRoundMarker(new RoundMarker(mContext));
    }

//    private String createNotificationChannel (String channelID, String channelNAME,int level){
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//            NotificationManager manager = (NotificationManager) getContext().getSystemService(channelID);
//            NotificationChannel channel = new NotificationChannel(channelID, channelNAME, level);
////            //设置提示音
////            channel.setSound();
//            // 开启指示灯
//            channel.enableLights(true);
//            //开启震动
//            channel.enableVibration(true);
////            //设置锁屏展示
////            channel.setLockscreenVisibility();
//            //设置渠道描述
//            channel.setDescription("A notification channel for warning the user.");
//
//            manager.createNotificationChannel(channel);
//            return channelID;
//        } else {
//            return null;

}


