package uz.algorithmgateway.tezkorakfa.presenter.measurer

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

class SpinnerTextAdapter(var context: Context) :
    BaseAdapter() {
    var list: List<String> = emptyList()
    var temp: Boolean = false


    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(position: Int): Any {
        return list[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

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
            binding.containerLayout.setBackgroundColor(context.getColor(R.color.layout_back_color))
            binding.viewSpinner.setBackgroundColor(context.getColor(R.color.layout_light))
        }

        return binding.root

    }
}