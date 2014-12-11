package ch.hsr.nsg.themenrundgang.view;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.widget.RelativeLayout;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.InjectView;
import butterknife.OnClick;
import ch.hsr.nsg.themenrundgang.R;
import ch.hsr.nsg.themenrundgang.dagger.InjectingActivity;
import ch.hsr.nsg.themenrundgang.ui.DividerItemDecoration;
import ch.hsr.nsg.themenrundgang.utils.AnimationUtils;
import ch.hsr.nsg.themenrundgang.utils.OnListener;
import ch.hsr.nsg.themenrundgang.view.adapter.SubjectAdapter;
import ch.hsr.nsg.themenrundgang.vm.SubjectViewModel;

public class SubjectsActivity extends InjectingActivity {

    @InjectView(R.id.recyler_cards)
    RecyclerView mRecyclerView;

    @InjectView(R.id.subject_info_card_selected)
    TextView mInfoCardSelected;

    MenuItem mMenuNext;

    @Inject
    SubjectViewModel viewModel;

    @InjectView(R.id.hideable)
    RelativeLayout mHideable;

    AnimationUtils mAnimationUtils;
    Animation mAnimationReduce;
    Animation mAnimationExpand;

    boolean forceOpen = false;

    @Override protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

        viewModel.addObserver(this); // register monitoring
		
		setContentView(R.layout.activity_subjects);

        mAnimationUtils = new AnimationUtils(SubjectsActivity.this);
        mAnimationReduce = mAnimationUtils.makeAnimation(mRecyclerView, 0, -70, 55);
        mAnimationExpand = mAnimationUtils.makeAnimation(mRecyclerView, 0, 70, 130);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getDrawable(R.drawable.divider_cardview)));
        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(!forceOpen && mHideable.getVisibility() == View.VISIBLE && layoutManager.findFirstCompletelyVisibleItemPosition() > 1) {
                    mHideable.setVisibility(View.GONE);
                    mRecyclerView.setAnimation(mAnimationReduce);
                }
            }
        });

        mRecyclerView.setAdapter(getObjectGraph().get(SubjectAdapter.class));

        mInfoCardSelected.setText(getSubjectsText());

        addOnListener(SubjectViewModel.KEY_SUBJECTS, new OnListener() {
            @Override
            public void onNotify() {
                mInfoCardSelected.setText(getSubjectsText());
                if (mMenuNext != null) {
                    mMenuNext.setEnabled(viewModel.getSubjectsChecked() > 0);
                }
            }
        });
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_subjects, menu);

        mMenuNext = menu.findItem(R.id.action_next);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_next:
                //TODO: Start ItemsActivity
                return true;
            default: return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private String getSubjectsText() {
        return  String.format(
                getResources().getString(R.string.subject_info_card_selected),
                viewModel.getSubjectsChecked(),
                viewModel.getSubjects().size());
    }

    @OnClick(R.id.subject_info_card_selected)
    public void OnInfoTextClick() {
        if(mHideable.getVisibility() != View.GONE) return;
        mHideable.setVisibility(View.VISIBLE);
        mRecyclerView.setAnimation(mAnimationExpand);
        forceOpen = true;
    }
}
