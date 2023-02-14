package net.herasevyan.tictactoe.data

import androidx.room.TypeConverter

class Converters {

    @TypeConverter
    fun listOfIntToString(list: List<Int>) = list.joinToString(",")

    @TypeConverter
    fun stringToListOfInt(str: String) = str.split(',').map { c -> c.toInt() }
}