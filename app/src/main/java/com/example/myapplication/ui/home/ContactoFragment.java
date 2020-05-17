package com.example.myapplication.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.R;

public class ContactoFragment extends Fragment {

    private static TextView nombre, email, departamento;
    private static ViewModel viewModel;

    public ContactoFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
        View view = inflater.inflate(R.layout.fragment_contacto_nuevo, container, false);

        nombre = view.findViewById(R.id.nombreRespuesta);
        email = view.findViewById(R.id.emailRespuesta);
        departamento= view.findViewById(R.id.departamentoRespuesta);

        return view;
    }
}
