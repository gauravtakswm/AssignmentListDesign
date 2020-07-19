package com.gauravtak.assignment_list_design.utils_classes;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.gauravtak.assignment_list_design.R;

public class ImageSetter {

    @BindingAdapter("bind:imageUrl")
    public static void loadImage(final ImageView view, String url) {
        Log.i( "loadImage: ",url+"");
        if(url!=null && view!=null)
            Glide.with(view.getContext()).load(url).listener(new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                  //  view.setImageResource(R.drawable.place_holder);
                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    return false;
                }
            }).placeholder(R.drawable.place_holder).into(view);
    }
}
