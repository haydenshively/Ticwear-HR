package info.haydenshively.sleepwear;

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

public final class Filer {
    private final File file;
    public Filer(final Context context) {
        file = new File(context.getFilesDir(), "data");
    }

    public void write(final int[] data) {write(Arrays.toString(data));}

    public int[] readData() {
        String data_string = read();
        if (data_string.length() >= 2) {
            data_string = data_string.substring(1, data_string.length() - 1);
            final String[] values = data_string.split(", ");
            int[] data = new int[values.length];
            for (int i = 0; i < values.length; i++) {
                if (values[i].length() > 0) data[i] = Integer.parseInt(values[i]);
            }
            return data;
        }else return new int[] {};

    }

    private void write(final String string) {
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
    private String read() {
        final int length = (int)file.length();
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


    public static int[] combine(final int[] a, final int[] b) {
        final int length = a.length + b.length;
        final int[] result = new int[length];
        System.arraycopy(a, 0, result, 0, a.length);
        System.arraycopy(b, 0, result, a.length, b.length);
        return result;
    }
}
