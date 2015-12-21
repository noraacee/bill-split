package pekkles.billsplit.dialog;

import android.app.Dialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pekkles.billsplit.R;
import pekkles.billsplit.model.Item;
import pekkles.billsplit.model.Person;

public class NewItemDialog extends NewModelDialog<Item> {
    private static final int ERROR_NAME = R.string.error_name;
    private static final int ERROR_PRICE = R.string.error_price;
    private static final int ERROR_QUANTITY = R.string.error_quantity;
    private static final int HINT_TAX = R.string.hint_tax;
    private static final int WARNING_TAX = R.string.warning_tax;

    private double price;

    private EditText nameView;
    private EditText priceView;
    private EditText taxView;
    private QuantitiesAdapter adapter;

    @Override
    protected void add() {
        String name = nameView.getText().toString().trim();

        int tax;
        try {
            tax = Integer.parseInt(taxView.getText().toString());
        } catch (NumberFormatException e) {
            tax = Item.DEFAULT_TAX;
        }

        Item item = new Item(name, price, tax);
        item.addPeople(adapter.getQuantities());

        onAdd(item);
        dismiss();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_new_item;
    }

    @Override
    protected void initDialog(Dialog dialog) {
        adapter = new QuantitiesAdapter(activity.getPeople());
        ListView quantitiesView = (ListView) dialog.findViewById(R.id.quantities);
        quantitiesView.setAdapter(adapter);

        nameView = (EditText) dialog.findViewById(R.id.name);
        priceView = (EditText) dialog.findViewById(R.id.price);

        taxView = (EditText) dialog.findViewById(R.id.tax);
        taxView.setHint(getResources().getString(HINT_TAX, Item.DEFAULT_TAX));
        taxView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    if (Integer.parseInt(s.toString()) > Item.THRESHOLD_TAX)
                        systemMessageManager.displayWarning(getResources().getString(WARNING_TAX, Item.THRESHOLD_TAX));
                } catch (NumberFormatException ignored) {
                }
            }
        });
    }

    @Override
    protected boolean validate() {
        if (nameView.getText().toString().trim().length() == 0) {
            systemMessageManager.displayError(getResources().getString(ERROR_NAME));
            return false;
        }

        try {
            price = Double.parseDouble(priceView.getText().toString());

            if (price <= 0) {
                systemMessageManager.displayError(getResources().getString(ERROR_PRICE));
                return false;
            }
        } catch (NumberFormatException e) {
            systemMessageManager.displayError(getResources().getString(ERROR_PRICE));
            return false;
        }

        if (adapter.getQuantities().isEmpty()) {
            systemMessageManager.displayError(getResources().getString(ERROR_QUANTITY));
            return false;
        }

        return true;
    }

    private class QuantitiesAdapter extends BaseAdapter {
        private List<Person> people;
        private Map<Person, Integer> quantities;

        public QuantitiesAdapter(List<Person> people) {
            this.people = people;
            quantities = new HashMap<>();
        }

        @Override
        public int getCount() {
            return people.size();
        }

        @Override
        public Person getItem(int position) {
            return people.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater().inflate(R.layout.list_quantity, parent, false);

                ViewHolder holder = new ViewHolder();
                holder.nameView = (TextView) convertView.findViewById(R.id.name);
                holder.quantityView = (EditText) convertView.findViewById(R.id.quantity);

                convertView.setTag(holder);
            }

            final ViewHolder holder = (ViewHolder) convertView.getTag();
            final Person person = getItem(position);

            holder.nameView.setText(person.getName());

            Integer quantity = quantities.get(person);
            if (quantity == null)
                holder.quantityView.setText("0");
            else
                holder.quantityView.setText(String.format("%d", quantity));

            holder.quantityView.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    try {
                        int quantity = Integer.parseInt(s.toString());
                        if (quantity > 0) {
                            quantities.put(person, quantity);
                        } else {
                            quantities.remove(person);
                            holder.quantityView.setSelection(0, s.length());
                        }
                    } catch (NumberFormatException e) {
                        quantities.remove(person);
                    }

                    if (s.length() == 0)
                        holder.quantityView.setText("0");
                }
            });


            return convertView;
        }

        public Map<Person, Integer> getQuantities() {
            return quantities;
        }

        private class ViewHolder {
            public TextView nameView;
            public EditText quantityView;
        }
    }
}
