package com.ricardotravez.cliente.proyecto.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ricardotravez.cliente.proyecto.R;
import com.ricardotravez.cliente.proyecto.activity.DetallePlatilloActivity;
import com.ricardotravez.cliente.proyecto.api.ConfigApi;
import com.ricardotravez.cliente.proyecto.communication.Communication;
import com.ricardotravez.cliente.proyecto.communication.MostrarBadgeCommunication;
import com.ricardotravez.cliente.proyecto.entity.service.DetallePedido;
import com.ricardotravez.cliente.proyecto.entity.service.Platillo;
import com.ricardotravez.cliente.proyecto.utils.DateSerializer;
import com.ricardotravez.cliente.proyecto.utils.TimeSerializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class PlatillosRecomendadosAdapter extends RecyclerView.Adapter<PlatillosRecomendadosAdapter.ViewHolder> {
    private List<Platillo> platillos;
    private final Communication communication;
    private final MostrarBadgeCommunication mostrarBadgeCommunication;

    public PlatillosRecomendadosAdapter(List<Platillo> platillos, Communication communication, MostrarBadgeCommunication mostrarBadgeCommunication) {
        this.platillos = platillos;
        this.communication = communication;
        this.mostrarBadgeCommunication = mostrarBadgeCommunication;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_platillos, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setItem(this.platillos.get(position));
    }

    @Override
    public int getItemCount() {
        return this.platillos.size();
    }

    public void updateItems(List<Platillo> platillo) {
        this.platillos.clear();
        this.platillos.addAll(platillo);
        this.notifyDataSetChanged();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void setItem(final Platillo p) {
            ImageView imgPlatillo = itemView.findViewById(R.id.imgPlatillo);
            TextView namePlatillo = itemView.findViewById(R.id.namePlatillo);
            Button btnOrdenar = itemView.findViewById(R.id.btnOrdenar);

            String url = ConfigApi.baseUrlE + "/api/documento-almacenado/download/" + p.getFoto().getFileName();

            Picasso picasso = new Picasso.Builder(itemView.getContext())
                    .downloader(new OkHttp3Downloader(ConfigApi.getClient()))
                    .build();
            picasso.load(url)
                    //.networkPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                    .error(R.drawable.image_not_found)
                    .into(imgPlatillo);
            namePlatillo.setText(p.getNombre());
            btnOrdenar.setOnClickListener(v -> {
                DetallePedido detallePedido = new DetallePedido();
                detallePedido.setPlatillo(p);
                detallePedido.setCantidad(1);
                detallePedido.setPrecio(p.getPrecio());
                mostrarBadgeCommunication.add(detallePedido);
                //successMessage(Carrito.agregarPlatillos(detallePedido));
            });

            //Inicializar la vista del detalle del platillo
            itemView.setOnClickListener(v -> {
                final Intent i = new Intent(itemView.getContext(), DetallePlatilloActivity.class);
                final Gson g = new GsonBuilder()
                        .registerTypeAdapter(Date.class, new DateSerializer())
                        .registerTypeAdapter(Time.class, new TimeSerializer())
                        .create();
                i.putExtra("detalleAccesorio", g.toJson(p));
                communication.showDetails(i);
            });
        }

        public void successMessage(String message) {
            new SweetAlertDialog(itemView.getContext(),
                    SweetAlertDialog.SUCCESS_TYPE).setTitleText("Buen Trabajo!")
                    .setContentText(message).show();
        }
    }
}
