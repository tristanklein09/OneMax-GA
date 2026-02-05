import java.io.IOException;
import java.util.Arrays;

public class Main {

    private static Main instance;

    Main() {
        instance = this;
    }

    public static Main getInstance() {
        if (instance == null) {
            instance = new Main();
        }
        return instance;
    }

    public static void main(String[] args) throws IOException {
        Genetic genetic = new Genetic(11, 100, 15);
        String[] array = genetic.generatePopulation();
        genetic.runAllGenerations();

        double[] x = {1,2,3};
        double[] y = {1,2,3};

        Graphing graphing = new Graphing(x, y);
        graphing.chartTest(x, y);
    }
}