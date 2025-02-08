package com.jaehan.soop.data.di

import com.jaehan.soop.data.repositoryimpl.RepoRepositoryImpl
import com.jaehan.soop.data.repositoryimpl.UserRepositoryImpl
import com.jaehan.soop.domain.repository.RepoRepository
import com.jaehan.soop.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    fun bindRepoRepository(repository: RepoRepositoryImpl): RepoRepository

    @Binds
    fun bindUserRepository(repository: UserRepositoryImpl): UserRepository
}
