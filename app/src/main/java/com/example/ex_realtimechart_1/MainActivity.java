package com.example.ex_realtimechart_1;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;
import java.util.Timer;
import java.util.TimerTask;
/* mpChart 테스트용 */
public class MainActivity extends AppCompatActivity {
    private LineChart chart1;
    private LineChart chart2;
    private LineChart chart3;
    private Button btn;
    private Timer realBpmTask;
    private int yValue = 0;
    private float randomVal = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = findViewById(R.id.btn_1);
        chart1 = (LineChart) findViewById(R.id.linechart1);
        chart2 = (LineChart) findViewById(R.id.linechart2);
        chart3 = (LineChart) findViewById(R.id.linechart3);
        initGraph(chart1);
        initGraph(chart2);
        initGraph(chart3);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timer_multi();
                timer_singleData();
            }
        });
    }

    public void timer_singleData() {
        TimerTask heart_task = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new TimerTask() {
                    @Override
                    public void run() {
                        randomVal = (float) (Math.random() * 10); // 앱1 값
                        addEntry(randomVal, chart2);
                        addEntry(randomVal, chart1);
                    }
                });
            }
        };

        realBpmTask = new Timer();
        realBpmTask.schedule(heart_task, 0, 2000);
    }

    public void timer_multi() {
        TimerTask heart_task = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new TimerTask() {
                    @Override
                    public void run() {
                        float val2 = (float) (Math.random() * 10); // 앱1 값

                        float val1 = 10; // 앱1 값
                        addEntry(val1, chart1);
                        addEntry(val1, chart2);
                        addEntryMultiple(val2, chart3);
                    }
                });
            }
        };

        realBpmTask = new Timer();
        realBpmTask.schedule(heart_task, 0, 60);
    }

    private void initGraph(LineChart chart) {
        //Legend //
        Legend l = chart.getLegend();
        l.setEnabled(true);
        l.setFormSize(10f); // set the size of the legend forms/shapes
        l.setTextSize(12f);
        l.setTextColor(Color.WHITE);


        //배경 설정
        chart.setBackgroundColor(Color.BLACK);
        chart.setGridBackgroundColor(Color.BLACK);
// touch gestures (false-비활성화)
        chart.setTouchEnabled(true);
        chart.setScaleEnabled(true);
        chart.setDragEnabled(true);
        chart.setAutoScaleMinMaxEnabled(true);
        chart.getDescription().setEnabled(true);

// scaling and dragging (false-비활성화)
        chart.setDrawGridBackground(false);
        chart.setDrawBorders(false);
        chart.setPinchZoom(false);


        //차트 선
        Description des = chart.getDescription();
        des.setEnabled(true);
        des.setText("Real-Time DATA");
        des.setTextSize(12f);
        des.setTextColor(Color.WHITE);


//X축
        chart.getXAxis().setDrawGridLines(true);
        chart.getXAxis().setDrawAxisLine(false);
        chart.getXAxis().setEnabled(true);
        chart.getXAxis().setAvoidFirstLastClipping(true);
        chart.getXAxis().setDrawGridLines(false);
//        chart.getXAxis().setGranularity(1f);
        chart.getXAxis().setAxisLineWidth(2f);

//Y축
        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setEnabled(true);
        leftAxis.setTextColor(Color.rgb(118, 118, 118));

        leftAxis.setDrawGridLines(false);
        leftAxis.setGridColor(Color.rgb(163, 163, 163));

        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setEnabled(false);

// don't forget to refresh the drawing
        chart.invalidate();
    }

    private void addEntryMultiple(double num, LineChart lineChart) {
        LineData data = lineChart.getData();

        if (data == null) {
            data = new LineData();
            lineChart.setData(data);
        }

        ILineDataSet set = data.getDataSetByIndex(0);
        ILineDataSet set2 = data.getDataSetByIndex(1);

        // set.addEntry(...); // can be called as well

        /* Data Set이 없는 경우 */
        if (set == null) {
            set = createSet();
            data.addDataSet(set);
        }

        if (set2 == null) {
            set2 = createSet();
            data.addDataSet(set2);
        }


        data.addEntry(new Entry((float) set.getEntryCount(), (float) num), 0);
        data.addEntry(new Entry((float) set2.getEntryCount(), (float) num - 1), 1);
        data.notifyDataChanged();

        // let the chart know it's data has changed
        lineChart.notifyDataSetChanged();

        lineChart.setVisibleXRangeMaximum(50);
        // this automatically refreshes the chart (calls invalidate())
        lineChart.moveViewTo(data.getEntryCount(), 50f, YAxis.AxisDependency.LEFT);
    }

    private void addEntry(double num, LineChart lineChart) {
        LineData data = lineChart.getData();

        if (data == null) {
            data = new LineData();
            lineChart.setData(data);
        }

        ILineDataSet set = data.getDataSetByIndex(0);
        // set.addEntry(...); // can be called as well

        if (set == null) {
            set = createSet();
            data.addDataSet(set);
        }
        float xAxis = set.getEntryCount();
        float xAxis2 = xAxis -= (set.getEntryCount() * 0.8);
        data.addEntry(new Entry((float) xAxis2, (float) num), 0);
        data.notifyDataChanged();

        // let the chart know it's data has changed
        lineChart.notifyDataSetChanged();

        lineChart.setVisibleXRangeMaximum(50);
        // this automatically refreshes the chart (calls invalidate())
        lineChart.moveViewTo(data.getEntryCount(), 50f, YAxis.AxisDependency.LEFT);

    }

    private LineDataSet createSet() {
        LineDataSet set = new LineDataSet(null, "Real-time Line Data");
        set.setLineWidth(0.5f);
        set.setDrawValues(false);
        set.setValueTextColor(getResources().getColor(R.color.red));
        set.setColor(getResources().getColor(R.color.white));
        set.setDrawCircles(false);
        set.setHighLightColor(Color.rgb(190, 190, 190));
        set.setMode(LineDataSet.Mode.CUBIC_BEZIER);
//        set.setMode(LineDataSet.Mode.LINEAR);
        return set;
    }

    class TemperatureChartValueFormatter extends ValueFormatter {
        private DecimalFormat mFormat;

        public TemperatureChartValueFormatter() {
            this.mFormat = new DecimalFormat("##,##,#0.00");
        }

        @Override
        public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
            super.getFormattedValue(value, entry, dataSetIndex, viewPortHandler);
            return mFormat.format((double) value);
        }
    }
}