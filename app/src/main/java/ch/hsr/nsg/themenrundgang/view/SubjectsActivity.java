package ch.hsr.nsg.themenrundgang.view;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toolbar;

import com.joanzapata.android.iconify.IconDrawable;
import com.joanzapata.android.iconify.Iconify;

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

    @Inject
    SubjectViewModel viewModel;

    @InjectView(R.id.hideable)
    RelativeLayout mHideable;

    @InjectView(R.id.toolbar)
    Toolbar mToolbar;
    @InjectView(R.id.btnNext)
    TextView mMenuNext;

    AnimationUtils mAnimationUtils;
    Animation mAnimationReduce;
    Animation mAnimationExpand;

    boolean forceOpen = false;

    @Override protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

        viewModel.addObserver(this); // register monitoring
		
		setContentView(R.layout.activity_subjects);

        mMenuNext.setCompoundDrawables(null, null, new IconDrawable(this, Iconify.IconValue.fa_arrow_right)
                .colorRes(android.R.color.white)
                .actionBarSize(), null);

        mAnimationUtils = new AnimationUtils(SubjectsActivity.this);
        mAnimationReduce = mAnimationUtils.makeAnimation(mRecyclerView, 0, -70, 55);
        mAnimationExpand = mAnimationUtils.makeAnimation(mRecyclerView, 0, 70, 135);

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
        mMenuNext.setVisibility(View.GONE);

        addOnListener(SubjectViewModel.KEY_SUBJECTS, new OnListener() {
            @Override
            public void onNotify() {
                mInfoCardSelected.setText(getSubjectsText());
                mMenuNext.setVisibility(viewModel.getSubjectsCheckedCount() > 0 ? View.VISIBLE : View.GONE);
            }

        });
	}

    private String getSubjectsText() {
        return  String.format(
                getResources().getString(R.string.subject_info_card_selected),
                viewModel.getSubjectsCheckedCount(),
                viewModel.getSubjects().size());
    }
    @OnClick(R.id.btnNext)
    public void OnNextClick() {
        startActivity(ItemsActivity.getIntent(this, viewModel.getSubjectsChecked()));
    }

    @OnClick(R.id.subject_info_card_selected)
    public void OnInfoTextClick() {
        if(mHideable.getVisibility() != View.GONE) return;
        mHideable.setVisibility(View.VISIBLE);
        mRecyclerView.setAnimation(mAnimationExpand);
        forceOpen = true;
    }
}
