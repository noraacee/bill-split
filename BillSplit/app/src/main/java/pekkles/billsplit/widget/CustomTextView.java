package pekkles.billsplit.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.TextView;

import pekkles.billsplit.R;


public class CustomTextView extends TextView {
    private static final boolean CLICKABLE = false;

    private static final int COLOR_BACKGROUND = R.color.background;
    private static final int COLOR_PRIMARY = R.color.primary_white;
    private static final int PADDING = R.dimen.padding_primary;
    private static final int TEXT_SIZE = R.dimen.text_size_primary;

    private static final String TYPEFACE = "fonts/eurof35.ttf";

    private boolean clickable;

    private float downX;
    private float downY;
    private float touchSlop;

    private int backgroundColor;
    private int textColor;

    private int pointerId;

    public CustomTextView(Context context) {
        super(context);
        init(null);
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @Override
    public boolean isClickable() {
        return clickable;
    }

    @Override
    public boolean onTouchEvent(@NonNull MotionEvent ev) {
        if (clickable) {
            switch (ev.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    downX = ev.getX();
                    downY = ev.getY();
                    pointerId = ev.getPointerId(0);

                    click(true);

                    invalidate();
                    break;
                case MotionEvent.ACTION_POINTER_UP:
                    int pointerIndex = (ev.getAction() & MotionEvent.ACTION_POINTER_INDEX_MASK) >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
                    if (pointerId != ev.getPointerId(pointerIndex))
                        break;
                case MotionEvent.ACTION_UP:
                    if (Math.abs(ev.getX() - downX) < touchSlop && Math.abs(ev.getY() - downY) < touchSlop)
                        performClick();
                case MotionEvent.ACTION_CANCEL:
                    downX = -1;
                    downY = -1;
                    pointerId = -1;

                    click(false);

                    invalidate();
                    break;
            }
        }

        return clickable;
    }

    @Override
    public void setClickable(boolean clickable) {
        this.clickable = clickable;
    }

    @Override
    public void setTextSize(float px) {
        setTextSize(TypedValue.COMPLEX_UNIT_PX, px);
    }

    public void click(boolean click) {
        if (click) {
            setBackgroundColor(textColor);
            setTextColor(backgroundColor);
        } else {
            setBackgroundColor(backgroundColor);
            setTextColor(textColor);
        }
    }

    private void init(AttributeSet attrs) {
        Context context = getContext();
        Resources resources = getResources();

        int paddingBottom;
        int paddingLeft;
        int paddingRight;
        int paddingTop;
        float textSize;
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.Pekkles);

            clickable = a.getBoolean(R.styleable.Pekkles_clickable, CLICKABLE);
            if (clickable) {
                backgroundColor = a.getColor(R.styleable.Pekkles_color_background, ContextCompat.getColor(context, COLOR_BACKGROUND));
                textColor = a.getColor(R.styleable.Pekkles_color_primary, ContextCompat.getColor(context, COLOR_PRIMARY));
            } else {
                backgroundColor = a.getColor(R.styleable.Pekkles_color_background, ContextCompat.getColor(context, COLOR_PRIMARY));
                textColor = a.getColor(R.styleable.Pekkles_color_primary, ContextCompat.getColor(context, COLOR_BACKGROUND));
            }

            paddingBottom = a.getDimensionPixelSize(R.styleable.Pekkles_padding_bottom, resources.getDimensionPixelSize(PADDING));
            paddingLeft = a.getDimensionPixelSize(R.styleable.Pekkles_padding_left, resources.getDimensionPixelSize(PADDING));
            paddingRight = a.getDimensionPixelSize(R.styleable.Pekkles_padding_right, resources.getDimensionPixelSize(PADDING));
            paddingTop = a.getDimensionPixelSize(R.styleable.Pekkles_padding_top, resources.getDimensionPixelSize(PADDING));
            textSize = a.getDimensionPixelSize(R.styleable.Pekkles_text_size_primary, resources.getDimensionPixelSize(TEXT_SIZE));

            a.recycle();
        } else {
            clickable = CLICKABLE;
            backgroundColor = ContextCompat.getColor(context, COLOR_PRIMARY);
            textColor = ContextCompat.getColor(context, COLOR_BACKGROUND);
            paddingBottom = resources.getDimensionPixelSize(PADDING);
            paddingLeft = resources.getDimensionPixelSize(PADDING);
            paddingRight = resources.getDimensionPixelSize(PADDING);
            paddingTop = resources.getDimensionPixelSize(PADDING);
            textSize = resources.getDimensionPixelSize(TEXT_SIZE);
        }

        touchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();

        setGravity(Gravity.CENTER);
        setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);

        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), TYPEFACE);
        setTypeface(tf);

        setBackgroundColor(backgroundColor);
        setTextColor(textColor);
        setTextSize(textSize);
    }
}