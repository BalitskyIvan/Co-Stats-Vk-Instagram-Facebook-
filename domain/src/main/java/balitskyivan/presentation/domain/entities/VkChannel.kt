package balitskyivan.presentation.domain.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import balitskyivan.presentation.domain.enums.ErrorTypes
import balitskyivan.presentation.domain.enums.SocialNetworks


@Entity
data class VkChannel(
    @PrimaryKey
    override var id: Int,
    override var error: ErrorTypes,
    override var logoUrl: String?,
    override var name: String?,
    override var type: SocialNetworks,
    override var subscribersCount: Int,
) : Channel