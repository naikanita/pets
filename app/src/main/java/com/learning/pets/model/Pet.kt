package com.learning.pets.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author Anita
 * This class is used to store pet value.
 * Each instance of Pet represents a row in a Pet table in the app's database.
 */
@Entity(tableName = "Pet")
data class Pet(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="pet_id")
    var id: Int? = null,
    @ColumnInfo(name = "pet_image")
    var imageUrl: String = "",
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB, name = "image_byte_array")
    var byteArray: ByteArray? = null,
    @ColumnInfo(name = "pet_name")
    var title: String = "",
    @ColumnInfo(name = "pet_description")
    var contentUrl: String = "",
    @ColumnInfo(name = "pet_date")
    var dataAdded: String = ""
)
{
    override fun hashCode(): Int {
        return super.hashCode()
    }
    override fun equals(other: Any?): Boolean {
        return super.equals(other)
    }
}



