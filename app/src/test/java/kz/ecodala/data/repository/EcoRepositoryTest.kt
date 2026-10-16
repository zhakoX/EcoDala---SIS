package kz.ecodala.data.repository

import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class EcoRepositoryTest {

    @Test
    fun loadRecyclingPoint_returnsMatchingPointOrNull() = runBlocking {
        val repository = EcoRepository()

        assertEquals(1, repository.loadRecyclingPoint(1)?.id)
        assertNull(repository.loadRecyclingPoint(-1))
    }

    @Test
    fun toggleFavorite_updatesOnlyKnownPoints() {
        val repository = EcoRepository()

        assertEquals(true, repository.toggleFavorite(1))
        assertTrue(repository.isFavorite(1))
        assertEquals(false, repository.toggleFavorite(1))
        assertFalse(repository.isFavorite(1))
        assertNull(repository.toggleFavorite(-1))
    }

    @Test(expected = EcoRepositoryException::class)
    fun loadHomeData_throwsWhenFailureIsEnabled() {
        runBlocking {
            EcoRepository(shouldFail = true).loadHomeData()
        }
    }
}
