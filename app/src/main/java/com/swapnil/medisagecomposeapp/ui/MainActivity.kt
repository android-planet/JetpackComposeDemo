package com.swapnil.medisagecomposeapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.swapnil.medisagecomposeapp.ui.details.MealDetailsScreen
import com.swapnil.medisagecomposeapp.ui.details.MealDetailsViewModel
import com.swapnil.medisagecomposeapp.ui.meal.MealCategoriesScreen
import com.swapnil.medisagecomposeapp.ui.theme.ui.theme.MedisageComposeAppTheme
import dagger.hilt.android.AndroidEntryPoint

var nameList: ArrayList<String> = arrayListOf("Swapnil", "Ram", "Sagar", "Nilesh")

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

                Surface(color = Color.White) {
                    //GreetingPreview()
                  //  MealCategoriesScreen()
                    FoodiezApp()
                }
        }
    }
}

@Composable
private fun FoodiezApp(){
    val navController = rememberNavController()
    NavHost(navController , startDestination = "destination_meals_list" ) {
        composable(route = "destination_meals_list"){
            MealCategoriesScreen(){ navigationMealId->
                navController.navigate("destination_meals_details/${navigationMealId}")
            }
        }
        composable(
            route = "destination_meals_details/{meal_category_id}",
            arguments = listOf(navArgument("meal_category_id"){
                type = NavType.StringType
            })
        ){
            val viewModel : MealDetailsViewModel = viewModel()
            MealDetailsScreen(viewModel.mealState.value)
        }
    }
}

