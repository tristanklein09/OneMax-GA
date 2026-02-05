//using xchart as its lightweight and exports as an image file
import org.knowm.xchart.*;

import java.io.IOException;

public class Graphing {

    private final double[] x;
    private final double[] y;

    public Graphing(double[] x, double[] y) {
        this.x = x;
        this.y = y;
    }

    public void chartTest(double[] x, double[] y) throws IOException {
        //Creating chart
        XYChart xyChart = new XYChartBuilder()
                .width(800)
                .height(600)
                .title("Test Chart")
                .xAxisTitle("X")
                .yAxisTitle("Y")
                .build();

        //Adding data to chart
        xyChart.addSeries("Test", x, y);

        //Saving as image file
        BitmapEncoder.saveBitmap(xyChart, "mychart", BitmapEncoder.BitmapFormat.PNG);
    }
}