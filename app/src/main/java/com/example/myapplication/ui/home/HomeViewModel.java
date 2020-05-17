package com.example.myapplication.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.ui.Model.Contacto;
import com.example.myapplication.ui.Repository.Repository;

import java.util.ArrayList;

public class HomeViewModel extends ViewModel {

    Repository repository;
    ArrayList<Contacto> lista_contactos_dept;
    MutableLiveData<ArrayList<Contacto>> liveData_lista_contactos;

    public HomeViewModel() {
        repository = Repository.getRepository();
        lista_contactos_dept = new ArrayList<>();
        liveData_lista_contactos = new MutableLiveData<>();
    }

    public void getContactosDepartamentos(String departamento) {
        repository.getContactosDepartamento(departamento);
        repository.getListaContactosDept().observeForever(new Observer<ArrayList<Contacto>>() {
            @Override
            public void onChanged(ArrayList<Contacto> contactos) {
                lista_contactos_dept = contactos;
                liveData_lista_contactos.postValue(lista_contactos_dept);
            }
        });
    }

    public LiveData<ArrayList<Contacto>> getListaContactosDept() {
        return liveData_lista_contactos;
    }
}