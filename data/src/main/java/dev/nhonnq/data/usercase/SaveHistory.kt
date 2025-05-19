package dev.nhonnq.data.usercase

import dev.nhonnq.data.entities.SearchHistory
import dev.nhonnq.data.repository.SearchLocalDataSource

/**
 * Use case for saving a search history to the local data source.
 *
 * @property local The local data source for search histories.
 */
class SaveHistory(
    private val local: SearchLocalDataSource
) {
    suspend operator fun invoke(history: SearchHistory) = local.saveHistory(history)
}
