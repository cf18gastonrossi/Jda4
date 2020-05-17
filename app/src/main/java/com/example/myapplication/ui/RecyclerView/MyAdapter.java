package com.example.myapplication.ui.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.ui.Model.Contacto;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private ArrayList<Contacto> entryList;

    public MyAdapter(ArrayList<Contacto> entryList) {
        this.entryList = entryList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_text_view, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int i) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        String nombre = entryList.get(i).getNombre();
        String apellido = entryList.get(i).getApellido();
        String email = entryList.get(i).getEmail();

        holder.nombre.setText(nombre);
        holder.apellido.setText(apellido);
        holder.email.setText(email);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return entryList.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView nombre, apellido, email;
        public MyViewHolder(View v) {
            super(v);

            nombre = v.findViewById(R.id.nombre);
            apellido = v.findViewById(R.id.apellido);
            email = v.findViewById(R.id.email);
        }
    }
}