package uz.algorithmgateway.tezkorakfa.presenter.measurer.ui.confirm_orders.adapter

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.algorithmgateway.tezkorakfa.R
import uz.algorithmgateway.tezkorakfa.core.util.layoutInflater
import uz.algorithmgateway.tezkorakfa.data.retrofit.models.sales_order_list.Result
import uz.algorithmgateway.tezkorakfa.databinding.ItemConfiremOrdersBinding

class ConfirmOrderListAdapter() :
    RecyclerView.Adapter<ConfirmOrderListAdapter.VH>() {

    var list: List<Result> = emptyList()
    var isCompleted = false
    lateinit var onpress: onPress

    constructor(list: ArrayList<Result>, isCompleted: Boolean, onpress: onPress) : this() {
        this.list = list
        this.isCompleted = isCompleted
        this.onpress = onpress
    }


    inner class VH(var itemview: ItemConfiremOrdersBinding) :
        RecyclerView.ViewHolder(itemview.root) {

        @SuppressLint("SetTextI18n")
        fun onBind(result: Result) {
            if (isCompleted) {
                itemview.imageStatus.setImageResource(R.drawable.ic_green)
                itemview.rejectOrder.visibility = View.GONE
                itemview.edit.visibility = View.GONE
            } else {
                itemview.imageStatus.setImageResource(R.drawable.ic_cancel_order)
                itemview.rejectOrder.visibility = View.VISIBLE
            }
            itemview.tvCustomerName.text = "${result.client.first_name} ${result.client.last_name}"
            itemview.orderNumber.text = "${result.id}"
            itemview.comment.text = "${result.comment}"
//            itemview.window.text = "Derazalar soni ${re}"
            itemview.configOrder.setOnClickListener {
                onpress.complete(result,itemview.sum.text.toString())
            }
            itemview.rejectOrder.setOnClickListener {
                onpress.reject(result)
            }
            itemview.edit.setOnClickListener {
                onpress.edit(result)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(
            ItemConfiremOrdersBinding.inflate(parent.layoutInflater, parent, false)
        )
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.onBind(list[position])

    }

    override fun getItemCount(): Int = list.size

    interface onPress {
        fun complete(result: Result,sum:String)
        fun reject(result: Result)
        fun edit(result: Result)
    }

}