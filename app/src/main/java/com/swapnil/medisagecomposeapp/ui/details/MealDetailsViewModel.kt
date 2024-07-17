package com.swapnil.medisagecomposeapp.ui.details

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.swapnil.medisagecomposeapp.model.MealsRepository
import com.swapnil.medisagecomposeapp.model.response.MealResponse

class MealDetailsViewModel(private val savedStateHandle: SavedStateHandle
):ViewModel() {
    private val repository: MealsRepository = MealsRepository.getInstance()
    var mealState = mutableStateOf<MealResponse?>(null)
    init {
        val mealId = savedStateHandle.get<String>("meal_category_id")?:""
        mealState.value = repository.getMeal(mealId)
    }
}