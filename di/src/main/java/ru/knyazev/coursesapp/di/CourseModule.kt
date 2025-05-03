package ru.knyazev.coursesapp.di

import android.content.Context
import androidx.room.Room
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import ru.knyazev.coursesapp.data.CourseDao
import ru.knyazev.coursesapp.data.CourseDatabase
import ru.knyazev.coursesapp.data.CourseService
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object CourseModule {
    private const val BASE_URL = "https://drive.usercontent.google.com/"

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): CourseDatabase =
        Room.databaseBuilder(
            context = context,
            klass = CourseDatabase::class.java,
            name = "database-courses"
        ).fallbackToDestructiveMigration(false).build()

    @Provides
    fun provideCategoryDao(courseDatabase: CourseDatabase): CourseDao =
        courseDatabase.courseDao()

    @Provides
    fun provideDispatcherIO(): CoroutineDispatcher {
        return Dispatchers.IO
    }

    @Provides
    fun provideHttpClient(): OkHttpClient {
        val logger = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder().addInterceptor(logger).build()
    }

    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        val contentType: MediaType = "application/json".toMediaType()
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(Json.asConverterFactory(contentType))
            .client(okHttpClient)
            .build()
    }

    @Provides
    fun provideCourseService(retrofit: Retrofit) =
        retrofit.create(CourseService::class.java)
}