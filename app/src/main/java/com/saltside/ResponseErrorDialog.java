package com.saltside;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.saltside.R;


public class ResponseErrorDialog extends Dialog implements
        View.OnClickListener {

    public Context context;
    public Button ok;
    public TextView responseTextView;
    public String responseMsg = "";

    ResponseErrorDialogSelectedListener mCallback;

    public interface ResponseErrorDialogSelectedListener {
        public void responseErrorDialogClickOk();
    }
    public ResponseErrorDialog(Context context, String responseMsg) {
        super(context);
        this.context = context;
        this.responseMsg = responseMsg;
        try {
            mCallback = (ResponseErrorDialogSelectedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement ResponseErrorDialogSelectedListener");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.fragment_response_error_dialog);
        responseTextView = (TextView) findViewById(R.id.response_text);
        responseTextView.setText(responseMsg);
        ok = (Button) findViewById(R.id.response_ok);
        ok.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.response_ok) {
            mCallback.responseErrorDialogClickOk();
            dismiss();
        }
    }

    @Override
    public void onBackPressed() {
        mCallback.responseErrorDialogClickOk();
        dismiss();
    }
}