package pekkles.billsplit.model;

import java.util.ArrayList;
import java.util.List;

public class Person {
    private static final int TIP_DEFAULT = 10;

    private double total;

    private int tip;

    private String name;

    private List<Item> items;

    public Person(String name) {
        this(name, TIP_DEFAULT);
    }

    public Person(String name, int tip) {
        this.name = name;
        this.tip = tip;

        total = 0;
        items = new ArrayList<>();
    }

    public void addItem(Item i) {
        items.add(i);
        total += i.calculateCost(this, tip);
    }

    public List<Item> getItems() {
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
