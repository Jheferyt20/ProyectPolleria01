package com.app.proyectpolleria.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.app.proyectpolleria.Datos.PlatoDatos;
import com.app.proyectpolleria.Entidad.Carrito;
import com.app.proyectpolleria.Entidad.Categoria;
import com.app.proyectpolleria.Entidad.Plato;
import com.app.proyectpolleria.Negocio.CarritoNegocio;
import com.app.proyectpolleria.Negocio.PlatoNegocio;
import com.app.proyectpolleria.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.firestore.v1.StructuredAggregationQuery;

import java.math.BigDecimal;
import java.util.List;
import android.os.Handler;
import android.widget.Toast;

public class CarritoAdapter extends RecyclerView.Adapter<CarritoAdapter.CarritoViewHolder> {

    private List<Carrito> listacarrito;
    private Context context;
    private Handler handler = new Handler();
    private boolean isClickPending = false;
    private final int DELAY_MILLISECONDS = 1000;

    public CarritoAdapter(Context context , List<Carrito> listacarritos){
        this.context = context;
        this.listacarrito = listacarritos;
    }
    public void updateCarrito(List<Carrito> newCarrito) {
        listacarrito.clear();
        listacarrito.addAll(newCarrito);
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public CarritoAdapter.CarritoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_carrito, parent, false);
        return new CarritoAdapter.CarritoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CarritoAdapter.CarritoViewHolder holder, int position) {
        PlatoNegocio negocioPlato = new PlatoNegocio();
        CarritoNegocio carritoNegocio = new CarritoNegocio();
        Carrito carrito = listacarrito.get(position);
        List<Plato> platos = negocioPlato.ListarPlato(carrito.getId_Plato());
        Plato plato = platos.get(0);
        SharedPreferences preferences = context.getSharedPreferences("datos_usuario", Context.MODE_PRIVATE);
        int idUsuario = preferences.getInt("USUARIO", 0);
        String url = plato.getImgPlato();
        BigDecimal preciototal = plato.getPrecio().multiply(BigDecimal.valueOf(carrito.getCantidad()));
        holder.PrecioTotal.setText(preciototal.toString());
        holder.Precio.setText(String.valueOf(plato.getPrecio()));
        holder.Nombre.setText(plato.getNombre());
        holder.Cantidad.setText(String.valueOf(carrito.getCantidad())); // Convertir a String

        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.defaultplaceholder)
                .error(R.drawable.img_4);

        Glide.with(holder.itemView.getContext())
                .load(url)
                .placeholder(R.drawable.defaultplaceholder)
                .apply(requestOptions)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .error(R.drawable.img_4)
                .into(holder.ImgPlato);



        holder.Sumar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int cantidadtexto = Integer.parseInt(holder.Cantidad.getText().toString());

                if (cantidadtexto >= 1 && cantidadtexto <= 9) {
                    final int cantidadfinal = cantidadtexto + 1;
                    BigDecimal preciototal2 = plato.getPrecio().multiply(BigDecimal.valueOf(cantidadfinal));
                    holder.Cantidad.setText(String.valueOf(cantidadfinal));
                    holder.PrecioTotal.setText(preciototal2.toString());

                    if (!isClickPending) {
                        isClickPending = true;

                        StringBuilder mensaje = new StringBuilder(); // Inicializa el StringBuilder
                        boolean resultado = carritoNegocio.OperacionCarrito(idUsuario, plato.getId(), true, cantidadfinal, mensaje);

                        if (!resultado) {
                            Toast.makeText(context, "No pudieron hacerse los cambios", Toast.LENGTH_SHORT).show();
                        }

                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                isClickPending = false;
                            }
                        }, DELAY_MILLISECONDS);
                    }
                }else {
                    Toast.makeText(context, "Has llegado al limite", Toast.LENGTH_SHORT).show();
                }
            }
        });

        holder.Restar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int cantidadtexto = Integer.parseInt(holder.Cantidad.getText().toString());

                if (cantidadtexto >= 1 && cantidadtexto <= 10) {
                    final int cantidadfinal = cantidadtexto - 1;
                    BigDecimal preciototal2 = plato.getPrecio().multiply(BigDecimal.valueOf(cantidadfinal));
                    holder.PrecioTotal.setText(preciototal2.toString());
                    holder.Cantidad.setText(String.valueOf(cantidadfinal));

                    if (!isClickPending) {
                        isClickPending = true;

                        StringBuilder mensaje = new StringBuilder(); // Inicializa el StringBuilder
                        boolean resultado = carritoNegocio.OperacionCarrito(idUsuario, plato.getId(), false, cantidadfinal, mensaje);

                        if (!resultado) {
                            Toast.makeText(context, "No pudieron hacerse los cambios", Toast.LENGTH_SHORT).show();
                        }
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                isClickPending = false;
                            }
                        }, DELAY_MILLISECONDS);
                    }
                }
                else {
                    Toast.makeText(context, "Has llegado al limite", Toast.LENGTH_SHORT).show();
                }
            }
        });


        holder.eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean resultado = carritoNegocio.eliminarCarrito(carrito.getId());
                if (resultado){
                    int posicionEliminada = listacarrito.indexOf(carrito);
                    if (posicionEliminada != -1) {
                        listacarrito.remove(posicionEliminada);
                        notifyItemRemoved(posicionEliminada);
                    }
                }else{
                    Toast.makeText(context, "No se pudo eliminar", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }


    @Override
    public int getItemCount() {
        return listacarrito.size();
    }

    public class CarritoViewHolder extends  RecyclerView.ViewHolder {

        public TextView Nombre;
        public TextView Cantidad;
        public  TextView Precio;
        public  TextView Sumar;
        public  TextView Restar;
        public  TextView PrecioTotal;
        public ImageView ImgPlato;
        public  ImageView eliminar;

        public  CarritoViewHolder(View view){
            super(view);
            ImgPlato = itemView.findViewById(R.id.img_carrito_com);
            Nombre = itemView.findViewById(R.id.txt_nomPlato);
            Precio = itemView.findViewById(R.id.txt_precio);
            PrecioTotal = itemView.findViewById(R.id.txtPrecio_total);
            Cantidad = itemView.findViewById(R.id.tvtCantidad);
            Sumar = itemView.findViewById(R.id.btnIncrease);
            Restar = itemView.findViewById(R.id.btnDecrease);
            eliminar = itemView.findViewById(R.id.icon_delete);
        }
    }
}
