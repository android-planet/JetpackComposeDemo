package com.swapnil.medisagecomposeapp.model

import com.swapnil.medisagecomposeapp.model.api.MealsWebService
import com.swapnil.medisagecomposeapp.model.response.MealResponse
import com.swapnil.medisagecomposeapp.model.response.MealsCategoriesResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response



class MealsRepository(private val webService: MealsWebService = MealsWebService()){

  private var cashedMeals = listOf<MealResponse>()

   suspend fun getMeals(): MealsCategoriesResponse{
       val response=webService.getMeals()
       cashedMeals = response.categories
       return response
    }

    fun getMeal(id:String): MealResponse? {
      return  cashedMeals.firstOrNull(){
            it.id == id
        }
    }
    companion object{
        @Volatile
        private var instance:MealsRepository ?= null

        fun getInstance() = instance?: synchronized(this){
            instance?: MealsRepository().also { instance = it }
        }
    }
}