package xyz.hihasan.ledgerlite.feature.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.update
import xyz.hihasan.ledgerlite.core.domain.usecase.SearchTransactionsUseCase
import xyz.hihasan.ledgerlite.core.model.Transaction
import xyz.hihasan.ledgerlite.core.model.TransactionCategory
import xyz.hihasan.ledgerlite.core.model.TransactionFilter
import xyz.hihasan.ledgerlite.core.model.TransactionType
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchTransactions: SearchTransactionsUseCase,
) : ViewModel() {

    private val _filter = MutableStateFlow(TransactionFilter())
    val filter = _filter.asStateFlow()

    val results: Flow<PagingData<Transaction>> = _filter
        .debounce(250)
        .flatMapLatest { searchTransactions(it) }
        .cachedIn(viewModelScope)

    fun onQueryChange(query: String) = _filter.update { it.copy(query = query) }

    fun toggleType(type: TransactionType) = _filter.update {
        it.copy(types = it.types.toggle(type))
    }

    fun toggleCategory(category: TransactionCategory) = _filter.update {
        it.copy(categories = it.categories.toggle(category))
    }

    fun clearFilters() = _filter.update { TransactionFilter(query = it.query) }

    private fun <T> Set<T>.toggle(value: T): Set<T> =
        if (contains(value)) this - value else this + value
}
