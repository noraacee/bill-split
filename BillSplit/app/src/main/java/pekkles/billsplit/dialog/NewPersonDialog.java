package pekkles.billsplit.dialog;

import android.app.Dialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import pekkles.billsplit.R;
import pekkles.billsplit.model.Person;

public class NewPersonDialog extends NewModelDialog<Person> {
    private static final int THRESHOLD_TIP = 30;

    private EditText nameView;
    private EditText tipView;

    protected void add() {
        String name = nameView.getText().toString().trim();

        int tip;
        try {
            tip = Integer.parseInt(tipView.getText().toString());
        } catch (NumberFormatException e) {
            tip = 0;
        }

        Person person;
        if (tip != 0)
            person = new Person(name, tip);
        else
            person = new Person(name);

        onAdd(person);
        dismiss();
    }

    protected int getLayoutId(){
        return R.layout.dialog_new_person;
    }

    protected void initDialog(Dialog dialog) {
        nameView = (EditText) dialog.findViewById(R.id.name);
        tipView = (EditText) dialog.findViewById(R.id.tip);
        tipView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    if (Integer.parseInt(s.toString()) > THRESHOLD_TIP)
                        systemMessageManager.displayWarning(getResources().getString(R.string.warning_tip, THRESHOLD_TIP));
                } catch (NumberFormatException ignored) {}
            }
        });
    }

    protected boolean validate() {
        if (nameView.getText().toString().trim().length() == 0) {
            systemMessageManager.displayError(getResources().getString(R.string.error_missing_name));
            return false;
        }

        return true;
    }
}
