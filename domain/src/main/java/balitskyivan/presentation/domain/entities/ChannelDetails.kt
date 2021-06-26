package balitskyivan.presentation.domain.entities

import balitskyivan.presentation.domain.enums.ErrorTypes

interface ChannelDetails {
    val id: Int
    val errorTypes: ErrorTypes
}