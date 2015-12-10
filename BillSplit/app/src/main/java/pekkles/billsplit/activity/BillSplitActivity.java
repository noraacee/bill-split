package pekkles.billsplit.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import pekkles.billsplit.R;
import pekkles.billsplit.model.Person;

public class BillSplitActivity extends FragmentActivity {
    private EditText nameView;
    private EditText tipView;
    private PeopleAdapter peopleAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_split);

        peopleAdapter = new PeopleAdapter();
        ListView peopleView = (ListView) findViewById(R.id.people);
        peopleView.setAdapter(peopleAdapter);

        nameView = (EditText) findViewById(R.id.name);
        tipView = (EditText) findViewById(R.id.tip);

        findViewById(R.id.add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Person person = new Person(nameView.getText().toString(), Integer.parseInt(tipView.getText().toString()));
                peopleAdapter.add(person);

                nameView.setText("");
                tipView.setText("");
            }
        });
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
                convertView = getLayoutInflater().inflate(R.layout.list_person, parent, false);

                ViewHolder holder = new ViewHolder();
                holder.nameView = (TextView) convertView.findViewById(R.id.name);
                holder.tipView = (TextView) convertView.findViewById(R.id.tip);

                convertView.setTag(holder);
            }

            ViewHolder holder = (ViewHolder) convertView.getTag();
            Person person = people.get(position);

            holder.nameView.setText(person.getName());
            holder.tipView.setText(person.getTip() + "%");

            return convertView;
        }

        public void add(Person person) {
            people.add(person);
            notifyDataSetChanged();
        }

        private class ViewHolder {
            TextView nameView;
            TextView tipView;
        }
    }
}
