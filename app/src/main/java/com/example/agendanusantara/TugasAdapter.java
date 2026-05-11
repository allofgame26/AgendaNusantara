package com.example.agendanusantara;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TugasAdapter extends RecyclerView.Adapter<TugasAdapter.MyViewHolder>{
    private Context context;
    private ArrayList<String> id_tugas, judul, tanggal, kategori;

    public TugasAdapter(Context context, ArrayList<String> id_tugas, ArrayList<String> judul, ArrayList<String> tanggal, ArrayList<String> kategori) {
        this.context = context;
        this.id_tugas = id_tugas;
        this.judul = judul;
        this.tanggal = tanggal;
        this.kategori = kategori;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_tugas, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tvJudul.setText(String.valueOf(judul.get(position)));
        holder.tvInfo.setText(String.valueOf(tanggal.get(position)) + " - " + String.valueOf(kategori.get(position)));

        // Logika warna Ikon (Penting = Merah, Biasa = Hijau)
        if (String.valueOf(kategori.get(position)).equalsIgnoreCase("Penting")) {
            holder.imgArrow.setColorFilter(Color.parseColor("#D32F2F")); // Merah
        } else {
            holder.imgArrow.setColorFilter(Color.parseColor("#388E3C")); // Hijau
        }
    }

    @Override
    public int getItemCount() {
        return judul.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvJudul, tvInfo;
        ImageView imgArrow;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvJudul = itemView.findViewById(R.id.tv_judul_tugas);
            tvInfo = itemView.findViewById(R.id.tv_info_tugas);
            imgArrow = itemView.findViewById(R.id.img_arrow);
        }
    }
}
