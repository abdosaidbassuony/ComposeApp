package com.compose_app.ui.presentaion.cateogries

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.compose_app.core.utils.Results
import com.compose_app.domain.entities.Category
import com.compose_app.domain.usecase.GetCategoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor(
    private val getCategoryUseCase: GetCategoryUseCase,
) : ViewModel() {
    private val _categories = mutableStateOf<Results<List<Category>>>(Results.Loading)
    val categories: State<Results<List<Category>>> = _categories
    private val _event = Channel<Event>()
    val event = _event.receiveAsFlow()
    
    init {
        viewModelScope.launch {
            _categories.value = getCategoryUseCase()
        }
    }
    
    fun onCategoryClicked(category: Category) {
        viewModelScope.launch {
            _event.send(Event.NavigateToDetails(category.id))
        }
    }
}

sealed class Event {
    data class NavigateToDetails(val categoryId: Int) : Event()
}
