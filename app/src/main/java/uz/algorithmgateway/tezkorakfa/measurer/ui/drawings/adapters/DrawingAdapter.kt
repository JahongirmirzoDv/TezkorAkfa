package uz.algorithmgateway.tezkorakfa.measurer.ui.drawings.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import uz.algorithmgateway.tezkorakfa.core.util.layoutInflater
import uz.algorithmgateway.tezkorakfa.databinding.DrawingItemBinding
import uz.algorithmgateway.tezkorakfa.measurer.ui.select_type.models.Drawing

class DrawingAdapter(var context: Context) : RecyclerView.Adapter<DrawingAdapter.Vh>() {
    var list = emptyList<Drawing>()

    inner class Vh(val itemview: DrawingItemBinding) : RecyclerView.ViewHolder(itemview.root) {
        @SuppressLint("SetTextI18n")
        fun bind(drawing: Drawing) {
            Glide.with(context)
                .load(drawing.projet_image_path) // Uri of the picture
                .listener(object : RequestListener<Drawable?> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable?>?,
                        isFirstResource: Boolean,
                    ): Boolean {
                        println(e.toString())
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable?>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean,
                    ): Boolean {
                        return false
                    }

                })
                .into(itemview.image)
            itemview.size.text = "${drawing.width}*${drawing.heigth}"
            itemview.number.text = drawing.id
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(DrawingItemBinding.inflate(parent.layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size
}