public class Genetic{

    private static Genetic instance;
    private final int bitStringLen;
    private final int populationSize;
    private final double mutationRate;

    Genetic(int bitStringLen, int populationSize, double mutationRate) {
        instance = this;
        this.bitStringLen = bitStringLen;
        this.populationSize = populationSize;
        this.mutationRate = mutationRate;

    }

    public String[][] generatePopulation(){
        String[][] populationArray = new String[bitStringLen][populationSize];



        return populationArray;
    }

    public static Genetic getInstance(int bitStringLen, int populationSize, double mutationRate) {
        if (instance == null) {
            instance = new Genetic(bitStringLen, populationSize, mutationRate);
        }
        return instance;
    }
}
