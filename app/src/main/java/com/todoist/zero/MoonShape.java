package com.todoist.zero;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Region;
import android.graphics.drawable.shapes.OvalShape;
import android.support.annotation.FloatRange;

public class MoonShape extends OvalShape {
    private float eclipseLevel = 1f;

    @Override
    public void draw(Canvas canvas, Paint paint) {
        float radius = getHeight() * eclipseLevel * 0.57f;
        float x = getWidth() * eclipseLevel * 0.15f;
        float y = getHeight() * eclipseLevel * 0.30f;

        Path clipPath = new Path();
        clipPath.addCircle(x, y, radius, Path.Direction.CW);
        canvas.clipPath(clipPath, Region.Op.DIFFERENCE);

        super.draw(canvas, paint);
    }

    public void setEclipseLevel(@FloatRange(from = 0.0, to = 1.0) float eclipseLevel) {
        this.eclipseLevel = eclipseLevel;
    }
}
