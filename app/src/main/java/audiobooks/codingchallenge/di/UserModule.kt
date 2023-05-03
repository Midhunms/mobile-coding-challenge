package audiobooks.codingchallenge.di

import audiobooks.codingchallenge.data.network.source.UserDataSource
import audiobooks.codingchallenge.data.network.source.UserDataSourceImpl
import audiobooks.codingchallenge.data.repository.UserRepository
import audiobooks.codingchallenge.data.repository.UserRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
annotation class UsersRepository

@InstallIn(SingletonComponent::class)
@Module
abstract class UserModule {

    @Binds
    @Singleton
    abstract fun bindUserRepository(impl: UserRepositoryImpl): UserRepository

    @Binds
    @Singleton
    abstract fun bindUserDataSource(impl: UserDataSourceImpl): UserDataSource
}