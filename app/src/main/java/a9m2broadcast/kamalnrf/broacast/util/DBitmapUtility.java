package a9m2broadcast.kamalnrf.broacast.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

/**
 * Created by kamalnrf on 3/7/16.
 */
public class DBitmapUtility
{
    //Convert from bit map to byte array..
    public static byte[] getBytes (Bitmap bitmap)
    {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);

        return stream.toByteArray();
    }

    //Convert from byte array to bitmap...
    public static Bitmap getImage(byte[] images)
    {
        return BitmapFactory.decodeByteArray(images, 0, images.length);
    }
}
