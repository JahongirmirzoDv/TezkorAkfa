package uz.algorithmgateway.tezkorakfa.measurer.ui.drawings.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import uz.algorithmgateway.tezkorakfa.databinding.PdfItemBinding
import uz.algorithmgateway.tezkorakfa.measurer.ui.select_type.models.Drawing

class PdfAdapter(var context: Context) : RecyclerView.Adapter<PdfAdapter.Vh>() {

    var list: List<Drawing> = emptyList()

    inner class Vh(val itemview: PdfItemBinding) : RecyclerView.ViewHolder(itemview.root) {
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
            itemview.romD.text = "${drawing.type} soni ${drawing.count}"
            itemview.profileD.text =
                "${drawing.profile_type} ${drawing.profile_type_two}(${drawing.profile_texture})"
            itemview.oynaD.text = "${drawing.mirror_color} ${drawing.mirror_layer}"
            itemview.podokonnikD.text = "${drawing.window_sill} "
            itemview.aksesuarD.text =
                "${drawing.handle}(${drawing.handle_petla}) ${drawing.handle_texture}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(PdfItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size
}