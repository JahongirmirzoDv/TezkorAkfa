package uz.algorithmgateway.tezkorakfa.utils

import android.content.Context
import android.os.CountDownTimer
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.Toast
import androidx.navigation.NavOptions
import uz.algorithmgateway.tezkorakfa.R


object Anim {

    val navOptions = NavOptions.Builder()
        .setEnterAnim(R.anim.enter)
        .setExitAnim(R.anim.exit)
        .setPopEnterAnim(R.anim.pop_enter)
        .setPopExitAnim(R.anim.pop_exit)
        .build()




}