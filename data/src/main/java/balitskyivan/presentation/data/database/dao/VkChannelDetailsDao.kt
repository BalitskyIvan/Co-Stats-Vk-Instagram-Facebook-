package balitskyivan.presentation.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update
import balitskyivan.presentation.domain.entities.VkAccountDetails
import balitskyivan.presentation.domain.entities.VkChannelDetails

@Dao
interface VkChannelDetailsDao {

    @Insert
    fun addDetails(channelDetails: VkChannelDetails)

    @Update
    fun updateDetails(channelDetails: VkChannelDetails)

    @Delete
    fun deleteDetails(channelDetails: VkChannelDetails)
}