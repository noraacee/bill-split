package pekkles.billsplit.model;

import java.util.List;

public class Item {
    private static final int TAX_ALCOHOL = 15;
    private static final int TAX = 5;

    private boolean alcohol;

    private int price;
    private int pricePer;
    private int tax;
    private int taxPer;
    private int total;

    private String name;

    private List<Person> people;

    public Item(String name, double price, boolean alcohol, List<Person> people) {
        if (name == null || name.length() == 0 || price < 0 || people == null || people.size() == 0)
            throw new IllegalArgumentException();

        this.name = name;
        this.price = (int) (price * 100);
        this.alcohol = alcohol;
        this.people = people;

        calculateItem();
        updatePeople();
    }

    public String getName() {
        return name;
    }

    public List<Person> getPeople() {
        return people;
    }

    public double getPrice() {
        return (double) price / 100;
    }

    public double getPricePer() {
        return (double) pricePer / 100;
    }

    public double getTax() {
        return (double) tax / 100;
    }

    public double getTaxPer() {
        return (double) taxPer / 100;
    }

    public int getTaxPercent() {
        if (alcohol)
            return TAX_ALCOHOL;
        else
            return TAX;
    }

    public double getTotal() {
        return (double) total / 100;
    }

    public double calculateCost(int tip) {
        int cost = pricePer + taxPer + pricePer * tip / 100;
        return (double) cost / 100;
    }

    private void calculateItem() {
        if (alcohol)
            tax = price * TAX_ALCOHOL / 100;
        else
            tax = price * TAX / 100;

        total = price + tax;
        pricePer = price / people.size();
        taxPer = tax / people.size();
    }

    private void updatePeople() {
        for (Person p : people)
            p.addItem(this);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null)
            return false;
        if (o == this)
            return true;
        if (!(o instanceof Item))
            return false;

        Item i = (Item) o;
        return i.getName().equals(name) && i.getPrice() == price;
    }

    @Override
    public int hashCode() {
        return name.hashCode() + price;
    }

    @Override
    public String toString() {
        return name + price;
    }
}
