package ch.hsr.nsg.themenrundgang.view;

import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.InjectView;
import ch.hsr.nsg.themenrundgang.R;
import ch.hsr.nsg.themenrundgang.dagger.InjectingActivity;
import ch.hsr.nsg.themenrundgang.model.Subject;
import ch.hsr.nsg.themenrundgang.model.SubjectRepository;
import ch.hsr.nsg.themenrundgang.ui.DividerItemDecoration;
import ch.hsr.nsg.themenrundgang.utils.OnListener;
import ch.hsr.nsg.themenrundgang.view.adapter.SubjectAdapter;
import ch.hsr.nsg.themenrundgang.vm.SubjectViewModel;

public class SubjectsActivity extends InjectingActivity {

    @InjectView(R.id.recyler_cards)
    RecyclerView mRecyclerView;

    @InjectView(R.id.info_title)
    TextView mInfoTitle;

    SubjectAdapter mAdapter;

    @Inject
    SubjectViewModel viewModel;

	@Override protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_subjects);


        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, null));

        mAdapter = getObjectGraph().get(SubjectAdapter.class);
        mRecyclerView.setAdapter(mAdapter);

        super.addOnListener(SubjectViewModel.KEY_SUBJECTS, new OnListener() {
            @Override
            public void onNotify() {

                String text = String.format(
                                getResources().getString(R.string.subject_info_card_selected),
                                viewModel.getSubjectsChecked(),
                                viewModel.getSubjects().size());

                mInfoTitle.setText(text);
            }
        });
	}


}
