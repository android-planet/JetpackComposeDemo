package com.swapnil.medisagecomposeapp.ui.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.swapnil.medisagecomposeapp.model.response.MealResponse

@Composable
fun MealDetailsScreen(meal: MealResponse?){
    Column {
        Row {
            Card {
                Image(painter = rememberImagePainter(data = meal?.imageUrl,
                    builder = {
                        transformations(CircleCropTransformation())
                    }),contentDescription = "Image Details",
                    modifier = Modifier.size(200.dp)
                        .padding(8.dp)

                )

            }
            Text(text = meal?.name?: "Default Name",
                modifier = Modifier.padding(16.dp)
                    .align(Alignment.CenterVertically))

        }
        Button( modifier = Modifier.padding(16.dp),
            onClick = { /*TODO*/ }) {
            Text(text = "Change State of meal profile picture")
        }
    }

}