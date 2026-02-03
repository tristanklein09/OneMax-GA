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

    public static void main(String[] args) {
        Genetic genetic = Genetic.getInstance(10, 100, 15);
        String[] array = genetic.generatePopulation();
        genetic.runGeneration(10, 100, array);

    }
}