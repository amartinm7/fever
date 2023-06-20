package com.amm.fever.infrastructure.services

import java.util.UUID

class UuidService {
    fun randomUUID(): UUID {
        return UUID.randomUUID()
    }

    fun fromString(uuid: String?): UUID {
        return UUID.fromString(uuid)
    }
}
