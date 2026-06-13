package com.mjapa21.musicplayerapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mjapa21.musicplayerapp.ui.theme.MusicPlayerAppTheme
import com.mjapa21.musicplayerapp.utils.toPlaybackTimeString
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MusicPlayerAppTheme {
                MusicPlayerScreen(
                    song = "Touch", artist = "Daft Punk"
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MusicPlayerScreen(song: String, artist: String) {
    var isPlaying by remember { mutableStateOf(true) }
    val songDuration = 180f
    var songProgress by remember {
        mutableFloatStateOf(0f)
    }

    LaunchedEffect(isPlaying) { //we need to listen to isPlaying state changes, so we can start or stop the song progress simulation
        //updating songProgress in every second when isPlaying is true
        //and when reaching the end of the song, isPlaying is automatically set to false
        while (isPlaying) {
            if (songProgress + 1f >= songDuration) {
                songProgress = songDuration
                isPlaying = false
                break
            }
            delay(1000)
            songProgress += 1f
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(), topBar = {
            TopAppBar(title = {
                Box(
                    modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center
                ) { Text(text = "Now Playing", fontSize = 16.sp) }
            }, navigationIcon = {
                IconButton(content = {
                    Icon(
                        painter = painterResource(R.drawable.outline_arrow_downward_24),
                        contentDescription = null
                    )
                }, onClick = { /* Handle back navigation */ })
            }, actions = {

                IconButton(content = {
                    Icon(
                        painter = painterResource(R.drawable.three_dots_image),
                        modifier = Modifier.size(24.dp),
                        contentDescription = null
                    )
                }, onClick = { /* Handle back navigation */ })


            })
        }) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    modifier = Modifier
                        .padding(horizontal = 28.dp)
                        .clip(RoundedCornerShape(24.dp)),
                    painter = painterResource(R.drawable.daft_punk_ram),
                    contentDescription = null
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 34.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier.padding(
                                vertical = 16.dp
                            ), horizontalAlignment = Alignment.Start
                        ) {
                            Text(text = song, fontSize = 24.sp)
                            Text(text = artist, fontSize = 16.sp, color = Color.Gray)
                        }
                        IconButton(content = {
                            Icon(
                                painter = painterResource(R.drawable.outline_favorite),
                                contentDescription = null,
                                modifier = Modifier.size(24.dp)
                            )
                        }, onClick = { /* Handle back navigation */ })
                    }


                    Slider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                        value = songProgress,
                        onValueChange = { newValue -> songProgress = newValue },
                        valueRange = 0f..songDuration,
                        thumb = {
                            Icon(
                                painter = painterResource(R.drawable.record_icon_thumb),
                                contentDescription = null,
                                modifier = Modifier.size(14.dp)
                            )
                        },
                        track = { sliderState ->
                            SliderDefaults.Track(
                                sliderState = sliderState,
                                drawStopIndicator = null //needed this to remove the default dot at the end
                            )
                        }
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = songProgress.toInt().toPlaybackTimeString(),
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                        Text(
                            text = songDuration.toInt().toPlaybackTimeString(),
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                    }


                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(content = {
                            Icon(
                                painter = painterResource(R.drawable.back_play),
                                contentDescription = null,
                                modifier = Modifier.size(32.dp)
                            )
                        }, onClick = { songProgress = 0f })


                        val scale by animateFloatAsState(
                            targetValue = if (isPlaying) 0.8f else 1f,
                            animationSpec = spring(
                                dampingRatio = 0.3f,
                                stiffness = Spring.StiffnessMediumLow
                            ),
                            label = "playPauseScale"
                        )

                        IconButton(
                            modifier = Modifier.size(100.dp),
                            onClick = { isPlaying = !isPlaying }
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .graphicsLayer( //NOTE: when I didn't use graphicsLayer and animated button size, other buttons in the row moved, and it looked weird,
                                        // but with graphicsLayer only the play/pause button is scaled and other buttons remain in the same exact place
                                        // ALSO the recomposition count is reduced with this approach compared to animating the button size between two values
                                        scaleX = scale,
                                        scaleY = scale
                                    )
                                    .clip(androidx.compose.foundation.shape.CircleShape)
                                    .background(Color.LightGray),
                                contentAlignment = Alignment.Center
                            ) {


                                AnimatedContent(
                                    targetState = isPlaying,
                                    transitionSpec = {
                                        fadeIn(animationSpec = tween(600)).togetherWith(
                                            fadeOut(
                                                animationSpec = tween(600)
                                            )
                                        )
                                    }
                                ) { isPlaying ->

                                    Icon(
                                        painter = if (isPlaying) painterResource(R.drawable.pause) else painterResource(
                                            R.drawable.play
                                        ),
                                        contentDescription = null,
                                        modifier = Modifier.size(36.dp),
                                        tint = Color.Unspecified
                                    )
                                }
                            }
                        }

                        IconButton(content = {
                            Icon(
                                painter = painterResource(R.drawable.forward_play),
                                contentDescription = null,
                                modifier = Modifier.size(32.dp)
                            )
                        }, onClick = { songProgress = songDuration })
                    }

                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MusicPlayerScreenPreview() {
    MusicPlayerAppTheme {
        MusicPlayerScreen("Touch", "Daft Punk")
    }
}