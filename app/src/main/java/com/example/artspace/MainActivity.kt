package com.example.artspace

import android.health.connect.datatypes.ElevationGainedRecord
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LocalAbsoluteTonalElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.artspace.ui.theme.ArtSpaceTheme
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ArtSpaceTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    ArtApp()
                }
            }
        }
    }
}

@Composable
fun ArtApp(modifier: Modifier = Modifier) {
    var currentArt by remember { mutableIntStateOf(1) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom,
        modifier = Modifier
            .fillMaxSize()
    ) {

        Surface(
            modifier = modifier
                .padding(16.dp)
                .border(BorderStroke(width = 2.dp, color = MaterialTheme.colorScheme.onBackground))
                .shadow(elevation = 8.dp, shape = RectangleShape, clip = false),
            color = MaterialTheme.colorScheme.background,

        ) {
            Image(
                painter = painterResource(id = R.drawable.halloween),
                contentDescription = null,
                modifier = modifier
                    .padding(32.dp)
            )
        }
        Spacer(modifier = modifier.padding(16.dp))

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .background(MaterialTheme.colorScheme.onPrimary)
                .padding(16.dp)
        ){
            Text(
                text = stringResource(id = R.string.pic_halloween),
                fontSize = 32.sp,
                modifier = modifier

            )
            val textBig = stringResource(id = R.string.artist_halloween)
            val index1 = textBig.indexOf(",")
            val substring1 = textBig.substring(0, index1+1)
            val long: Int = textBig.length
            val substring2 = textBig.substring(index1+1, long)
            Text(
                text = buildAnnotatedString {
                                            withStyle(
                                                style = SpanStyle(fontWeight = FontWeight.Bold)
                                            ){
                                                append(substring1)
                                            }
                    append(substring2)
                },
                //fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic,
                fontSize = 24.sp,
                modifier = modifier
                    .padding(bottom = 4.dp)
            )
        }
        Spacer(modifier = modifier.padding(32.dp))
        Row(
            modifier = modifier
                .fillMaxWidth()
                .size(84.dp)
                .padding(top = 32.dp, bottom = 16.dp)
                .align(alignment = Alignment.CenterHorizontally),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = { /*TODO*/ },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                modifier = modifier
                    .size(width = 140.dp, height = 60.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.btn_previous),
                    fontSize = 18.sp
                )
            }
            Spacer(modifier = modifier.padding(32.dp))
            Button(
                onClick = { /*TODO*/ },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                modifier = modifier
                    .size(width = 140.dp, height = 60.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.btn_next),
                    fontSize = 18.sp
                )
            }
        }

    }

}

@Composable
fun textArt(

){

}

@Preview(showBackground = true)
@Composable
fun ArtPreview() {
    ArtSpaceTheme {
        ArtApp()
    }
}