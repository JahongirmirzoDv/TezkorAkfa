package uz.algorithmgateway.tezkorakfa.presenter.supplier.givenMoney

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import uz.algorithmgateway.tezkorakfa.core.util.layoutInflater
import uz.algorithmgateway.tezkorakfa.data.models.GivenMoney
import uz.algorithmgateway.tezkorakfa.data.retrofit.models.supplier_models.get_money_list.GetMoneyListRes
import uz.algorithmgateway.tezkorakfa.databinding.ItemGivenMoneyBinding

class AdapterGivenMoneyList : RecyclerView.Adapter<AdapterGivenMoneyList.VH>() {
    private var myList = mutableListOf<GetMoneyListRes>()
    private lateinit var ctx: Context

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newList: List<GetMoneyListRes>) {
        myList.clear()
        myList.addAll(newList)
        this.notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        ctx = parent.context
        return VH(
            ItemGivenMoneyBinding.inflate(parent.layoutInflater, parent, false)
        )
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.onBind(myList[position])
    }

    override fun getItemCount(): Int = myList.size

    inner class VH(private val binding: ItemGivenMoneyBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @RequiresApi(Build.VERSION_CODES.M)
        fun onBind(item: GetMoneyListRes) {
            binding.apply {
                tvNumber.text = item.contract_number.toString()
                tvRequiredPrice.text = item.purchase_all_price.toString()
                tvGivenPrice.text = item.supplier_send_price_all.toString()
                tvGivenDate.text = item.measurement_time.subSequence(0, 10)
            }

//            binding.tvNumber.text = item.orderId.toString()
//            binding.tvRequiredPrice.text = item.requiredMoney
//            binding.tvGivenPrice.text = item.givenMoney
//            binding.tvGivenDate.text = item.givenDate
        }
    }
}