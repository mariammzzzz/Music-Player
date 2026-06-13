package com.mjapa21.musicplayerapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
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
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mjapa21.musicplayerapp.ui.theme.MusicPlayerAppTheme

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

                    LinearProgressIndicator(
                        progress = { 0.5f },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp)

                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "1:30", fontSize = 12.sp, color = Color.Gray)
                        Text(text = "3:00", fontSize = 12.sp, color = Color.Gray)
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
                        }, onClick = {})

                        IconButton(
                            modifier = Modifier.size(70.dp),
                            onClick = { isPlaying = !isPlaying }
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(androidx.compose.foundation.shape.CircleShape)
                                    .background(Color.LightGray),
                                contentAlignment = Alignment.Center
                            ) {


                                AnimatedContent(
                                    targetState = isPlaying,
                                    transitionSpec = {
                                        fadeIn(animationSpec = tween(600)).togetherWith(fadeOut(
                                            animationSpec = tween(600))
                                        )
                                    }
                                ) { isPlaying ->

                                    Icon(
                                        painter = if (isPlaying) painterResource(R.drawable.play) else painterResource(
                                            R.drawable.pause
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
                        }, onClick = {})
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