package es.carmenapps.stupidsimpleapp.di
import es.carmenapps.stupidsimpleapp.data.remote.RemoteService

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RemoteModule {

  private const val BASE_INNOCV_USERS = "https://hello-world.innocv.com/api/"

  @Singleton
  @Provides fun provideRemoteService(): RemoteService {
    val retrofit = Retrofit.Builder().baseUrl(BASE_INNOCV_USERS)
      .addConverterFactory(GsonConverterFactory.create())
      .build()
    return retrofit.create(RemoteService::class.java)
  }
}