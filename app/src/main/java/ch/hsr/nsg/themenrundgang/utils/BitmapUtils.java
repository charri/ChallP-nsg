package ch.hsr.nsg.themenrundgang.utils;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;

/**
 * Created by dominik on 15.12.14.
 */
public class BitmapUtils {
    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 50, stream);
        return stream.toByteArray();
    }
}
