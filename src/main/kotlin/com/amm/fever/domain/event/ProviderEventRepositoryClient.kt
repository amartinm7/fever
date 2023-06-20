package com.amm.fever.domain.event

interface ProviderEventRepositoryClient : ProviderEventRepositoryClientRead {
}

interface ProviderEventRepositoryClientRead {
    fun findBy(): List<Event>
}