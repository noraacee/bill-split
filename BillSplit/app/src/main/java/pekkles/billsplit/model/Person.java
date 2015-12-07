package pekkles.billsplit.model;

public class Person {
    private int tip;
    private String name;

    public Person(String name, int tip) {
        this.name = name;
        this.tip = tip;
    }

    public int getTip() {
        return tip;
    }

    public String getName() {
        return name;
    }
}
