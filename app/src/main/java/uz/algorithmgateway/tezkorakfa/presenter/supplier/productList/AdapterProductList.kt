package uz.algorithmgateway.tezkorakfa.presenter.supplier.productList

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import uz.algorithmgateway.tezkorakfa.core.util.layoutInflater
import uz.algorithmgateway.tezkorakfa.data.models.Product
import uz.algorithmgateway.supplier.productList.InterfaceProductClick
import uz.algorithmgateway.tezkorakfa.R
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

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.onBind(myList[position])
    }

    override fun getItemCount(): Int = myList.size

    inner class VH(private val binding: ItemProductTableBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @RequiresApi(Build.VERSION_CODES.M)
        fun onBind(item: Product) {
            binding.tvNumber.text = item.id.toString()
            binding.tvName.text = item.name
            binding.tvCount.text = item.count.toString()
            binding.tvPrice.text = item.price.toString()
            binding.tvTotal.text = item.total.toString()

            if (item.isFound) {
                binding.btnFound.setBackgroundColor(context.getColor(R.color.color_found_button))
                binding.btnFound.text = "Topilgan"
                binding.btnFound.isEnabled = false
            } else {
                binding.btnFound.setBackgroundColor(context.getColor(R.color.select_blue))
                binding.btnFound.text = "Topildi"
                binding.btnFound.setOnClickListener {
                    itemClick.invoke(item)
//                    adapterOnClick.onButtonClick(item)
                }
            }
        }
    }
}