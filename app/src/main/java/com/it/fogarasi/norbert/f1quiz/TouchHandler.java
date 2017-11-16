package com.it.fogarasi.norbert.f1quiz;

import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

public class TouchHandler implements View.OnTouchListener {

    Button b1, b2, b3, b4;

    public TouchHandler(Button b1, Button b2, Button b3, Button b4) {
        this.b1 = b1;
        this.b2 = b2;
        this.b3 = b3;
        this.b4 = b4;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            switch (v.getId()) {
                case R.id.btn_1:
                    b2.setEnabled(false);
                    b3.setEnabled(false);
                    b4.setEnabled(false);
                    break;
                case R.id.btn_2:
                    b1.setEnabled(false);
                    b3.setEnabled(false);
                    b4.setEnabled(false);
                    break;
                case R.id.btn_3:
                    b2.setEnabled(false);
                    b1.setEnabled(false);
                    b4.setEnabled(false);
                    break;
                case R.id.btn_4:
                    b2.setEnabled(false);
                    b1.setEnabled(false);
                    b3.setEnabled(false);
                    break;
            }
        }
        if(event.getAction() == MotionEvent.ACTION_BUTTON_RELEASE || event.getAction() == MotionEvent.ACTION_CANCEL || event.getAction() == MotionEvent.ACTION_UP){
            b2.setEnabled(true);
            b1.setEnabled(true);
            b3.setEnabled(true);
            b4.setEnabled(true);
        }
        return false;
    }
}