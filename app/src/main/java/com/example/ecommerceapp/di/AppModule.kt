package com.example.ecommerceapp.di

import android.content.Context
import com.example.ecommerceapp.repositories.CartRepository
import com.example.ecommerceapp.room.AppDatabase
import com.example.ecommerceapp.room.CartDao
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    // Provide firebase firestore instance
    @Provides
    @Singleton
    fun provideFirebaseFirestore(): FirebaseFirestore{
        return FirebaseFirestore.getInstance()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext
                           applContext: Context):AppDatabase{
        return AppDatabase.getDatabase(applContext)
    }

    @Provides
    fun provideCartDao(appDatabase: AppDatabase): CartDao{
        return appDatabase.cartDao()

    }
    @Provides
    fun provideCartRepository(cartDao: CartDao):
            CartRepository{
        return CartRepository(cartDao)
    }

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = Firebase.auth


}