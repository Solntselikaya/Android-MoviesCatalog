package com.example.movies_catalog

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview(showBackground = true)
@Composable
fun SignUpScreen() {
    DefaultPreview();
}

@Composable
fun DefaultPreview(){
    Image(
        modifier = Modifier
            .fillMaxWidth()
            .padding(56.dp, 106.dp, 106.59.dp, 16.dp)
            .size(147.41.dp, 100.dp),
        painter = painterResource(R.drawable.logo),
        contentDescription = "Logo picture")
}