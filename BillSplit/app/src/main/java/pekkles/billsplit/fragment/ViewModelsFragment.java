package pekkles.billsplit.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import pekkles.billsplit.R;

public abstract class ViewModelsFragment<E> extends Fragment {
    private static final float DIVIDER_WIDTH = 0.75f;

    protected ModelsAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        adapter = initAdapter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_view_models, container, false);

        ListView models = (ListView) rootView.findViewById(R.id.models);
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

    protected abstract ModelsAdapter initAdapter();

    protected abstract class ModelsAdapter extends BaseAdapter {
        private List<E> models;

        private LinearLayout.LayoutParams leftDivider;
        private LinearLayout.LayoutParams rightDivider;

        public ModelsAdapter() {
            init();
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

        private void init() {
            models = new ArrayList<>();

            DisplayMetrics metrics = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);

            int dividerWidth = (int) (metrics.widthPixels * DIVIDER_WIDTH);
            int dividerHeight = getResources().getDimensionPixelSize(R.dimen.divider_height);

            leftDivider = new LinearLayout.LayoutParams(dividerWidth, dividerHeight);
            leftDivider.gravity = Gravity.START;

            rightDivider = new LinearLayout.LayoutParams(dividerWidth, dividerHeight);
            rightDivider.gravity = Gravity.END;
        }

        protected void setDivider(AbstractViewHolder holder, int position) {
            if (position == getCount() - 1) {
                holder.divider.setVisibility(View.GONE);
            } else {
                holder.divider.setVisibility(View.VISIBLE);

                if (position % 2 == 0)
                    holder.divider.setLayoutParams(leftDivider);
                else
                    holder.divider.setLayoutParams(rightDivider);
            }
        }

        protected abstract class AbstractViewHolder {
            public View divider;
            public View view;
        }
    }
}
