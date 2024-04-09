package com.app.proyectpolleria.Adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.app.proyectpolleria.Entidad.Categoria;
import com.app.proyectpolleria.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import java.util.List;

public class CategoriaAdapter extends RecyclerView.Adapter<CategoriaAdapter.CategoriaViewHolder> {

    private List<Categoria> listacategoria;
    private Context context;

    public CategoriaAdapter(Context context , List<Categoria> listacategorias){
        this.context = context;
        this.listacategoria = listacategorias;
    }

    public void updateCategoria(List<Categoria> newCategoria) {
        listacategoria.clear();
        listacategoria.addAll(newCategoria);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public CategoriaAdapter.CategoriaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_menu, parent, false);
        return new CategoriaAdapter.CategoriaViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriaAdapter.CategoriaViewHolder holder, int position) {
        Categoria categoria = listacategoria.get(position);
        holder.Nombre.setText(categoria.getNombre());
        String imageUrl = categoria.getImgCategoria();
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.defaultplaceholder)
                .error(R.drawable.img_4);

        Glide.with(holder.itemView.getContext())
                .load(imageUrl)
                .placeholder(R.drawable.defaultplaceholder)
                .apply(requestOptions)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .error(R.drawable.img_4)
                .into(holder.ImgPlato);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController((Activity)  context, R.id.nav_host_fragment_activity_home);
                int itemId = categoria.getId();
                Bundle bundle = new Bundle();
                bundle.putInt("itemId", itemId);
                navController.navigate(R.id.fragmento_plato , bundle);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listacategoria.size();
    }

    public class CategoriaViewHolder extends RecyclerView.ViewHolder {
        public TextView Nombre;
        public ImageView ImgPlato;
        public CardView cardView;

        public CategoriaViewHolder (View view){
            super(view);
            Nombre = itemView.findViewById(R.id.nom_categoria);
            ImgPlato = itemView.findViewById(R.id.menuImg);
            cardView = itemView.findViewById(R.id.cardView);
            ImgPlato.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }

    }
}
