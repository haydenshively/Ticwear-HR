package info.haydenshively.sleepwear.Model;

import android.content.Context;

import java.util.Arrays;

/**
 * Created by h_shively on 7/16/2018.
 */

public final class F_Permissions extends Filer {
    public F_Permissions(final Context context) {super(context, "permissions");}

    public String[] mread() {
        String str = super.read();
        if (str.length() >= 2) {
            str = str.substring(1, str.length() - 1);
            return str.split(", ");
        }else return new String[] {};
    }

    public void write(final String[] permissions) {super.write(Arrays.toString(permissions));}

    public boolean contains(final String permission) {return Arrays.asList(mread()).contains(permission);}
}
