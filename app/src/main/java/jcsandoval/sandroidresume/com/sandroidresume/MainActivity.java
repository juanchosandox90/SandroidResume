package jcsandoval.sandroidresume.com.sandroidresume;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.doctoror.particlesdrawable.ParticlesDrawable;

public class MainActivity extends AppCompatActivity {


    private final ParticlesDrawable mDrawable = new ParticlesDrawable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
