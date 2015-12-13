package pekkles.billsplit.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import pekkles.billsplit.R;
import pekkles.billsplit.dialog.NewPersonDialog;
import pekkles.billsplit.fragment.ViewItemsFragment;
import pekkles.billsplit.fragment.ViewPeopleFragment;
import pekkles.billsplit.interf.OnAddListener;
import pekkles.billsplit.model.Person;

public class BillSplitActivity extends FragmentActivity {
    private static final int COUNT_PAGES = 2;
    private static final int POSITION_ITEMS = 1;
    private static final int POSITION_PEOPLE = 0;

    private static final String TAG_PERSON = "person";

    private ViewPager viewPager;

    private ViewItemsFragment viewItemsFragment;
    private ViewPeopleFragment viewPeopleFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_split);

        viewItemsFragment = new ViewItemsFragment();
        viewPeopleFragment = new ViewPeopleFragment();

        BillSplitPagerAdapter adapter = new BillSplitPagerAdapter(getSupportFragmentManager());
        viewPager = (ViewPager) findViewById(R.id.view);
        viewPager.setAdapter(adapter);

        findViewById(R.id.add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(viewPager.getCurrentItem()) {
                    case POSITION_PEOPLE:
                        showNewPersonDialog();
                        break;
                    case POSITION_ITEMS:
                        break;
                    default:
                        showNewPersonDialog();
                        break;
                }
            }
        });


    }

    private void showNewPersonDialog() {
        NewPersonDialog dialog = new NewPersonDialog();
        dialog.setOnAddListener(new OnAddListener<Person>() {
            @Override
            public void onAdd(Person person) {
                viewPeopleFragment.add(person);
            }
        });
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
