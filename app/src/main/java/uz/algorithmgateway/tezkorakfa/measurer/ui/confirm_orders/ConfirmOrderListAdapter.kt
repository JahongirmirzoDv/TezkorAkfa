package uz.algorithmgateway.tezkorakfa.measurer.ui.confirm_orders

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import uz.algorithmgateway.tezkorakfa.core.util.layoutInflater
import uz.algorithmgateway.tezkorakfa.data.models.UiConfirmOrder
import uz.algorithmgateway.tezkorakfa.databinding.ItemConfirmBinding

class ConfirmOrderListAdapter(
    var list: List<UiConfirmOrder>,
    private val onAcceptClick: (uiOrder: UiConfirmOrder) -> Unit
) :
    RecyclerView.Adapter<ConfirmOrderListAdapter.VH>() {

    inner class VH(private val binding: ItemConfirmBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun onBind(model: UiConfirmOrder) {
            itemView.apply {
                binding.imageStatus.setImageResource(model.status)
                binding.btnAccept.isVisible = model.btnStatus
            }
            binding.btnAccept.setOnClickListener {
                onAcceptClick(model)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(
            ItemConfirmBinding.inflate(parent.layoutInflater, parent, false)
        )
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int = list.size

}