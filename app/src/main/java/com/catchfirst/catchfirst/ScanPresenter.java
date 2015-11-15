package com.catchfirst.catchfirst;

import android.content.Context;

/**
 * Created by aselims on 15/11/15.
 */
public class ScanPresenter implements Contract.presenter, Scanner.DistanceCallback {

    double detectionDistance = 20;
    double boomDistance = 1;
    Contract.scanner scanner;
    private boolean activated = false;
    private Contract.view view;
    private boolean lost;


    @Override
    public void start(Contract.view view) {
        this.view = view;
        scanner = new Scanner();
        scanner.startScan((Context) view, this);


    }

    @Override
    public void restart() {
        activated = true;
        lost = false;
    }

    private void deactivate() {
        activated = false;

    }

    @Override
    public void onDistanceChanged(double distance) {
        if(lost)
            return;
        if(activated) {
            if (distance > detectionDistance) {
                view.showSafe();
            } else if (distance <= detectionDistance && distance > boomDistance) {
                view.showDetectionMode(view.isButtonPressed());
                view.showDistance(distance);
            } else if (distance <= boomDistance) {
                if (view.isButtonPressed()) {
                    deactivate();
                    view.showDeactivated();
                    view.showDistance(distance);
                } else {
                    view.showBoom();
                    lost = true;
                }
            }
        }else{
            view.showDistance(distance);
        }
    }
}
