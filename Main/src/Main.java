import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {


        //Genetic genetic = new Genetic(10 ,100,15);
        //genetic.runAllGenerations();

        int minBitStringLen = 4;
        int maxBitStringLen = 42;
        int populationSize = 100;
        double mutationRate = 15;
        double trialCount = 10;

        double[] x = new double[(maxBitStringLen - minBitStringLen) / 2]; //bitString length
        double[] y = new double[(maxBitStringLen - minBitStringLen) / 2]; //mean no of generations

        int indexCounter = 0;
        //i += 2 because bitString length must be even
        for (int i = minBitStringLen; i < maxBitStringLen; i+=2) {

            double meanGenerationCount = 0;

            for (int j = 0; j < trialCount; j++) { //10 trials for each bit string length
                Genetic genetic = new Genetic(i, populationSize, mutationRate);
                String[] array = new String[populationSize];
                array = genetic.generatePopulation();
                double count = genetic.runAllGenerations();
                meanGenerationCount += count;
            }

            meanGenerationCount = meanGenerationCount / trialCount; //mean number of generations for each bit string length
            System.out.println(meanGenerationCount);
            y[indexCounter] = meanGenerationCount;
            x[indexCounter] = i;
            indexCounter ++;
        }

        Graphing graphing = new Graphing(x, y);
        graphing.createXYChart(x, y, "Mean Time to Solution vs Bit String Length", "Bit String Length ", "Mean Time to Solution (ms)", "Mean Time to Solution", "TimeVsBitStringLength", 800, 800);


    }





}