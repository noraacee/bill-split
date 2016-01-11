package pekkles.billsplit.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import pekkles.billsplit.R;
import pekkles.billsplit.dialog.NewItemDialog;
import pekkles.billsplit.dialog.NewPersonDialog;
import pekkles.billsplit.fragment.ViewItemsFragment;
import pekkles.billsplit.fragment.ViewPeopleFragment;
import pekkles.billsplit.model.Item;
import pekkles.billsplit.model.Person;
import pekkles.billsplit.utility.SystemMessageManager;

public class BillSplitActivity extends FragmentActivity {
    private static final int ERROR_PEOPLE = R.string.error_people;

    private static final int COUNT_PAGES = 2;
    private static final int POSITION_ITEMS = 1;
    private static final int POSITION_PEOPLE = 0;

    private static final String TAG_ITEM = "item";
    private static final String TAG_PERSON = "person";

    private SystemMessageManager systemMessageManager;

    private ViewPager viewPager;
    private ViewItemsFragment viewItemsFragment;
    private ViewPeopleFragment viewPeopleFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_split);

        systemMessageManager = new SystemMessageManager(this, (TextView) findViewById(R.id.system_message));

        viewItemsFragment = new ViewItemsFragment();
        viewPeopleFragment = new ViewPeopleFragment();

        BillSplitPagerAdapter adapter = new BillSplitPagerAdapter(getSupportFragmentManager());
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        viewPager.setAdapter(adapter);

        findViewById(R.id.add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(viewPager.getCurrentItem()) {
                    case POSITION_PEOPLE:
                        showNewPersonDialog();
                        break;
                    case POSITION_ITEMS:
                        if (getPeople().isEmpty())
                            systemMessageManager.displayError(getString(ERROR_PEOPLE));
                        else
                            showNewItemDialog();
                        break;
                    default:
                        showNewPersonDialog();
                        break;
                }
            }
        });


    }

    public void add(Object o) {
        if (o instanceof Person)
            viewPeopleFragment.add((Person) o);
        else if (o instanceof Item)
            viewItemsFragment.add((Item) o);
    }

    public List<Person> getPeople() {
        return viewPeopleFragment.getList();
    }

    public void notifyDataSetChanged(boolean people) {
        if (people)
            viewPeopleFragment.notifyDataSetChanged();
        else
            viewItemsFragment.notifyDataSetChanged();
    }

    private void showNewItemDialog() {
        NewItemDialog dialog = new NewItemDialog();
        dialog.show(getSupportFragmentManager(), TAG_ITEM);
    }

    private void showNewPersonDialog() {
        NewPersonDialog dialog = new NewPersonDialog();
        dialog.show(getSupportFragmentManager(), TAG_PERSON);
    }

    private class BillSplitPagerAdapter extends FragmentPagerAdapter {

        public BillSplitPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            switch(position) {
                case POSITION_PEOPLE:
                    return viewPeopleFragment;
                case POSITION_ITEMS:
                    return viewItemsFragment;
                default:
                    return viewPeopleFragment;
            }
        }

        @Override
        public int getCount() {
            return COUNT_PAGES;
        }
    }
}
