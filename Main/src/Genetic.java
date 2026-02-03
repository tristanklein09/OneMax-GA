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
    public int score(String bitString) {
        int score = 0;
        for (int i = 0; i < bitString.length(); i ++) {
            if (bitString.charAt(i) == '1') {
                score++;
            }
        }
        return score;
    }

    //Add recursion?
    public String[] generatePopulation(){
        String[] populationArray = new String[populationSize];

        for (int i = 0; i < populationSize; i++) {
            String bitString = "";
            for (int j = 0; j < bitStringLen; j++) {
                bitString += String.valueOf((int) (Math.random() * 2)); //0-1
            }
            //Solution is perfect therefore do not add to array and regenerate
            if (score(bitString) == bitStringLen) { //Calling score everytime may be inefficient?
                i--;
            }
            else {
                populationArray[i] = bitString;
            }
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

    public Pair splitBitString(String bitString) {
        int stringLength = bitString.length();

        String half1 = bitString.substring(0, stringLength / 2);
        String half2 = bitString.substring(stringLength / 2, stringLength);

        return new Pair(half1, half2);
    }

    public String mutate(String bitString) {
        char[] bitCharArray = bitString.toCharArray();
        for (int i = 0; i < bitCharArray.length; i++) {
            //Generate random number between 0 and mutationRate
            int random = (int) (Math.random() * (mutationRate + 1));
            //If the random number is equal to the mutation rate, flip the bit
            if (random == mutationRate) {
                //System.out.println("Mutation has occurred at index " + i);
                if (bitCharArray[i] == '1') { //The bit is 1
                    bitCharArray[i] = '0';
                } else { //The bit is 0
                    bitCharArray[i] = '1';
                }
            }
        }

        return String.valueOf(bitCharArray);
    }

    //Generates offspring using the crossover strategy

    public static Genetic getInstance(int bitStringLen, int populationSize, double mutationRate) {
        if (instance == null) {
            instance = new Genetic(bitStringLen, populationSize, mutationRate);
        }
        return instance;
    }
}