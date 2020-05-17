package com.example.myapplication.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.R;
import com.example.myapplication.ui.Model.Contacto;
import com.example.myapplication.ui.viewModels.HomeViewModel;

public class ContactoFragment extends Fragment {

    private static TextView nombre, email, departamento;
    private static HomeViewModel mViewModel;

    public ContactoFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
        View view = inflater.inflate(R.layout.fragment_contacto_nuevo, container, false);

        nombre = view.findViewById(R.id.nombreRespuesta);
        email = view.findViewById(R.id.emailRespuesta);
        departamento= view.findViewById(R.id.departamentoRespuesta);

        cargarDatosContacto(mViewModel.getContactAtPosition(getArguments().getInt("POSITION")));

        return view;
    }

    public void cargarDatosContacto(Contacto contacto) {

        nombre.setText(contacto.getNombre() + " " + contacto.getApellido());
        email.setText(contacto.getEmail());
        departamento.setText(getArguments().getString("DEP"));

    }
}
