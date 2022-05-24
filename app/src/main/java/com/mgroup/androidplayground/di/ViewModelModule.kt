package com.mgroup.androidplayground.di

import android.content.Context
import androidx.work.WorkManager
import com.mgroup.androidplayground.model.CurrencyRepository
import com.mgroup.androidplayground.model.CurrencyRepositoryImpl
import com.mgroup.androidplayground.model.CurrencyService
import com.mgroup.androidplayground.ui.general.CoroutinesDispatcherProvider
import com.mgroup.androidplayground.ui.screens.mainscreen.util.MainScreenViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory


@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule {

    @Provides
    fun provideCoroutineDispatcher() : CoroutinesDispatcherProvider {
        return CoroutinesDispatcherProvider()
    }

    @Provides
    fun provideMainViewModel(@ApplicationContext context: Context) : MainScreenViewModel {
        return MainScreenViewModel(
            provideCoroutineDispatcher(),
            provideRepository(context)
            )
    }

    @Provides
    fun provideRepository(@ApplicationContext context: Context) : CurrencyRepository{
        return CurrencyRepositoryImpl(
            provideRetrofit(),
            provideWorkManager(context)
        )
    }

    @Provides
    fun provideWorkManager(@ApplicationContext context : Context) : WorkManager{
        return WorkManager.getInstance(context)
    }

    @Provides
    fun provideRetrofit(): CurrencyService{
        val retrofit = Retrofit.Builder()
            .baseUrl("https://cdn.jsdelivr.net/gh/fawazahmed0/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(CurrencyService::class.java)
    }
}