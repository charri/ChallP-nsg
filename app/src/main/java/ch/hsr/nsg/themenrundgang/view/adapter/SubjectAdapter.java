package ch.hsr.nsg.themenrundgang.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import ch.hsr.nsg.themenrundgang.R;
import ch.hsr.nsg.themenrundgang.dagger.InjectingActivityModule;
import ch.hsr.nsg.themenrundgang.model.Subject;
import ch.hsr.nsg.themenrundgang.model.SubjectRepository;
import ch.hsr.nsg.themenrundgang.vm.SubjectViewModel;


public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.ViewHolder> {

    private final ArrayList<SubjectViewModel.UiSubject> mSubjects;
    private final ImageLoader imageLoader;

    @Inject
    public SubjectAdapter(SubjectViewModel viewModel, ImageLoader imageLoader) {
        this.imageLoader = imageLoader;
        mSubjects = viewModel.getSubjects();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_subject, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        final SubjectViewModel.UiSubject subject = mSubjects.get(i);
        viewHolder.title.setText(subject.getName());
        viewHolder.image.setImageDrawable(null);
        if(subject.getImageUrl() != null) {
            imageLoader.displayImage(subject.getImageUrl(), viewHolder.image);
        }
        viewHolder.checkBox.setChecked(subject.isChecked());
        viewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                subject.setChecked(isChecked);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mSubjects.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.title) public TextView title;
        @InjectView(R.id.card_image) public ImageView image;
        @InjectView(R.id.checkbox) public CheckBox checkBox;

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.inject(this, itemView);
        }

    }
}
