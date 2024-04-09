package com.app.proyectpolleria.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.app.proyectpolleria.Entidad.Plato;
import com.app.proyectpolleria.R;
import com.app.proyectpolleria.descripcion_c;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;

import java.math.BigDecimal;
import java.util.List;

public class PlatoAdapter extends RecyclerView.Adapter<PlatoAdapter.PlatoViewHolder> {


    private List<Plato> listaplato;
    private Context context;

    public PlatoAdapter(Context context , List<Plato> listaplatos){
        this.context = context;
        this.listaplato = listaplatos;
    }

    public void updatePlato(List<Plato> newPlatos) {
        listaplato.clear();
        listaplato.addAll(newPlatos);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public PlatoAdapter.PlatoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_platos, parent, false);
        return new PlatoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PlatoAdapter.PlatoViewHolder holder, int position) {
        Plato plato = listaplato.get(position);
        holder.Nombre.setText(plato.getNombre());
        BigDecimal precio = plato.getPrecio();
        if (precio != null) {
            holder.Precio.setText("S/ "+precio.toString());
        } else {
            holder.Precio.setText("Precio no disponible");
        }
        String imageUrl = plato.getImgPlato();
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.defaultplaceholder)
                .error(R.drawable.image);

        Glide.with(holder.itemView.getContext())
                .load(imageUrl)
                .placeholder(R.drawable.defaultplaceholder)
                .apply(requestOptions)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .error(R.drawable.image)
                .into(holder.ImgPlato);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String platoJson = new Gson().toJson(plato);
                Intent intent = new Intent(context, descripcion_c.class);
                intent.putExtra("plato", platoJson);
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return listaplato.size(); // Devuelve el tamaño de la lista de platos
    }


    public class PlatoViewHolder extends RecyclerView.ViewHolder {

        public TextView Nombre;
        public TextView Precio;
        public ImageView ImgPlato;
        public CardView cardView;
        public PlatoViewHolder (View view){
            super(view);
            Nombre = itemView.findViewById(R.id.nom_platos);
            Precio = itemView.findViewById(R.id.nom_precio);
            cardView = itemView.findViewById(R.id.cardView);
            ImgPlato = itemView.findViewById(R.id.platoImg);
            ImgPlato.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }
    }
}
