package com.techandteach.curatorship.application.dtos

import kotlinx.serialization.Serializable

@Serializable
data class CreateAirlineDTO(
    val name: String
)