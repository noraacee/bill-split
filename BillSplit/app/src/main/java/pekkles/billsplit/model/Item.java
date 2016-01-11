package pekkles.billsplit.model;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Item {
    public static final int DEFAULT_TAX = 5;
    public static final int THRESHOLD_TAX = 20;

    private int price;
    private int quantity;
    private int tax;
    private int taxPercentage;
    private int total;

    private String name;

    private List<QuantityPriceTax> quantities;
    private Map<Person, Integer> people;

    public Item(String name, double price, int taxPercentage) {
        this.name = name;
        this.price = (int) (price * 100);
        this.taxPercentage = taxPercentage;

        quantity = 0;
        quantities = new ArrayList<>();
    }

    public void addPeople(Map<Person, Integer> people) {
        this.people = people;

        for (Map.Entry<Person, Integer> p : people.entrySet()) {
            Log.d(p.getKey().getName(), Integer.toString(p.getValue()));
            addPerson(p.getKey(), p.getValue());
        }
    }

    public double calculateCost(Person p, int tip) {
        if (!people.containsKey(p))
            return 0;

        int quantity = people.get(p);

        int cost = 0;
        QuantityPriceTax q;
        for (int i = 0; i < quantity; i++) {
            q = quantities.get(i);
            cost += q.getPricePer() + q.getPricePer() * tip / 100 + q.getTaxPer();
        }


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

    public int getQuantity() {
        return quantity;
    }

    public double getTax() {
        return (double) tax / 100;
    }

    public int getTaxPercentage() {
        return taxPercentage;
    }

    public double getTotal() {
        return (double) total / 100;
    }

    public void init() {
        calculateItem();
        updatePeople();
    }

    private void addPerson(Person p, int q) {
        if (quantity < q)
            quantity = q;

        people.put(p, q);

        for (int i = 0; i < q; i++) {
            if (quantities.size() < i + 1)
                quantities.add(new QuantityPriceTax());
            else
                quantities.get(i).addQuantity();
        }
    }

    private void calculateItem() {
        tax = price * taxPercentage / 100;
        total = price * quantity + tax * quantity;

        for (QuantityPriceTax q : quantities) {
            q.calculate();
        }
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

    private class QuantityPriceTax {
        private int quantity;
        private int pricePer;
        private int taxPer;

        public QuantityPriceTax() {
            quantity = 1;
            pricePer = 0;
            taxPer = 0;
        }

        public void addQuantity() {
            quantity++;
        }

        public void calculate() {
            pricePer = price / quantity;
            taxPer = tax / quantity;
        }

        public int getPricePer() {
            return pricePer;
        }

        public int getTaxPer() {
            return taxPer;
        }

        public void subtractQuantity() {
            quantity--;
        }
    }
}
