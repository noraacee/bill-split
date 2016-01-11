package pekkles.billsplit.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import pekkles.billsplit.R;
import pekkles.billsplit.activity.BillSplitActivity;
import pekkles.billsplit.model.Person;
import pekkles.billsplit.widget.CustomTextView;

public class ViewPersonDialog extends ViewModelDialog<Person> {
    private static final float DIVIDER_WIDTH = 0.75f;

    private static final int TEXT_TIP = R.string.text_tip;
    private static final int TEXT_TOTAL = R.string.text_total;

    private static final String KEY_POSITION = "position";

    private Person person;

    public static ViewPersonDialog newInstance(int position) {
        Bundle args = new Bundle();
        args.putInt(KEY_POSITION, position);

        ViewPersonDialog dialog = new ViewPersonDialog();
        dialog.setArguments(args);

        return dialog;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        int position = getArguments().getInt(KEY_POSITION);
        person = ((BillSplitActivity) activity).getPeople().get(position);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity());

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_view_person);

        if (!person.getItems().isEmpty())
            dialog.findViewById(R.id.divider).setVisibility(View.VISIBLE);

        TextView nameView = (TextView) dialog.findViewById(R.id.name);
        nameView.setText(person.getName());

        TextView tipView = (TextView) dialog.findViewById(R.id.tip);
        tipView.setText(getString(TEXT_TIP, person.getTip()));

        TextView totalView = (TextView) dialog.findViewById(R.id.total);
        totalView.setText(getString(TEXT_TOTAL, person.getTotal()));

        ListView itemsView = (ListView) dialog.findViewById(R.id.items);
        ItemsAdapter adapter = new ItemsAdapter();
        itemsView.setAdapter(adapter);

        return dialog;
    }

    private class ItemsAdapter extends BaseAdapter {
        private List<String> items;
        private Map<String, Double> prices;

        public ItemsAdapter() {
            items = new ArrayList<>();
            prices = person.getItems();

            for (String s : prices.keySet()) {
                items.add(s);
            }
        }

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public String getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater().inflate(R.layout.list_item, parent, false);

                ViewHolder holder = new ViewHolder();
                holder.nameView = (CustomTextView) convertView.findViewById(R.id.name);
                holder.costView = (CustomTextView) convertView.findViewById(R.id.cost);

                convertView.setTag(holder);
            }

            ViewHolder holder = (ViewHolder) convertView.getTag();

            String name = items.get(position);

            holder.nameView.setText(name);
            holder.costView.setText(String.format("$%.2f", prices.get(name)));

            return convertView;
        }

        private class ViewHolder {
            public CustomTextView nameView;
            public CustomTextView costView;
        }
    }
}
