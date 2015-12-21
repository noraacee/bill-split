package pekkles.billsplit.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import pekkles.billsplit.R;
import pekkles.billsplit.activity.BillSplitActivity;
import pekkles.billsplit.utility.SystemMessageManager;

public abstract class NewModelDialog<E> extends DialogFragment {
    protected BillSplitActivity activity;
    protected SystemMessageManager systemMessageManager;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

       this.activity = (BillSplitActivity) activity;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity());

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(getLayoutId());

        systemMessageManager = new SystemMessageManager(getActivity(), (TextView) dialog.findViewById(R.id.system_message));

        dialog.findViewById(R.id.name).setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
            }
        });

        dialog.findViewById(R.id.add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate())
                    add();
            }
        });

        initDialog(dialog);

        return dialog;
    }

    protected abstract void add();

    protected abstract int getLayoutId();

    protected abstract void initDialog(Dialog dialog);

    protected abstract boolean validate();

    protected void onAdd(E e) {
        activity.add(e);
    }
}
