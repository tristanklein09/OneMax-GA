//Implementing Pair data type so that javafx doesn't have to be installed
public class Pair {

    public final String key;
    public final String value;

    public Pair(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}