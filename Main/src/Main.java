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
        System.out.println("Hello, World!");
    }
}
