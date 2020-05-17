package com.example.myapplication.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.ui.Model.Contacto;
import com.example.myapplication.ui.RecyclerView.MyAdapter;
import com.example.myapplication.ui.viewModels.HomeViewModel;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private static HomeViewModel homeViewModel;
    private static RecyclerView recyclerView;
    private Button newContact;
    private static RecyclerView.Adapter mAdapter;
    private static RecyclerView.LayoutManager layoutManager;
    private static ArrayList<Contacto> listado_contactos = new ArrayList<>();
    private String departamento;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        Spinner spinner;

        final Bundle bundle = new Bundle();
        homeViewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        newContact = root.findViewById(R.id.newContactButton);

        spinner = root.findViewById(R.id.spinner);
        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.departamentos_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                departamento = adapterView.getItemAtPosition(i).toString();
                homeViewModel.getContactosDepartamentos(departamento);
                bundle.putString("DEPARTAMENTO", departamento);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        homeViewModel.getListaContactosDept().observe(getViewLifecycleOwner(), new Observer<ArrayList<Contacto>>() {
            @Override
            public void onChanged(ArrayList<Contacto> contactos) {
                //Modificar recycler
                listado_contactos = contactos;
                changeData(departamento);

            }
        });

        newContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.newContactFragment, bundle);
            }
        });



        recyclerView = root.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new MyAdapter(listado_contactos,departamento);
        recyclerView.setAdapter(mAdapter);

        return root;
    }

    private static void changeData(String departamento) {
        //Utilizo este metodo para actualizar la recycler view porque el notify del adapter no me estaba funcionando

        mAdapter = new MyAdapter(listado_contactos, departamento);
        recyclerView.setAdapter(mAdapter);
    }
}