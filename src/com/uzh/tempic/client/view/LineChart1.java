package com.uzh.tempic.client.view;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.googlecode.gwt.charts.client.ChartLoader;
import com.googlecode.gwt.charts.client.ChartPackage;
import com.googlecode.gwt.charts.client.ColumnType;
import com.googlecode.gwt.charts.client.DataTable;
import com.googlecode.gwt.charts.client.corechart.LineChart;
import com.googlecode.gwt.charts.client.corechart.LineChartOptions;
import com.googlecode.gwt.charts.client.options.HAxis;
import com.googlecode.gwt.charts.client.options.VAxis;
/**
 * Created by Robin on 20.11.2016.
 */
public class LineChart1 extends DockLayoutPanel {
    public LineChart chart;

        public LineChart1() {
            super(Unit.PX);
            initialize();
        }

        private void initialize() {
            ChartLoader chartLoader = new ChartLoader(ChartPackage.CORECHART);
            chartLoader.loadApi(new Runnable() {

                @Override
                public void run() {
                    // Create and attach the chart
                    chart = new LineChart();
                    add(chart);
                    draw();
                }
            });
        }

        private void draw() {
            String[] countries = new String[] { "Austria", "Bulgaria", "Denmark", "Greece" };
            int[] years = new int[] { 2003, 2004, 2005, 2006, 2007, 2008 };
            int[][] values = new int[][] { { 1336060, 1538156, 1576579, 1600652, 1968113, 1901067 },
                    { 400361, 366849, 440514, 434552, 393032, 517206 },
                    { 1001582, 1119450, 993360, 1004163, 979198, 916965 },
                    { 997974, 941795, 930593, 897127, 1080887, 1056036 } };

            // Prepare the data
            DataTable dataTable = DataTable.create();
            dataTable.addColumn(ColumnType.STRING, "Year");
            for (int i = 0; i < countries.length; i++) {
                dataTable.addColumn(ColumnType.NUMBER, countries[i]);
            }
            dataTable.addRows(years.length);
            for (int i = 0; i < years.length; i++) {
                dataTable.setValue(i, 0, String.valueOf(years[i]));
            }
            for (int col = 0; col < values.length; col++) {
                for (int row = 0; row < values[col].length; row++) {
                    dataTable.setValue(row, col + 1, values[col][row]);
                }
            }

            // Set options
            LineChartOptions options = LineChartOptions.create();
            options.setBackgroundColor("#f0f0f0");
            options.setFontName("Tahoma");
            options.setTitle("Yearly Coffee Consumption by Country");
            options.setHAxis(HAxis.create("Year"));
            options.setVAxis(VAxis.create("Cups"));

            // Draw the chart
            chart.draw(dataTable, options);
        }

}
