package uz.algorithmgateway.tezkorakfa.presenter.supplier.orderHistory

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import uz.algorithmgateway.tezkorakfa.core.util.layoutInflater
import uz.algorithmgateway.tezkorakfa.data.models.OrderSupplier
import uz.algorithmgateway.supplier.orderList.InterfaceOrderClick
import uz.algorithmgateway.tezkorakfa.R
import uz.algorithmgateway.tezkorakfa.data.retrofit.models.supplier_models.get_history.GetHistoryRes
import uz.algorithmgateway.tezkorakfa.databinding.ItemOrderHistoryListBinding

class AdapterOrderHistory(
//    private val orderClick: InterfaceOrderClick
) : RecyclerView.Adapter<AdapterOrderHistory.VH>() {
    private var myList = mutableListOf<GetHistoryRes>()
//    private lateinit var ctx: Context

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newList: List<GetHistoryRes>) {
        myList.clear()
        myList.addAll(newList)
        this.notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
//        ctx = parent.context
        return VH(
            ItemOrderHistoryListBinding.inflate(parent.layoutInflater, parent, false)
        )
    }

    //    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.onBind(myList[position])
    }

    override fun getItemCount(): Int = myList.size

    inner class VH(private val binding: ItemOrderHistoryListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        //        @RequiresApi(Build.VERSION_CODES.M)
        fun onBind(item: GetHistoryRes) {
            binding.apply {
                tvNumber.text = item.contract_number
                tvPrice.text = item.purchase_all_price.toString()
                when (item.degrees) {
                    "Medium" -> {
                        iconDegree.setImageResource(R.drawable.ic_medium)
                    }
                    "Slow" -> {
                        iconDegree.setImageResource(R.drawable.ic_low)
                    }
                    "Hard" -> {
                        iconDegree.setImageResource(R.drawable.ic_high)
                    }
                }
            }

//            binding.tvNumber.text = item.id.toString()
//            binding.tvPrice.text = item.price
//            binding.tvTime.text = item.time
        }
    }
}