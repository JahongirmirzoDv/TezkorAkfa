package uz.algorithmgateway.tezkorakfa.presenter.supplier.orderList

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.algorithmgateway.tezkorakfa.R
import uz.algorithmgateway.tezkorakfa.core.util.layoutInflater
import uz.algorithmgateway.tezkorakfa.data.retrofit.models.supplier_models.SupplierOrderlistItem
import uz.algorithmgateway.tezkorakfa.databinding.ItemOrderListSupplierBinding

class AdapterOrderList(
    private var myList: ArrayList<SupplierOrderlistItem>,
    var itemClickListener: (String) -> Unit
) : RecyclerView.Adapter<AdapterOrderList.VH>() {
//    private var myList = mutableListOf<OrderSupplier>()
    private lateinit var ctx: Context

    private var count: Int = 1

    //    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newList: ArrayList<SupplierOrderlistItem>) {
        myList.clear()
//        myList.addAll(newList)
        this.notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        ctx = parent.context
        return VH(
            ItemOrderListSupplierBinding.inflate(parent.layoutInflater, parent, false)
        )
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.onBind(myList[position])
    }

    override fun getItemCount(): Int = myList.size

    inner class VH(private val binding: ItemOrderListSupplierBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(item: SupplierOrderlistItem) {
            binding.apply {
                tvNumber.text = item.contract_number
                when(item.degrees){
                    "Hard"-> {
                        iconDegree.setImageResource(R.drawable.ic_high)
                        tvTime.text = "Juda baland"
                    }
                    "Slow"->{
                        iconDegree.setImageResource(R.drawable.ic_low)
                        tvTime.text = "Past"
                    }
                    "Medium"->{
                        iconDegree.setImageResource(R.drawable.ic_medium)
                        tvTime.text = "O'rtacha"
                    }
                }

                number.setText(count.toString())
                count++
            }

//            when (item.status) {
//                1 -> binding.ivStatus.setBackgroundColor(ctx.getColor(R.color.color_green))
//                2 -> binding.ivStatus.setBackgroundColor(ctx.getColor(R.color.color_gray))
//                3 -> binding.ivStatus.setBackgroundColor(ctx.getColor(R.color.color_red))
//            }

            itemView.setOnClickListener {
//                itemClickListener.invoke(item.contract_number)
                itemClickListener.invoke(item.id.toString())
            }
        }
    }
}