package com.ypf.myapp.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;
import com.ypf.myapp.R;
import com.ypf.myapp.view.HeightChartView;
import com.ypf.myapp.view.WeightChartView;

/**
 * Created by ypf on 2016/2/28.
 */
public class ChartViewActivity extends Activity {
    private Context context;
    private TextView text_weight;
    private WeightChartView weight_chi;
    private TextView text_height;
    private HeightChartView heightChartView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chartview);
        context = this;

        text_weight = (TextView) findViewById(R.id.text_weight);
        weight_chi = (WeightChartView) findViewById(R.id.weight_chi);
        weight_chi.setWeightOnChangedListener(weightOnChangedListener);

        text_height = (TextView) findViewById(R.id.text_height);
        heightChartView = (HeightChartView) findViewById(R.id.heightChartView);
        heightChartView.setHeightOnChangedListener(heightOnChangedListener);
    }

    private WeightChartView.WeightOnChangedListener weightOnChangedListener = new WeightChartView.WeightOnChangedListener() {
        @Override
        public void onChanged(float change) {
            text_weight.setText("" + change);
        }
    };

    private HeightChartView.HeightOnChangedListener heightOnChangedListener = new HeightChartView.HeightOnChangedListener() {
        @Override
        public void onChanged(float change) {
            text_height.setText("" + change);
        }
    };
}