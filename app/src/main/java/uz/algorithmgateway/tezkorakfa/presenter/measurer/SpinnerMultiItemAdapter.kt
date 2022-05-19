package uz.algorithmgateway.tezkorakfa.presenter.measurer

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import uz.algorithmgateway.tezkorakfa.data.models.UISpinner
import uz.algorithmgateway.tezkorakfa.databinding.ItemMultiSpinnerBinding

class SpinnerMultiItemAdapter(private val context: Context, private val list: List<UISpinner>) :
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

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val binding = ItemMultiSpinnerBinding.inflate(LayoutInflater.from(context))
        binding.textViewSpinner.text = list[position].title
        binding.imageItemSpinner.setImageResource(list[position].image)
        return binding.root
    }
}