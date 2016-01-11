package pekkles.billsplit.dialog;

import android.app.Dialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.HashMap;
import java.util.Map;

import pekkles.billsplit.R;
import pekkles.billsplit.model.Item;
import pekkles.billsplit.model.Person;
import pekkles.billsplit.widget.CustomEditText;
import pekkles.billsplit.widget.CustomTextView;

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

    private Map<Person, Integer> quantities;

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
        item.addPeople(quantities);
        item.init();

        onAdd(item);
        activity.notifyDataSetChanged(true);
        dismiss();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_new_item;
    }

    @Override
    protected void initDialog(Dialog dialog) {
        quantities = new HashMap<>();

        LinearLayout peopleView = (LinearLayout) dialog.findViewById(R.id.people);
        for (final Person p : activity.getPeople()) {
            View v = getActivity().getLayoutInflater().inflate(R.layout.list_quantity, peopleView, false);
            ((CustomTextView) v.findViewById(R.id.name)).setText(p.getName());

            final CustomEditText quantityView = (CustomEditText) v.findViewById(R.id.quantity);
            Integer quantity = quantities.get(p);
            if (quantity == null)
                quantityView.setText("0");
            else
                quantityView.setText(String.format("%d", quantity));

            quantityView.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (s.length() == 0) {
                        quantities.remove(p);
                        quantityView.setText("0");
                    } else {
                        try {
                            int quantity = Integer.parseInt(s.toString());
                            if (quantity > 0) {
                                quantities.put(p, quantity);
                                Log.d(p.getName(), Integer.toString(quantity));
                            } else {
                                quantities.remove(p);
                                quantityView.setSelection(0, s.length());
                            }
                        } catch (NumberFormatException e) {
                            quantities.remove(p);
                            quantityView.setText("0");
                            quantityView.setSelection(0, s.length());
                        }
                    }
                }
            });

            peopleView.addView(v);
        }

        nameView = (EditText) dialog.findViewById(R.id.name);
        priceView = (EditText) dialog.findViewById(R.id.price);

        taxView = (EditText) dialog.findViewById(R.id.tax);
        taxView.setHint(getString(HINT_TAX, Item.DEFAULT_TAX));
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
                        systemMessageManager.displayWarning(getString(WARNING_TAX, Item.THRESHOLD_TAX));
                } catch (NumberFormatException ignored) {
                }
            }
        });
    }

    @Override
    protected boolean validate() {
        if (nameView.getText().toString().trim().length() == 0) {
            systemMessageManager.displayError(getString(ERROR_NAME));
            return false;
        }

        try {
            price = Double.parseDouble(priceView.getText().toString());

            if (price <= 0) {
                systemMessageManager.displayError(getString(ERROR_PRICE));
                return false;
            }
        } catch (NumberFormatException e) {
            systemMessageManager.displayError(getString(ERROR_PRICE));
            return false;
        }

        if (quantities.isEmpty()) {
            systemMessageManager.displayError(getString(ERROR_QUANTITY));
            return false;
        }

        return true;
    }
}
