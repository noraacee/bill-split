package pekkles.billsplit.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public abstract class ViewModelsFragment<E> extends Fragment {
    protected ModelsAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        adapter = initAdapter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(getLayoutId(), container, false);

        ListView models = (ListView) rootView.findViewById(getListViewId());
        models.setAdapter(adapter);

        return rootView;
    }

    public void add(E e) {
        adapter.add(e);
    }

    public List<E> getList() {
        return adapter.getList();
    }

    public void notifyDataSetChanged() {
        adapter.notifyDataSetChanged();
    }

    protected abstract int getLayoutId();

    protected abstract int getListViewId();

    protected abstract ModelsAdapter initAdapter();

    protected abstract class ModelsAdapter extends BaseAdapter {
        private List<E> models;

        public ModelsAdapter() {
            models = new ArrayList<>();
        }

        @Override
        public int getCount() {
            return models.size();
        }

        @Override
        public E getItem(int position) {
            return models.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public void add(E e) {
            models.add(e);
            notifyDataSetChanged();
        }

        public List<E> getList() {
            return models;
        }
    }
}
