package balitskyivan.presentation.domain.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import balitskyivan.presentation.domain.enums.ErrorTypes

@Entity
data class VkChannelDetails(
    @PrimaryKey
    override val id: Int,
    override val errorTypes: ErrorTypes,
    val comments: Int,
    val likes: Int,
    val subscribed: Int,
    val unsubscribed: Int
) : ChannelDetails