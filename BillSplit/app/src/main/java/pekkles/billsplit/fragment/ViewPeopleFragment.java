package pekkles.billsplit.fragment;

import android.view.View;
import android.view.ViewGroup;

import pekkles.billsplit.R;
import pekkles.billsplit.model.Person;
import pekkles.billsplit.widget.CustomTextView;

public class ViewPeopleFragment extends ViewModelsFragment<Person> {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_view_people;
    }

    @Override
    protected int getListViewId() {
        return R.id.people;
    }

    @Override
    protected ModelsAdapter initAdapter() {
        return new PeopleAdapter();
    }

    private class PeopleAdapter extends ModelsAdapter {
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater().inflate(R.layout.list_person, parent, false);

                ViewHolder holder = new ViewHolder();
                holder.nameView = (CustomTextView) convertView.findViewById(R.id.name);

                convertView.setTag(holder);
            }

            ViewHolder holder = (ViewHolder) convertView.getTag();
            Person person = getItem(position);

            holder.nameView.setText(person.getName().toUpperCase());

            return convertView;
        }

        private class ViewHolder {
            public CustomTextView nameView;
        }
    }
}
