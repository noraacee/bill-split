package pekkles.billsplit.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.EditText;

import pekkles.billsplit.R;

public class CustomEditText extends EditText {
    private static final int COLOR_BACKGROUND = R.color.transparent;
    private static final int COLOR_HINT = R.color.background;
    private static final int COLOR_TEXT = R.color.primary;
    private static final int MIN_EMS = 20;
    private static final int PADDING = R.dimen.padding_primary;
    private static final int TEXT_SIZE = R.dimen.text_size_primary;

    private static final String TYPEFACE = "fonts/eurof35.ttf";

    public CustomEditText(Context context) {
        super(context);
        init(null);
    }

    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public CustomEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        Context context = getContext();
        Resources resources = getResources();

        int backgroundColor;
        int hintColor;
        int minEms;
        int padding;
        int textColor;
        float textSize;
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.Pekkles);

            backgroundColor = a.getColor(R.styleable.Pekkles_color_background, ContextCompat.getColor(context, COLOR_BACKGROUND));
            hintColor = a.getColor(R.styleable.Pekkles_color_info, ContextCompat.getColor(context, COLOR_HINT));
            minEms = a.getInt(R.styleable.Pekkles_min_ems, MIN_EMS);
            padding = a.getDimensionPixelSize(R.styleable.Pekkles_padding, resources.getDimensionPixelSize(PADDING));
            textColor = a.getColor(R.styleable.Pekkles_color_primary, ContextCompat.getColor(context, COLOR_TEXT));
            textSize = a.getDimensionPixelSize(R.styleable.Pekkles_text_size_primary, resources.getDimensionPixelSize(TEXT_SIZE));

            a.recycle();
        } else {
            backgroundColor = ContextCompat.getColor(context, COLOR_BACKGROUND);
            hintColor = ContextCompat.getColor(context, COLOR_HINT);
            minEms = MIN_EMS;
            padding = resources.getDimensionPixelSize(PADDING);
            textColor = ContextCompat.getColor(context, COLOR_TEXT);
            textSize = resources.getDimension(TEXT_SIZE);
        }

        setBackgroundColor(backgroundColor);
        setHintTextColor(hintColor);
        setMinEms(minEms);
        setPadding(padding, padding, padding, padding);
        setTextColor(textColor);
        setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        setTypeface(Typeface.createFromAsset(getContext().getAssets(), TYPEFACE));
    }
}
