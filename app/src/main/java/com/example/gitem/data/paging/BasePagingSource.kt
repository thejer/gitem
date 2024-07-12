package com.example.gitem.data.paging

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException
import java.io.IOException

private const val GITHUB_STARTING_PAGE_INDEX = 1
private const val NETWORK_PAGE_SIZE = 50

class BasePagingSource<V : Any>(
    private val query: String,
    private val block: suspend (String, Int, Int) -> List<V>
) : PagingSource<Int, V>() {

    override fun getRefreshKey(state: PagingState<Int, V>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, V> {
        val page = params.key ?: GITHUB_STARTING_PAGE_INDEX
        val queryKey = query

        return try {
            val response = block(queryKey, page, params.loadSize)
            val repos = response

            val nextKey = if (repos.isEmpty()) {
                null
            } else {
                page + (params.loadSize / NETWORK_PAGE_SIZE)
            }
            LoadResult.Page(
                data = repos,
                prevKey = if (page == GITHUB_STARTING_PAGE_INDEX) null else page - 1,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }
}

fun <V : Any> createPager(
    queryKey: String,
    enablePlaceholders: Boolean = false,
    block: suspend (String, Int, Int) -> List<V>
): Flow<PagingData<V>> = Pager(
    config = PagingConfig(
        pageSize = NETWORK_PAGE_SIZE,
        enablePlaceholders = enablePlaceholders),
    pagingSourceFactory = { BasePagingSource(queryKey, block) }
).flow