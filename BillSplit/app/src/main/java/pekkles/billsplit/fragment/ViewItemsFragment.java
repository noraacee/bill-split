package pekkles.billsplit.fragment;

import android.view.View;
import android.view.ViewGroup;

import pekkles.billsplit.R;
import pekkles.billsplit.model.Item;

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
            return null;
        }
    }
}
