package pekkles.billsplit.model;

import java.util.List;

public class Item {
    private static final int TAX_ALCOHOL = 15;
    private static final int TAX = 5;

    private boolean alcohol;

    private int costPer;
    private int price;
    private int total;

    private String name;

    private List<Person> people;

    public Item(String name, double price, boolean alcohol, List<Person> people) {
        this.name = name;
        this.price = (int) price * 100;
        this.alcohol = alcohol;
        this.people = people;

        calculateCosts();
        updatePeople();
    }

    public double getTotal() {
        return ((double) total) / 100;
    }

    private void calculateCosts() {
        if (alcohol)
            total = price + price * TAX_ALCOHOL / 100;
        else
            total = price + price * TAX / 100;

        costPer = total / people.size();
    }

    private void updatePeople() {
        int tip;
        for (Person p : people) {
            tip =  * p.getTip() / 100;

        }
    }
}
