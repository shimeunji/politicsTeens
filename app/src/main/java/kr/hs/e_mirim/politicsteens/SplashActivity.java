package kr.hs.e_mirim.politicsteens;

import android.app.Activity;
import android.content.Intent;
import android.hardware.camera2.params.Face;
import android.os.Bundle;

/**
 * Created by User on 2017-06-20.
 */

public class SplashActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        try {
            Thread.sleep(3000);
        }catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        startActivity(new Intent(this,FacebookLoginActivity.class));
        finish();
    }
}
