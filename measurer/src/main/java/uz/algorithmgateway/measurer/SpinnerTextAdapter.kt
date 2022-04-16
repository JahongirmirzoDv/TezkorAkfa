package uz.algorithmgateway.measurer

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.annotation.RequiresApi
import uz.algorithmgateway.measurer.R
import uz.algorithmgateway.measurer.databinding.ItemSpinnerBinding

class SpinnerTextAdapter(private val context: Context, private val list: List<String>, private val temp:Boolean) :
    BaseAdapter() {
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