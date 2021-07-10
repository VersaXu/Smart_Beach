package com.example.myhelloworldapplication.marker;

import android.content.Context;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;
import com.example.myhelloworldapplication.R;

import java.math.BigDecimal;

public class DetailsMarkerView extends MarkerView{
    private TextView mTvMonth;
    private TextView mTvChart;

    public DetailsMarkerView(Context context) {
        super(context, R.layout.item_chart_des_maker);
        mTvChart = findViewById(R.id.tv_chart_month);
        mTvMonth = findViewById(R.id.tv_chart_1);
    }
    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        super.refreshContent(e, highlight);
        try {
            //收入
            if (e.getY() == 0) {
                mTvChart.setText("暂无数据");
            } else {
                mTvChart.setText(concat(e.getY(), "value："));
            }
            mTvMonth.setText(String.valueOf((int) e.getX() + 1).concat(":05"));
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        super.refreshContent(e, highlight);
    }


    @Override
    public MPPointF getOffset() {
        return new MPPointF(-(getWidth() / 2), -getHeight());
    }


    public String concat(float money, String values) {
        return values + new BigDecimal(money).setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "℃";
    }

}
