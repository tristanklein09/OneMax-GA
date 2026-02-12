//Implementing Pair data type so that javafx doesn't have to be installed
public class Pair {

    private final String key;
    private final String value;

    public Pair(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() { //Get the first value of the pair
        return this.key;
    }

    public String getValue() { //Get the second value of the pair
        return this.value;
    }
}




