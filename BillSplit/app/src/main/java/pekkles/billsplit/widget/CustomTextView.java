package pekkles.billsplit.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
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
    private static final boolean BORDER_ALL = false;
    private static final boolean BORDER_BOTTOM = false;
    private static final boolean BORDER_LEFT = false;
    private static final boolean BORDER_RIGHT = false;
    private static final boolean BORDER_SINGLE_COLOR = false;
    private static final boolean BORDER_TOP = false;
    private static final boolean SQUARE = false;

    private static final int COUNT_BORDER = 4;
    private static final int INDEX_BOTTOM = 3;
    private static final int INDEX_LEFT = 0;
    private static final int INDEX_RIGHT = 2;
    private static final int INDEX_TOP = 1;

    private static final int MODE_BUTTON = 1;
    private static final int MODE_SELECT = 2;
    private static final int MODE_TEXT = 0;

    private static final int COLOR_BACKGROUND = R.color.background;
    private static final int COLOR_PRIMARY = R.color.primary;
    private static final int COLOR_SECONDARY = R.color.secondary;
    private static final int PADDING = R.dimen.padding_primary;
    private static final int STROKE = R.dimen.stroke;
    private static final int TEXT_SIZE = R.dimen.text_size_primary;

    private static final String TYPEFACE = "fonts/eurof35.ttf";

    private boolean selected;
    private boolean singleBorderColor;
    private boolean square;
    private boolean[] border;

    private float downX;
    private float downY;
    private float stroke;
    private float touchSlop;

    private int backgroundColor;
    private int mode;
    private int pointerId;
    private int primaryColor;
    private int secondaryColor;

    private Paint borderPaint;

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
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (border[INDEX_BOTTOM])
            canvas.drawLine(0, getHeight() - stroke / 2, getWidth(), getHeight() - stroke / 2, borderPaint);
        if (border[INDEX_LEFT])
            canvas.drawLine(stroke / 2, 0, stroke / 2, getHeight(), borderPaint);
        if (border[INDEX_RIGHT])
            canvas.drawLine(getWidth() - stroke / 2, 0, getWidth() - stroke / 2, getHeight(), borderPaint);
        if (border[INDEX_TOP])
            canvas.drawLine(0, stroke / 2, getWidth(), stroke / 2, borderPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (square) {
            int size = getMeasuredWidth();
            int height = getHeight();

            if (size < height)
                size = height;

            setMeasuredDimension(size, size);
        }
    }

    @Override
    public boolean onTouchEvent(@NonNull MotionEvent ev) {
        if (mode != MODE_TEXT) {
            switch (ev.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    downX = ev.getX();
                    downY = ev.getY();
                    pointerId = ev.getPointerId(0);

                    if (mode == MODE_BUTTON)
                        invertColors(true);

                    invalidate();
                    break;
                case MotionEvent.ACTION_POINTER_UP:
                    int pointerIndex = (ev.getAction() & MotionEvent.ACTION_POINTER_INDEX_MASK) >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
                    if (pointerId != ev.getPointerId(pointerIndex))
                        break;
                case MotionEvent.ACTION_UP:
                    if (Math.abs(ev.getX() - downX) < touchSlop && Math.abs(ev.getY() - downY) < touchSlop) {
                        if (mode == MODE_SELECT) {
                            selected = !selected;
                            invertColors(selected);
                        }

                        performClick();
                    }
                case MotionEvent.ACTION_CANCEL:
                    downX = -1;
                    downY = -1;
                    pointerId = -1;

                    if (mode == MODE_BUTTON)
                        invertColors(false);

                    invalidate();
                    break;
            }

            return true;
        }

        return false;
    }

    @Override
    public void setTextSize(float px) {
        setTextSize(TypedValue.COMPLEX_UNIT_PX, px);
    }

    @Override
    public boolean isSelected() {
        return selected;
    }

    private void init(AttributeSet attrs) {
        Context context = getContext();
        Resources resources = getResources();

        border = new boolean[COUNT_BORDER];

        boolean borderAll;
        int borderColor;
        int paddingBottom;
        int paddingLeft;
        int paddingRight;
        int paddingTop;
        float textSize;
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.Pekkles);

            borderAll = a.getBoolean(R.styleable.Pekkles_border, BORDER_ALL);
            border[INDEX_BOTTOM] = a.getBoolean(R.styleable.Pekkles_border_bottom, BORDER_BOTTOM) || borderAll;
            border[INDEX_LEFT] = a.getBoolean(R.styleable.Pekkles_border_left, BORDER_LEFT) || borderAll;
            border[INDEX_RIGHT] = a.getBoolean(R.styleable.Pekkles_border_right, BORDER_RIGHT) || borderAll;
            border[INDEX_TOP] = a.getBoolean(R.styleable.Pekkles_border_top, BORDER_TOP) || borderAll;
            singleBorderColor = a.getBoolean(R.styleable.Pekkles_border_single_color, BORDER_SINGLE_COLOR);
            square = a.getBoolean(R.styleable.Pekkles_square, SQUARE);

            mode = a.getInt(R.styleable.Pekkles_mode, MODE_TEXT);

            backgroundColor = a.getColor(R.styleable.Pekkles_color_background, ContextCompat.getColor(context, COLOR_BACKGROUND));
            primaryColor = a.getColor(R.styleable.Pekkles_color_primary, ContextCompat.getColor(context, COLOR_PRIMARY));
            secondaryColor = a.getColor(R.styleable.Pekkles_color_secondary, ContextCompat.getColor(context, COLOR_SECONDARY));
            borderColor = a.getColor(R.styleable.Pekkles_color_border, secondaryColor);

            stroke = a.getDimensionPixelSize(R.styleable.Pekkles_stroke, resources.getDimensionPixelSize(STROKE));
            paddingBottom = a.getDimensionPixelSize(R.styleable.Pekkles_padding_bottom, resources.getDimensionPixelSize(PADDING));
            paddingLeft = a.getDimensionPixelSize(R.styleable.Pekkles_padding_left, resources.getDimensionPixelSize(PADDING));
            paddingRight = a.getDimensionPixelSize(R.styleable.Pekkles_padding_right, resources.getDimensionPixelSize(PADDING));
            paddingTop = a.getDimensionPixelSize(R.styleable.Pekkles_padding_top, resources.getDimensionPixelSize(PADDING));
            textSize = a.getDimensionPixelSize(R.styleable.Pekkles_text_size_primary, resources.getDimensionPixelSize(TEXT_SIZE));

            a.recycle();
        } else {
            border[INDEX_BOTTOM] = BORDER_BOTTOM;
            border[INDEX_LEFT] = BORDER_LEFT;
            border[INDEX_RIGHT] = BORDER_RIGHT;
            border[INDEX_TOP] = BORDER_TOP;
            singleBorderColor = BORDER_SINGLE_COLOR;
            square = SQUARE;

            mode = MODE_TEXT;

            backgroundColor = ContextCompat.getColor(context, COLOR_BACKGROUND);
            primaryColor = ContextCompat.getColor(context, COLOR_PRIMARY);
            secondaryColor = ContextCompat.getColor(context, COLOR_SECONDARY);
            borderColor = secondaryColor;

            stroke = resources.getDimensionPixelSize(STROKE);
            paddingBottom = resources.getDimensionPixelSize(PADDING);
            paddingLeft = resources.getDimensionPixelSize(PADDING);
            paddingRight = resources.getDimensionPixelSize(PADDING);
            paddingTop = resources.getDimensionPixelSize(PADDING);
            textSize = resources.getDimensionPixelSize(TEXT_SIZE);
        }

        borderPaint = new Paint();
        borderPaint.setColor(borderColor);
        borderPaint.setStrokeWidth(stroke);

        selected = false;
        touchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), TYPEFACE);

        setBackgroundColor(backgroundColor);
        setGravity(Gravity.CENTER);
        setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
        setTextColor(primaryColor);
        setTextSize(textSize);
        setTypeface(tf);
    }

    private void invertColors(boolean invert) {
        if (invert) {
            if (!singleBorderColor)
                borderPaint.setColor(backgroundColor);

            setBackgroundColor(secondaryColor);
            setTextColor(backgroundColor);
        } else {
            if (!singleBorderColor)
                borderPaint.setColor(secondaryColor);
            setBackgroundColor(backgroundColor);
            setTextColor(primaryColor);
        }
    }
}