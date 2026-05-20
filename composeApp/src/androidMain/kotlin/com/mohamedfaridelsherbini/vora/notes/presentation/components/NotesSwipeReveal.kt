package com.mohamedfaridelsherbini.vora.notes.presentation.components

import androidx.compose.animation.core.animate
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DeleteOutline
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mohamedfaridelsherbini.vora.presentation.theme.VoraColors
import com.mohamedfaridelsherbini.vora.presentation.theme.voraTypography
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
private fun ActionBlock(
    text: String,
    icon: ImageVector,
    backgroundColor: Color,
    onClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .width(74.dp)
            .background(backgroundColor, RoundedCornerShape(18.dp))
            .clickable(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Icon(icon, contentDescription = null, tint = Color.White)
        Spacer(Modifier.size(4.dp))
        Text(
            text = text,
            color = Color.White,
            style = voraTypography().bodySmall.copy(
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
            ),
        )
    }
}

@Composable
internal fun SwipeRevealItem(
    onRename: () -> Unit,
    onDelete: () -> Unit,
    content: @Composable () -> Unit,
) {
    val density = LocalDensity.current
    val actionWidthPx = with(density) { 164.dp.toPx() }
    var offsetX by remember { mutableFloatStateOf(0f) }
    var animationJob by remember { mutableStateOf<Job?>(null) }
    val scope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .pointerInput(Unit) {
                detectHorizontalDragGestures(
                    onDragStart = { _ ->
                        animationJob?.cancel()
                    },
                    onDragEnd = {
                        animationJob = scope.launch {
                            val target = if (offsetX < -actionWidthPx / 2) -actionWidthPx else 0f
                            animate(
                                initialValue = offsetX,
                                targetValue = target,
                                animationSpec = tween(300)
                            ) { value, _ ->
                                offsetX = value
                            }
                        }
                    },
                    onDragCancel = {
                        animationJob = scope.launch {
                            animate(
                                initialValue = offsetX,
                                targetValue = 0f,
                                animationSpec = tween(300)
                            ) { value, _ ->
                                offsetX = value
                            }
                        }
                    },
                    onHorizontalDrag = { _, dragAmount ->
                        offsetX = (offsetX + dragAmount).coerceIn(-actionWidthPx, 0f)
                    },
                )
            },
    ) {
        Row(
            modifier = Modifier
                .matchParentSize()
                .padding(start = 8.dp),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            ActionBlock(
                text = "Rename",
                icon = Icons.Outlined.Edit,
                backgroundColor = Color(0xFF607078),
                onClick = {
                    animationJob?.cancel()
                    animationJob = scope.launch {
                        animate(initialValue = offsetX, targetValue = 0f) { value, _ ->
                            offsetX = value
                        }
                    }
                    onRename()
                },
            )
            Spacer(modifier = Modifier.width(8.dp))
            ActionBlock(
                text = "Delete",
                icon = Icons.Outlined.DeleteOutline,
                backgroundColor = VoraColors.Danger,
                onClick = {
                    animationJob?.cancel()
                    animationJob = scope.launch {
                        animate(initialValue = offsetX, targetValue = 0f) { value, _ ->
                            offsetX = value
                        }
                    }
                    onDelete()
                },
            )
        }

        Box(
            modifier = Modifier.offset { IntOffset(offsetX.roundToInt(), 0) },
        ) {
            content()
        }
    }
}
