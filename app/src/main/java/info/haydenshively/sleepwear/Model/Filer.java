package info.haydenshively.sleepwear.Model;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

/**
 * Created by h_shively on 7/3/2018.
 */

abstract class Filer {
    private final File file;
    protected Filer(final Context context, final String filename) {file = new File(context.getApplicationContext().getFilesDir(), filename);}

    protected void write(final String string) {
        try {
            final FileOutputStream stream = new FileOutputStream(file);
            try {
                stream.write(string.getBytes());
                stream.close();
            }catch (IOException e) {
                Log.e("Filer", "Write error: " + e.toString());
            }
        }catch (FileNotFoundException e) {
            Log.e("Filer", "File not found: " + e.toString());
        }
    }

    @NonNull
    protected String read() {
        final int length = (int) file.length();
        byte[] bytes = new byte[length];

        try {
            final FileInputStream stream = new FileInputStream(file);
            try {
                stream.read(bytes);
                stream.close();
            }catch (IOException e) {
                Log.e("Filer", "Read error: " + e.toString());
            }
        }catch (FileNotFoundException e) {
            Log.e("Filer", "File not found: " + e.toString());
        }

        return new String(bytes);
    }
}
