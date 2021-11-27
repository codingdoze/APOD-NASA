package wale.nasa.apod.di

import android.content.Context
import com.goldmedal.hrapp.data.db.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import wale.nasa.apod.data.network.RemoteDataSource
import wale.nasa.apod.data.network.PictureOfTheDayApi
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Singleton
    @Provides
    fun provideUserApi(
        remoteDataSource: RemoteDataSource,
        @ApplicationContext context: Context
    ): PictureOfTheDayApi {
        return remoteDataSource.buildApi(PictureOfTheDayApi::class.java)
    }

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.invoke(context)
    }
}