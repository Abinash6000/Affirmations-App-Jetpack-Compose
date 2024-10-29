package com.project.affirmations.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun AffirmationDetailScreen(navController: NavController, quoteId: Int, authorId: Int, imageId: Int) {
    Scaffold(
        topBar = {
            TopAppBar(navController = navController, showBackButton = true)
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Image(
                        painter = painterResource(id = imageId),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp),
                        contentScale = ContentScale.Crop
                    )
                }
               Text(
                    text = stringResource(id = quoteId),
                    fontSize = 24.sp,
                    modifier = Modifier.padding(top = 16.dp, start = 24.dp, end = 24.dp, bottom = 4.dp)
                )
                Text(
                    text = "~ " + stringResource(id = authorId),
                    fontSize = 18.sp,
                    modifier = Modifier.padding(top = 4.dp, start = 24.dp, end = 24.dp)
                )
            }
        }
    )
}
