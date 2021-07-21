package com.example.malawibloodtransfusion.hospital.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.malawibloodtransfusion.R;
//import com.example.malawibloodtransfusion.hospital.R;

public class NotificationsFragment extends Fragment {



    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.hospital_fragment_management, container, false);




        return root;
    }
}