package balitskyivan.presentation.domain.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import balitskyivan.presentation.domain.enums.ErrorTypes
import balitskyivan.presentation.domain.enums.SocialNetworks
import com.facebook.AccessToken

@Entity
data class InstagramAccount(
    @PrimaryKey
    override var id: Long,
    override var error: ErrorTypes,
    override var avatarUrl: String?,
    override var name: String?,
    override var surname: String? = "",
    override var accountType: SocialNetworks,
    override var subscribersCount: Int?,
    override var accessToken: String
) : Account