package jcsandoval.sandroidresume.com.sandroidresume.Activities;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.doctoror.particlesdrawable.ParticlesDrawable;

import java.util.List;

import butterknife.BindViews;
import butterknife.ButterKnife;
import jcsandoval.sandroidresume.com.sandroidresume.Adapter.AnimatedViewPager;
import jcsandoval.sandroidresume.com.sandroidresume.Adapter.AuthAdapter;
import jcsandoval.sandroidresume.com.sandroidresume.R;

public class MainActivity extends AppCompatActivity {


    private ParticlesDrawable mDrawable = new ParticlesDrawable();
    @BindViews(value = {R.id.logo, R.id.first, R.id.second, R.id.last})
    protected List<ImageView> sharedElements;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mDrawable = (ParticlesDrawable) ContextCompat
                .getDrawable(this, R.drawable.particles_dots);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        findViewById(R.id.root).setBackground(mDrawable);
        final AnimatedViewPager pager = ButterKnife.findById(this, R.id.pager);
        final ImageView background = ButterKnife.findById(this, R.id.scrolling_background);
        int[] screenSize = screenSize();

        for (ImageView element : sharedElements) {
            @ColorRes int color = element.getId() != R.id.logo ? R.color.white_transparent : R.color.white_transparent;
            DrawableCompat.setTint(element.getDrawable(), ContextCompat.getColor(this, color));
        }
        Glide.with(this)
                .load(R.drawable.high_res_bg_sandroid_alpha)
                .asBitmap()
                .override(screenSize[0] * 2, screenSize[1])
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(new ImageViewTarget<Bitmap>(background) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        background.setImageBitmap(resource);
                        background.post(() -> {
                            //we need to scroll to the very left edge of the image
                            //fire the scale animation
                            background.scrollTo(-background.getWidth() / 2, 0);
                            ObjectAnimator xAnimator = ObjectAnimator.ofFloat(background, View.SCALE_X, 4f, background.getScaleX());
                            ObjectAnimator yAnimator = ObjectAnimator.ofFloat(background, View.SCALE_Y, 4f, background.getScaleY());
                            AnimatorSet set = new AnimatorSet();
                            set.playTogether(xAnimator, yAnimator);
                            set.setDuration(getResources().getInteger(R.integer.duration));
                            set.start();
                        });
                        pager.post(() -> {
                            AuthAdapter adapter = new AuthAdapter(getSupportFragmentManager(), pager, background, sharedElements);
                            pager.setAdapter(adapter);
                        });
                    }
                });
    }

    private int[] screenSize() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return new int[]{size.x, size.y};

    }
}
