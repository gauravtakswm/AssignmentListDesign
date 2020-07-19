package com.gauravtak.assignment_list_design.utils_classes;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.gauravtak.assignment_list_design.R;

//this class file is used to provide image binding support with layout file using glide library
public class ImageSetter {

    @BindingAdapter("bind:imageUrl")
    public static void loadImage(final ImageView view, String url) {
        Log.i( "loadImage: ",url+"");
        if(url!=null && view!=null && !url.isEmpty()) {
        view.setVisibility(View.VISIBLE);
        Glide.with(view.getContext()).load(url).listener(new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    //  view.setImageResource(R.drawable.place_holder);
                    view.setVisibility(View.GONE);
                    //because as per the need, if the view content is not available, we need to dynamic size the view
                   //so if the image url is not valid, then we will hide the image view
                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    return false;
                }
            }).into(view);
        }
        else {
            view.setVisibility(View.GONE);
            //because as per the need, if the view content is not available, we need to dynamic size the view
            //so if the image url is not valid, then we will hide the image view
        }
    }
}
