package ru.vladikadiroff.pagination.di.modules

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import ru.vladikadiroff.pagination.di.annotations.DispatcherIO
import ru.vladikadiroff.pagination.di.annotations.DispatcherMain
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DispatchersModule {

    @Provides
    @Singleton
    @DispatcherIO
    fun provideDispatcherIO() = Dispatchers.IO

    @Provides
    @Singleton
    @DispatcherMain
    fun provideDispatcherMain() = Dispatchers.Main

}