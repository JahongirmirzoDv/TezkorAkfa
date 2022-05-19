package uz.algorithmgateway.tezkorakfa.presenter.measurer.ui.select_type.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.algorithmgateway.tezkorakfa.R
import uz.algorithmgateway.tezkorakfa.data.retrofit.models.window.Width
import uz.algorithmgateway.tezkorakfa.databinding.CardsItemBinding


class CardsAdapter(val onpress: onPress, val context: Context) :
    RecyclerView.Adapter<CardsAdapter.Vh>() {
    var list: List<Width> = emptyList()
    var oldItem = -1
    var itemViewList = ArrayList<CardsItemBinding>()

    inner class Vh(val itemview: CardsItemBinding) : RecyclerView.ViewHolder(itemview.root) {
        @SuppressLint("ResourceAsColor")
        fun bind(width: Width, position: Int) {
            var isClick = false
            itemview.textView4.text = width.name
            itemview.cardView4.setOnClickListener {

                itemview.cardView4.radius = 15f
                if (oldItem < 0) {
                    itemview.color.setBackgroundColor(context.getColor(R.color.blue))
                } else {
                        itemViewList[oldItem].color.setBackgroundColor(context.getColor(R.color.layout_back_color))
                        itemViewList[position].color.setBackgroundColor(context.getColor(R.color.blue))
                }
                onpress.click(width, position)
                oldItem = position
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        val vh = Vh(CardsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        itemViewList.add(vh.itemview)
        return vh
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.bind(list[position], position)
    }

    override fun getItemCount(): Int = list.size

    interface onPress {
        fun click(width: Width, position: Int)
    }
}