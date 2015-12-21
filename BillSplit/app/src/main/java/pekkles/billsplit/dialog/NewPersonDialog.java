package pekkles.billsplit.dialog;

import android.app.Dialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import pekkles.billsplit.R;
import pekkles.billsplit.model.Person;

public class NewPersonDialog extends NewModelDialog<Person> {
    private static final int ERROR_NAME = R.string.error_name;
    private static final int HINT_TAX = R.string.hint_tip;
    private static final int WARNING_TIP = R.string.warning_tip;

    private EditText nameView;
    private EditText tipView;

    @Override
    protected void add() {
        String name = nameView.getText().toString().trim();

        int tip;
        try {
            tip = Integer.parseInt(tipView.getText().toString());
        } catch (NumberFormatException e) {
            tip = Person.DEFAULT_TIP;
        }

        Person person = new Person(name, tip);

        onAdd(person);
        dismiss();
    }

    @Override
    protected int getLayoutId(){
        return R.layout.dialog_new_person;
    }

    @Override
    protected void initDialog(Dialog dialog) {
        nameView = (EditText) dialog.findViewById(R.id.name);

        tipView = (EditText) dialog.findViewById(R.id.tip);
        tipView.setHint(getResources().getString(HINT_TAX, Person.DEFAULT_TIP));
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
                    if (Integer.parseInt(s.toString()) > Person.THRESHOLD_TIP)
                        systemMessageManager.displayWarning(getResources().getString(WARNING_TIP, Person.THRESHOLD_TIP));
                } catch (NumberFormatException ignored) {}
            }
        });
        tipView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    if (validate())
                        add();
                }

                return false;
            }
        });
    }

    @Override
    protected boolean validate() {
        if (nameView.getText().toString().trim().length() == 0) {
            systemMessageManager.displayError(getResources().getString(ERROR_NAME));
            return false;
        }

        return true;
    }
}
