package com.app.test.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.app.test.R;
import com.app.test.databinding.ListitempokemonBinding;
import com.app.test.model.Results;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Locale;

public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.ViewHolder> {

    private Fragment fragment;
    private List<Results> arrayList;
    private int lastPosition = -1;
    private ListitempokemonBinding viewBinding;
    private OnKlickListener onKlickListener;
    public String fileType;

    private final static int FADE_DURATION = 1000;

    public PokemonAdapter(Fragment fragment, List<Results> transaksiArrayList, OnKlickListener onKlickListener) {
        this.fragment = fragment;
        this.arrayList = transaksiArrayList;
        this.onKlickListener = onKlickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        viewBinding = ListitempokemonBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(viewBinding,onKlickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Results results = arrayList.get(position);
        try {
            String fileTypeArray[] = results.getUrl().split("/");
            fileType = "";
            if(fileTypeArray != null && fileTypeArray.length > 0) {
                fileType = fileTypeArray[fileTypeArray.length - 1];
            }
            Glide.with(fragment)
                    .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/home/"+fileType+".png")
                    .fitCenter()
                    .placeholder(R.drawable.ic_international_pok_mon_logo)
                    .error(R.drawable.ic_international_pok_mon_logo)
                    .listener(new RequestListener() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model,
                                                    Target target, boolean isFirstResource) {
                            // Log the GlideException here (locally or with a remote logging framework):
                            viewBinding.tvGambarPokemon.setImageDrawable(fragment.getResources().getDrawable(R.drawable.ic_international_pok_mon_logo));
                            return false; // Allow calling onLoadFailed on the Target.
                        }

                        @Override
                        public boolean onResourceReady(Object resource, Object model, Target target, DataSource dataSource, boolean isFirstResource) {
                            return false;
                        }
                    })
                    .into(viewBinding.tvGambarPokemon);
        } catch (Exception e) {
            e.printStackTrace();
        }

        holder.viewBinding.tvNamaPokemon.setText(results.getName());

        setAnimation(holder.itemView, position);
    }

    private void setAnimation(View itemView, int position) {
        if (position > lastPosition) {
            AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
            anim.setDuration(FADE_DURATION);
            itemView.startAnimation(anim);
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ListitempokemonBinding viewBinding;
        private OnKlickListener onKlickListener;

        public ViewHolder(@NonNull ListitempokemonBinding binding, OnKlickListener onKlickListener) {
            super(binding.getRoot());
            viewBinding = binding;

            this.onKlickListener = onKlickListener;
            binding.clView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onKlickListener.onKlickClick(getPosition());
        }
    }

    public interface OnKlickListener {
        void onKlickClick(int position);
    }

    public void setDataList(List<Results> data) {
        this.arrayList = data;
        notifyDataSetChanged();
    }
}
