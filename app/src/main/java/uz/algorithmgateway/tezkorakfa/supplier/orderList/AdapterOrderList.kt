package uz.algorithmgateway.tezkorakfa.supplier.orderList

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
import uz.algorithmgateway.tezkorakfa.data.retrofit.models.sales_order_list.OrderList
import uz.algorithmgateway.tezkorakfa.data.retrofit.models.sales_order_list.Result
import uz.algorithmgateway.tezkorakfa.databinding.ItemOrderListBinding

class AdapterOrderList(
    private val myList: List<Result>,
    var itemClickListener: (Result) -> Unit
) : RecyclerView.Adapter<AdapterOrderList.VH>() {
    //    private var myList = mutableListOf<OrderSupplier>()
    private lateinit var ctx: Context

//    @SuppressLint("NotifyDataSetChanged")
//    fun updateList(newList: List<OrderSupplier>) {
//        myList.clear()
//        myList.addAll(newList)
//        this.notifyDataSetChanged()
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        ctx = parent.context
        return VH(
            ItemOrderListBinding.inflate(parent.layoutInflater, parent, false)
        )
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.onBind(myList[position])
    }

    override fun getItemCount(): Int = myList.size

    inner class VH(private val binding: ItemOrderListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(item: Result) {
            binding.tvNumber.text = item.contract_number.toString()
            binding.tvPrice.text = item.total_price.toString()
            binding.tvTime.text = item.measurement_time.toString()
//            when (item.status) {
//                1 -> binding.ivStatus.setBackgroundColor(ctx.getColor(R.color.color_green))
//                2 -> binding.ivStatus.setBackgroundColor(ctx.getColor(R.color.color_gray))
//                3 -> binding.ivStatus.setBackgroundColor(ctx.getColor(R.color.color_red))
//            }

            itemView.setOnClickListener {
                itemClickListener.invoke(item)
            }
        }
    }
}