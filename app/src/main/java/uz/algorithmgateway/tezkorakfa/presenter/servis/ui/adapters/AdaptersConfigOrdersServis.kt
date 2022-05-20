package uz.algorithmgateway.tezkorakfa.presenter.servis.ui.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import uz.algorithmgateway.tezkorakfa.core.util.layoutInflater
import uz.algorithmgateway.tezkorakfa.data.models.GivenMoney
import uz.algorithmgateway.tezkorakfa.databinding.ItemGivenMoneyBinding
import uz.algorithmgateway.tezkorakfa.databinding.MeterItemBinding
import uz.algorithmgateway.tezkorakfa.presenter.servis.ui.models.ModelConfigOrdersServis

class AdaptersConfigOrdersServis : RecyclerView.Adapter<AdaptersConfigOrdersServis.VH>() {
    private var myList = mutableListOf<ModelConfigOrdersServis>()
    private lateinit var ctx: Context
    var count: Int = 1

    fun updateList(newList: List<ModelConfigOrdersServis>) {
        myList.clear()
        myList.addAll(newList)
        this.notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        ctx = parent.context
        return VH(
            MeterItemBinding.inflate(parent.layoutInflater, parent, false)
        )
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.onBind(myList[position])
    }

    override fun getItemCount(): Int = myList.size

    inner class VH(private val binding: MeterItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(item: ModelConfigOrdersServis) {
            binding.apply {
                number.setText("$count")
                productName.setText(item.name)
                productNumber.setText(item.number)
                aldoks.setText(item.aldoks)
                pvh.setText(item.pvh)
                exclusive.setText(item.exclusive)
                count++
            }
//            binding.tvNumber.text = item.orderId.toString()
//            binding.tvRequiredPrice.text = item.requiredMoney
//            binding.tvGivenPrice.text = item.givenMoney
//            binding.tvGivenDate.text = item.givenDate
        }
    }
}