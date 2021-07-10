package com.example.myhelloworldapplication.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;

import com.example.myhelloworldapplication.R;

import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.interfaces.datasets.IRadarDataSet;

import java.util.ArrayList;


public class RadarFragment extends Fragment {
    private RadarChart radarChart;
    private RadarData radarData;

    //x 轴数据
    private ArrayList<String> xDatas = new ArrayList<>();
    //y 轴数据
    private ArrayList<RadarEntry> yDatas = new ArrayList<>();
    private ArrayList<RadarEntry> yDatas2 = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 填充数据
        xDatas.add("气温");
        xDatas.add("水温");
        xDatas.add("紫外线");
        xDatas.add("风力");
        xDatas.add("气压");
        xDatas.add("湿度");

        for (int i = 0; i < 6; i++) {
            yDatas.add((RadarEntry) new Entry((float) (Math.random() * 10), i));
            yDatas2.add((RadarEntry) new Entry((float) (Math.random() * 10), i));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.radar_fragment, container, false);
        radarChart = view.findViewById(R.id.radarchart_f);
        radarData = getRadarData();
        showRadarChart(radarChart, radarData);
        return view;
    }

    /**
     * 赔指数型并显示雷达图
     * @param radarChart
     * @param radarData
     */
    private void showRadarChart(RadarChart radarChart, RadarData radarData) {
////        描述
//        radarChart.setDescription("雷达图");
//        //可旋转
//        radarChart.setRotationEnabled(true);
//        //Description位置
//        radarChart.setDescrip(750, 70);
//        //Description字体
//        radarChart.setDescriptionTextSize(50);
        //花蜘蛛网
        radarChart.setDrawWeb(true);
        //设置背景颜色
        radarChart.setBackgroundColor(Color.rgb(255, 102, 0));
        //设置Web主干颜色
        radarChart.setWebLineWidth(1);
        //设置Web主干线宽
        radarChart.setWebColor(Color.rgb(255, 255, 0));
        //设置Web支线颜色
        radarChart.setWebColorInner(Color.rgb(255, 255, 0));
        //设置Web支线线宽
        radarChart.setWebLineWidthInner(1);

        //设置图例
        Legend legend = radarChart.getLegend();
        legend.setPosition(Legend.LegendPosition.LEFT_OF_CHART_INSIDE);//图例位置
        legend.setForm(Legend.LegendForm.CIRCLE);//图例颜色块形状

        radarChart.setData(radarData);//填充数据
        radarChart.invalidate();//刷新界面
    }

    /**
     * 得到数据
     * @return
     */
    public RadarData getRadarData() {
        RadarDataSet radarDataSet = new RadarDataSet(yDatas, "属性");
        radarDataSet.setColor(Color.rgb(255, 99, 71));//设置雷达图外边框颜色
        radarDataSet.setFillColor(Color.rgb(255, 174, 185));//设置雷达图内部颜色填充
        radarDataSet.setDrawFilled(true);//填充
        radarDataSet.setFillAlpha(180);//设置透明度
        radarDataSet.setLineWidth(2f);//设置雷达图边界线宽
        radarDataSet.setDrawHighlightCircleEnabled(true);
        radarDataSet.setDrawHighlightIndicators(false);

        RadarDataSet radarDataSet2 = new RadarDataSet(yDatas2, "第二组属性");
        radarDataSet2.setColor(Color.rgb(121, 162, 175));
        radarDataSet2.setFillColor(Color.rgb(147, 112, 219));
        radarDataSet2.setDrawFilled(true);
        radarDataSet2.setFillAlpha(180);
        radarDataSet2.setLineWidth(2f);
        radarDataSet2.setDrawHighlightCircleEnabled(true);
        radarDataSet2.setDrawHighlightIndicators(false);

        //填充Y轴数据
        ArrayList<IRadarDataSet> radarDataSets = new ArrayList<>();
        radarDataSets.add(radarDataSet);
        radarDataSets.add(radarDataSet2);

        RadarData radarData = new RadarData();//生成RadarData
        return radarData;
    }
}
