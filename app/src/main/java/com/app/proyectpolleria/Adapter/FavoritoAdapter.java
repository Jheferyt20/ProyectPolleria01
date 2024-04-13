package com.app.proyectpolleria.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.proyectpolleria.Entidad.Categoria;
import com.app.proyectpolleria.Entidad.Favorito;
import com.app.proyectpolleria.Entidad.Plato;
import com.app.proyectpolleria.Negocio.PlatoNegocio;
import com.app.proyectpolleria.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class FavoritoAdapter extends RecyclerView.Adapter<FavoritoAdapter.FavoritoViewHolder> {
    private List<Favorito> listafavorito;
    private Context context;

    public FavoritoAdapter(Context context , List<Favorito> listafavoritos){
        this.context = context;
        this.listafavorito = listafavoritos;
    }

    public void updateFavorito(List<Favorito> newFavorito) {
        listafavorito.clear();
        listafavorito.addAll(newFavorito);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FavoritoAdapter.FavoritoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favorito, parent, false);
        return new FavoritoAdapter.FavoritoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoritoAdapter.FavoritoViewHolder holder, int position) {
        PlatoNegocio platoNegocio = new PlatoNegocio();
        Favorito favorito = listafavorito.get(position);
        List<Plato> platos = platoNegocio.ListarPlato(favorito.getIdPlato());
        Plato plato = platos.get(0);
        holder.nombre.setText(plato.getNombre());
        String url = plato.getImgPlato();
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.defaultplaceholder)
                .error(R.drawable.img_4);

        Glide.with(holder.itemView.getContext())
                .load(url)
                .placeholder(R.drawable.defaultplaceholder)
                .apply(requestOptions)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .error(R.drawable.img_4)
                .into(holder.imgPlato);
    }

    @Override
    public int getItemCount() {
        return listafavorito.size();
    }

    public class FavoritoViewHolder extends  RecyclerView.ViewHolder {

        private TextView nombre;
        private ImageView imgPlato;

        public  FavoritoViewHolder(View view){
            super(view);
            nombre = itemView.findViewById(R.id.txtnombreplato_fav);
            imgPlato = itemView.findViewById(R.id.menuImg);
        }
    }
}
