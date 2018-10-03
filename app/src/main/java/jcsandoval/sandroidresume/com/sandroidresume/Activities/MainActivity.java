package jcsandoval.sandroidresume.com.sandroidresume.Activities;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.doctoror.particlesdrawable.ParticlesDrawable;

import jcsandoval.sandroidresume.com.sandroidresume.R;

public class MainActivity extends AppCompatActivity {


    private ParticlesDrawable mDrawable = new ParticlesDrawable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mDrawable = (ParticlesDrawable) ContextCompat
                .getDrawable(this, R.drawable.particles_dots);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.view).setBackground(mDrawable);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mDrawable.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mDrawable.stop();
    }
}
