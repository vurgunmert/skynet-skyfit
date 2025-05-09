package com.vurgun.skyfit.core.data

import com.vurgun.skyfit.core.data.domain.repository.AppConfigRepository
import com.vurgun.skyfit.core.data.domain.repository.AuthRepository
import com.vurgun.skyfit.core.data.domain.repository.ChatbotApiUseCase
import com.vurgun.skyfit.core.data.domain.repository.MemberRepository
import com.vurgun.skyfit.core.data.domain.repository.ProfileRepository
import com.vurgun.skyfit.core.data.domain.repository.TrainerRepository
import com.vurgun.skyfit.core.data.domain.repository.UserManager
import com.vurgun.skyfit.core.data.domain.repository.UserRepository
import com.vurgun.skyfit.core.data.onboarding.OnboardingApiService
import com.vurgun.skyfit.core.data.onboarding.OnboardingRepository
import com.vurgun.skyfit.core.data.onboarding.OnboardingRepositoryImpl
import com.vurgun.skyfit.core.data.repository.AppConfigRepositoryImpl
import com.vurgun.skyfit.core.data.repository.AuthRepositoryImpl
import com.vurgun.skyfit.core.data.repository.ChatbotRepository
import com.vurgun.skyfit.core.data.repository.PostureAnalysisRepository
import com.vurgun.skyfit.core.data.repository.ProfileRepositoryImpl
import com.vurgun.skyfit.core.data.repository.SettingsApiService
import com.vurgun.skyfit.core.data.repository.SettingsRepositoryImpl
import com.vurgun.skyfit.core.data.repository.UserManagerImpl
import com.vurgun.skyfit.core.data.repository.UserRepositoryImpl
import com.vurgun.skyfit.core.data.schedule.CourseApiService
import com.vurgun.skyfit.core.data.schedule.domain.repository.CourseRepository
import com.vurgun.skyfit.core.data.schedule.mapper.LessonSessionItemViewDataMapper
import com.vurgun.skyfit.core.data.schedule.repository.CourseRepositoryImpl
import com.vurgun.skyfit.core.data.service.AuthApiService
import com.vurgun.skyfit.core.data.service.ProfileApiService
import com.vurgun.skyfit.core.data.service.UserApiService
import com.vurgun.skyfit.core.data.storage.DataStoreStorage
import com.vurgun.skyfit.core.data.storage.Storage
import com.vurgun.skyfit.core.data.storage.TokenManager
import com.vurgun.skyfit.core.network.dataNetworkModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.core.module.Module
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

val dataCoreModule = module {
    includes(platformModule, dataNetworkModule)

    single(named("app-scope-coroutine")) {
        CoroutineScope(SupervisorJob() + Dispatchers.Default)
    }

    singleOf(::DataStoreStorage) {
        bind<Storage>()
    }

    single {
        TokenManager(
            storage = get(),
            scope = get(named("app-scope-coroutine"))
        )
    }

    single<UserApiService> { UserApiService(get()) }
    single<ProfileApiService> { ProfileApiService(get()) }

    single<UserRepository> {
        UserRepositoryImpl(
            apiService = get(),
            dispatchers = get(),
            storage = get(),
            tokenManager = get()
        )
    }

    single<UserManager> {
        UserManagerImpl(
            appScope = get(named("app-scope-coroutine")),
            storage = get(),
            repository = get(),
            tokenManager = get()
        )
    }

    single<ProfileRepository> {
        ProfileRepositoryImpl(
            apiService = get(),
            dispatchers = get(),
            tokenManager = get(),
            remoteImageDataSource = get()
        )
    }

    single { AuthApiService(get()) }
    single<AuthRepository> { AuthRepositoryImpl(get(), get(), get(), get()) }

    single<AppConfigRepository> { AppConfigRepositoryImpl() }
    single { PostureAnalysisRepository() }
    single<ChatbotApiUseCase> { ChatbotRepository() }
    single { OnboardingApiService(get()) }
    single<OnboardingRepository> { OnboardingRepositoryImpl(get(), get(), get()) }

    single { SettingsApiService(get()) }
    single { SettingsRepositoryImpl(get(), get(), get()) }

    single<MemberRepository> { get<SettingsRepositoryImpl>() }
    single<TrainerRepository> { get<SettingsRepositoryImpl>() }

    single<CourseApiService> { CourseApiService(get()) }
    single<LessonSessionItemViewDataMapper> { LessonSessionItemViewDataMapper() }
    single<CourseRepository> { CourseRepositoryImpl(get(), get(), get()) }
}

internal expect val platformModule: Module