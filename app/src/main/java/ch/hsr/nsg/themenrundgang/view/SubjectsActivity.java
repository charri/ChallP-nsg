package ch.hsr.nsg.themenrundgang.view;

import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import javax.inject.Inject;

import butterknife.InjectView;
import ch.hsr.nsg.themenrundgang.R;
import ch.hsr.nsg.themenrundgang.dagger.InjectingActivity;
import ch.hsr.nsg.themenrundgang.model.Subject;
import ch.hsr.nsg.themenrundgang.model.SubjectRepository;
import ch.hsr.nsg.themenrundgang.view.adapter.SubjectAdapter;
import ch.hsr.nsg.themenrundgang.vm.SubjectViewModel;

public class SubjectsActivity extends InjectingActivity {

    @InjectView(R.id.recyler_cards)
    RecyclerView mRecyclerView;

    SubjectAdapter mAdapter;

	@Override protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_subjects);


        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mAdapter = getObjectGraph().get(SubjectAdapter.class);
        mRecyclerView.setAdapter(mAdapter);

	}


}
