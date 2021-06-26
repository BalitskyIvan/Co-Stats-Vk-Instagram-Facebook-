package balitskyivan.presentation.domain.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import balitskyivan.presentation.domain.enums.ErrorTypes
import balitskyivan.presentation.domain.enums.SocialNetworks
import com.vk.api.sdk.auth.VKAccessToken

@Entity
data class VkAccount(
    @PrimaryKey
    override var id: Long,
    override var error: ErrorTypes,
    override var avatarUrl: String?,
    override var name: String?,
    override var surname: String?,
    override var accountType: SocialNetworks,
    override var subscribersCount: Int?,
    override var accessToken: String
) : Account