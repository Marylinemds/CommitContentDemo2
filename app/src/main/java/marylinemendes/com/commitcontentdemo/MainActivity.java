package marylinemendes.com.commitcontentdemo;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((CommitEditText) findViewById(R.id.edit)).setCommitListener(new CommitEditText.CommitListener() {
            @Override
            public void onCommitContent(final Uri uri) {

                final ImageView imageView = findViewById(R.id.image);
                loadBitmapWithGlide(MainActivity.this, imageView, uri);

                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        final ImageView iv = (ImageView) v;
                        Drawable drawable = iv.getDrawable();

                        if ( drawable == null || drawable.getClass().equals(GifDrawable.class)){
                           loadBitmapWithGlide(MainActivity.this, iv, uri);
                        }else {
                            loadGifWithGlide(MainActivity.this, iv, uri);
                        }
                    }
                });
            }
        });
    }

    public void loadBitmapWithGlide(Activity activity, final ImageView imageView, Uri uri){
        GlideApp.with(activity)
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
    }

    public void loadGifWithGlide(Activity activity, final  ImageView imageView, Uri uri){
        GlideApp.with(MainActivity.this)
                .asGif()
                .load(uri)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);

    }


}
