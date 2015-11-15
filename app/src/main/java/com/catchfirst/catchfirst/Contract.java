package com.catchfirst.catchfirst;

/**
 * Created by aselims on 15/11/15.
 */
public interface Contract {

    interface view {
        void showDistance(double distance);

        void showSafe();

        void showBoom();

        void showDeactivated();

        boolean isButtonPressed();
    }

    interface presenter {
        void start(view view);

    }

    interface scanner {
        double getDistance();
    }
}
