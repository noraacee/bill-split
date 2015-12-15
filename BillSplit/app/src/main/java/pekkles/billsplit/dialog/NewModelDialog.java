package pekkles.billsplit.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import pekkles.billsplit.R;
import pekkles.billsplit.utility.SystemMessageManager;

public abstract class NewModelDialog<E> extends DialogFragment {
    public interface OnAddListener<E>{
        void onAdd(E e);
    }

    private OnAddListener<E> onAddListener;

    protected SystemMessageManager systemMessageManager;

    @Override
    @SuppressWarnings("unchecked")
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        onAddListener = (OnAddListener<E>) activity;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity());

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(getLayoutId());

        systemMessageManager = new SystemMessageManager((TextView) dialog.findViewById(R.id.system_message));

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
        onAddListener.onAdd(e);
    }
}
