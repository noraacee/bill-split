package pekkles.billsplit.model;

import java.util.HashMap;
import java.util.Map;

public class Person {
    public static final int DEFAULT_TIP = 10;
    public static final int THRESHOLD_TIP = 30;

    private double total;

    private int tip;

    private String name;

    private Map<String, Double> items;

    public Person(String name, int tip) {
        this.name = name;
        this.tip = tip;

        total = 0;
        items = new HashMap<>();
    }

    public void addItem(Item i) {
        double cost = i.calculateCost(this, tip);
        items.put(i.getName(), cost);
        total += cost;
    }

    public Map<String, Double> getItems() {
        return items;
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

    @Override
    public boolean equals(Object o) {
        if (o == null)
            return false;
        if (o == this)
            return true;
        if (!(o instanceof Person))
            return false;

        Person p = (Person) o;
        return name.equals(p.getName());
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return name;
    }
}
