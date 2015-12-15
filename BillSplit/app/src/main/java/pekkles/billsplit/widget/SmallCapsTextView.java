package pekkles.billsplit.widget;

import android.content.Context;
import android.text.Html;
import android.util.AttributeSet;
import android.widget.TextView;

public class SmallCapsTextView extends TextView {

    public SmallCapsTextView(Context context) {
        super(context);
    }

    public SmallCapsTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SmallCapsTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setTextSmallCaps(String text, boolean capWords) {
        String result = "";
        if (capWords) {
            String[] texts = text.split(" ");
            for (int i = 0; i < texts.length; i++) {
                result += Character.toUpperCase(texts[i].charAt(0));
                result += "<small>";
                result += texts[i].substring(1).toUpperCase();
                result += "</small>";

                if (i != texts.length - 1)
                    result += " ";
            }
        } else {
            result += Character.toUpperCase(text.charAt(0));
            result += "<small>";
            result += text.substring(1).toUpperCase();
            result += "</small>";
        }

        setText(Html.fromHtml(result));
    }
}
