package com.rectangle.cepuonline

import android.app.Application
import com.rectangle.cepuonline.data.db.AppDatabase
import com.rectangle.cepuonline.data.network.MyApi
import com.rectangle.cepuonline.data.network.NetworkConnectionInterceptor
import com.rectangle.cepuonline.data.preferences.PreferenceProvider
import com.rectangle.cepuonline.data.repositories.UserRepository
import com.rectangle.cepuonline.ui.auth.AuthViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class CepuOnline : Application(), KodeinAware {
    override val kodein = Kodein.lazy {
        import(androidXModule(this@CepuOnline))

        bind() from singleton { NetworkConnectionInterceptor(instance()) }
        bind() from singleton { AppDatabase(instance()) }
        bind() from singleton { MyApi(instance()) }
        bind() from singleton { UserRepository(instance(), instance()) }
        bind() from singleton { PreferenceProvider(instance()) }
//        bind() from singleton { QuotesRepository(instance(),instance(),instance()) }
        bind() from provider { AuthViewModelFactory(instance()) }
//        bind() from provider { ProfileViewModelFactory(instance()) }
//        bind() from provider {
//            QuotesViewModelFactory(
//                instance()
//            )
//        }
    }
}