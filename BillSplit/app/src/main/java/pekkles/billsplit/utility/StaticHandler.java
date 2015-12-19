package pekkles.billsplit.utility;

import android.os.Handler;

import java.lang.ref.WeakReference;

public class StaticHandler<T> extends Handler {
    private final WeakReference<T> reference;

    public StaticHandler(T instance) {
        reference = new WeakReference<>(instance);
    }

    protected T getInstance() {
        return reference.get();
    }
}
