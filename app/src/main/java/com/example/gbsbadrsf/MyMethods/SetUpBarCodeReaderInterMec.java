package com.example.gbsbadrsf.MyMethods;

import static com.example.gbsbadrsf.MainActivity.getDeviceName;

import com.example.gbsbadrsf.MainActivity;
import com.intermec.aidc.BarcodeReadEvent;
import com.intermec.aidc.BarcodeReadListener;
import com.intermec.aidc.BarcodeReader;
import com.intermec.aidc.BarcodeReaderException;
import com.intermec.aidc.VirtualWedge;
import com.intermec.aidc.VirtualWedgeException;

public class SetUpBarCodeReaderInterMec {
    private BarcodeReader bcr;
    private VirtualWedge wedge;
    private BarcodeReadListener barcodeReadListener;
    public static SetUpBarCodeReaderInterMec barCodeReader;
    public static SetUpBarCodeReaderInterMec getInstance(BarcodeReadListener barcodeReadListener){
        if (barCodeReader==null){
            barCodeReader = new SetUpBarCodeReaderInterMec(barcodeReadListener);
        }
        return barCodeReader;
    }
    public SetUpBarCodeReaderInterMec(BarcodeReadListener barcodeReadListener) {
        this.barcodeReadListener = barcodeReadListener;
        installBarcodeReader();
    }

    private void installBarcodeReader() {
        try {
            //get bar code instance from MainActivity
            bcr = MainActivity.getBarcodeObjectIntermec();
            wedge = MainActivity.getWedge();
            if(bcr != null)
            {
                //enable scanner
                bcr.setScannerEnable(true);

                //set listener
                bcr.addBarcodeReadListener(barcodeReadListener);
            }

        } catch (BarcodeReaderException e) {
            e.printStackTrace();
        }
        }



    public void onPause(){
        if (getDeviceName().equals("Foxconn International Holdings Limited CN51 NCU")) {
            try {

//                if (wedge != null) {
//                    wedge.setEnable(true);
//                    wedge = null;
//                }

                if (bcr != null) {
                    bcr.removeBarcodeReadListener(barcodeReadListener);
//                    bcr.close();
//                    bcr = null;

                }

            } catch (BarcodeReaderException e) {
                e.printStackTrace();
            }

            //disconnect from data collection service
//            com.intermec.aidc.AidcManager.disconnectService();
        }
    }
    public String scannedData(BarcodeReadEvent barcodeReadEvent){
        return String.valueOf(barcodeReadEvent.getBarcodeData()).trim();
    }
    public String getDeviceSerialNo(BarcodeReadEvent barcodeReadEvent){
        return String.valueOf(barcodeReadEvent.getDeviceId()).trim();
    }
}
