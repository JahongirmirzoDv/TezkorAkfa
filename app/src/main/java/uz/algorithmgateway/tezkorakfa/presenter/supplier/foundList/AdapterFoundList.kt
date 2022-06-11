package uz.algorithmgateway.tezkorakfa.presenter.supplier.foundList

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import uz.algorithmgateway.tezkorakfa.core.util.layoutInflater
import uz.algorithmgateway.tezkorakfa.data.retrofit.models.supplier_models.get_found_product_by_id.GetFoundProductByIdItem
import uz.algorithmgateway.tezkorakfa.databinding.ItemFoundListBinding

class AdapterFoundList : RecyclerView.Adapter<AdapterFoundList.VH>() {
    private var myList = mutableListOf<GetFoundProductByIdItem>()
    private lateinit var ctx: Context

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newList: List<GetFoundProductByIdItem>) {
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

    //    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.onBind(myList[position], position.plus(1).toString())
    }

    override fun getItemCount(): Int = myList.size

    inner class VH(private val binding: ItemFoundListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        //        @RequiresApi(Build.VERSION_CODES.M)
        fun onBind(item: GetFoundProductByIdItem, counter: String) {
            binding.apply {
                tvNumber.text = counter
                tvName.text = "${item.title}-${item.articul}(${item.color})"
                tvCount.text = item.count.toString()
                tvPrice.text = item.price.toString()

                val data = item.purchase.get(0)
                if (data.place_of_origin.equals("Ko'chadan")) {
                    tvOutsideNumber.text = data.count.toString()
                    tvOutsidePrice.text = data.total_price.toString()
                    tvDealerNumber.text = ""
                    tvDealerPrice.text = ""
                } else if (data.place_of_origin.equals("Dillerdan")) {
                    tvOutsideNumber.text = ""
                    tvOutsidePrice.text = ""
                    tvDealerNumber.text = data.count.toString()
                    tvDealerPrice.text = data.total_price.toString()
                }

            }
        }

//            binding.tvNumber.text = item.id.toString()
//            binding.tvName.text = item.name
//            binding.tvCount.text = item.count.toString()
//            binding.tvPrice.text = item.price

//            if(item.countByDealer != 0){
//                binding.tvDealerNumber.text = item.countByDealer.toString()
//                binding.tvDealerPrice.text = item.priceByDealer
//            }else{
//                binding.tvDealerNumber.text = ""
//                binding.tvDealerPrice.text = ""
//            }
//
//            if(item.countByOutside != 0){
//                binding.tvOutsideNumber.text = item.countByOutside.toString()
//                binding.tvOutsidePrice.text = item.priceByOutside
//            }else{
//                binding.tvOutsideNumber.text = ""
//                binding.tvOutsidePrice.text = ""
//            }
    }
}
