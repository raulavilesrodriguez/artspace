package com.example.artspace

import android.health.connect.datatypes.ElevationGainedRecord
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.pointerInput
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
import androidx.compose.ui.unit.IntSize
import androidx.compose.foundation.verticalScroll

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ArtSpaceTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    ArtApp(changeValDown, changeValUp)
                }
            }
        }
    }
}

@Composable
fun ArtApp(
    changeValDown: (Int) -> Int,
    changeValUp: (Int) -> Int,
    modifier: Modifier = Modifier
) {
    var currentArt by remember { mutableIntStateOf(1) }
    var zoomed by remember { mutableStateOf(false) }
    var zoomOffset by remember { mutableStateOf(Offset.Zero) }

    val data = dataArt(currentArt)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom,
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(MaterialTheme.colorScheme.surface)
    ) {

        Surface(
            modifier = modifier
                .padding(16.dp)
                .border(BorderStroke(width = 2.dp, color = MaterialTheme.colorScheme.onBackground))
                .shadow(elevation = 8.dp, shape = RectangleShape, clip = false),
            color = MaterialTheme.colorScheme.background,
            onClick = {currentArt = changeValDown(currentArt)}
        ) {
            Image(
                painter = painterResource(id = data[0]),
                contentDescription = stringResource(id = data[3]),
                modifier = modifier
                    .padding(32.dp)
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onPress = { tapOffset ->
                                zoomOffset = if (zoomed) Offset.Zero else
                                    calculateOffset(tapOffset, size)
                                zoomed = !zoomed
                            }
                        )
                    }
                    .graphicsLayer {
                        scaleX = if (zoomed) 4f else 1f
                        scaleY = if (zoomed) 4f else 1f
                        translationX = zoomOffset.x
                        translationY = zoomOffset.y
                    }
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
                text = stringResource(id = data[1]),
                fontSize = 32.sp,
                modifier = modifier

            )
            val textBig = stringResource(id = data[2])
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
                onClick = { currentArt = changeValDown(currentArt) },
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
                onClick = { currentArt = changeValUp(currentArt) },
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
fun dataArt(
    currentArt: Int
): List<Int>  {
    val data = when(currentArt){
        1 -> listOf(R.drawable.halloween, R.string.pic_halloween, R.string.artist_halloween, R.string.desc_halloween)
        2 -> listOf(R.drawable.aigenerated, R.string.pic_woman, R.string.artist_woman, R.string.desc_woman)
        3 -> listOf(R.drawable.christmas, R.string.pic_christmas, R.string.artist_christmas, R.string.desc_christmas)
        4 -> listOf(R.drawable.flowers, R.string.pic_flowers, R.string.artist_flowers, R.string.desc_flowers)
        else -> listOf(R.drawable.halloween, R.string.pic_halloween, R.string.artist_halloween, R.string.desc_halloween)
    }
    return data
}

val changeValUp: (Int) -> Int = { x ->
    val result = when(x){
        1 -> 2
        2 -> 3
        3 -> 4
        else -> 1
    }
    result
}

val changeValDown: (Int) -> Int = {x ->
    val result = when(x){
        4 -> 3
        3 -> 2
        2 -> 1
        else -> 4
    }
    result
}

private fun calculateOffset(tapOffset: Offset, size: IntSize): Offset {
    val offsetX = (-(tapOffset.x - (size.width / 2f)) * 2f)
        .coerceIn(-size.width / 2f, size.width / 2f)
    return Offset(offsetX, 0f)
}

@Preview(showBackground = true)
@Composable
fun ArtPreview() {
    ArtSpaceTheme {
        ArtApp(changeValDown, changeValUp)
    }
}