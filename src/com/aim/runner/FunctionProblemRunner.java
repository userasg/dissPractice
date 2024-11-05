//package com.aim.runner;
//
//import com.aim.problems.FunctionProblem;
//import org.jfree.chart.ChartFactory;
//import org.jfree.chart.ChartPanel;
//import org.jfree.chart.JFreeChart;
//import org.jfree.chart.plot.XYPlot;
//import org.jfree.chart.renderer.xy.XYBlockRenderer;
//import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
//import org.jfree.data.xy.DefaultXYZDataset;
//import org.jfree.data.xy.XYSeriesCollection;
//
//import javax.swing.*;
//import java.awt.*;
//
//public class FunctionProblemRunner {
//
//    public void displayChart() {
//        FunctionProblem functionProblem = new FunctionProblem();
//
//        // Create datasets
//        DefaultXYZDataset functionDataset = functionProblem.createDataset();
//        XYSeriesCollection minPointDataset = functionProblem.createMinPointDataset();
//
//        // Create the chart
//        JFreeChart chart = ChartFactory.createScatterPlot(
//                "Discretized Integer Function f(x, y) = (x-1)^2 + (y+2)^2",
//                "X", "Y", functionDataset
//        );
//
//        // Configure the main plot
//        XYPlot plot = chart.getXYPlot();
//        XYBlockRenderer renderer = new XYBlockRenderer();
//        renderer.setPaintScale(new org.jfree.chart.renderer.GrayPaintScale(0, 50));
//        plot.setRenderer(0, renderer);
//
//        // Add a renderer for the minimum point
//        XYLineAndShapeRenderer minPointRenderer = new XYLineAndShapeRenderer(false, true);
//        minPointRenderer.setSeriesPaint(0, Color.RED);
//        minPointRenderer.setSeriesShape(0, new java.awt.geom.Ellipse2D.Double(-3, -3, 6, 6)); // Custom shape for visibility
//        plot.setDataset(1, minPointDataset);
//        plot.setRenderer(1, minPointRenderer);
//
//        // Display the chart in a frame
//        JFrame frame = new JFrame("Function Visualization");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.add(new ChartPanel(chart), BorderLayout.CENTER);
//        frame.pack();
//        frame.setVisible(true);
//    }
//
//    public static void main(String[] args) {
//        FunctionProblemRunner runner = new FunctionProblemRunner();
//        runner.displayChart();
//    }
//}
