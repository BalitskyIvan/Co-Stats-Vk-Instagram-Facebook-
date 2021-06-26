package balitskyivan.presentation.domain.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import balitskyivan.presentation.domain.enums.ErrorTypes
import balitskyivan.presentation.domain.enums.SocialNetworks

@Entity
data class VkAccountDetails(
    @PrimaryKey
    override var id: Long,
    override var error: ErrorTypes,
    var subscribersCount: Int,
    var friendsCount: Int,
    var nowOnline: Int,
    override var type: SocialNetworks = SocialNetworks.VK_TYPE
) : AccountDetails