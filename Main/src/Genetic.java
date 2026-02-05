import java.util.Arrays;

public class Genetic{

    private static int bitStringLen;
    private static int populationSize;
    private static double mutationRate;
    public static boolean hasReachedPerfectSolution = false;
    public static int indexWithPerfectSolution;

    Genetic(int bitStringLen, int populationSize, double mutationRate) {
        this.bitStringLen = bitStringLen;
        this.populationSize = populationSize;
        this.mutationRate = mutationRate;

        //Throws an error if the bit string length is odd, as the crossover strategy relies on splitting the bit string into two equal halves
        if (bitStringLen % 2 != 0) {
            throw new IllegalArgumentException("Bit string length must be even");
        }
    }

    //Add recursion?
    public static int score(String bitString) {
        int score = 0;
        for (int i = 0; i < bitString.length(); i ++) {
            if (bitString.charAt(i) == '1') {
                score++;
            }
        }
        return score;
    }

    //Add recursion?
    public static String[] generatePopulation(){
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

    public Pair splitBitString(String bitString) {
        int stringLength = bitString.length();

        String half1 = bitString.substring(0, stringLength / 2);
        String half2 = bitString.substring(stringLength / 2, stringLength);

        return new Pair(half1, half2);
    }

    public static String mutate(String bitString) {
        char[] bitCharArray = bitString.toCharArray();
        double probability = mutationRate / 100.0;

        for (int i = 0; i < bitCharArray.length; i++) {
            //Generate random number between 0 and 1
            double random = Math.random();
            //If the random number is equal to the mutation rate, flip the bit
            if (random < probability) {
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
    public static Pair crossover(Pair parents) {
        String parent1 = parents.getKey();
        String parent2 = parents.getValue();

        //Child 1 = first half of parent 1 + second half of parent 2
        //Child 2 = second half of parent 1 + first half of parent 1
        String child1 = parent1.substring(0, bitStringLen / 2) + parent2.substring(bitStringLen / 2, bitStringLen);
        String child2 = parent2.substring(0, bitStringLen / 2) + parent1.substring(bitStringLen / 2, bitStringLen);

        return new Pair(child1, child2);
    }

    //Shorten code?
    public static Pair generateOffspring(Pair parents) {
        Pair offspring = crossover(parents); //Use crossover method to generate offspring
        String child1 =  offspring.getKey();
        String child2 = offspring.getValue();
        //Mutate the children
        child1 = mutate(child1);
        child2 = mutate(child2);

        return new Pair(child1, child2);
    }

    public static String[] runGeneration(int bitStringLen, int populationSize, String[] populationArray) {
        //i += 2, pairs of parents follow this rule: (n, n + 1), (n + 2, n + 3)... This means that each parent can only have two children
        for (int i = 0; i < populationSize; i+=2) {
            //Define the parents and generate their children
            Pair parents = new Pair(populationArray[i], populationArray[i+1]); //Parent 1 is key, parent 2 is value
            Pair children = generateOffspring(parents); //Child 1 is key, Child 2 is value

            //Generate the scores for the parents and children
            int parent1Score =  score(parents.getKey());
            int parent2Score =  score(parents.getValue());
            int child1Score =  score(children.getKey());
            int child2Score =  score(children.getValue());

            boolean parent1HasLowestScore;

            //Check which parent has the lowest or highest score
            if (parent1Score > parent2Score) { //Parent 2 has lowest score
                parent1HasLowestScore = false;
            } else { //Parent 1 has lowest score, or they have the same score
                parent1HasLowestScore = true;
            }

            //Replace the parent with the lowest score with the child with the highest score
            //If none of the conditions below are met, both children have lower scores then the parents, so nothing is replaced
            if (child1Score > child2Score) { //Child 1 has higher score
                //If the conditions below are not met, child1 has a lower score than either of the parents
                if (parent1HasLowestScore && child1Score > parent1Score) { //Replacing parent 1
                    populationArray[i] = children.getKey();
                    indexWithPerfectSolution = i; //This value is only used if there has been a perfect solution
                }
                else if (!parent1HasLowestScore && child1Score > parent2Score) { //Replacing parent 2
                    populationArray[i + 1] = children.getKey();
                    indexWithPerfectSolution = i + 1;
                }
            } else { //Child 2 has a higher score, or they have the same score
                if (parent1HasLowestScore && child2Score > parent1Score) {
                    populationArray[i] = children.getValue();
                    indexWithPerfectSolution = i;
                }
                else if (!parent1HasLowestScore && child2Score > parent2Score) {
                    populationArray[i + 1] = children.getValue();
                    indexWithPerfectSolution = i + 1;
                }
            }

            //Perfect solution has been reached
            if (child1Score == bitStringLen) {
                hasReachedPerfectSolution = true;

                return populationArray;
            } else if (child2Score == bitStringLen) {
                hasReachedPerfectSolution = true;
                return populationArray;
            }

        }

        System.out.println("One generation pass has occurred");
        return populationArray;
    }

    public static int runAllGenerations() {
        Timer timer = new Timer();
        int generationCount = 0;


        String [] populationArray = generatePopulation();

        long startTimer = timer.startTimer();
        while (!hasReachedPerfectSolution) {
            generationCount++;
            populationArray = runGeneration(bitStringLen, populationSize, populationArray);
        }

        double endTime  = timer.stopTimer(startTimer);
        System.out.println("Time taken: " + endTime + " ms");

        System.out.println("Perfect solution reached after "+generationCount+" generations");

        return generationCount;
    }
}