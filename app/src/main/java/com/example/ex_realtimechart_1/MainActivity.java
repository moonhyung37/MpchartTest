package com.example.ex_realtimechart_1;

import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.github.mikephil.charting.charts.LineChart;
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
    private LineChart zoomInchart;
    private Button btnStart;
    private Button btnZoom;
    private Timer realBpmTask;
    private int yValue = 0;
    private float randomVal = 0;
    private float lastX = 0f;
    private boolean isChartShow = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnStart = findViewById(R.id.btn_Start);
        btnZoom = findViewById(R.id.btn_zoomIn);
        chart1 = (LineChart) findViewById(R.id.linechart1);
        chart2 = (LineChart) findViewById(R.id.linechart2);
        zoomInchart = findViewById(R.id.linechartZoomIn);
        initGraph(chart1);
        initAccrueChart(chart2);
        initZoomInChart(zoomInchart);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startMultiGraph();
                startSingleGraph();
            }
        });

        btnZoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isChartShow) {
                    isChartShow = false;
                    chart1.setVisibility(View.VISIBLE);
                    chart2.setVisibility(View.VISIBLE);
                    zoomInchart.setVisibility(View.GONE);
                } else {
                    isChartShow = true;
                    chart1.setVisibility(View.GONE);
                    chart2.setVisibility(View.GONE);
                    zoomInchart.setVisibility(View.VISIBLE);
                }
            }
        });


    }

    public void startSingleGraph() {
        TimerTask heart_task = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new TimerTask() {
                    @Override
                    public void run() {
                        randomVal = (float) (Math.random() * 10); // 앱1 값
//                        randomVal = 7; // 앱1 값
                        float val1 = 10f; // 앱1 값
                        addEntrySingleData(randomVal, chart2);
                        addEntryZoomInData(randomVal, zoomInchart);
//                        addEntrySingleData(randomVal, zoomInchart);
                    }
                });
            }
        };

        realBpmTask = new Timer();
        realBpmTask.schedule(heart_task, 0, 1000);
    }

    //
    public void startMultiGraph() {
        TimerTask heart_task = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new TimerTask() {
                    @Override
                    public void run() {
                        float val2 = (float) (Math.random() * 10); // 앱1 값

                        float val1 = 10; // 앱1 값
                        addEntryMultiple(val2, chart1);
                    }
                });
            }
        };

        realBpmTask = new Timer();
        realBpmTask.schedule(heart_task, 0, 200);
    }

    private void initAccrueChart(LineChart chart) {
        //배경 설정
        chart.setBackgroundColor(Color.BLACK);
        chart.setGridBackgroundColor(Color.BLACK);
        chart.setVisibleXRangeMinimum(5f);
        chart.getDescription().setEnabled(false);
        chart.setTouchEnabled(false);
        chart.setScaleEnabled(false);
        chart.setDragEnabled(false);
        chart.setAutoScaleMinMaxEnabled(true);

// scaling and dragging (false-비활성화)
        chart.setDrawGridBackground(false);
        chart.setDrawBorders(false);
        chart.setPinchZoom(false);


//X축
        chart.getXAxis().setDrawGridLines(true);
        chart.getXAxis().setDrawAxisLine(false);
        chart.getXAxis().setEnabled(true);
        chart.getXAxis().setAvoidFirstLastClipping(true);
        chart.getXAxis().setDrawGridLines(false);
        chart.getXAxis().setAxisLineWidth(2f);
//Y축
        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setAxisMinimum(0f);
        leftAxis.setAxisMaximum(12f);
        leftAxis.setEnabled(true);
        leftAxis.setTextColor(Color.rgb(118, 118, 118));
        leftAxis.setDrawGridLines(false);
        leftAxis.setGridColor(Color.rgb(163, 163, 163));
        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setEnabled(false);
    }


    private void initZoomInChart(LineChart chart) {
        //배경 설정
        chart.setBackgroundColor(Color.BLACK);
        chart.setGridBackgroundColor(Color.BLACK);
        chart.setVisibleXRangeMinimum(5f);
        chart.getDescription().setEnabled(false);
        chart.setTouchEnabled(true);
        chart.setScaleEnabled(true);
        chart.setDragEnabled(true);
        chart.setAutoScaleMinMaxEnabled(true);

// scaling and dragging (false-비활성화)
        chart.setDrawGridBackground(false);
        chart.setDrawBorders(false);
        chart.setPinchZoom(false);
//X축
        chart.getXAxis().setDrawGridLines(true);
        chart.getXAxis().setDrawAxisLine(false);
        chart.getXAxis().setEnabled(true);
        chart.getXAxis().setAvoidFirstLastClipping(true);
        chart.getXAxis().setDrawGridLines(false);
        chart.getXAxis().setAxisLineWidth(2f);
        chart.getXAxis().setAxisMaximum(chart.getMaxVisibleCount());

//Y축
        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setAxisMinimum(0f);
        leftAxis.setAxisMaximum(12f);
        leftAxis.setEnabled(true);
        leftAxis.setTextColor(Color.rgb(118, 118, 118));
        leftAxis.setDrawGridLines(false);
        leftAxis.setGridColor(Color.rgb(163, 163, 163));
        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setEnabled(false);
    }


    private void initGraph(LineChart chart) {
        //배경 설정
        chart.setBackgroundColor(Color.BLACK);
        chart.setGridBackgroundColor(Color.BLACK);
// touch gestures (false-비활성화)
        chart.setTouchEnabled(false);
        chart.setScaleEnabled(true);
        chart.setDragEnabled(true);
        chart.setAutoScaleMinMaxEnabled(false);
        chart.getDescription().setEnabled(false);

// scaling and dragging (false-비활성화)
        chart.setDrawGridBackground(false);
        chart.setDrawBorders(false);
        chart.setPinchZoom(false);


//X축
        chart.getXAxis().setDrawGridLines(true);
        chart.getXAxis().setDrawAxisLine(false);
        chart.getXAxis().setEnabled(true);
        chart.getXAxis().setAvoidFirstLastClipping(false);
        chart.getXAxis().setDrawGridLines(false);
        chart.getXAxis().setAxisLineWidth(2f);
        chart.getXAxis().setAxisMaximum(100);
//Y축
        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setEnabled(true);
        leftAxis.setTextColor(Color.rgb(118, 118, 118));
        leftAxis.setDrawGridLines(false);
        leftAxis.setGridColor(Color.rgb(163, 163, 163));
        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setEnabled(false);
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

    private void addEntrySingleData(float value, LineChart lineChart) {
        LineData data = lineChart.getData();

        if (data == null) {
            data = new LineData();
            lineChart.setData(data);
        }

        ILineDataSet set = data.getDataSetByIndex(0);
        // set.addEntry(...); // can be called as well

        if (set == null) {
            set = createSetLinear();
            data.addDataSet(set);
        }
        Toast.makeText(getApplicationContext(), "1111", Toast.LENGTH_SHORT).show();
        data.addEntry(new Entry(set.getEntryCount(), value), 0);

//        data.addEntry(new Entry((float) set2.getEntryCount(), (float) num - 1), 1);

        data.notifyDataChanged();
        final float highestVisibleIndex = chart2.getMaxVisibleCount();
        if (data.getEntryCount() > highestVisibleIndex) {
            lastX += 0.5;
        } else {
            lastX = lineChart.getMaxVisibleCount();
        }
        Log.e("set.getEntryCount()", String.valueOf(set.getEntryCount()));
        lineChart.getXAxis().setAxisMaximum(lastX);
        lineChart.notifyDataSetChanged();
        lineChart.invalidate();
    }

    private void addEntryZoomInData(float value, LineChart lineChart) {
        LineData data = lineChart.getData();

        if (data == null) {
            data = new LineData();
            lineChart.setData(data);
        }

        ILineDataSet set = data.getDataSetByIndex(0);
        // set.addEntry(...); // can be called as well
        if (set == null) {
            set = createSetLinear();
            data.addDataSet(set);
        }
        data.addEntry(new Entry(set.getEntryCount(), value), 0);
//        data.addEntry(new Entry((float) set2.getEntryCount(), (float) num - 1), 1);

        data.notifyDataChanged();
        final float highestVisibleIndex = lineChart.getMaxVisibleCount();
        if (data.getEntryCount() > highestVisibleIndex) {
            lastX += 0.5;
        } else {
            lastX = lineChart.getMaxVisibleCount();
        }
        Log.e("set.getEntryCount()", String.valueOf(set.getEntryCount()));
        lineChart.getXAxis().setAxisMaximum(lastX);
        lineChart.notifyDataSetChanged();
        lineChart.invalidate();
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

    private LineDataSet createSetLinear() {
        LineDataSet set = new LineDataSet(null, "Real-time Line Data");
        set.setLineWidth(0.5f);
        set.setCircleRadius(2.5f);
        set.setDrawValues(false);
        set.setDrawCircles(true);
        set.setValueTextColor(getResources().getColor(R.color.red));
        set.setColor(getResources().getColor(R.color.white));
        set.setHighLightColor(Color.rgb(190, 190, 190));
//        set.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        set.setMode(LineDataSet.Mode.LINEAR);
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