package com.learning.pets.model

/**
 * @author Anita
 * This class is used to working hours data
 */
data class WorkingHours(
    val startDay: Char,
    val endDay: Char,
    val startTime: Long,
    val endTime: Long
)