package com.example.swordssocks.ui.theme

import androidx.compose.animation.core.*
//import androidx.compose.animation.core.AnimationConstants.Infinite
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.swordssocks.R

@Composable
fun FlashingImage(imageResId: Int, flashDurationMillis: Int = 500) {
    val imagePainter = painterResource(imageResId)
    val transition = rememberInfiniteTransition()

    val opacity by transition.animateFloat(
        initialValue = 1f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = flashDurationMillis),
            repeatMode = RepeatMode.Reverse
        )
    )

    Image(
        painter = imagePainter,
        contentDescription = null,
        modifier = Modifier
            .size(100.dp)
            .graphicsLayer(alpha = opacity)
    )
}

@Preview
@Composable
fun FlashingImagePreview() {
    FlashingImage(imageResId = R.drawable.ic_launcher_foreground)
}
