package pekkles.billsplit.model;

public class Person {
    private double total;
    private int tip;
    private String name;

    public Person(String name, int tip) {
        this.name = name;
        this.tip = tip;

        total = 0;
    }

    public void addToTotal(double cost) {
        total += cost;
    }

    public String getName() {
        return name;
    }

    public int getTip() {
        return tip;
    }

    public double getTotal() {
        return total;
    }
}
