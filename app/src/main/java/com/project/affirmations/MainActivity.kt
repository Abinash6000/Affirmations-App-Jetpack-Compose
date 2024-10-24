package com.project.affirmations

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.project.affirmations.data.Datasource
import com.project.affirmations.model.Affirmation
import com.project.affirmations.ui.theme.AffirmationsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AffirmationsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "list") {
                        composable("list") {
                            AffirmationsApp(navController)
                        }
                        composable("details/{quote}/{author}/{imageId}") { backStackEntry ->
                            val quote = backStackEntry.arguments?.getString("quote")?.toIntOrNull() ?: 0
                            val author = backStackEntry.arguments?.getString("author")?.toIntOrNull() ?: 0
                            val imageId = backStackEntry.arguments?.getString("imageId")?.toIntOrNull() ?: 0
                            AffirmationDetailScreen(quoteId = quote, authorId = author, imageId = imageId)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AffirmationsApp(navController: NavController) {
    AffirmationList(
        affirmationList = Datasource().loadAffirmations(),
        onClick = { affirmation ->
            navController.navigate("details/${affirmation.stringResourceId}/${affirmation.authorResourceId}/${affirmation.imageResourceId}")
        }
    )
}



@Composable
fun AffirmationList(affirmationList: List<Affirmation>, onClick: (Affirmation) -> Unit, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier) {
        items(affirmationList) { affirmation ->
            AffirmationCard(
                affirmation = affirmation,
                modifier = Modifier
                    .padding(12.dp, 8.dp)
                    .clickable { onClick(affirmation) }
            )
        }
    }
}
@Composable
fun AffirmationCard(affirmation: Affirmation, modifier: Modifier = Modifier) {
    Card(modifier = modifier) {
        Column {
            Image(
                painter = painterResource(id = affirmation.imageResourceId),
                contentDescription = stringResource(id = affirmation.stringResourceId),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(194.dp),
                contentScale = ContentScale.Crop
            )
            // Use affirmation's stringResourceId for the text
            Text(
                text = stringResource(id = affirmation.stringResourceId),
                modifier = Modifier.padding(top = 16.dp , bottom = 8.dp , start = 16.dp , end = 16.dp),
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "~ "+stringResource(id = affirmation.authorResourceId),
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth(Alignment.End)
                    .padding(8.dp),
                style = MaterialTheme.typography.titleSmall,
            )
        }
    }
}

@Composable
fun AffirmationDetailScreen(quoteId: Int, authorId: Int, imageId: Int) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = imageId),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
            contentScale = ContentScale.Crop
        )
        Text(
            text = stringResource(id = quoteId),
            fontSize = 24.sp,
            modifier = Modifier.padding(top = 16.dp)
        )
        Text(
            text = "~ "+stringResource(id = authorId),
            fontSize = 18.sp,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

@Preview
@Composable
private fun AffirmationCardPreview() {
    AffirmationCard(Affirmation(R.string.affirmation1, R.drawable.image1, R.string.author1))
}

@Preview(showBackground = true)
@Composable
private fun AffirmationAppPreview() {
    val navController = rememberNavController()
    AffirmationsApp(navController = navController)
}
