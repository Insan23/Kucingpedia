package com.insan.kucingpedia.adapter

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnLongClickListener
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.insan.kucingpedia.R
import com.insan.kucingpedia.model.Kucing

class KucingAdapter(
    private val kucings: MutableList<Kucing?>,
    private val onItemClick: OnItemClickListener,
    private val onItemLongClick: OnItemLongClickListener
) : RecyclerView.Adapter<KucingAdapter.ViewHolder?>() {
    interface OnItemClickListener {
        fun onItemClick(model: Kucing?)
    }

    interface OnItemLongClickListener {
        fun onItemLongClick(model: Kucing?)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_kucing, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(kucings.get(position)!!, onItemClick, onItemLongClick)
    }

    override fun getItemCount(): Int {
        return kucings.size
    }

    inner class ViewHolder(view: View) :
        RecyclerView.ViewHolder(view) /*implements View.OnCreateContextMenuListener*/ {
        private val deskripsi: TextView
        private val ras: TextView
        private val foto: ImageView

        init {
            ras = view.findViewById<TextView>(R.id.ras)
            deskripsi = view.findViewById<TextView>(R.id.deskripsi)
            foto = view.findViewById<ImageView>(R.id.foto)
            //            view.setOnCreateContextMenuListener(this);
        }

        fun bind(
            model: Kucing,
            listener: OnItemClickListener,
            listenerLong: OnItemLongClickListener
        ) {
            ras.setText(model.ras)
            deskripsi.setText(model.deskripsi)
            foto.setImageBitmap(
                BitmapFactory.decodeByteArray(
                    model.foto,
                    0,
                    model.foto?.size ?: 0
                )
            )

            itemView.setOnClickListener(object : View.OnClickListener {
                override fun onClick(view: View?) {
                    listener.onItemClick(model)
                }
            })
            itemView.setOnLongClickListener(object : OnLongClickListener {
                override fun onLongClick(view: View?): Boolean {
                    listenerLong.onItemLongClick(model)
                    return true
                }
            })
        }
    }
}
