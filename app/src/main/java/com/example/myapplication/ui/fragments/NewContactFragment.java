package com.example.myapplication.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.example.myapplication.R;
import com.example.myapplication.ui.viewModels.AddContactoViewModel;

public class NewContactFragment extends Fragment {

    private AddContactoViewModel addContactoViewModel;
    private TextView nombreApellido, email, departamento;
    private Button confirmarContacto, cambiarFoto;
    private ImageView foto;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        addContactoViewModel = ViewModelProviders.of(this).get(AddContactoViewModel.class);
        View root = inflater.inflate(R.layout.fragment_anadir_contacto, container, false);
        nombreApellido = root.findViewById(R.id.nombreApellido);
        email = root.findViewById(R.id.email);
        departamento = root.findViewById(R.id.departamento);
        departamento.setText(getArguments().getString("DEPARTAMENTO"));
        confirmarContacto = root.findViewById(R.id.confirmarContacto);
        cambiarFoto = root.findViewById(R.id.cambiarFoto);
        foto = root.findViewById(R.id.imageView2);

        confirmarContacto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.navigation_home);
            }
        });

        cambiarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return root;
    }
}
