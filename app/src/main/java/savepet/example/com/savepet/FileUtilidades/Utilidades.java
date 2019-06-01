package savepet.example.com.savepet.FileUtilidades;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Utilidades {
    public static File BitmapDrawableAFile(BitmapDrawable drawableBitmap, Context context, String nombre)
    {
        File f = new File(context.getCacheDir(), nombre + ".png");

        try {
            f.createNewFile();
            Bitmap bitmap = ((BitmapDrawable)drawableBitmap).getBitmap();
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, bos);
            byte[] bitmapdata = bos.toByteArray();

            FileOutputStream fos = new FileOutputStream(f);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return f;
    }
}
