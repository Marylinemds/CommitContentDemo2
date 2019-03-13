package marylinemendes.com.commitcontentdemo;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory;
import com.bumptech.glide.request.transition.Transition;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((CommitEditText) findViewById(R.id.edit)).setCommitListener(new CommitEditText.CommitListener() {
            @Override
            public void onCommitContent(final Uri uri) {



                final ImageView imageView = findViewById(R.id.image);
                GlideApp.with(MainActivity.this)
                        .asBitmap()
                        .load(uri)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(new SimpleTarget<Bitmap>() {

                            @Override
                            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                resource = BitmapUtils.applyOverlay(getApplicationContext(), resource, R.drawable.play_image);

                                imageView.setImageBitmap(resource);
                            }

                        });

                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        GlideApp.with(MainActivity.this)
                                .asGif()
                                .load(uri)
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .listener(new RequestListener<GifDrawable>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<GifDrawable> target, boolean isFirstResource) {
                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(GifDrawable resource, Object model, Target<GifDrawable> target, DataSource dataSource, boolean isFirstResource) {
                                        resource.setLoopCount(2);

                                        return false;
                                    }
                                })
                                .into(imageView);
                    }
                });
            }
        });
    }


}
