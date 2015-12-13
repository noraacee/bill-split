package pekkles.billsplit.util;

import android.widget.TextView;

/**
 * Created by Aaron Chan on 2015-12-13.
 */
public class SystemMessageManager {
    private TextView systemMessage;

    public SystemMessageManager(TextView systemMessage) {
        this.systemMessage = systemMessage;
    }

    public void displayError(String error) {
        systemMessage.setText(error);
    }
}
