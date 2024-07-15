package com.swapnil.medisagecomposeapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import dagger.hilt.android.AndroidEntryPoint

var nameList: ArrayList<String> = arrayListOf("Swapnil", "Ram", "Sagar", "Nilesh")

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            /*MedisageComposeAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                  //  Greeting2("Android")
                    SplashScreen()
                }
            }*/
            //GreetingText(name = "world")
            UsersApplication()
            // GreetingList(names = nameList)
        }
    }
}

@Composable
fun UsersApplication(userProfiles: List<UserProfile> = userProfileList) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "user_list") {
        composable("user_list") {
            UsersListScreen(userProfiles, navController)
        }
        composable(
            route = "user_details/{userId}",
            arguments = listOf(navArgument("userId") {
                type = NavType.IntType
            })
        ) { navBackStackEntry ->
            UserDetailsScreen(navBackStackEntry.arguments!!.getInt("userId"), navController)
        }

    }
}

@Composable
fun GreetingList(
    nameList: List<String>,
    buttonClick: () -> Unit,
    textFieldValue: String,
    textFieldValueUpdate: (newName: String) -> Unit
) {

    for (name in nameList) {
        GreetingText(name = name)
    }


    TextField(value = textFieldValue, onValueChange = textFieldValueUpdate)

    Button(onClick = buttonClick) {
        Text(text = "Add a New Name")
    }

}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun UsersListScreen(userProfiles: List<UserProfile>, navController: NavHostController?) {
    //TextFieldButtonState()
    Scaffold(topBar = {
        AppBar(
            title = "Users list",
            icon = Icons.Default.Home
        ) { }
    }, content = { paddingValues ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
        ) {
            LazyColumn {
                items(userProfiles) { userProfile ->
                    UserProfile(userProfile = userProfile) {
                        navController?.navigate("user_details/${userProfile.id}")
                    }
                }
            }
        }
    }
    )

}

@Composable
fun UserProfile(userProfile: UserProfile, clickAction: () -> Unit) {


    Surface(color = Color.LightGray,
        modifier = Modifier
            .wrapContentSize()
            .clickable { clickAction.invoke() }) {
        Card(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Row(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                ProfilePicture(userProfile.pictureUrl, userProfile.status)
                ProfileContent(userProfile.name, userProfile.status)
            }

        }


    }
}

@Composable
fun ProfilePicture(pictureUrl: String, onlineStatus: Boolean) {
    Card(
        shape = CircleShape, border = BorderStroke(
            width = 2.dp,
            if (onlineStatus)
                Color.Green
            else Color.Red
        )
    ) {

        Image(
            painter = rememberImagePainter(
                data = pictureUrl,
                builder = {
                    transformations(CircleCropTransformation())
                },
            ),
            modifier = Modifier.size(72.dp),
            contentDescription = "Profile picture description",
        )
    }


}

@Composable
fun ProfileContent(name: String, onlineStatus: Boolean) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Text(text = name, style = MaterialTheme.typography.bodyLarge)

        Text(
            text = if (onlineStatus)
                "Active Now"
            else
                "Offline Now", style = MaterialTheme.typography.bodyMedium
        )
    }

}

@Composable
fun TextFieldButtonState() {
    val greetingStateList = remember { mutableStateListOf<String>("Swapnil", "Nilesh") }
    val newNameStateContent = remember {
        mutableStateOf("")
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        GreetingList(
            greetingStateList,
            { greetingStateList.add(newNameStateContent.value) },
            newNameStateContent.value,
            { newName -> newNameStateContent.value = newName })
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreviewMainActivity() {
    //GreetingText(name = "world")
    UsersListScreen(userProfiles = userProfileList, null)
    // GreetingList ()
}

@Composable
fun GreetingText(name: String) {
    Text(
        text = " Hello $name!",
        modifier = Modifier
            .background(colorResource(R.color.grd_splash_1))
            .clickable(onClick = { Log.d("Hi", "Click working") }),
        style = TextStyle(
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )

    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun UserDetailsScreen(userId: Int, navController: NavHostController?) {
    val userProfile = userProfileList.first { userProfile -> userId == userProfile.id }
    //TextFieldButtonState()
    Scaffold(
        topBar = {
            AppBar(
                title = "User profile details",
                icon = Icons.Default.ArrowBack
            ) {
                navController?.navigateUp()
            }
        },
        content = { paddingValues ->
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(paddingValues)
            ) {
                Column(
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    ProfilePicture(userProfile.pictureUrl, userProfile.status)
                    ProfileContent(userProfile.name, userProfile.status)
                }
            }
        }
    )


}

@Preview(showBackground = true)
@Composable
fun UserProfileDetailsScreen() {
    //GreetingText(name = "world")
    UserDetailsScreen(userId = 0, null)
    // GreetingList ()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(title: String, icon: ImageVector, iconClickAction: () -> Unit) {
    TopAppBar(
        navigationIcon = {
            Icon(
                icon,
                contentDescription = "",
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .clickable(onClick = { iconClickAction.invoke() })
            )
        },
        title = { Text(title) }
    )
}

/*@Composable
fun SplashScreen() {

    Box (
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.white)),

        contentAlignment = Alignment.Center
    ){
        val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.medisage_splash))
        composition?.let {
            LottieAnimation(composition = it,
                modifier = Modifier.size(350.dp))
        }


    }

}


@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
    MedisageComposeAppTheme {
       // Greeting2("Android")
        SplashScreen()
    }
}*/
