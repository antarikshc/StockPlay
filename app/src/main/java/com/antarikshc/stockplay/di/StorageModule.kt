package com.antarikshc.stockplay.di

import android.content.Context
import com.antarikshc.stockplay.data.local.StockDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object StorageModule {

    @Singleton
    @Provides
    fun provideStockDatabase(@ApplicationContext context: Context): StockDatabase {
        return StockDatabase.getInstance(context)
    }

}