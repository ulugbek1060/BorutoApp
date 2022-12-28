package com.android.borutoapp.domain.model

import kotlinx.serialization.Serializable

@Serializable
class ApiResponse(
    val success: Boolean,
    val message: String? = null,
    val prevPage: Int? = null,
    val nextPage: Int? = null,
    val heroes: List<Hero> = emptyList(),
    val lastUpdated: Long? = null
)