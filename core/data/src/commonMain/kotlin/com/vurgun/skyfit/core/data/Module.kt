package com.vurgun.skyfit.core.data

import com.vurgun.skyfit.core.data.storage.DataStoreStorage
import com.vurgun.skyfit.core.data.storage.Storage
import com.vurgun.skyfit.core.data.storage.TokenManager
import com.vurgun.skyfit.core.data.v1.data.account.repository.AccountManagerImpl
import com.vurgun.skyfit.core.data.v1.data.account.repository.AccountRepositoryImpl
import com.vurgun.skyfit.core.data.v1.data.account.service.AccountApiService
import com.vurgun.skyfit.core.data.v1.data.auth.repository.AuthRepositoryImpl
import com.vurgun.skyfit.core.data.v1.data.auth.service.AuthApiService
import com.vurgun.skyfit.core.data.v1.data.chatbot.ChatbotRepositoryImpl
import com.vurgun.skyfit.core.data.v1.data.config.repository.AppConfigRepositoryImpl
import com.vurgun.skyfit.core.data.v1.data.explore.ExploreRepositoryImpl
import com.vurgun.skyfit.core.data.v1.data.explore.service.ExploreApiService
import com.vurgun.skyfit.core.data.v1.data.facility.repository.FacilityRepositoryImpl
import com.vurgun.skyfit.core.data.v1.data.facility.service.FacilityApiService
import com.vurgun.skyfit.core.data.v1.data.lesson.mapper.LessonSessionItemViewDataMapper
import com.vurgun.skyfit.core.data.v1.data.lesson.repository.LessonRepositoryImpl
import com.vurgun.skyfit.core.data.v1.data.lesson.service.LessonApiService
import com.vurgun.skyfit.core.data.v1.data.trainer.TrainerApiService
import com.vurgun.skyfit.core.data.v1.data.trainer.repository.TrainerRepositoryImpl
import com.vurgun.skyfit.core.data.v1.data.user.repository.UserRepositoryImpl
import com.vurgun.skyfit.core.data.v1.data.user.service.UserApiService
import com.vurgun.skyfit.core.data.v1.data.workout.repository.WorkoutRepositoryImpl
import com.vurgun.skyfit.core.data.v1.data.workout.service.WorkoutApiService
import com.vurgun.skyfit.core.data.v1.domain.account.manager.ActiveAccountManager
import com.vurgun.skyfit.core.data.v1.domain.account.repository.AccountRepository
import com.vurgun.skyfit.core.data.v1.domain.auth.repository.AuthRepository
import com.vurgun.skyfit.core.data.v1.domain.chatbot.ChatbotApiUseCase
import com.vurgun.skyfit.core.data.v1.domain.config.AppConfigRepository
import com.vurgun.skyfit.core.data.v1.domain.explore.ExploreRepository
import com.vurgun.skyfit.core.data.v1.domain.facility.repository.FacilityRepository
import com.vurgun.skyfit.core.data.v1.domain.lesson.repository.LessonRepository
import com.vurgun.skyfit.core.data.v1.domain.posture.repository.PostureAnalysisRepository
import com.vurgun.skyfit.core.data.v1.domain.trainer.repository.TrainerRepository
import com.vurgun.skyfit.core.data.v1.domain.user.repository.UserRepository
import com.vurgun.skyfit.core.data.v1.domain.workout.repository.WorkoutRepository
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

    //Core
    single(named("app-scope-coroutine")) {
        CoroutineScope(SupervisorJob() + Dispatchers.Default)
    }
    singleOf(::DataStoreStorage) { bind<Storage>() }
    single { TokenManager(get(), get(named("app-scope-coroutine"))) }

    //Config
    single<AppConfigRepository> { AppConfigRepositoryImpl() }

    //Auth
    single { AuthApiService(get()) }
    single<AuthRepository> { AuthRepositoryImpl(get(), get(), get(), get()) }

    //Account
    single<AccountApiService> { AccountApiService(get()) }
    single<AccountRepository> { AccountRepositoryImpl(get(), get(), get(), get()) }
    single<ActiveAccountManager> { AccountManagerImpl(get(named("app-scope-coroutine")), get(), get(), get()) }

    //User
    single { UserApiService(get()) }
    single<UserRepository> { UserRepositoryImpl(get(), get(), get()) }

    //Trainer
    single { TrainerApiService(get()) }
    single<TrainerRepository> { TrainerRepositoryImpl(get(), get(), get()) }

    //Facility
    single<FacilityApiService> { FacilityApiService(get()) }
    single<FacilityRepository> { FacilityRepositoryImpl(get(), get(), get()) }

    //Workout
    single<WorkoutApiService> { WorkoutApiService(get()) }
    single<WorkoutRepository> { WorkoutRepositoryImpl(get(), get(), get()) }

    //Lesson
    single<LessonApiService> { LessonApiService(get()) }
    single<LessonRepository> { LessonRepositoryImpl(get(), get(), get()) }
    single<LessonSessionItemViewDataMapper> { LessonSessionItemViewDataMapper() }

    //Posture Analysis
    single { PostureAnalysisRepository() }

    //Chatbot
    single<ChatbotApiUseCase> { ChatbotRepositoryImpl() }

    //Explore
    single<ExploreApiService> { ExploreApiService(get()) }
    single<ExploreRepository> { ExploreRepositoryImpl(get(), get(), get()) }
}

internal expect val platformModule: Module