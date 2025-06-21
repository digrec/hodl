package com.digrec.hodl.core.data.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Model for cryptocurrency.
 *
 * Created by Dejan Igrec
 */
@Entity
data class Currency(
    @PrimaryKey
    val id: Long,
    val name: String,
    val symbol: String,
)
