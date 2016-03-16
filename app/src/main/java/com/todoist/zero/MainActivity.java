package com.todoist.zero;

import android.animation.ValueAnimator;
import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity {

    private FrameLayout mZeroLayout;
    private View mZeroOcean;
    private View mZeroMoon;
    private View mZeroPanorama;
    private MoonShape moonShape;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mZeroLayout = (FrameLayout) findViewById(R.id.zero_layout);
        mZeroOcean = findViewById(R.id.zero_ocean);
        mZeroPanorama = findViewById(R.id.zero_panorama);
        mZeroMoon = findViewById(R.id.zero_moon);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop,
                                       int oldRight,
                                       int oldBottom) {
                prepareAnimation(); //This is just to let the animation be invisible when you first start the app.
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                prepareAnimation();
                animateAllTheThings();

            }
        });

        moonShape = new MoonShape();

        ShapeDrawable sd = new ShapeDrawable(moonShape);
        sd.getPaint().setColor(Color.WHITE);
        mZeroMoon.setBackground(sd);

    }

    private void prepareAnimation() {
        setViewScale(0.3f, mZeroMoon);
        setViewAlpha(0f, mZeroLayout, mZeroPanorama);
    }

    private void animateAllTheThings() {
        int startX = mZeroLayout.getWidth() / -4;
        int startY = mZeroLayout.getHeight() / -4;

        TranslateAnimation trans = new TranslateAnimation(startX, 0, startY, 0);
        trans.setDuration(500);

        mZeroMoon.animate().scaleX(1f).scaleY(1f).setDuration(500).start();

        ValueAnimator animation = ValueAnimator.ofFloat(0f, 1f);
        animation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                moonShape.setEclipseLevel((Float) animation.getAnimatedValue());
                mZeroMoon.invalidate();
            }
        });
        animation.setDuration(500);
        animation.start();

        mZeroMoon.startAnimation(trans);

        mZeroLayout.animate().alpha(1f).setDuration(200).start();
        mZeroPanorama.animate().alpha(1f).setDuration(500).start();
    }

    private void setViewAlpha(float alpha, View... views) {
        for (View view : views) {
            view.setAlpha(alpha);
        }
    }

    private void setViewScale(float size, View... views) {
        for (View view : views) {
            view.setScaleX(size);
            view.setScaleY(size);
        }
    }
}
