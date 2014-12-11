package ch.hsr.nsg.themenrundgang.view;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.InjectView;
import butterknife.OnClick;
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

    @InjectView(R.id.subject_info_card_selected)
    TextView mInfoCardSelected;

    SubjectAdapter mAdapter;

    @Inject
    SubjectViewModel viewModel;

	@Override protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

        viewModel.addObserver(this); // register monitoring
		
		setContentView(R.layout.activity_subjects);

        Drawable divider = getDrawable(R.drawable.divider_cardview);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(divider));

        mAdapter = getObjectGraph().get(SubjectAdapter.class);
        mRecyclerView.setAdapter(mAdapter);

        mInfoCardSelected.setText(getSubjectsText());

        super.addOnListener(SubjectViewModel.KEY_SUBJECTS, new OnListener() {
            @Override
            public void onNotify() {
                mInfoCardSelected.setText(getSubjectsText());
            }
        });
	}

    private String getSubjectsText() {
        return  String.format(
                getResources().getString(R.string.subject_info_card_selected),
                viewModel.getSubjectsChecked(),
                viewModel.getSubjects().size());
    }

}
