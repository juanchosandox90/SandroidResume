package jcsandoval.sandroidresume.com.sandroidresume.Activities;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.doctoror.particlesdrawable.ParticlesDrawable;

import jcsandoval.sandroidresume.com.sandroidresume.R;

public class MainActivity extends AppCompatActivity {


    private ParticlesDrawable mDrawable = new ParticlesDrawable();
    private Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mDrawable = (ParticlesDrawable) ContextCompat
                .getDrawable(this, R.drawable.particles_dots);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.view).setBackground(mDrawable);
        register = (Button) findViewById(R.id.register_button);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });
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
