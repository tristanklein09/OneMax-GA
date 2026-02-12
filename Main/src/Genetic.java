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


        if (bitStringLen % 2 != 0) { //Throws an error if the bit string length is odd, as the crossover strategy relies on splitting the bit string into two equal halves
            throw new IllegalArgumentException("Bit string length must be even");
        }
        if (populationSize % 2 != 0) { //Throws an error if the population size is odd, as otherwise there would be one bit string that can't produce offspring
            throw new IllegalArgumentException("Population size must be even");
        }
        if (mutationRate > 100) { //Throws an error if the mutation rate is more than 100, as having a more than 100% chance of a mutation happening is impossible
            throw new IllegalArgumentException("Mutation rate must be less than 100");
        }

    }

    public static int score(String bitString) {
        int score = 0;
        //Increment score if the bit is a 1
        for (int i = 0; i < bitString.length(); i ++) {
            if (bitString.charAt(i) == '1') {
                score++;
            }
        }
        return score;
    }

    //Generates the initial population
    public static String[] generatePopulation(){
        String[] populationArray = new String[populationSize];

        for (int i = 0; i < populationSize; i++) {
            //Generating the bitString
            String bitString = "";
            for (int j = 0; j < bitStringLen; j++) {
                bitString += String.valueOf((int) (Math.random() * 2)); //Will generate either 0 or 1
            }

            //Solution is perfect therefore do not add to array and regenerate
            if (score(bitString) == bitStringLen) {
                i--; //At the next cycle we remain at the same index
            } else {
                populationArray[i] = bitString;
            }
        }

        return populationArray;
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

            //Perfect solution has been reached
            if (child1Score == bitStringLen) {
                hasReachedPerfectSolution = true;
                return populationArray;
            } else if (child2Score == bitStringLen) {
                hasReachedPerfectSolution = true;
                return populationArray;
            }

            // Evaluate children independently
            boolean child1BeatsP1 = child1Score > parent1Score;
            boolean child1BeatsP2 = child1Score > parent2Score;

            boolean child2BeatsP1 = child2Score > parent1Score;
            boolean child2BeatsP2 = child2Score > parent2Score;

            // Replace parent 1 with the stronger child that beats it
            if (child1BeatsP1 || child2BeatsP1) {
                if (child1Score >= child2Score) {
                    populationArray[i] = children.getKey(); // Child 1
                    indexWithPerfectSolution = i;
                } else {
                    populationArray[i] = children.getValue(); // Child 2
                    indexWithPerfectSolution = i;
                }
            }
            // Replace parent 2 with the stronger child that beats it
            if (child1BeatsP2 || child2BeatsP2) {
                if (child1Score >= child2Score) {
                    populationArray[i + 1] = children.getKey(); // Child 1
                    indexWithPerfectSolution = i + 1;
                } else {
                    populationArray[i + 1] = children.getValue(); // Child 2
                    indexWithPerfectSolution = i + 1;
                }
            }
        }

        return populationArray;
    }

    public static double runAllGenerations() {
        Timer timer = new Timer();
        int generationCount = 1; //Keep count of how many generations have passed

        String [] populationArray = generatePopulation();

        long startTimer = timer.startTimer();

        //Running all generations until a perfect solution is reached
        while (!hasReachedPerfectSolution) {
            populationArray = runGeneration(bitStringLen, populationSize, populationArray);
            generationCount++;
        }

        double endTime  = timer.stopTimer(startTimer);
        System.out.println("Time taken: " + endTime + " ms");

        System.out.println("Perfect solution reached after "+generationCount+" generations");

        hasReachedPerfectSolution = false; //Resetting for next trial

        return endTime;
    }
}