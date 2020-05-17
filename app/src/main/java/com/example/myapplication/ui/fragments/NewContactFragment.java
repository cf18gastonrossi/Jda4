package com.example.myapplication.ui.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
import com.example.myapplication.ui.Model.Contacto;
import com.example.myapplication.ui.viewModels.AddContactoViewModel;

import static android.app.Activity.RESULT_OK;

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
        confirmarContacto.setVisibility(View.INVISIBLE);
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
                Contacto contacto = new Contacto(nombreApellido.getText().toString().split(" ")[0],
                        nombreApellido.getText().toString().split(" ")[1],
                        email.getText().toString()
                );

                if (contacto.checkInput()) {
                    addContactoViewModel.addNewContact(contacto);
                    cargar_imagen_galeria();
                    cambiarFoto.setVisibility(View.INVISIBLE);
                    confirmarContacto.setVisibility(View.VISIBLE);
                }

            }
        });

        return root;
    }

    private void cargar_imagen_galeria() {

        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 10);


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Bitmap bitmap = null;

        if (requestCode == 10 && resultCode == RESULT_OK) {

            Uri uri;
            uri = data.getData();
            addContactoViewModel.setURIfoto(uri);

            try {

                bitmap = MediaStore.Images.Media
                        .getBitmap(getActivity().getContentResolver(), uri);


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (bitmap != null) {
            foto.setImageBitmap(bitmap);
        }


    }
}

