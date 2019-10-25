package com.insan.kucingpedia.adapter;

import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.insan.kucingpedia.R;
import com.insan.kucingpedia.model.Kucing;

import java.util.List;

public class KucingAdapter extends RecyclerView.Adapter<KucingAdapter.ViewHolder> {

    private List<Kucing> kucings;
    private OnItemClickListener onItemClick;
    private OnItemLongClickListener onItemLongClick;

    public KucingAdapter(List<Kucing> kucing, OnItemClickListener onItemClick, OnItemLongClickListener onItemLongClick) {
        kucings = kucing;
        this.onItemClick = onItemClick;
        this.onItemLongClick = onItemLongClick;
    }

    public interface OnItemClickListener {
        void onItemClick(Kucing model);
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(Kucing model);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_kucing, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(kucings.get(position), onItemClick, onItemLongClick);
    }

    @Override
    public int getItemCount() {
        return kucings.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder /*implements View.OnCreateContextMenuListener*/ {
        private TextView deskripsi, ras;
        private ImageView foto;

        ViewHolder(@NonNull View view) {
            super(view);
            ras = view.findViewById(R.id.ras);
            deskripsi = view.findViewById(R.id.deskripsi);
            foto = view.findViewById(R.id.foto);
//            view.setOnCreateContextMenuListener(this);
        }

        void bind(final Kucing model, final OnItemClickListener listener, final OnItemLongClickListener listenerLong) {
            ras.setText(model.getRas());
            deskripsi.setText(model.getDeskripsi());
            foto.setImageBitmap(BitmapFactory.decodeByteArray(model.getFoto(), 0, model.getFoto().length));

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(model);
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    listenerLong.onItemLongClick(model);
                    return true;
                }
            });
        }
    }
}
