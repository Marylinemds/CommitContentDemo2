package marylinemendes.com.commitcontentdemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.view.Gravity;


public class BitmapUtils {

    public static Bitmap applyOverlay(Context context, Bitmap sourceImage, int overlayDrawableResourceId){
        Bitmap bitmap = null;
        try{

            int height = sourceImage.getHeight();

            Drawable imageAsDrawable =  new BitmapDrawable(context.getResources(), sourceImage);
            Drawable[] layers = new Drawable[2];

            layers[0] = imageAsDrawable;
            layers[1] = context.getDrawable(R.drawable.play_image);
            LayerDrawable layerDrawable = new LayerDrawable(layers);
            layerDrawable.setLayerHeight(1, height/2);
            layerDrawable.setLayerWidth(1, height/2);
            layerDrawable.setLayerGravity(1, Gravity.CENTER);
            bitmap = BitmapUtils.drawableToBitmap(layerDrawable);
        }catch (Exception ex){}
        return bitmap;
    }


    public static Bitmap drawableToBitmap (Drawable drawable) {
        Bitmap bitmap = null;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if(bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if(drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }
}
