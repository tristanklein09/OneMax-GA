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
    //TODO: Cannot generate perfect solution imediately
    public String[] generatePopulation(){
        String[] populationArray = new String[populationSize];

        for (int i = 0; i < populationSize; i++) {
            String bitString = "";
            for (int j = 0; j < bitStringLen; j++) {
                bitString += String.valueOf((int) (Math.random() * 2)); //0-1
            }
            populationArray[i] = bitString;
        }
        return populationArray;
    }

    public int[] generateScoreArray(String[] populationArray) {
        int[] scoreArray = new int[populationSize];

        for (int i = 0; i < populationSize; i++) {
            scoreArray[i] = score(populationArray[i]);
        }
        return  scoreArray;
    }

    //Add recursion?
    public int score(String bitString) {
        int score = 0;
        for (int i = 0; i < bitString.length(); i ++) {
            if (bitString.charAt(i) == '1') {
                score++;
            }
        }
        return score;
    }

    public Pair splitBitString(String bitString) {
        int stringLength = bitString.length();

        String half1 = bitString.substring(0, stringLength / 2);
        String half2 = bitString.substring(stringLength / 2, stringLength);

        Pair splitBitString = new Pair(half1, half2);
        return splitBitString;
    }

    //Generates offspring using the crossover strategy

    public static Genetic getInstance(int bitStringLen, int populationSize, double mutationRate) {
        if (instance == null) {
            instance = new Genetic(bitStringLen, populationSize, mutationRate);
        }
        return instance;
    }
}