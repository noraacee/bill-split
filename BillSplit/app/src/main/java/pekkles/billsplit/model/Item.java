package pekkles.billsplit.model;

import java.util.HashMap;
import java.util.Map;

public class Item {
    private static final int TAX_ALCOHOL = 15;
    private static final int TAX = 5;

    private boolean alcohol;

    private int price;
    private int pricePer;
    private int priceTotal;
    private int quantity;
    private int quantityTotal;
    private int tax;
    private int taxPer;
    private int total;

    private String name;

    private Map<Person, Integer> people;

    public Item(String name, double price, int quantity, boolean alcohol) {
        this.name = name;
        this.price = (int) (price * 100);
        this.quantity = quantity;
        this.alcohol = alcohol;

        quantityTotal = 0;
        people = new HashMap<>();
    }

    public void addPerson(Person p, int quantity) {
        people.put(p, quantity);
        quantityTotal += quantity;
    }

    public double calculateCost(Person p, int tip) {
        if (!people.containsKey(p))
            return 0;

        int quantity = people.get(p);
        int cost = pricePer * quantity  + taxPer * quantity + pricePer * quantity * tip / 100;

        return (double) cost / 100;
    }

    public String getName() {
        return name;
    }

    public Map<Person, Integer> getPeople() {
        return people;
    }

    public double getPrice() {
        return (double) price / 100;
    }

    public double getPriceTotal() {
        return (double) priceTotal / 100;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getTax() {
        return (double) tax / 100;
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

    public void init() {
        calculateItem();
        updatePeople();
    }

    private void calculateItem() {
        priceTotal = price * quantity;

        if (alcohol)
            tax = price * TAX_ALCOHOL / 100;
        else
            tax = price * TAX / 100;

        total = price + tax;
        pricePer = price / quantityTotal;
        taxPer = tax / quantityTotal;
    }

    private void updatePeople() {
        for (Person p : people.keySet())
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
