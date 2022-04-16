package uz.algorithmgateway.measurer.ui

import android.os.Bundle
import moxy.MvpAppCompatActivity
import org.koin.android.ext.android.get
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import uz.algorithmgateway.measurer.databinding.ActivityMeasurerBinding


class MeasurerActivity : MvpAppCompatActivity(), MeasurerView {
    private lateinit var binding: ActivityMeasurerBinding

    @InjectPresenter
    lateinit var presenter: MeasurerPresenter

    @ProvidePresenter
    fun providePresenter():MeasurerPresenter = get()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMeasurerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
    }


}