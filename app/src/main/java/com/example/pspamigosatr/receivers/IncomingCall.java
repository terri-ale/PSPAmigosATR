package com.example.pspamigosatr.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.example.pspamigosatr.util.CallSaver;

import java.util.Date;
import java.util.GregorianCalendar;

public class IncomingCall extends BroadcastReceiver {

    private final String TAG=this.getClass().toString();
    private boolean notRegistered=true;
    //I have tested this class, and I have noticed that several times onReceive runs more than once,
    //even with conditionals filtering the state RINGING. With this boolean notRegistered, I make sure
    //that the call is registered only once.

    @Override
    public void onReceive(Context context, Intent intent) {

        if(intent.getStringExtra(TelephonyManager.EXTRA_STATE).equalsIgnoreCase(TelephonyManager.EXTRA_STATE_RINGING) && notRegistered) {


            TelephonyManager telephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            telephony.listen(new PhoneStateListener() {
                @Override
                public void onCallStateChanged(int state, String incomingNumber) {
                    super.onCallStateChanged(state, incomingNumber);
                    if (incomingNumber == null || incomingNumber.equals("")) return; //incomingNumber could be null if readCallLog permissions are not granted
                    if (state == TelephonyManager.CALL_STATE_RINGING) {
                        if(notRegistered) {
                            notRegistered=false;
                            Log.v("xyzyx ", " incoming: " + incomingNumber);

                            CallSaver saver = new CallSaver(context, incomingNumber, new Date().getTime());
                            saver.save();
                        }
                    }
                }
            }, PhoneStateListener.LISTEN_CALL_STATE);
        }
    }
    //String number = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
    //I could have got the incoming number with that line, and not registering a PhoneStateListener
    //but I prefer not to use deprecated methods or constants (EXTRA_INCOMING_NUMBER)
}