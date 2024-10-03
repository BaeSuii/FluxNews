package com.baesuii.fluxnews.domain

import com.baesuii.fluxnews.domain.manager.LocalUserManager
import com.baesuii.fluxnews.domain.use_case.ReadAppEntry
import com.baesuii.fluxnews.domain.use_case.SaveAppEntry
import com.google.common.truth.Truth.assertThat
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class AppEntryUseCasesTest {
    @RelaxedMockK
    private lateinit var localUserManager: LocalUserManager

    private lateinit var readAppEntry: ReadAppEntry
    private lateinit var saveAppEntry: SaveAppEntry

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        readAppEntry = ReadAppEntry(localUserManager)
        saveAppEntry = SaveAppEntry(localUserManager)
    }

    @Test
    fun readAppEntry_returnsCorrectValueFromLocalUserManager() = runTest {
        // Given
        coEvery { localUserManager.readAppEntry() } returns flowOf(true)

        // When
        val result = readAppEntry().first()

        // Then
        assertThat(result).isTrue()
        coVerify { localUserManager.readAppEntry() }
    }

    @Test
    fun saveAppEntry_callsLocalUserManagerToSaveEntry() = runTest {
        // When
        saveAppEntry()

        // Then
        coVerify { localUserManager.saveAppEntry() }
    }
}
