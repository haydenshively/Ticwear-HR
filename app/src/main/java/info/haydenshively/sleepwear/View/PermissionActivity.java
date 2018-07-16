package info.haydenshively.sleepwear.View;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.HashMap;
import java.util.Map;

import info.haydenshively.sleepwear.Model.F_Permissions;
import info.haydenshively.sleepwear.R;

public class PermissionActivity extends Activity {
    public static final String[] requiredPermissions = new String[] {Manifest.permission.BODY_SENSORS};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);

//        requestAll();
    }

//    @Override
//    public void onRequestPermissionsResult(final int requestCode, final String[] permissions, final int[] grantResults) {
//        (new F_Permissions(this)).write(permissions);
//        if (permissions == requiredPermissions) startActivity(new Intent(this, MainActivity.class));
//    }

    private static boolean granted(final Context context, final String permission) {return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;}

    static boolean grantedAll(final Context context) {
        for (String permission : requiredPermissions) {
            final boolean granted = granted(context, permission);
            if (!granted) return false;
        }
        (new F_Permissions(context)).write(requiredPermissions);
        return true;
    }

//    private void request(final String permission) {ActivityCompat.requestPermissions(this, new String[] {permission}, 0);}

//    void requestAll() {ActivityCompat.requestPermissions(this, requiredPermissions, 0);}

}
