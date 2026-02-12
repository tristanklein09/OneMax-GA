//using xchart as its lightweight and exports as an image file
import org.knowm.xchart.*;
import java.io.IOException;

public class Graphing {

    //Double data for the x and y-axis
    private final double[] x;
    private final double[] y;

    public Graphing(double[] x, double[] y) {
        this.x = x;
        this.y = y;
    }

    public void createXYChart(double[] x, double[] y, String title, String xTitle, String yTitle, String seriesName, String fileName, int width, int height) throws IOException {
        XYChart xyChart = new XYChartBuilder()
                .width(width)
                .height(height)
                .title(title)
                .xAxisTitle(xTitle)
                .yAxisTitle(yTitle)
                .build();

        xyChart.addSeries(seriesName, x, y);

        BitmapEncoder.saveBitmap(xyChart, fileName, BitmapEncoder.BitmapFormat.PNG);
    }
}