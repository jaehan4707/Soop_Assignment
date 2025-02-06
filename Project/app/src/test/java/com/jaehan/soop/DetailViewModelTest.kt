package com.jaehan.soop

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.jaehan.soop.domain.model.ApiResponse
import com.jaehan.soop.domain.model.Repo
import com.jaehan.soop.domain.model.User
import com.jaehan.soop.domain.repository.RepoRepository
import com.jaehan.soop.domain.repository.UserRepository
import com.jaehan.soop.ui.screen.detail.DetailViewModel
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

class DetailViewModelTest {
    private lateinit var viewModel: DetailViewModel
    private lateinit var savedStateHandle: SavedStateHandle
    private val repoRepository: RepoRepository = mockk()
    private val userRepository: UserRepository = mockk()

    private val fakeRepo = Repo(
        repositoryName = "jaehanRepo",
        userName = "jaehan4707",
        starCount = 100,
        watchers = 50,
        forks = 10,
        description = "Description",
        userProfileImage = "profileImageUrl",
        topics = listOf("topic1", "topic2")
    )

    private val fakeUser = User(
        userName = "jaehan4707",
        followers = 100,
        following = 50,
        repositoryCount = 10,
        bio = "Bio",
        userProfileImage = "profileImageUrl"
    )

    @Before
    fun setUp() {
        savedStateHandle =
            SavedStateHandle(mapOf("owner" to "jaehan4707", "repositoryName" to "jaehanRepo"))
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun finish() {
        Dispatchers.resetMain()
    }

    @Test
    fun `사용자의 레포지토리 정보를 조회할 수 잇다`() = runTest {
        // given
        coEvery {
            repoRepository.getRepositoryInfo(
                owner = fakeRepo.userName,
                repositoryName = fakeRepo.repositoryName,
            )
        } returns flowOf(ApiResponse.Success(data = fakeRepo))
        viewModel = DetailViewModel(repoRepository, userRepository, savedStateHandle)

        //when
        viewModel.getRepositoryInfo(
            owner = fakeRepo.userName,
            repositoryName = fakeRepo.repositoryName
        )

        //then
        viewModel.uiState.test {
            val state = awaitItem()
            assertFalse(state.isLoading)
            assertEquals(fakeRepo.userName, state.userName)
            assertEquals(fakeRepo.repositoryName, state.repositoryName)
            assertEquals(fakeRepo.starCount, state.starCount)
            assertEquals(fakeRepo.watchers, state.watchers)
            assertEquals(fakeRepo.forks, state.forks)
            assertEquals(fakeRepo.description, state.description)
            assertEquals(fakeRepo.userProfileImage, state.userProfileImage)
            assertEquals(fakeRepo.topics, state.topics)
        }
    }

    @Test
    fun `사용자의 상세 정보를 조회할 수 있다`() = runTest {
        //given
        val languages = listOf("kotlin", "java")
        coEvery { userRepository.getUserInfo(fakeUser.userName) } returns flowOf(
            ApiResponse.Success(fakeUser)
        )
        coEvery { userRepository.getUserRepositories(fakeUser.userName) } returns flowOf(
            ApiResponse.Success(languages)
        )

        coEvery {
            repoRepository.getRepositoryInfo(
                fakeRepo.userName,
                fakeRepo.repositoryName
            )
        } returns flowOf()
        viewModel = DetailViewModel(repoRepository, userRepository, savedStateHandle)

        //when
        viewModel.getUserInfoAndRepositories(fakeUser.userName)

        //then
        viewModel.bottomDetailUiState.test {
            val state = awaitItem()
            assertFalse(state.isLoading)
            assertEquals(fakeUser.followers, state.followers)
            assertEquals(fakeUser.following, state.following)
            assertEquals(fakeUser.repositoryCount, state.repositoryCount)
            assertEquals(fakeUser.bio, state.bio)
            assertEquals(fakeUser.userName, state.userName)
            assertEquals(fakeUser.userProfileImage, state.userProfileImage)
            assertEquals(languages, state.language)
        }
    }
}