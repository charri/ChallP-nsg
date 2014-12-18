package ch.hsr.nsg.themenrundgang.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import butterknife.InjectView;
import butterknife.OnClick;
import ch.hsr.nsg.themenrundgang.R;
import ch.hsr.nsg.themenrundgang.dagger.InjectingFragmentActivity;
import ch.hsr.nsg.themenrundgang.ui.SlidingTabLayout;
import ch.hsr.nsg.themenrundgang.vm.model.UiSubject;

public class ItemsActivity extends InjectingFragmentActivity {

    @InjectView(R.id.pager)
    ViewPager mViewPager;


    @InjectView(R.id.info_card)
    View mInfoCard;

    @InjectView(R.id.sliding_tabs)
    SlidingTabLayout tabLayout;

    UiSubject[] mSubjects;

    ItemsPagerAdapter mAdapter;

    private final static String EXTRA_SUBJECTS = ItemsActivity.class.getName() + ":subjects";

    public static Intent getIntent(Context context, UiSubject[] subjects) {
        Intent intent = new Intent(context, ItemsActivity.class);
        intent.putExtra(EXTRA_SUBJECTS, subjects);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setStatusBarColor(getResources().getColor(R.color.blue_dark));

        setContentView(R.layout.activity_items);

        Parcelable[] ps = getIntent().getParcelableArrayExtra(EXTRA_SUBJECTS);
        mSubjects = new UiSubject[ps.length];
        System.arraycopy(ps, 0, mSubjects, 0, ps.length);

        mAdapter = new ItemsPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);
        tabLayout.setViewPager(mViewPager);
        tabLayout.setDividerColors(Color.TRANSPARENT);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }

    @OnClick(R.id.info_icon)
    public void OnIconClick() {

        mInfoCard.setVisibility(View.GONE);
    }

    private class ItemsPagerAdapter extends FragmentStatePagerAdapter {

        private final Fragment[] fragments;

        public ItemsPagerAdapter(FragmentManager fm) {
            super(fm);

            fragments = new Fragment[] {
                ItemsFragmentBeacons.newInstance(mSubjects),
                ItemsFragmentAll.newInstance(mSubjects)
            };
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch(position) {
                case 0: return getResources().getString(R.string.items_near);
                case 1: return getResources().getString(R.string.items_all);
            }
            return "Tab " + position;
        }

        @Override
        public Fragment getItem(int position) {

            return fragments[position];
        }


        @Override
        public int getCount() {
            return fragments.length;
        }
    }
}
