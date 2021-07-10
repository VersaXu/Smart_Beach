package com.example.myhelloworldapplication;

import android.graphics.Color;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

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
import com.github.mikephil.charting.utils.ColorTemplate;
//import com.github.mikephil.charting.renderer.RadarChartRenderer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WeatherActivity extends AppCompatActivity {

    MyLineChart mLineChart;
    RadarChart mRadarChart;

    List<RadarEntry> list;
    List<RadarEntry> list1;
    List<RadarEntry> list2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        mLineChart = findViewById(R.id.chart);
        mRadarChart = findViewById(R.id.radar_test);

        list = new ArrayList<>();
        list1 = new ArrayList<>(); // list1 for the red level in this radar chart
        list2 = new ArrayList<>(); // list2 for the safe level in this radar chart

//        list.add(new RadarEntry((int) (Math.random() * 90)));
//        list.add(new RadarEntry(35));
//        list.add(new RadarEntry(40));
//        list.add(new RadarEntry(35));
//        list.add(new RadarEntry(20));


        // Add data to main radar chart (赋y轴值)
        for (int i = 0; i < 5; i++) list.add(new RadarEntry((int) (Math.random() * 90)));
        // Add fixed data to red region
        for (int i = 0; i < 5; i++) list2.add(new RadarEntry(100));
        // Add fixed data to red region
        for (int i = 0; i < 5; i++) list1.add(new RadarEntry(60));

        // 最上层，主要图
        RadarDataSet radarDataSet = new RadarDataSet(list,"current");
        radarDataSet.setColor(Color.RED);
        radarDataSet.setLineWidth(1.2f);
        // safe 部分
        RadarDataSet radarDataSet1 = new RadarDataSet(list1, "safe");
        radarDataSet1.setColor(Color.GREEN);
        radarDataSet1.setFillColor(Color.GREEN);
        radarDataSet1.setDrawFilled(true);
        // red 部分
        RadarDataSet radarDataSet2 = new RadarDataSet(list2, "有风险");
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
        rxAxis.setTextSize(16);     //X轴字体大小
        //自定义X轴坐标描述
        rxAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float v, AxisBase axisBase) {
                if (v==0){
                    return "气温";
                }
                if (v==1){
                    return "水温";
                }
                if (v==2){
                    return "紫外线";
                }
                if (v==3){
                    return "风力";
                }
                if (v==4){
                    return "大气压";
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

        findViewById(R.id.btn_show).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<ILineDataSet> dataSets = mLineChart.getLineData().getDataSets();
                for (ILineDataSet set : dataSets)
                    set.setVisible(!set.isVisible());
                mLineChart.animateXY(500, 500);
                mLineChart.invalidate();
            }
        });

        findViewById(R.id.btn_update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //1,准备要更换的数据
                List<Entry> entries = new ArrayList<>();
                for (int i = 0; i < 24; i++)
                    entries.add(new Entry(i, new Random().nextInt(30)));

                //2. 获取LineDataSet线条数据集
                List<ILineDataSet> dataSets = mLineChart.getLineData().getDataSets();

                //是否存在
                if (dataSets != null && dataSets.size() > 0) {
                    //直接更换数据源
                    for (ILineDataSet set : dataSets) {
                        LineDataSet data = (LineDataSet) set;
                        data.setValues(entries);
                    }
                } else {
                    //重新生成LineDataSet线条数据集
                    LineDataSet dataSet = new LineDataSet(entries, "Label"); // add entries to dataset
                    dataSet.setDrawCircles(false);
                    dataSet.setColor(Color.parseColor("#7d7d7d"));//线条颜色
                    dataSet.setCircleColor(Color.parseColor("#7d7d7d"));//圆点颜色
                    dataSet.setLineWidth(1.5f);//线条宽度
                    LineData lineData = new LineData(dataSet);
                    //是否绘制线条上的文字
                    lineData.setDrawValues(false);
                    mLineChart.setData(lineData);
                }
                //更新
                mLineChart.invalidate();
            }
        });

        findViewById(R.id.btn_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //清空数据
                mLineChart.getLineData().clearValues();
                mLineChart.highlightValues(null);
                mLineChart.invalidate();
            }
        });


        findViewById(R.id.btn_slide).setOnClickListener(new View.OnClickListener() {
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
        List<Entry> entries = new ArrayList<>();
        for (int i = 0; i < 24; i++){
            entries.add(new Entry(i, new Random().nextInt(30)));
            System.out.println(entries.get(i));
        }


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
        rightAxis.setAxisMaximum(dataSet.getYMax() * 2);
        leftAxis.setAxisMaximum(dataSet.getYMax() * 2);
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
            list.add(String.valueOf(i + 1).concat(":00"));
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

    }

    /**
     * 创建覆盖物
     */
    public void createMakerView() {
        DetailsMarkerView detailsMarkerView = new DetailsMarkerView(this);
        detailsMarkerView.setChartView(mLineChart);
        mLineChart.setDetailsMarkerView(detailsMarkerView);
        mLineChart.setPositionMarker(new PositionMarker(this));
        mLineChart.setRoundMarker(new RoundMarker(this));
    }

}