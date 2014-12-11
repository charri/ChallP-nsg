package ch.hsr.nsg.themenrundgang.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import ch.hsr.nsg.themenrundgang.vm.ItemViewModel;

public class ItemAdapter extends RecyclerView.Adapter<SubjectAdapter.ViewHolder> {

    private final ArrayList<ItemViewModel.UiItem> mItems;

    public ItemAdapter(ItemViewModel viewModel) {
        mItems = viewModel.getItems();
    }

    @Override
    public SubjectAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(SubjectAdapter.ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}