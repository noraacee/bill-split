package pekkles.billsplit.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import pekkles.billsplit.R;
import pekkles.billsplit.interf.OnAddListener;
import pekkles.billsplit.model.Person;
import pekkles.billsplit.util.SystemMessageManager;

public class NewPersonDialog extends DialogFragment {
    private static final String ERROR_MISSING_NAME = "Name cannot be left empty";

    private EditText nameView;
    private EditText tipView;

    private OnAddListener<Person> onAddListener;

    private SystemMessageManager systemMessageManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        onAddListener = null;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity());

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_new_person);

        systemMessageManager = new SystemMessageManager((TextView) dialog.findViewById(R.id.system_message));

        nameView = (EditText) dialog.findViewById(R.id.name);
        tipView = (EditText) dialog.findViewById(R.id.tip);

        dialog.findViewById(R.id.add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate())
                    add();
            }
        });

        return dialog;
    }

    public void setOnAddListener(OnAddListener<Person> onAddListener) {
        this.onAddListener = onAddListener;
    }

    private void add() {
        String name = nameView.getText().toString().trim();
        String tipString = tipView.getText().toString().trim();

        int tip;
        try {
            tip = Integer.parseInt(tipString);
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

    private void onAdd(Person person) {
        if (onAddListener != null)
            onAddListener.onAdd(person);
    }

    private boolean validate() {
        if (nameView.getText().toString().trim().length() == 0) {
            systemMessageManager.displayError(ERROR_MISSING_NAME);
            return false;
        }

        return true;
    }
}
