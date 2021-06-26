package balitskyivan.presentation.domain.entities

import balitskyivan.presentation.domain.enums.ErrorTypes
import balitskyivan.presentation.domain.enums.SocialNetworks

interface AccountDetails {
    var id : Long
    var error: ErrorTypes
    var type : SocialNetworks
}