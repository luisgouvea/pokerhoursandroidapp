package com.example.baseandroidapp.core.common.mapper

interface DomainMapper<in T, out Model> {
    fun toDomain(from: T): Model
    fun toDomain(from: List<T>): List<Model> = from.map { it -> toDomain(it) }
}