package com.catchfirst.catchfirst;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.Context;
import android.os.ParcelUuid;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aselims on 15/11/15.
 */
public class Scanner implements Contract.scanner {

    private static final String TAG = "BEACON";
    private BluetoothManager bluetoothManager;
    private BluetoothAdapter bluetoothAdapter;
    private BluetoothLeScanner bluetoothLeScanner;

    private ScanCallback scanCallback;

    // The Eddystone Service UUID, 0xFEAA.
    private static final ParcelUuid EDDYSTONE_SERVICE_UUID = ParcelUuid.fromString("0000FEAA-0000-1000-8000-00805F9B34FB");

    // Eddystone frame types
    private static final byte TYPE_UID = 0x00;
    private static final byte TYPE_URL = 0x10;
    private static final byte TYPE_TLM = 0x20;



    public Scanner() {
    }

    public void startScan(Context context, final DistanceCallback distanceCallback) {
        bluetoothManager = (BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE);
        bluetoothAdapter = bluetoothManager.getAdapter();
        bluetoothLeScanner = bluetoothAdapter.getBluetoothLeScanner();

        //various nice parameters but requires API23
        ScanSettings settings = new ScanSettings.Builder()
                .setScanMode(ScanSettings.SCAN_MODE_BALANCED)
                .build();


        //UUID F9:82:48:1C:E5:D7
        //URL D7:C0:EF:57:4E:07
        List<ScanFilter> filters = new ArrayList<ScanFilter>();
        filters.add(
                new ScanFilter.Builder()
                        .setServiceUuid(EDDYSTONE_SERVICE_UUID)
                        .build());

        scanCallback = new ScanCallback() {
            @Override
            public void onScanResult(int callbackType, ScanResult result) {
                super.onScanResult(callbackType, result);

                byte[] data = result.getScanRecord().getServiceData(EDDYSTONE_SERVICE_UUID);
                if (data == null)
                    return;

                 /*
                byte frameType = data[0];
                if (frameType != TYPE_UID)
                    return;

                String namespace = new BigInteger(1, Arrays.copyOfRange(data, 2, 12)).toString(16);
                String instance = new BigInteger(1, Arrays.copyOfRange(data, 12, 18)).toString(16);

                if (!(namespace + instance).equals("edd1ebeac04e5defa017" + "c5612a8cc253"))
                    return;
*/

                //Received signal strength indication
                int rssi;
                int txPower;
                double distance;
                String deviceAddress = result.getDevice().getAddress();
                if (deviceAddress.equals("F9:82:48:1C:E5:D7")) {
                    Log.i(TAG, String.valueOf(result.getRssi()));
                    rssi = result.getRssi();

                    txPower = data[1];
                    // pathLoss = (txPower at 0m - rssi);
                    distance = Math.pow(10, ((txPower - rssi) - 41) / 20.0);
                    // because rssi is unstable, usually  only proximity zones are used:
                    // - immediate (very close to the beacon)
                    // - near (about 1-3 m from the beacon)
                    // - far (further away or the signal is fluctuating too much to make a better estimate)
                    // - unknown
                    Log.i("distance", String.format("%.2fm", distance));
                   // distanceTV.setText(String.format("%.2fm", distance));
                    distanceCallback.onDistanceChanged(distance);


                }

            }

            @Override
            public void onScanFailed(int errorCode) {
                super.onScanFailed(errorCode);
            }
        };

        bluetoothLeScanner.startScan(filters, settings, scanCallback);
    }

    interface DistanceCallback{
        void onDistanceChanged(double distance);
    }

}
