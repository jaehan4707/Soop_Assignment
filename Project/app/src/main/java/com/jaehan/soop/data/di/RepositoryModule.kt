package com.jaehan.soop.data.di

import com.jaehan.soop.data.repositoryimpl.RepoRepositoryImpl
import com.jaehan.soop.domain.repository.RepoRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    fun bindUserRepository(repository: RepoRepositoryImpl): RepoRepository
}