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
    protected View mView; // ????????????????????????
    protected Context mContext; // ???????????????????????????
    MyLineChart mLineChart;
    RadarChart mRadarChart;

//    List<RadarEntry> list_radar = new ArrayList<>();
    // ????????????
    List<List<Double>> list_radar = new ArrayList<>();
    List<Double> list_radar_change = new ArrayList<>();
    List<Double> list_radar_temp = new ArrayList<>();
    // ????????????????????????????????????
    List<RadarEntry> list_radar_item = new ArrayList<>();
    List<RadarEntry> list1 = new ArrayList<>();
    List<RadarEntry> list2 = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity(); // ??????????????????????????????
        mView = inflater.inflate(R.layout.activity_weather, null); // ????????????
        mRadarChart = mView.findViewById(R.id.radar_test);
        mLineChart = mView.findViewById(R.id.chart); // ??????????????????????????????

        // ??????json??????
        StringBuilder sb = new StringBuilder();

        // ?????? assets/?????????json????????????
        try {
            //??????assets???????????????json??????
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



        //??????????????????
        List<ChannelBean> channels = new ArrayList<ChannelBean>();

        List<FeedBean> feed = new ArrayList<FeedBean>();

        try {

            //???sb??????????????????jsonObject
            JSONObject jsonObject = new JSONObject(sb.toString());

            //???jsonObject??????????????????channel??????JSON??????
            JSONObject jsonObject1 = jsonObject.getJSONObject("channel");

            //?????????ChannelBean()
            ChannelBean channelBean = new ChannelBean();

            //??????channel??????????????????
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




            //???jsonObject??????????????????feeds??????JSON??????
            JSONArray jsonArray = jsonObject.getJSONArray("feed");

            //????????????
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject2 = (JSONObject) jsonArray.get(i);

                ////?????????ChannelBean()
                FeedBean feedBean = new FeedBean();

                //??????feeds??????????????????
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

        //???????????????sb??????(??????sb)
        sb = new StringBuilder();


        //??????channel????????????
        for ( ChannelBean lang : channels){
            //??????????????????????????????????????????

            sb.append(lang.getName()+lang.getDescription()+"\n");

        }

        //??????feeds????????????
        for ( FeedBean lang : feed){
            //??????????????????????????????????????????

            sb.append("Time" + lang.getField1().toString()+"\n");

            System.out.println(sb);

            // ??????????????????????????????????????????????????????????????????
            list_radar.add(lang.getField1());


        }
        System.out.println(list_radar);


        // ??????????????????????????????????????????????????????
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

        // ?????????????????????
        RadarDataSet radarDataSet = new RadarDataSet(list_radar_item,"current weather");
        radarDataSet.setColor(Color.RED);
        radarDataSet.setLineWidth(1.2f);
        // safe ??????
        RadarDataSet radarDataSet1 = new RadarDataSet(list1, "safe");
        radarDataSet1.setColor(Color.GREEN);
        radarDataSet1.setFillColor(Color.GREEN);
        radarDataSet1.setDrawFilled(true);
        // red ??????
        RadarDataSet radarDataSet2 = new RadarDataSet(list2, "risk");
        radarDataSet2.setColor(Color.YELLOW);
        radarDataSet2.setFillColor(Color.YELLOW);
        radarDataSet2.setDrawFilled(true);
        // ????????????
        RadarData radarData = new RadarData(radarDataSet);
        radarData.addDataSet(radarDataSet1);
        radarData.addDataSet(radarDataSet2);
        mRadarChart.setData(radarData);

        //Y????????????????????????????????????????????????????????????Y????????????
        mRadarChart.getYAxis().setAxisMinimum(0);

        //???????????????????????????????????????????????????
        mRadarChart.setWebColor(Color.BLACK);
        mRadarChart.setWebLineWidthInner(0.8f);
        //????????????????????????,????????????
        mRadarChart.setWebColorInner(Color.BLACK);
        mRadarChart.setWebLineWidth(0.8f);
        //???????????????????????????
        mRadarChart.setBackgroundColor(Color.WHITE);

        XAxis rxAxis = mRadarChart.getXAxis();
        rxAxis.setTextColor(Color.BLUE);//X???????????????
        rxAxis.setTextSize(10);     //X???????????????
        //?????????X???????????????????????????????????????????????????,?????????0???1???2???3???4???
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


        //??????????????????????????????????????????????????????    ???Y????????????????????????????????? ??????????????????  ?????????true
        radarDataSet.setDrawValues(true);
        radarDataSet.setValueTextSize(12);  //???????????????????????????????????????????????????
        radarDataSet.setValueTextColor(Color.BLACK);//???????????????????????????????????????????????????

        YAxis yAxis = mRadarChart.getYAxis();
        //????????????Y????????????  ??????????????????????????????????????? ?????????????????? ?????????true
        yAxis.setDrawLabels(false);
        yAxis.setTextColor(Color.GRAY);//Y????????????????????????
        yAxis.setAxisMaximum(80);   //Y???????????????
        yAxis.setAxisMinimum(0);   //Y???????????????
        //Y??????????????????    ????????????????????????false     true??????????????????????????? ???????????????X??????????????????????????????
        yAxis.setLabelCount(5,false);


        //????????????????????????????????????
        mRadarChart.getDescription().setEnabled(false);                  //???????????????????????????
        mRadarChart.getDescription().setText("?????????????????????????????????");    //??????????????????????????????
        mRadarChart.getDescription().setTextSize(16);                    //????????????
        mRadarChart.getDescription().setTextColor(Color.CYAN);             //????????????

        //??????
        Legend rlegend = mRadarChart.getLegend();
        rlegend.setEnabled(true);    //??????????????????
        rlegend.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);    //???????????????
        // ???????????????????????????


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
                        .setTitle("Current weather data")//????????????????????????
                        .setMessage("Temperature:  " + current_temp  + "???" + "\n Humidity:  " + current_humidity +"%"
                        + "\n UV Index:  " + current_uv + "\n atmosphere pressure: " + current_atmos +"h/Pa"
                        + "\n wind speed:  " + current_wind + "m/s")//????????????????????????
                        //????????????????????????
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
//                //????????????
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

        //1.??????x??????y?????????
        for (int i = 0; i < list_radar.size(); i++){
            list_radar_temp.add(list_radar.get(i).get(0));
        }

        List<Entry> entries = new ArrayList<>();
        for (int i = 0; i < 24; i++)
            entries.add(new Entry(i, (float) (list_radar_temp.get(i).doubleValue())));

        //2.??????????????????????????????
        LineDataSet dataSet = new LineDataSet(entries, "Label"); // add entries to dataset
        dataSet.setDrawCircles(false);
        dataSet.setColor(Color.parseColor("#7d7d7d"));//????????????
        dataSet.setCircleColor(Color.parseColor("#7d7d7d"));//????????????
        dataSet.setLineWidth(1.5f);//????????????
        mLineChart.setScaleEnabled(false);

        //mLineChart.getLineData().getDataSets().get(0).setVisible(true);
        //????????????
        YAxis rightAxis = mLineChart.getAxisRight();
        //?????????????????????y?????????
        rightAxis.setEnabled(false);
        YAxis leftAxis = mLineChart.getAxisLeft();
        //?????????????????????y???
        leftAxis.setEnabled(true);
        rightAxis.setAxisMaximum(dataSet.getYMax() + 10);
        leftAxis.setAxisMaximum(dataSet.getYMax() + 10);
        //??????x???
        XAxis xAxis = mLineChart.getXAxis();
        xAxis.setTextColor(Color.parseColor("#333333"));
        xAxis.setTextSize(11f);
        xAxis.setAxisMinimum(0f);
        xAxis.setDrawAxisLine(true);//??????????????????
        xAxis.setDrawGridLines(false);//??????x???????????????????????????
        xAxis.setDrawLabels(true);//????????????  ???x?????????????????????
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//??????x??????????????????
        xAxis.setGranularity(1f);//????????????x???????????????
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            list.add(String.valueOf(i + 1).concat(":05"));
        }
        xAxis.setValueFormatter(new IndexAxisValueFormatter(list));

        //???????????????
        Legend legend = mLineChart.getLegend();
        legend.setForm(Legend.LegendForm.NONE);
        legend.setTextColor(Color.WHITE);
        //legend.setYOffset(-2);

        //????????????????????????
        mLineChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                //??????????????????????????????
                if (mLineChart.isMarkerAllNull()) {
                    //?????????????????????
                    createMakerView();
                    //???????????????????????????
                    mLineChart.highlightValue(h);
                }
            }

            @Override
            public void onNothingSelected() {

            }
        });

        //??????x?????????
        Description description = new Description();
        description.setEnabled(false);
        mLineChart.setDescription(description);

        //???????????????
        createMakerView();

        //3.chart????????????
        LineData lineData = new LineData(dataSet);
        //??????????????????????????????
        lineData.setDrawValues(false);
        mLineChart.setData(lineData);
        mLineChart.invalidate(); // refresh



//        if (current_temp >= 60){
//            //??????Notification
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
//                            .bigText("??????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????"))
//                    .setAutoCancel(true);
//            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getContext());
//            notificationManager.notify(100, notification.build());
//        }
//        if (current_humidity >=60 ){
//            //??????Notification
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
//                            .bigText("??????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????"))
//                    .setAutoCancel(true);
//            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getContext());
//            notificationManager.notify(100, notification.build());
//        }
//        if (current_uv >= 60){
//            //??????Notification
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
//                            .bigText("??????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????"))
//                    .setAutoCancel(true);
//            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getContext());
//            notificationManager.notify(100, notification.build());
//        }
//        if (current_atmos >= 60){
//            //??????Notification
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
//                            .bigText("??????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????"))
//                    .setAutoCancel(true);
//            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getContext());
//            notificationManager.notify(100, notification.build());
//        }
//        if (current_wind >= 60){
//            //??????Notification
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
//                            .bigText("??????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????"))
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
////            //???????????????
////            channel.setSound();
//            // ???????????????
//            channel.enableLights(true);
//            //????????????
//            channel.enableVibration(true);
////            //??????????????????
////            channel.setLockscreenVisibility();
//            //??????????????????
//            channel.setDescription("A notification channel for warning the user.");
//
//            manager.createNotificationChannel(channel);
//            return channelID;
//        } else {
//            return null;

}


