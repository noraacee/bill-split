package pekkles.billsplit.fragment;

import android.view.View;
import android.view.ViewGroup;

import pekkles.billsplit.R;
import pekkles.billsplit.model.Item;
import pekkles.billsplit.widget.CustomTextView;

public class ViewItemsFragment extends ViewModelsFragment<Item> {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_view_items;
    }

    @Override
    protected int getListViewId() {
        return R.id.items;
    }

    @Override
    protected ModelsAdapter initAdapter() {
        return new ItemsAdapter();
    }

    private class ItemsAdapter extends ModelsAdapter {
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater().inflate(R.layout.list_item, parent, false);

                ViewHolder holder = new ViewHolder();
                holder.nameView = (CustomTextView) convertView.findViewById(R.id.name);
                holder.priceView = (CustomTextView) convertView.findViewById(R.id.price);
                holder.taxView = (CustomTextView) convertView.findViewById(R.id.tax);
                holder.quantityView = (CustomTextView) convertView.findViewById(R.id.quantity);
                holder.totalView = (CustomTextView) convertView.findViewById(R.id.total);

                convertView.setTag(holder);
            }

            ViewHolder holder = (ViewHolder) convertView.getTag();
            Item item = getItem(position);

            holder.nameView.setText(item.getName().toUpperCase());
            holder.priceView.setText(getString(R.string.text_price, item.getPrice()));
            holder.taxView.setText(getString(R.string.text_tax, item.getTax()));
            holder.quantityView.setText(getString(R.string.text_quantity, item.getQuantity()));
            holder.totalView.setText(getString(R.string.text_total, item.getTotal()));

            return convertView;
        }

        private class ViewHolder {
            public CustomTextView nameView;
            public CustomTextView priceView;
            public CustomTextView taxView;
            public CustomTextView quantityView;
            public CustomTextView totalView;
        }
    }
}
