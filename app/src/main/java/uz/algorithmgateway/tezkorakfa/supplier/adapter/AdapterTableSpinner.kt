package uz.algorithmgateway.tezkorakfa.supplier.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.annotation.RequiresApi
import uz.algorithmgateway.tezkorakfa.R
import uz.algorithmgateway.tezkorakfa.databinding.ItemSpinnerBinding

class AdapterTableSpinner(
    private val context: Context,
    private val list: List<String>,
    private val temp: Boolean
) :
    BaseAdapter() {

    @RequiresApi(Build.VERSION_CODES.M)
    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val binding = ItemSpinnerBinding.inflate(LayoutInflater.from(context))
        binding.textViewSpinner.text = list[position]

        /**
        temp -> spinnerni card ichida joylashgan yoki joylashmaganiga qarab,
        uni o`ziga va itemiga backround berish
         */

        if (temp) {
            binding.containerLayout.setBackgroundColor(context.getColor(R.color.color_bg_supplier_view))
            binding.viewSpinner.setBackgroundColor(context.getColor(R.color.color_border_table))
        }
        return binding.root

    }

    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(position: Int): Any {
        return list[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
}