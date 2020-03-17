package com.kong.nightrunning;

import android.graphics.Color;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

public class StepNumberChartFragment extends Fragment {

    private CombinedChart stepNumberChart;
    public CombinedChartManager manager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recent_step_count, container, false);
        stepNumberChart = view.findViewById(R.id.stepNumberChart);
         manager = new CombinedChartManager(stepNumberChart);
        List<Float> barYValues = manager.getBarYAxisValues();
        List<Float> lineYValues = manager.getLineYAxisValues(barYValues);
        List<String> xAxisValues = manager.getXAxisValues(barYValues.size());

        manager.showCombinedChart(xAxisValues, barYValues, lineYValues, "步数(步数)", "卡路里(百卡)",
                Color.rgb(128, 0, 255),
                Color.rgb(255, 128, 0));
        return view;
    }

    class CombinedChartManager {

        private CombinedChart mCombinedChart;
        private YAxis leftAxis;
        private YAxis rightAxis;
        private XAxis xAxis;

        public CombinedChartManager(CombinedChart combinedChart) {
            this.mCombinedChart = combinedChart;
            leftAxis = mCombinedChart.getAxisLeft();
            rightAxis = mCombinedChart.getAxisRight();
            xAxis = mCombinedChart.getXAxis();
        }

        /**
         * 初始化Chart
         */
        private void initChart() {
            //不显示描述内容
            mCombinedChart.getDescription().setEnabled(false);

            mCombinedChart.setDrawOrder(new CombinedChart.DrawOrder[]{
                    CombinedChart.DrawOrder.BAR,
                    CombinedChart.DrawOrder.LINE
            });

            mCombinedChart.setBackgroundColor(Color.WHITE);
            mCombinedChart.setDrawGridBackground(false);
            mCombinedChart.setDrawBarShadow(false);
            mCombinedChart.setHighlightFullBarEnabled(false);
            //显示边界
            mCombinedChart.setDrawBorders(false);
            //图例说明
            Legend legend = mCombinedChart.getLegend();
            legend.setWordWrapEnabled(true);

            legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
            legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
            legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
            legend.setDrawInside(false);
            //Y轴设置
            leftAxis.setDrawGridLines(false);
            leftAxis.setAxisMinimum(0f);
            leftAxis.setDrawLabels(false);
            leftAxis.setEnabled(false);

            rightAxis.setDrawGridLines(false);
            rightAxis.setDrawLabels(false);
            rightAxis.setAxisMinimum(0f);
            rightAxis.setEnabled(false);

            mCombinedChart.animateX(0); // 立即执行的动画,x轴
        }

        /**
         * 设置X轴坐标值
         *
         * @param xAxisValues x轴坐标集合
         */
        public void setXAxis(final List<String> xAxisValues) {

            //设置X轴在底部
            final XAxis xAxis = mCombinedChart.getXAxis();
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setGranularity(1f);

            xAxis.setLabelCount(xAxisValues.size() - 1, false);
            xAxis.setValueFormatter(new ValueFormatter() {
                @Override
                public String getFormattedValue(float value) {
                    return xAxisValues.get((int) value);
                }
            });
            mCombinedChart.invalidate();
        }

        /**
         * 得到折线图(一条)
         *
         * @param lineChartY 折线Y轴值
         * @param lineName   折线图名字
         * @param lineColor  折线颜色
         * @return
         */
        private LineData getLineData(List<Float> lineChartY, String lineName, int lineColor) {
            LineData lineData = new LineData();

            ArrayList<Entry> yValue = new ArrayList<>();
            for (int i = 0; i < lineChartY.size(); i++) {
                yValue.add(new Entry(i, lineChartY.get(i)));
            }
            LineDataSet dataSet = new LineDataSet(yValue, lineName);

            dataSet.setColor(lineColor);
            //设置圆的半径
            dataSet.setCircleRadius(5f);
            dataSet.setCircleColor(lineColor);
            dataSet.setValueTextColor(lineColor);
            dataSet.setLineWidth(3f);

            //显示值
            dataSet.setDrawValues(true);
            dataSet.setValueTextSize(15f);
            dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            dataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
            lineData.addDataSet(dataSet);
            return lineData;
        }


        /**
         * 得到柱状图
         *
         * @param barChartY Y轴值
         * @param barName   柱状图名字
         * @param barColor  柱状图颜色
         * @return
         */

        private BarData getBarData(List<Float> barChartY, String barName, int barColor) {
            BarData barData = new BarData();
            ArrayList<BarEntry> yValues = new ArrayList<>();
            for (int i = 0; i < barChartY.size(); i++) {
                yValues.add(new BarEntry(i, barChartY.get(i)));
            }

            BarDataSet barDataSet = new BarDataSet(yValues, barName);
            barDataSet.setColor(barColor);
            barDataSet.setValueTextSize(20f);
            barDataSet.setValueTextColor(barColor);
            barDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
            barData.addDataSet(barDataSet);

            //以下是为了解决 柱状图 左右两边只显示了一半的问题 根据实际情况 而定
            xAxis.setAxisMinimum(-0.5f);
            xAxis.setAxisMaximum((float) (barChartY.size() - 0.5));
            return barData;
        }

        /**
         * 显示混合图(柱状图+折线图)
         *
         * @param xAxisValues X轴坐标
         * @param barChartY   柱状图Y轴值
         * @param lineChartY  折线图Y轴值
         * @param barName     柱状图名字
         * @param lineName    折线图名字
         * @param barColor    柱状图颜色
         * @param lineColor   折线图颜色
         */

        public void showCombinedChart(
                List<String> xAxisValues, List<Float> barChartY, List<Float> lineChartY
                , String barName, String lineName, int barColor, int lineColor) {
            initChart();
            setXAxis(xAxisValues);

            CombinedData combinedData = new CombinedData();
            combinedData.setData(getBarData(barChartY, barName, barColor));
            combinedData.setData(getLineData(lineChartY, lineName, lineColor));
            mCombinedChart.setData(combinedData);
            mCombinedChart.invalidate();
        }

        public List<String> getXAxisValues(int num) {
            List<String> xAxisData = new ArrayList<String>();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd");
            Calendar calendar = null;
            for (int i = num - 1; i >= 0; --i) {
                calendar = Calendar.getInstance();
                calendar.add(Calendar.DAY_OF_MONTH, -i);
                xAxisData.add(simpleDateFormat.format(calendar.getTime()));
            }
            return xAxisData;
        }

        public List<Float> getBarYAxisValues() {
            NightRunningDatabase helper = ((MainActivity) getActivity()).helper;
            List<Float> barYAxisValues= helper.selectRecentTimeStepNumber(helper.getReadableDatabase(), "测试1", "date('now','localtime','-7 days')");
            barYAxisValues.remove(barYAxisValues.size()-1);
            barYAxisValues.add(new Float(NightRunningSensorEventListener.getTodayAddStepNumber()));
            return barYAxisValues;
        }

        public List<Float> getLineYAxisValues(List<Float> barYAxisValues) {
            List<Float> lineYAXisValues = new ArrayList<Float>();
            for (int i = 0; i < barYAxisValues.size(); ++i) {
                //卡路里计算公式
                lineYAXisValues.add(new Float((barYAxisValues.get(i) * 50 * 0.8) / 100));
            }
            return lineYAXisValues;
        }

        public void drawCombinedChart(){
            CombinedData combinedData = new CombinedData();
            combinedData.setData(getBarData(getBarYAxisValues(), "步数(步)", Color.rgb(128, 0, 255)));
            combinedData.setData(getLineData(getLineYAxisValues(getBarYAxisValues()), "卡路里(百卡)", Color.rgb(255, 128, 0)));
            mCombinedChart.setData(combinedData);
            mCombinedChart.invalidate();
        }

    }

}
