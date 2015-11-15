package com.catchfirst.catchfirst;

import android.content.Context;

/**
 * Created by aselims on 15/11/15.
 */
public interface Contract {

    interface view {
        void showDistance(double distance);

        void showSafe();
        void showDetectionMode(boolean buttonPressed);

        void showBoom();

        void showDeactivated();

        boolean isButtonPressed();
    }

    interface presenter {
        void start(view view);

        void restart();

    }

    interface scanner {
        void startScan(Context context, final Scanner.DistanceCallback distanceCallback);
    }
}
