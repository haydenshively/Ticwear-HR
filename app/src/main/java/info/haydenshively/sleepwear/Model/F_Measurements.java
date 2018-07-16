package info.haydenshively.sleepwear.Model;

import android.content.Context;

import java.util.Arrays;

/**
 * Created by h_shively on 7/16/2018.
 */

public final class F_Measurements extends Filer {
    public F_Measurements(final Context context) {super(context, "measurements");}

    public int[] mread() {
        String str = super.read();
        if (str.length() >= 2) {
            str = str.substring(1, str.length() - 1);
            final String[] values = str.split(", ");
            int[] data = new int[values.length];
            for (int i = 0; i < values.length; i++) {
                if (values[i].length() > 0) data[i] = Integer.parseInt(values[i]);
            }
            return data;
        }else return new int[] {};
    }

    public void write(final int[] data) {super.write(Arrays.toString(data));}

    public static int[] combine(final int[] a, final int[] b) {
        final int length = a.length + b.length;
        final int[] result = new int[length];
        System.arraycopy(a, 0, result, 0, a.length);
        System.arraycopy(b, 0, result, a.length, b.length);
        return result;
    }
}
