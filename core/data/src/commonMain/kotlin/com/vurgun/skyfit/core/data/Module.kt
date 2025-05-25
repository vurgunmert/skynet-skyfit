package com.vurgun.skyfit.core.data

import com.vurgun.skyfit.core.data.access.data.repository.AppConfigRepositoryImpl
import com.vurgun.skyfit.core.data.access.data.repository.AuthApiService
import com.vurgun.skyfit.core.data.access.data.repository.AuthRepositoryImpl
import com.vurgun.skyfit.core.data.access.domain.repository.AppConfigRepository
import com.vurgun.skyfit.core.data.access.domain.repository.AuthRepository
import com.vurgun.skyfit.core.data.connect.domain.repository.ChatbotApiUseCase
import com.vurgun.skyfit.core.data.connect.domain.repository.ChatbotRepository
import com.vurgun.skyfit.core.data.onboarding.data.repository.OnboardingRepositoryImpl
import com.vurgun.skyfit.core.data.onboarding.data.service.OnboardingApiService
import com.vurgun.skyfit.core.data.onboarding.domain.repository.OnboardingRepository
import com.vurgun.skyfit.core.data.persona.data.repository.ProfileRepositoryImpl
import com.vurgun.skyfit.core.data.persona.data.repository.SettingsRepositoryImpl
import com.vurgun.skyfit.core.data.persona.data.repository.UserManagerImpl
import com.vurgun.skyfit.core.data.persona.data.repository.UserRepositoryImpl
import com.vurgun.skyfit.core.data.persona.data.service.ProfileApiService
import com.vurgun.skyfit.core.data.persona.data.service.SettingsApiService
import com.vurgun.skyfit.core.data.persona.data.service.UserApiService
import com.vurgun.skyfit.core.data.persona.domain.repository.FacilityRepository
import com.vurgun.skyfit.core.data.persona.domain.repository.MemberRepository
import com.vurgun.skyfit.core.data.persona.domain.repository.ProfileRepository
import com.vurgun.skyfit.core.data.persona.domain.repository.TrainerRepository
import com.vurgun.skyfit.core.data.persona.domain.repository.UserManager
import com.vurgun.skyfit.core.data.persona.domain.repository.UserRepository
import com.vurgun.skyfit.core.data.schedule.data.mapper.LessonSessionItemViewDataMapper
import com.vurgun.skyfit.core.data.schedule.data.repository.CourseRepositoryImpl
import com.vurgun.skyfit.core.data.schedule.data.repository.UserCalendarRepositoryImpl
import com.vurgun.skyfit.core.data.schedule.data.service.CourseApiService
import com.vurgun.skyfit.core.data.schedule.data.service.UserCalendarApiService
import com.vurgun.skyfit.core.data.schedule.domain.repository.CourseRepository
import com.vurgun.skyfit.core.data.schedule.domain.repository.UserCalendarRepository
import com.vurgun.skyfit.core.data.storage.DataStoreStorage
import com.vurgun.skyfit.core.data.storage.Storage
import com.vurgun.skyfit.core.data.storage.TokenManager
import com.vurgun.skyfit.core.data.wellbeing.domain.repository.PostureAnalysisRepository
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

    singleOf(::DataStoreStorage) { bind<Storage>() }

    single { TokenManager(get(), get(named("app-scope-coroutine"))) }

    single<UserApiService> { UserApiService(get()) }
    single<ProfileApiService> { ProfileApiService(get()) }

    single<UserRepository> { UserRepositoryImpl(get(), get(), get(), get()) }

    single<UserManager> { UserManagerImpl(get(named("app-scope-coroutine")), get(), get(), get()) }

    single<ProfileRepository> { ProfileRepositoryImpl(get(), get(), get(), get()) }

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
    single<FacilityRepository> { get<SettingsRepositoryImpl>() }

    single<CourseApiService> { CourseApiService(get()) }
    single<LessonSessionItemViewDataMapper> { LessonSessionItemViewDataMapper() }
    single<CourseRepository> { CourseRepositoryImpl(get(), get(), get()) }
    single { UserCalendarApiService(get()) }
    single<UserCalendarRepository> { UserCalendarRepositoryImpl(get(), get(), get()) }
}

internal expect val platformModule: Module