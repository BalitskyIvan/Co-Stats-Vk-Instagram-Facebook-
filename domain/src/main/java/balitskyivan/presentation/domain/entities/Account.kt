package balitskyivan.presentation.domain.entities

import balitskyivan.presentation.domain.enums.ErrorTypes
import balitskyivan.presentation.domain.enums.SocialNetworks

interface Account {
    var id: Long
    var error: ErrorTypes
    var avatarUrl: String?
    var name: String?
    var surname: String?
    var accountType: SocialNetworks
    var subscribersCount: Int?
    var accessToken: String
}