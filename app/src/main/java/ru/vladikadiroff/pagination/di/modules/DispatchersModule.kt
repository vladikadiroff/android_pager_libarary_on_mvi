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

    private val dispatcherIO = Dispatchers.IO
    private val dispatcherMain = Dispatchers.Main

    @Provides
    @Singleton
    @DispatcherIO
    fun provideDispatcherIO() = dispatcherIO

    @Provides
    @Singleton
    @DispatcherMain
    fun provideDispatcherMain() = dispatcherMain

}