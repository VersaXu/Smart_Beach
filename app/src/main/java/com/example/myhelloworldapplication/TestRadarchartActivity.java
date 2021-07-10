package com.example.myhelloworldapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.ArrayList;
import java.util.List;

public class TestRadarchartActivity extends AppCompatActivity {

    private RadarChart radar;
    List<RadarEntry> list;
    List<RadarEntry> list2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_radarchart);
        radar = (RadarChart) findViewById(R.id.radar_test);
        list=new ArrayList<>();
        list2=new ArrayList<>();

        list.add(new RadarEntry(30));
        list.add(new RadarEntry(35));
        list.add(new RadarEntry(40));
        list.add(new RadarEntry(35));
        list.add(new RadarEntry(20));

        list2.add(new RadarEntry(50));
        list2.add(new RadarEntry(45));
        list2.add(new RadarEntry(55));
        list2.add(new RadarEntry(40));
        list2.add(new RadarEntry(60));

        RadarDataSet radarDataSet=new RadarDataSet(list,"男性");
        radarDataSet.setColor(Color.RED);
        RadarDataSet radarDataSet1=new RadarDataSet(list2,"女性");
        radarDataSet1.setColor(Color.BLUE);
        RadarData radarData=new RadarData(radarDataSet);
        radarData.addDataSet(radarDataSet1);
        radar.setData(radarData);

        //Y轴最小值不设置会导致数据中最小值默认成为Y轴最小值
        radar.getYAxis().setAxisMinimum(0);

        //大字的颜色（中心点和各顶点的连线）
        radar.setWebColor(Color.CYAN);
        //所有五边形的颜色
        radar.setWebColorInner(Color.CYAN);
        //整个控件的背景颜色
        radar.setBackgroundColor(Color.LTGRAY);

        XAxis xAxis=radar.getXAxis();
        xAxis.setTextColor(Color.RED);//X轴字体颜色
        xAxis.setTextSize(16);     //X轴字体大小
        //自定义X轴坐标描述（也就是五个顶点上的文字,默认是0、1、2、3、4）
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float v, AxisBase axisBase) {
                if (v==0){
                    return "语文";
                }
                if (v==1){
                    return "数学";
                }
                if (v==2){
                    return "英语";
                }
                if (v==3){
                    return "生物";
                }
                if (v==4){
                    return "地理";
                }
                return "";
            }
        });


        //是否绘制雷达框上对每个点的数据的标注    和Y轴坐标点一般不同时存在 否则显得很挤  默认为true
        radarDataSet.setDrawValues(false);
        radarDataSet1.setDrawValues(false);
        radarDataSet.setValueTextSize(12);  //数据值得字体大小（这里只是写在这）
        radarDataSet.setValueTextColor(Color.CYAN);//数据值得字体颜色（这里只是写在这）

        YAxis yAxis=radar.getYAxis();
        //是否绘制Y轴坐标点  和雷达框数据一般不同时存在 否则显得很挤 默认为true
        yAxis.setDrawLabels(true);
        yAxis.setTextColor(Color.GRAY);//Y轴坐标数据的颜色
        yAxis.setAxisMaximum(80);   //Y轴最大数值
        yAxis.setAxisMinimum(0);   //Y轴最小数值
        //Y轴坐标的个数    第二个参数一般填false     true表示强制设置标签数 可能会导致X轴坐标显示不全等问题
        yAxis.setLabelCount(10,false);


        //对于右下角一串字母的操作
        radar.getDescription().setEnabled(false);                  //是否显示右下角描述
        radar.getDescription().setText("这是修改那串英文的方法");    //修改右下角字母的显示
        radar.getDescription().setTextSize(20);                    //字体大小
        radar.getDescription().setTextColor(Color.CYAN);             //字体颜色

        //图例
        Legend legend=radar.getLegend();
        legend.setEnabled(true);    //是否显示图例
        legend.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);    //图例的位置

    }
}