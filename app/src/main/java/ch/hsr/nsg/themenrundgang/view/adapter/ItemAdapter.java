package ch.hsr.nsg.themenrundgang.view.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import ch.hsr.nsg.themenrundgang.R;
import ch.hsr.nsg.themenrundgang.model.Subject;
import ch.hsr.nsg.themenrundgang.vm.ItemViewModel;
import ch.hsr.nsg.themenrundgang.vm.model.UiItem;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    private final ItemViewModel mViewModel;
    private final ImageLoader imageLoader;
    ArrayList<UiItem> mItems;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.mOnClickListener = onClickListener;
    }

    private OnClickListener mOnClickListener;

    @Inject
    public ItemAdapter(ItemViewModel viewModel, ImageLoader imageLoader) {
        mViewModel = viewModel;
        this.imageLoader = imageLoader;
        mItems = new ArrayList<>();
    }

    public void setSubjects(Subject[] subjects) {
        mViewModel.setSubjects(subjects);
    }

    public void loadAllItemsForSubject() {
        mViewModel.loadAllItems();
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
        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnClickListener != null) mOnClickListener.onClick(v, item);
            }
        });
    }

    public void removeItem(UiItem item) {

        for(int i=mItems.size() - 1; i >= 0; i--) {
            if(mItems.get(i).getId() != item.getId()) continue;
            mItems.remove(i);
            notifyItemRemoved(i);
        }
    }

    public void addItem(UiItem item) {

        for(int i=0; i < mItems.size(); i++) {
            if (mItems.get(i).getId() == item.getId()) return;
        }
        mItems.add(0, item);
        notifyItemInserted(0);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.title) public TextView title;
        @InjectView(R.id.card_image) public ImageView image;
        @InjectView(R.id.container) public CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.inject(this, itemView);
        }
    }

    public interface OnClickListener {
        void onClick(View view, UiItem item);
    }
}