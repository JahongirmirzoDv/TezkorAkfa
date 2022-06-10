package uz.algorithmgateway.tezkorakfa.presenter.supplier.productList

import android.annotation.SuppressLint
import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.algorithmgateway.tezkorakfa.core.util.layoutInflater
import uz.algorithmgateway.tezkorakfa.data.models.supplier.OrderDetailsModel
import uz.algorithmgateway.tezkorakfa.data.models.supplier.Product
import uz.algorithmgateway.tezkorakfa.databinding.ItemProductTableBinding

class AdapterProductList(
    private val context: Context,
    val itemClick: (Product) -> Unit
) : RecyclerView.Adapter<AdapterProductList.VH>() {
    private var myList = mutableListOf<Product>()

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newList: List<Product>) {
        myList.clear()
        myList.addAll(newList)
        this.notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(
            ItemProductTableBinding.inflate(parent.layoutInflater, parent, false)
        )
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.onBind(myList[position])
    }

    override fun getItemCount(): Int = myList.size

    inner class VH(private val binding: ItemProductTableBinding) :
        RecyclerView.ViewHolder(binding.root) {

        //        @RequiresApi(Build.VERSION_CODES.M)
        fun onBind(item: Product) {
            binding.tvNumber.text = item.id.toString()
            binding.tvName.text = "${item.title}-${item.sub_title}(${item.color})"
            binding.tvCount.text = item.items.get(0).quantity.toString()
            binding.tvPrice.text = item.price.toString()
            binding.tvTotal.text = item.items.get(0).total_price.toString()

//            if (item.isFound) {
//                binding.btnFound.setBackgroundColor(context.getColor(R.color.color_found_button))
//                binding.btnFound.text = "Topilgan"
//                binding.btnFound.isEnabled = false
//            } else {
//                binding.btnFound.setBackgroundColor(context.getColor(R.color.select_blue))
//                binding.btnFound.text = "Topildi"
//                binding.btnFound.setOnClickListener {
//                    itemClick.invoke(item)
////                    adapterOnClick.onButtonClick(item)
//                }
//            }
        }
    }
}