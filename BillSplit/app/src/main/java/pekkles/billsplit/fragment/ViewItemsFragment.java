package pekkles.billsplit.fragment;

import android.view.View;
import android.view.ViewGroup;

import pekkles.billsplit.R;
import pekkles.billsplit.model.Item;
import pekkles.billsplit.widget.CustomTextView;

public class ViewItemsFragment extends ViewModelsFragment<Item> {
    private static final int TEXT_TOTAL = R.string.text_total;

    @Override
    protected ModelsAdapter initAdapter() {
        return new ItemsAdapter();
    }

    private class ItemsAdapter extends ModelsAdapter {
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater().inflate(R.layout.list_model, parent, false);

                ViewHolder holder = new ViewHolder();
                holder.nameView = (CustomTextView) convertView.findViewById(R.id.name);
                holder.totalView = (CustomTextView) convertView.findViewById(R.id.total);
                holder.divider = convertView.findViewById(R.id.divider);

                convertView.setTag(holder);
            }

            ViewHolder holder = (ViewHolder) convertView.getTag();
            Item item = getItem(position);

            holder.nameView.setText(item.getName());
            holder.totalView.setText(getString(TEXT_TOTAL, item.getTotal()));

            setDivider(holder, position);

            return convertView;
        }

        private class ViewHolder extends AbstractViewHolder {
            public CustomTextView nameView;
            public CustomTextView totalView;
        }
    }
}
