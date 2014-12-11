package ch.hsr.nsg.themenrundgang.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import butterknife.InjectView;
import ch.hsr.nsg.themenrundgang.R;
import ch.hsr.nsg.themenrundgang.dagger.InjectingActivity;
import ch.hsr.nsg.themenrundgang.model.Subject;
import ch.hsr.nsg.themenrundgang.ui.DividerItemDecoration;
import ch.hsr.nsg.themenrundgang.view.adapter.ItemAdapter;

public class ItemsActivity extends InjectingActivity {

    @InjectView(R.id.recyler_items)
    RecyclerView mRecyclerView;

    Subject[] mSubjects;

    private ItemAdapter mAdapter;

    private final static String EXTRA_SUBJECTS = ItemsActivity.class.getName() + ":subjects";

    public static Intent getIntent(Context context, Subject[] subjects) {
        Intent intent = new Intent(context, ItemsActivity.class);
        intent.putExtra(EXTRA_SUBJECTS, subjects);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_items);

        mSubjects = (Subject[])getIntent().getParcelableArrayExtra(EXTRA_SUBJECTS);


        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, null));
        mRecyclerView.setHasFixedSize(true);

        mAdapter = getObjectGraph().get(ItemAdapter.class);
        mRecyclerView.setAdapter(mAdapter);



    }
}
