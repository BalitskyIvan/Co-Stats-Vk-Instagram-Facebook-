package balitskyivan.presentation.domain.entities

import balitskyivan.presentation.domain.enums.ErrorTypes
import balitskyivan.presentation.domain.enums.SocialNetworks

interface Channel {
    var id : Int
    var error: ErrorTypes
    var logoUrl : String?
    var name : String?
    var type : SocialNetworks
    var subscribersCount : Int
}