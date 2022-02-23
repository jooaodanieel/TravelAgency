package com.techandteach.utils

import java.util.UUID

interface Repository<E> {
    fun add(entity: E): E

    fun findById(id: UUID): E?

    fun remove(entity: E): E?
    fun removeById(id: UUID): E?
}