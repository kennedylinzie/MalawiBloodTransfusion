package com.example.malawibloodtransfusion;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class BottomSheetDialog extends BottomSheetDialogFragment {

@Override
public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
        {
        View v = inflater.inflate(R.layout.bottom_sheet_layout, container, false);
            TextView msg = v.findViewById(R.id.message_view);
            Button  close = v.findViewById(R.id.algo_button);

            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });



        event_Prefs eve = new event_Prefs();
        eve.donor_get_message(getContext());
        msg.setText(eve.getMessage());







            return v;
            }
        }
