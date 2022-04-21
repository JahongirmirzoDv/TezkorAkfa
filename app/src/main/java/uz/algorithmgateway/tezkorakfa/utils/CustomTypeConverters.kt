package uz.algorithmgateway.tezkorakfa.utils

import com.google.gson.Gson

object CustomTypeConverters {
    var gson = Gson()

//    @TypeConverter
//    fun foodItemToString(foodItems: List<Data>): String {
//        return gson.toJson(foodItems)
//    }
//
//    @TypeConverter
//    fun stringToFoodItem(data: String): List<Data> {
//        val listType = object : TypeToken<List<Data>>() {
//        }.type
//        return gson.fromJson(data, listType)
//    }
}