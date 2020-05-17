package com.example.myapplication.ui.viewModels;

import android.net.Uri;

import androidx.lifecycle.ViewModel;

import com.example.myapplication.ui.Model.Contacto;
import com.example.myapplication.ui.Repository.Repository;

public class AddContactoViewModel extends ViewModel {

    Repository repository;

    public AddContactoViewModel() {
        repository = Repository.getRepository();
    }

    public void addNewContact(Contacto contacto) {

        repository.addNewContact(contacto);
    }

    public void setURIfoto(Uri uri) {
        repository.uploadURIfoto(uri);
    }
}
