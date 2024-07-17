package com.swapnil.medisagecomposeapp.ui.meal

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.swapnil.medisagecomposeapp.model.response.MealResponse

@Composable
fun MealCategoriesScreen(navigationCallback: (String) -> Unit){
    val viewModel:MealsCategoriesViewModel = viewModel()
    val meals = viewModel.mealState.value
    LazyColumn(modifier = Modifier.padding(10.dp)) {
        items(meals){ meal ->
            MealsCategory(meal, navigationCallback)
        }
    }

}
@Composable
fun MealsCategory(meals : MealResponse,navigationCallback: (String) -> Unit){
    var isExpanded by remember { mutableStateOf(false) }
    Card(colors = CardDefaults.cardColors(Color.White),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(2.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
            .clickable {
                navigationCallback(meals.id)
            }) {
        Row(modifier = Modifier.animateContentSize()) {
            Image(painter = rememberAsyncImagePainter(meals.imageUrl), contentDescription = null,
                modifier = Modifier
                    .size(88.dp)
                    .padding(4.dp)
                    .align(Alignment.CenterVertically))

            Column(modifier = Modifier
                .align(Alignment.CenterVertically)
                .fillMaxWidth(0.8f)
                .padding(16.dp)) {
                Text(text = meals.name,
                    style = MaterialTheme.typography.bodyMedium)

                Text(text = meals.description,
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.bodySmall,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = if (isExpanded) 10 else 4
                )
            }
            Icon(imageVector = if(isExpanded)
                Icons.Filled.KeyboardArrowUp
              else
                Icons.Filled.KeyboardArrowDown
                , contentDescription = "Expand Icon",
                modifier = Modifier
                    .padding(16.dp)
                    .align(if (isExpanded)
                        Alignment.Bottom
                    else
                        Alignment.CenterVertically
                    )
                    .clickable { isExpanded =!isExpanded })
        }

    }

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MealCategoriesScreen({ })
}