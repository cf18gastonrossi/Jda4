package com.example.myapplication.ui.Repository;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.ui.Model.Contacto;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class Repository {
    private static Repository srepository;

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
        Log.i("RESULT", "https://jdarestaurant.firebaseio.com/JDAVersion4/Directorio/" + departamento + ".json");
        thread.execute("https://jdarestaurant.firebaseio.com/JDAVersion4/Directorio/" + departamento + ".json");

    }

    public LiveData<ArrayList<Contacto>> getListaContactosDept() {
        return lista_contactos_dept;
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
