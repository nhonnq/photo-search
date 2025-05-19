package dev.nhonnq.data.usercase

import dev.nhonnq.data.entities.SearchHistory
import dev.nhonnq.data.repository.SearchLocalDataSource
import dev.nhonnq.domain.util.Result

/**
 * Use case for retrieving all search histories from the local data source.
 *
 * @property local The local data source for search histories.
 */
class GetAllHistories(
    private val local: SearchLocalDataSource
) {
    suspend operator fun invoke(): Result<List<SearchHistory>?> = local.getAllHistories()
}
