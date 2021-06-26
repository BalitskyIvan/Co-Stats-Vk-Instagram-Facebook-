package balitskyivan.presentation.domain.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import balitskyivan.presentation.domain.enums.ErrorTypes
import balitskyivan.presentation.domain.enums.SocialNetworks

@Entity
data class InstagramAccountDetails(
    @PrimaryKey
    override var id: Long,
    override var error: ErrorTypes,
    var subscribersCount: Int,
    var followsCount: Int,
    var profileViews: Int,
    override var type: SocialNetworks = SocialNetworks.INSTAGRAM_TYPE
) : AccountDetails