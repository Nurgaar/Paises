package com.ngg.android.paises;

import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.navigation.Navigation;

import com.ngg.android.paises.placeholder.PlaceholderContent.Pais;
import com.ngg.paises.R;
import com.ngg.paises.databinding.FragmentPaisCardBinding;

import java.io.InputStream;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Pais}.
 * TODO: Replace the implementation with code for your data type.
 */
public class PaisRecyclerViewAdapter extends RecyclerView.Adapter<PaisRecyclerViewAdapter.ViewHolder> {

    private final List<Pais> mValues;

    public PaisRecyclerViewAdapter(List<Pais> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentPaisCardBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        InputStream is = getClass().getResourceAsStream("/" + mValues.get(position).bandera);
        holder.mImageView.setImageDrawable(Drawable.createFromStream(is,""));
        holder.mContentView.setText(mValues.get(position).nombre);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View

            .OnClickListener {
        public final ImageView mImageView;
        public final TextView mContentView;
        public Pais mItem;

        public ViewHolder(@NonNull FragmentPaisCardBinding binding) {
            super(binding.getRoot());
            mImageView =binding.imageView;
            mContentView = binding.content;
            binding.getRoot().setOnClickListener(this);




        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }

        @Override
        public void onClick(View view) {
            Bundle args = new Bundle();
            args.putParcelable("pais", mItem);
            args.putString("title", mItem.nombre);

            Navigation.findNavController(view).navigate(R.id.action_paisFragment_to_detallePaisesFragment, args);
        }

    }
}