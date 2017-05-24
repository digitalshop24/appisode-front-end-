package com.example.romanchuk.appisode.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.example.romanchuk.appisode.R;

/**
 * Created by romanchuk on 22.02.2017.
 */

public class AccentArcView extends View {

    private Paint paint1, paint2;
    float center_x;
    float center_y;
    float radius;
    float angle;

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public void setCenter_x(float center_x) {
        this.center_x = center_x;
        invalidate();
    }

    public void setCenter_y(float center_y) {
        this.center_y = center_y;
        invalidate();
    }

    public void setRadius(float radius) {
        this.radius = radius;
        invalidate();
    }

    public AccentArcView(Context context) {
        super(context);
        paint1 = new Paint();
        paint1.setColor(Color.GRAY);

        paint2 = new Paint();
        paint2.setColor(Color.WHITE);
    }

    /**
     * Class constructor taking a context and an attribute set. This constructor
     * is used by the layout engine to construct a {@link AccentArcView} from a set of
     * XML attributes.
     *
     * @param context
     * @param attrs   An attribute set which can contain attributes from
     *                {@link com.example.romanchuk.appisode.R.styleable} as well as attributes inherited
     *                from {@link View}.
     */
    public AccentArcView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.AccentArcView,
                0, 0
        );

        try {
            center_x = a.getDimension(R.styleable.AccentArcView_center_x, 2.0f);
            center_y = a.getDimension(R.styleable.AccentArcView_center_y, 2.0f);
            radius = a.getDimension(R.styleable.AccentArcView_radius, 2.0f);
        } finally {
            // release the TypedArray so that it can be reused.
            a.recycle();
        }
        paint1 = new Paint();
        paint1.setColor(getResources().getColor(R.color.color_accent));
        paint1.setStyle(Paint.Style.STROKE);
        paint1.setStrokeWidth(6);
        paint1.setAntiAlias(true);

        paint2 = new Paint();
        paint2.setColor(getResources().getColor(R.color.color_white));
        paint2.setStyle(Paint.Style.STROKE);
        paint2.setStrokeWidth(2);
        paint2.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        final RectF oval = new RectF();
        oval.set(center_x - radius,
                center_y - radius,
                center_x + radius,
                center_y + radius);
        //canvas.drawCircle(center_x, center_y, radius, paint2);
        canvas.drawArc(oval, -90, angle * 360, false, paint1);
    }

}
