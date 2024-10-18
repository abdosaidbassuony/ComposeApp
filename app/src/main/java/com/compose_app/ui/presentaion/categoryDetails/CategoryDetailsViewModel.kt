package com.compose_app.ui.presentaion.categoryDetails

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.compose_app.core.utils.Results
import com.compose_app.domain.entities.Category
import com.compose_app.domain.usecase.GetCategoryDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryDetailsViewModel @Inject constructor(
				savedStateHandle: SavedStateHandle,
				private val getCategoryDetailsUseCase: GetCategoryDetailsUseCase,
) : ViewModel() {
				private val categoryId = savedStateHandle.get<String>("categoryId")
								?: throw IllegalArgumentException("Missing categoryId")
				private val _categories = mutableStateOf<Results<List<Category>>>(Results.Loading)
				val category: State<Results<List<Category>>> = _categories
				private val _event = Channel<Event>()
				val event = _event.receiveAsFlow()
				fun onCategoryClicked(category: Category) {
								viewModelScope.launch {
												_event.send(Event.NavigateToDetails(category.id))
								}
				}
				
				init {
								viewModelScope.launch {
												_categories.value = getCategoryDetailsUseCase(categoryId)
								}
				}
}

sealed class Event {
				data class NavigateToDetails(val categoryId: Int) : Event()
}