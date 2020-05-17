package com.example.myapplication.ui.Repository;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.ui.Model.Contacto;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.UUID;

public class Repository {
    private static Repository srepository;

    private static String departamentoActual;
    private Context context;
    private MutableLiveData<ArrayList<Contacto>> lista_contactos_dept;

    private Repository(Context context) {
        this.context = context;
        lista_contactos_dept = new MutableLiveData<>();
    }

    public static Repository get(Context context) {
        if (srepository == null) {
            srepository = new Repository(context);
        }
        return srepository;
    }

    public static Repository getRepository() {
        return srepository;
    }

    public void getContactosDepartamento(String departamento) {

        //Lanzar thread de consulta JSON API
        MiHilo thread = new MiHilo();
        thread.execute("https://jda4v-538ee.firebaseio.com/Directorio/" + departamento + ".json");
        departamentoActual = departamento;
    }

    public LiveData<ArrayList<Contacto>> getListaContactosDept() {
        return lista_contactos_dept;
    }

    public void addNewContact(Contacto contacto) {
        ArrayList<Contacto> arrayListContactos = lista_contactos_dept.getValue();
        arrayListContactos.add(contacto);
        FirebaseDatabase.getInstance().getReference().child("Directorio").child(departamentoActual).setValue(arrayListContactos);
    }

    public void uploadURIfoto(Uri uri) {

        UUID nombreAleatorio = UUID.randomUUID();
        final StorageReference ref = FirebaseStorage.getInstance().getReference().child("images").child("JdaP4")
                .child(nombreAleatorio + ".jpg");

        UploadTask uploadTask = ref.putFile(uri);

        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }

                // Continue with the task to get the download URL
                return ref.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    Log.i("URL FOTO", downloadUri.toString());
                    writeURLFotoFirebase(downloadUri.toString());
                } else {
                }
            }
        });
    }

    private void writeURLFotoFirebase(String urlDownload) {

        int posicion = lista_contactos_dept.getValue().size()-1;

        FirebaseDatabase.getInstance().getReference().child("Directorio")
                .child(departamentoActual)
                .child(String.valueOf(posicion))
                .child("urlfoto")
                .setValue(urlDownload);

    }

    public class MiHilo extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            HttpURLConnection connection;
            URL url;
            connection = null;
            String result;
            result = "";

            try {
                url = new URL(strings[0]);
                connection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = connection.getInputStream();

                int data = inputStream.read();

                while (data != -1) {
                    result += (char) data;
                    data = inputStream.read();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            Log.i("RESULT", result);
            return result;
        }

        @Override
        protected void onPostExecute(String data) {
            super.onPostExecute(data);

            ArrayList<Contacto> llista_contactos = new ArrayList<>();

            //JSON format
            try {
                JSONArray jsonArray = new JSONArray(data);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject;
                    jsonObject = jsonArray.getJSONObject(i);

                    //Traspasar el formato JSON a mi model
                    Contacto contacto = new Contacto(jsonObject);
                    llista_contactos.add(contacto);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            lista_contactos_dept.postValue(llista_contactos);

        }
    }
}
