package pekkles.billsplit.fragment;

import android.view.View;
import android.view.ViewGroup;

import pekkles.billsplit.R;
import pekkles.billsplit.dialog.ViewPersonDialog;
import pekkles.billsplit.model.Person;
import pekkles.billsplit.widget.CustomTextView;

public class ViewPeopleFragment extends ViewModelsFragment<Person> {
    private static final int TEXT_TOTAL = R.string.text_total;

    private static final String TAG_PERSON = "person";

    @Override
    protected ModelsAdapter initAdapter() {
        return new PeopleAdapter();
    }

    private class PeopleAdapter extends ModelsAdapter {
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater().inflate(R.layout.list_model, parent, false);

                final ViewHolder holder = new ViewHolder();

                holder.nameView = (CustomTextView) convertView.findViewById(R.id.name);
                holder.totalView = (CustomTextView) convertView.findViewById(R.id.total);
                holder.divider = convertView.findViewById(R.id.divider);
                holder.view = convertView.findViewById(R.id.view);

                convertView.setTag(holder);
            }

            ViewHolder holder = (ViewHolder) convertView.getTag();
            Person person = getItem(position);

            holder.nameView.setText(person.getName());
            holder.totalView.setText(getString(TEXT_TOTAL, person.getTotal()));

            holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showViewPersonDialog(position);
                }
            });

            setDivider(holder, position);

            return convertView;
        }

        private class ViewHolder extends AbstractViewHolder {
            public CustomTextView nameView;
            public CustomTextView totalView;
        }
    }

    private void showViewPersonDialog(int position) {
        ViewPersonDialog dialog = ViewPersonDialog.newInstance(position);
        dialog.show(getFragmentManager(), TAG_PERSON);
    }
}
