package com.catchfirst.catchfirst;

import android.content.Context;

/**
 * Created by aselims on 15/11/15.
 */
public class ScanPresenter implements Contract.presenter, Scanner.DistanceCallback {

    double detectionDistance = 5;
    double boomDistance = 2;
    Contract.scanner scanner;
    private boolean activated = true;
    private Contract.view view;


    @Override
    public void start(Contract.view view) {
        this.view = view;
        scanner = new Scanner();
        scanner.startScan((Context) view, this);


    }

    private void deactivate() {
//        activated = false;

    }

    @Override
    public void onDistanceChanged(double distance) {
        if(activated) {

            if (distance > detectionDistance) {
                view.showSafe();
            } else if (distance <= detectionDistance && distance > boomDistance) {
                view.showDistance(distance);
                view.showDetectionMode();
            } else if (distance <= boomDistance) {
                if (view.isButtonPressed()) {
                    deactivate();
                    view.showDeactivated();
                } else {
                    view.showBoom();
                }
            }
        }else{
            view.showDistance(distance);
        }
    }
}
