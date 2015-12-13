package pekkles.billsplit.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import pekkles.billsplit.R;
import pekkles.billsplit.model.Person;

public class ViewPeopleFragment extends Fragment {
    private static final String PERCENT = "%";

    private PeopleAdapter peopleAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        peopleAdapter = new PeopleAdapter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_people, container, false);

        ListView peopleView = (ListView) rootView.findViewById(R.id.people);
        peopleView.setAdapter(peopleAdapter);

        return rootView;
    }

    public void add(Person person) {
        peopleAdapter.add(person);
    }

    private class PeopleAdapter extends BaseAdapter {
        private List<Person> people;

        public PeopleAdapter() {
            people = new ArrayList<>();
        }

        @Override
        public int getCount() {
            return people.size();
        }

        @Override
        public Person getItem(int position) {
            return people.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater().inflate(R.layout.list_person, parent, false);

                ViewHolder holder = new ViewHolder();
                holder.nameView = (TextView) convertView.findViewById(R.id.name);
                holder.tipView = (TextView) convertView.findViewById(R.id.tip);

                convertView.setTag(holder);
            }

            ViewHolder holder = (ViewHolder) convertView.getTag();
            Person person = getItem(position);

            holder.nameView.setText(person.getName());
            holder.tipView.setText(person.getTip() + PERCENT);

            return convertView;
        }

        public void add(Person person) {
            people.add(person);
            notifyDataSetChanged();
        }

        private class ViewHolder {
            public TextView nameView;
            public TextView tipView;
        }
    }
}
