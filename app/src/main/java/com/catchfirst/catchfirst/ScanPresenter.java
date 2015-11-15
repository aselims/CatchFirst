package com.catchfirst.catchfirst;

/**
 * Created by aselims on 15/11/15.
 */
public class ScanPresenter implements Contract.presenter {

    double detectionDistance;
    double boomDistance;
    Contract.scanner scanner;
    private boolean activated = true;

    @Override
    public void start(Contract.view view) {
        scanner = new Scanner();
        final double distance = scanner.getDistance();

        if(activated) {

            if (distance > detectionDistance) {
                view.showSafe();
            } else if (distance <= detectionDistance && distance > boomDistance) {
                view.showDistance(distance);
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

    private void deactivate() {
        activated = false;

    }
}
