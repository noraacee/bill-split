package pekkles.billsplit.utility;

import android.content.Context;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import pekkles.billsplit.R;

public class SystemMessageManager {
    private static final int COLOR_ERROR = R.color.red_300;
    private static final int COLOR_WARNING = R.color.amber_300;

    private static final int DURATION_ERROR = 5000;
    private static final int DURATION_WARNING = 5000;

    private int colorError;
    private int colorWarning;

    private MessageHandler handler;
    private TextView systemMessage;

    public SystemMessageManager(Context context, TextView systemMessage) {
        this.systemMessage = systemMessage;
        handler = new MessageHandler(this);

        colorError = ContextCompat.getColor(context, COLOR_ERROR);
        colorWarning = ContextCompat.getColor(context, COLOR_WARNING);
    }

    public void displayError(String error) {
        systemMessage.setBackgroundColor(colorError);
        systemMessage.setText(error);
        systemMessage.setVisibility(View.VISIBLE);

        handler.removeMessages(0);
        handler.sendEmptyMessageDelayed(0, DURATION_ERROR);
    }

    public void displayWarning(String warning) {
        systemMessage.setBackgroundColor(colorWarning);
        systemMessage.setText(warning);
        systemMessage.setVisibility(View.VISIBLE);

        handler.removeMessages(0);
        handler.sendEmptyMessageDelayed(0, DURATION_WARNING);
    }

    public void removeMessage() {
        systemMessage.setVisibility(View.GONE);
    }

    private static class MessageHandler extends StaticHandler<SystemMessageManager> {
        public MessageHandler(SystemMessageManager instance) {
            super(instance);
        }

        @Override
        public void handleMessage(Message msg) {
            getInstance().removeMessage();
        }
    }
}
