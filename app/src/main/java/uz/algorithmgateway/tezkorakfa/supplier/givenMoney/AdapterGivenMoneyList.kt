package uz.algorithmgateway.tezkorakfa.supplier.givenMoney

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import uz.algorithmgateway.core.util.layoutInflater
import uz.algorithmgateway.data.models.GivenMoney
import uz.algorithmgateway.tezkorakfa.databinding.ItemGivenMoneyBinding

class AdapterGivenMoneyList() : RecyclerView.Adapter<AdapterGivenMoneyList.VH>() {
    private var myList = mutableListOf<GivenMoney>()
    private lateinit var ctx: Context

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newList: List<GivenMoney>) {
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
        fun onBind(item: GivenMoney) {
            binding.tvNumber.text = item.orderId.toString()
            binding.tvRequiredPrice.text = item.requiredMoney
            binding.tvGivenPrice.text = item.givenMoney
            binding.tvGivenDate.text = item.givenDate
        }
    }
}