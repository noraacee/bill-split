package pekkles.billsplit.utility;

import android.widget.TextView;

public class SystemMessageManager {
    private TextView systemMessage;

    public SystemMessageManager(TextView systemMessage) {
        this.systemMessage = systemMessage;
    }

    public void displayError(String error) {
        systemMessage.setText(error);
    }

    public void displayWarning(String warning) {
        systemMessage.setText(warning);
    }
}
