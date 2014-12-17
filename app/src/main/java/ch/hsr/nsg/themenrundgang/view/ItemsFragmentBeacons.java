package ch.hsr.nsg.themenrundgang.view;


import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import ch.hsr.nsg.themenrundgang.R;
import ch.hsr.nsg.themenrundgang.dagger.InjectingFragment;
import ch.hsr.nsg.themenrundgang.ui.DividerItemDecoration;
import ch.hsr.nsg.themenrundgang.view.adapter.ItemAdapter;
import ch.hsr.nsg.themenrundgang.vm.ItemViewModel;
import ch.hsr.nsg.themenrundgang.vm.model.UiItem;
import ch.hsr.nsg.themenrundgang.vm.model.UiSubject;

public class ItemsFragmentBeacons extends InjectingFragment implements ItemViewModel.UiItemListener {

    private final static String EXTRA_SUBJECTS = ItemsFragmentBeacons.class.getName() + ":subjects";

    public static ItemsFragmentBeacons newInstance(UiSubject[] subjects) {
        ItemsFragmentBeacons f = new ItemsFragmentBeacons();
        Bundle args = new Bundle();
        args.putParcelableArray(EXTRA_SUBJECTS, subjects);
        f.setArguments(args);
        return f;
    }


    @InjectView(R.id.recyler_items)
    RecyclerView mRecyclerView;

    UiSubject[] mSubjects;

    ItemAdapter mAdapter;

    @Inject
    ItemViewModel mViewModel;

    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_items_all, container, false);

        ButterKnife.inject(this, rootView);

        Parcelable[] ps =getArguments().getParcelableArray(EXTRA_SUBJECTS);
        mSubjects = new UiSubject[ps.length];
        System.arraycopy(ps, 0, mSubjects, 0, ps.length);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getResources().getDrawable(R.drawable.divider_cardview)));
        mAdapter = getObjectGraph().get(ItemAdapter.class);
        mAdapter.setSubjects(mSubjects);
        mAdapter.setOnClickListener(new ItemAdapter.OnClickListener() {
            @Override
            public void onClick(View view, UiItem item) {
                startActivity(DetailActivity.getIntent(getActivity(), item));
            }
        });
        mRecyclerView.setAdapter(mAdapter);

        mViewModel.setUiItemListener(this);

        return rootView;
    }

    @Override
    public void addItem(final UiItem item) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mAdapter.addItem(item);
            }
        });
    }

    @Override
    public void removeItem(final UiItem item) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mAdapter.removeItem(item);
            }
        });
    }
}
