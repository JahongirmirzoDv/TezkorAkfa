package uz.algorithmgateway.tezkorakfa.presenter.supplier.foundList

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import uz.algorithmgateway.tezkorakfa.core.util.layoutInflater
import uz.algorithmgateway.tezkorakfa.data.models.FoundProduct
import uz.algorithmgateway.tezkorakfa.databinding.ItemFoundListBinding

class AdapterFoundList() : RecyclerView.Adapter<AdapterFoundList.VH>() {
    private var myList = mutableListOf<FoundProduct>()
    private lateinit var ctx: Context

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newList: List<FoundProduct>) {
        myList.clear()
        myList.addAll(newList)
        this.notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        ctx = parent.context
        return VH(
            ItemFoundListBinding.inflate(parent.layoutInflater, parent, false)
        )
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.onBind(myList[position])
    }

    override fun getItemCount(): Int = myList.size

    inner class VH(private val binding: ItemFoundListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @RequiresApi(Build.VERSION_CODES.M)
        fun onBind(item: FoundProduct) {
            binding.tvNumber.text = item.id.toString()
            binding.tvName.text = item.name
            binding.tvCount.text = item.count.toString()
            binding.tvPrice.text = item.price

            if(item.countByDealer != 0){
                binding.tvDealerNumber.text = item.countByDealer.toString()
                binding.tvDealerPrice.text = item.priceByDealer
            }else{
                binding.tvDealerNumber.text = ""
                binding.tvDealerPrice.text = ""
            }

            if(item.countByOutside != 0){
                binding.tvOutsideNumber.text = item.countByOutside.toString()
                binding.tvOutsidePrice.text = item.priceByOutside
            }else{
                binding.tvOutsideNumber.text = ""
                binding.tvOutsidePrice.text = ""
            }
        }
    }
}