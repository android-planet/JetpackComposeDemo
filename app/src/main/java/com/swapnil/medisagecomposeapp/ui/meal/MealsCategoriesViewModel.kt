package com.swapnil.medisagecomposeapp.ui.meal

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.swapnil.medisagecomposeapp.model.MealsRepository
import com.swapnil.medisagecomposeapp.model.response.MealResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MealsCategoriesViewModel(private val repository: MealsRepository = MealsRepository.getInstance()): ViewModel() {

    init {
      viewModelScope.launch(Dispatchers.IO){
            val meals = getMeals()
            mealState.value=meals
        }
    }
    val mealState: MutableState<List<MealResponse>> = mutableStateOf(emptyList<MealResponse>())

    private suspend fun getMeals():List<MealResponse>{
        return repository.getMeals().categories
    }
}