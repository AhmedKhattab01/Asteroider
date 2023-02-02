package com.example.domain.usecase

import com.example.domain.repo.NeoRepository

class GetNeoUseCase(private val neoRepository: NeoRepository) {
    suspend operator fun invoke() = neoRepository.getNeoFromNetwork()
}