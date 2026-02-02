//TODO: Create own pair data type

public class Genetic{

    private static Genetic instance;
    private final int bitStringLen;
    private int populationSize;
    private final double mutationRate;

    Genetic(int bitStringLen, int populationSize, double mutationRate) {
        instance = this;
        this.bitStringLen = bitStringLen;
        this.populationSize = populationSize;
        this.mutationRate = mutationRate;

    }

    //Add recursion?
    public String[][] generatePopulation(){
        String[][] populationArray = new String[populationSize][bitStringLen];

        for (int i = 0; i < populationSize; i++) {
            for (int j = 0; j < bitStringLen; j++) {
                String temp = String.valueOf((int) (Math.random() * 2)); //0-1
                populationArray[i][j] = temp;
            }
        }
        return populationArray;
    }

    //Add recursion?
    public int[] assignScore(String[][] populationArray){
        //Each index in scoreArray corresponds to each bitstring in populationArray
        int[] scoreArray = new int[populationSize];
        int score = 0;

        for (int i = 0; i < populationSize; i++) {
            for (int j = 0; j < bitStringLen; j++) {
                if (populationArray[i][j].equals("1")) {
                    score++;
                }
                //Adds score to array at position corresponding to the bitstring's position in
                scoreArray[i] = score;
            }
            score = 0;
        }
        return scoreArray;
    }

    //Generates offspring using the crossover strategy

    public static Genetic getInstance(int bitStringLen, int populationSize, double mutationRate) {
        if (instance == null) {
            instance = new Genetic(bitStringLen, populationSize, mutationRate);
        }
        return instance;
    }
}
