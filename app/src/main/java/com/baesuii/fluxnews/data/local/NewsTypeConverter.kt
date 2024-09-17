package com.baesuii.fluxnews.data.local

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.baesuii.fluxnews.domain.model.Source

@ProvidedTypeConverter
class NewsTypeConverter {

    @TypeConverter
    fun sourceToString(source: Source): String {
        return "${source.name},${source.id}"
    }

    @TypeConverter
    fun stringToSource(source: String): Source {
        return source.split(",").let { sourceArray ->
            Source(sourceArray[0], sourceArray[1])
        }
    }

}