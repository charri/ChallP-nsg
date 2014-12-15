package ch.hsr.nsg.themenrundgang.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import ch.hsr.nsg.themenrundgang.R;
import ch.hsr.nsg.themenrundgang.model.Subject;
import ch.hsr.nsg.themenrundgang.vm.ItemsAllViewModel;
import ch.hsr.nsg.themenrundgang.vm.model.UiItem;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {


    private final ItemsAllViewModel mViewModel;
    private final ImageLoader imageLoader;
    ArrayList<UiItem> mItems;

    @Inject
    public ItemAdapter(ItemsAllViewModel viewModel, ImageLoader imageLoader) {
        mViewModel = viewModel;
        this.imageLoader = imageLoader;

    }

    public void setSubjects(Subject[] subjects) {
        mViewModel.setSubjects(subjects);
        mItems = mViewModel.getItems();
    }

    @Override
    public ItemAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_item, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ItemAdapter.ViewHolder viewHolder, int i) {
        final UiItem item = mItems.get(i);
        viewHolder.title.setText(item.getName());
        imageLoader.displayImage(item.getImageUrl(), viewHolder.image);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.title) public TextView title;
        @InjectView(R.id.card_image) public ImageView image;
        @InjectView(R.id.btnDetail) public Button btnDetail;

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.inject(this, itemView);
        }

        @OnClick({R.id.btnDetail})
        public void onClick() {

        }
    }
}