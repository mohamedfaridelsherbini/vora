package com.mohamedfaridelsherbini.vora.presentation.notes

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.HelpOutline
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.DeleteOutline
import androidx.compose.material.icons.outlined.DirectionsCar
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.KeyboardVoice
import androidx.compose.material.icons.outlined.PhoneAndroid
import androidx.compose.material.icons.outlined.PriorityHigh
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Sync
import androidx.compose.material.icons.outlined.Watch
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.ui.window.Dialog
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Surface
import androidx.compose.material3.IconButton
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.outlined.Close
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import kotlinx.coroutines.launch
import kotlin.math.roundToInt
import com.mohamedfaridelsherbini.vora.presentation.theme.VoraColors
import com.mohamedfaridelsherbini.vora.presentation.theme.VoraSpacing
import com.mohamedfaridelsherbini.vora.presentation.theme.interFontFamily

// ── Dialogs ──────────────────────────────────────────────────────────────────

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun VoraRenameBottomSheet(
    memo: NoteListItemUi,
    onConfirm: (String) -> Unit,
    onDismiss: () -> Unit,
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val focusRequester = remember { FocusRequester() }
    val isDark = isSystemInDarkTheme()
    
    var fieldValue by remember {
        mutableStateOf(
            TextFieldValue(
                text = memo.title,
                selection = TextRange(0, memo.title.length),
            ),
        )
    }
    val trimmed = fieldValue.text.trim()
    val canSave = trimmed.isNotBlank() && trimmed != memo.title.trim()

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = if (isDark) Color(0xFF161F2D) else Color.White,
        dragHandle = {
            Box(
                modifier = Modifier
                    .padding(top = 16.dp, bottom = 8.dp)
                    .size(width = 32.dp, height = 4.dp)
                    .background(Color(0xFFE0E5EA), CircleShape)
            )
        },
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .padding(bottom = 32.dp)
        ) {
            Text(
                text = "Rename memo",
                fontFamily = interFontFamily(),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = if (isDark) Color.White else VoraColors.LogoInk
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Recorded ${memo.time} · ${memo.source} · ${memo.subtitle}",
                fontFamily = interFontFamily(),
                fontWeight = FontWeight.Medium,
                fontSize = 12.sp,
                color = VoraColors.VoraMuted
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester),
                value = fieldValue,
                onValueChange = { if (it.text.length <= 80) fieldValue = it },
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = { if (canSave) onConfirm(trimmed) },
                ),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = VoraColors.LogoInk,
                    unfocusedBorderColor = if (isDark) Color(0xFF24314F) else Color(0xFFE7EDF3),
                    focusedContainerColor = if (isDark) Color(0xFF161F2D) else Color(0xFFF5F7FA),
                    unfocusedContainerColor = if (isDark) Color(0xFF161F2D) else Color(0xFFF5F7FA),
                    focusedTextColor = if (isDark) Color.White else VoraColors.LogoInk,
                    unfocusedTextColor = if (isDark) Color.White else VoraColors.LogoInk,
                ),
                textStyle = androidx.compose.ui.text.TextStyle(
                    fontFamily = interFontFamily(),
                    fontWeight = FontWeight.Medium,
                    fontSize = 15.sp,
                ),
                shape = RoundedCornerShape(12.dp),
                trailingIcon = {
                    if (fieldValue.text.isNotEmpty()) {
                        IconButton(onClick = { fieldValue = TextFieldValue("") }) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Clear",
                                tint = VoraColors.VoraMuted,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                }
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Names appear in the list and in transcripts.",
                    fontFamily = interFontFamily(),
                    fontSize = 12.sp,
                    color = VoraColors.VoraMuted
                )
                Text(
                    text = "${fieldValue.text.length} / 80",
                    fontFamily = interFontFamily(),
                    fontSize = 12.sp,
                    color = VoraColors.VoraMuted
                )
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp)
                        .background(
                            if (isDark) Color(0xFF1D2633) else Color.White,
                            RoundedCornerShape(12.dp)
                        )
                        .border(
                            1.dp,
                            if (isDark) Color(0xFF24314F) else Color(0xFFE7EDF3),
                            RoundedCornerShape(12.dp)
                        )
                        .clickable(onClick = onDismiss),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Cancel",
                        fontFamily = interFontFamily(),
                        fontWeight = FontWeight.Medium,
                        color = VoraColors.VoraMuted
                    )
                }
                
                val saveBg = if (canSave) (if (isDark) Color.White else Color(0xFF1A1D20)) else Color(0xFFE0E5EA)
                val saveFg = if (canSave) (if (isDark) Color(0xFF1A1D20) else Color.White) else VoraColors.VoraMuted
                
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp)
                        .background(saveBg, RoundedCornerShape(12.dp))
                        .clickable(enabled = canSave, onClick = { onConfirm(trimmed) }),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (isDark && canSave) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = null,
                                tint = saveFg,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(Modifier.width(8.dp))
                        }
                        Text(
                            text = "Save",
                            fontFamily = interFontFamily(),
                            fontWeight = FontWeight.SemiBold,
                            color = saveFg
                        )
                    }
                }
            }
        }
    }

    LaunchedEffect(Unit) { focusRequester.requestFocus() }
}

@Composable
internal fun VoraDeleteDialog(
    memo: NoteListItemUi,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
) {
    val isDark = isSystemInDarkTheme()
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(24.dp),
            color = if (isDark) Color(0xFF161F2D) else Color.White,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Icon
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(
                            if (isDark) Color(0xFF3B1A1A) else Color(0xFFFDE8E8),
                            CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null,
                        tint = VoraColors.Danger
                    )
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Text(
                    text = "Delete this memo?",
                    fontFamily = interFontFamily(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = if (isDark) Color.White else VoraColors.LogoInk,
                    textAlign = TextAlign.Center
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Text(
                    text = "${memo.title} · ${memo.subtitle} will be permanently removed from this device. This can't be undone.",
                    fontFamily = interFontFamily(),
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp,
                    color = VoraColors.VoraMuted,
                    textAlign = TextAlign.Center
                )
                
                Spacer(modifier = Modifier.height(24.dp))
                
                // Delete button
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .background(VoraColors.Danger, RoundedCornerShape(12.dp))
                        .clickable(onClick = onConfirm),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (isDark) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(Modifier.width(8.dp))
                        }
                        Text(
                            text = "Delete memo",
                            fontFamily = interFontFamily(),
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(8.dp))
                
                // Cancel button
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .clickable(
                            onClick = onDismiss,
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Cancel",
                        fontFamily = interFontFamily(),
                        fontWeight = FontWeight.Medium,
                        color = if (isDark) Color.White else Color(0xFF1A1D20)
                    )
                }
            }
        }
    }
}

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
        verticalArrangement = Arrangement.Center
    ) {
        Icon(icon, contentDescription = null, tint = Color.White)
        Spacer(Modifier.height(4.dp))
        Text(text, color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Medium, fontFamily = interFontFamily())
    }
}

@Composable
internal fun SwipeRevealItem(
    onRename: () -> Unit,
    onDelete: () -> Unit,
    content: @Composable () -> Unit,
) {
    val density = LocalDensity.current
    val actionWidthPx = with(density) { 164.dp.toPx() } // 74 * 2 + 8 spacing + 8 start spacing
    val offsetX = remember { Animatable(0f) }
    val scope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .pointerInput(Unit) {
                detectHorizontalDragGestures(
                    onDragEnd = {
                        scope.launch {
                            val target = if (offsetX.value < -actionWidthPx / 2) -actionWidthPx else 0f
                            offsetX.animateTo(target, tween(300))
                        }
                    },
                    onDragCancel = {
                        scope.launch { offsetX.animateTo(0f, tween(300)) }
                    },
                    onHorizontalDrag = { _, dragAmount ->
                        scope.launch {
                            val newOffset = (offsetX.value + dragAmount).coerceIn(-actionWidthPx, 0f)
                            offsetX.snapTo(newOffset)
                        }
                    }
                )
            }
    ) {
        // Background actions
        Row(
            modifier = Modifier.matchParentSize().padding(start = 8.dp),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            ActionBlock(
                text = "Rename",
                icon = Icons.Outlined.Edit,
                backgroundColor = Color(0xFF607078),
                onClick = {
                    scope.launch { offsetX.animateTo(0f) }
                    onRename()
                }
            )
            Spacer(modifier = Modifier.width(8.dp))
            ActionBlock(
                text = "Delete",
                icon = Icons.Outlined.DeleteOutline,
                backgroundColor = VoraColors.Danger,
                onClick = {
                    scope.launch { offsetX.animateTo(0f) }
                    onDelete()
                }
            )
        }

        // Foreground content
        Box(
            modifier = Modifier.offset { IntOffset(offsetX.value.roundToInt(), 0) }
        ) {
            content()
        }
    }
}

// ── List components ───────────────────────────────────────────────────────────

@Composable
internal fun NotesHeader(
    titleColor: Color,
    subtitleColor: Color,
    summaryText: String,
    statusLabel: String,
    statusBackground: Color,
    statusTextColor: Color,
) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Text(
            text = "Notes",
            color = titleColor,
            fontFamily = interFontFamily(),
            fontWeight = FontWeight.Bold,
            fontSize = 34.sp,
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            Text(
                text = summaryText,
                color = subtitleColor,
                fontFamily = interFontFamily(),
                fontWeight = FontWeight.Medium,
                fontSize = 12.sp,
            )
            StatusChip(
                label = statusLabel,
                background = statusBackground,
                textColor = statusTextColor,
                icon = if (statusLabel == "Syncing") Icons.Outlined.Sync else Icons.Outlined.CheckCircle,
            )
        }
    }
}

@Composable
internal fun NotesSearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    background: Color,
    borderColor: Color,
    textColor: Color,
    iconColor: Color,
) {
    androidx.compose.foundation.text.BasicTextField(
        value = query,
        onValueChange = onQueryChange,
        textStyle = androidx.compose.ui.text.TextStyle(
            color = textColor.copy(alpha = 0.85f),
            fontFamily = interFontFamily(),
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
        ),
        singleLine = true,
        cursorBrush = androidx.compose.ui.graphics.SolidColor(textColor),
        decorationBox = { innerTextField ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(background, RoundedCornerShape(16.dp))
                    .border(1.dp, borderColor, RoundedCornerShape(16.dp))
                    .padding(horizontal = VoraSpacing.SearchHorizontal, vertical = VoraSpacing.SearchVertical),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                Icon(
                    imageVector = Icons.Outlined.Search,
                    contentDescription = null,
                    tint = iconColor.copy(alpha = 0.65f),
                    modifier = Modifier.size(16.dp),
                )
                Box(modifier = Modifier.weight(1f)) {
                    if (query.isEmpty()) {
                        Text(
                            text = "Search transcripts",
                            color = iconColor.copy(alpha = 0.65f),
                            fontFamily = interFontFamily(),
                            fontWeight = FontWeight.Medium,
                            fontSize = 14.sp,
                        )
                    }
                    innerTextField()
                }
                if (query.isNotEmpty()) {
                    IconButton(
                        onClick = { onQueryChange("") },
                        modifier = Modifier.size(16.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Close,
                            contentDescription = "Clear search",
                            tint = iconColor.copy(alpha = 0.65f),
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }
        }
    )
}

@Composable
internal fun NotesFilterRow(
    filters: List<NotesSourceFilterUi>,
    background: Color,
    textColor: Color,
    onSelect: (String) -> Unit,
) {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        filters.forEach { filter ->
            FilterChip(
                label = "${filter.label} ${filter.count}",
                selected = filter.selected,
                background = background,
                textColor = textColor,
                icon = filter.icon(),
                onClick = { onSelect(filter.key) },
            )
        }
    }
}

@Composable
internal fun NotesLoadingFilterRow(
    background: Color,
    selectedBackground: Color,
) {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        LoadingPill(width = 48.dp, background = selectedBackground)
        LoadingPill(width = 70.dp, background = background)
        LoadingPill(width = 82.dp, background = Color(0xFFF8ECE9))
    }
}

@Composable
internal fun NotesRecentLabel(textColor: Color) {
    Text(
        text = "RECENT",
        color = textColor,
        fontFamily = interFontFamily(),
        fontWeight = FontWeight.SemiBold,
        fontSize = 11.sp,
    )
}

@Composable
internal fun NotesEmptyStateCard(
    modifier: Modifier = Modifier,
    titleColor: Color,
    subtitleColor: Color,
    background: Color,
    borderColor: Color,
    iconColor: Color,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(background, RoundedCornerShape(18.dp))
            .border(1.dp, borderColor, RoundedCornerShape(18.dp))
            .padding(horizontal = 22.dp, vertical = 26.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Outlined.HelpOutline,
            contentDescription = null,
            tint = iconColor,
            modifier = Modifier.size(20.dp),
        )
        Text(
            text = "No voice memos yet",
            color = titleColor,
            fontFamily = interFontFamily(),
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
        )
        Text(
            text = "Tap record to capture your first thought.",
            color = subtitleColor,
            fontFamily = interFontFamily(),
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
        )
    }
}

@Composable
internal fun NotesLoadingStatusCard(
    titleColor: Color,
    subtitleColor: Color,
    background: Color,
    borderColor: Color,
    iconTint: Color,
    accentColor: Color,
    skeletonColor: Color,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(background, RoundedCornerShape(18.dp))
            .border(1.dp, borderColor, RoundedCornerShape(18.dp))
            .padding(horizontal = 16.dp, vertical = 14.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .size(22.dp)
                .background(Color.Transparent, CircleShape)
                .border(1.dp, borderColor, CircleShape),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = Icons.Outlined.Sync,
                contentDescription = null,
                tint = iconTint,
                modifier = Modifier.size(12.dp),
            )
        }
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(3.dp),
        ) {
            Text(
                text = "Restoring recent memos",
                color = titleColor,
                fontFamily = interFontFamily(),
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
            )
            Text(
                text = "Checking local files before cloud sync.",
                color = subtitleColor,
                fontFamily = interFontFamily(),
                fontWeight = FontWeight.Medium,
                fontSize = 12.sp,
            )
            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                LoadingLine(width = 56.dp, height = 5.dp, color = accentColor)
                LoadingLine(width = 108.dp, height = 5.dp, color = skeletonColor)
            }
        }
    }
}

@Composable
internal fun LoadingMemoCard(
    background: Color,
    borderColor: Color,
    lineColor: Color,
    chipColor: Color,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(background, RoundedCornerShape(18.dp))
            .border(1.dp, borderColor, RoundedCornerShape(18.dp))
            .padding(horizontal = 14.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp), verticalAlignment = Alignment.CenterVertically) {
            LoadingLine(
                modifier = Modifier.weight(1f),
                width = null,
                height = 12.dp,
                color = lineColor,
            )
            LoadingLine(width = 46.dp, height = 12.dp, color = chipColor)
        }
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp), verticalAlignment = Alignment.CenterVertically) {
            LoadingLine(width = 96.dp, height = 8.dp, color = chipColor)
            Spacer(modifier = Modifier.weight(1f))
            LoadingLine(width = 42.dp, height = 10.dp, color = chipColor)
        }
    }
}

@Composable
internal fun MemoCard(
    memo: NoteListItemUi,
    titleColor: Color,
    metaColor: Color,
    cardBackground: Color,
    cardBorder: Color,
    sourceBackground: Color,
    sourceTextColor: Color,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(cardBackground, RoundedCornerShape(18.dp))
            .border(1.dp, cardBorder, RoundedCornerShape(18.dp))
            .padding(horizontal = VoraSpacing.CardHorizontal, vertical = VoraSpacing.CardVertical),
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(
                    text = memo.title,
                    color = titleColor,
                    fontFamily = interFontFamily(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                )
                Text(
                    text = memo.subtitle,
                    color = metaColor,
                    fontFamily = interFontFamily(),
                    fontWeight = FontWeight.Medium,
                    fontSize = 12.sp,
                )
            }
            Text(
                text = memo.time,
                color = metaColor,
                fontFamily = interFontFamily(),
                fontWeight = FontWeight.Medium,
                fontSize = 12.sp,
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            StatusChip(
                label = memo.source,
                background = sourceBackground,
                textColor = sourceTextColor,
                icon = memo.source.icon(),
            )
        }
    }
}

@Composable
internal fun RecordFab(
    modifier: Modifier = Modifier,
    background: Color,
    textColor: Color,
    onClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .size(64.dp)
            .background(background, CircleShape)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            imageVector = Icons.Outlined.KeyboardVoice,
            contentDescription = "Record memo",
            tint = textColor,
            modifier = Modifier.size(22.dp),
        )
    }
}


@Composable
private fun StatusChip(
    label: String,
    background: Color,
    textColor: Color,
    icon: ImageVector? = null,
) {
    Row(
        modifier = Modifier
            .background(background, RoundedCornerShape(999.dp))
            .padding(horizontal = VoraSpacing.ChipHorizontal, vertical = VoraSpacing.ChipVertical),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        if (icon != null) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = textColor,
                modifier = Modifier.size(12.dp),
            )
        }
        Text(
            text = label,
            color = textColor,
            fontFamily = interFontFamily(),
            fontWeight = FontWeight.SemiBold,
            fontSize = 12.sp,
        )
    }
}

@Composable
private fun FilterChip(
    label: String,
    selected: Boolean,
    background: Color,
    textColor: Color,
    icon: ImageVector? = null,
    onClick: (() -> Unit)? = null,
) {
    Row(
        modifier = Modifier
            .background(
                color = if (selected) VoraColors.LogoInk else background,
                shape = RoundedCornerShape(999.dp),
            )
            .then(if (onClick != null) Modifier.clickable(onClick = onClick) else Modifier)
            .padding(horizontal = VoraSpacing.ChipHorizontal, vertical = VoraSpacing.ChipVertical),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        val foreground = if (selected) VoraColors.LogoPaper else textColor
        if (icon != null) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = foreground,
                modifier = Modifier.size(12.dp),
            )
            Spacer(modifier = Modifier.size(6.dp))
        }
        Text(
            text = label,
            color = foreground,
            fontFamily = interFontFamily(),
            fontWeight = FontWeight.SemiBold,
            fontSize = 12.sp,
        )
    }
}

@Composable
private fun LoadingPill(
    width: androidx.compose.ui.unit.Dp,
    background: Color,
) {
    Box(
        modifier = Modifier
            .size(width = width, height = 22.dp)
            .background(background, RoundedCornerShape(999.dp)),
    )
}

@Composable
private fun LoadingLine(
    width: androidx.compose.ui.unit.Dp?,
    height: androidx.compose.ui.unit.Dp,
    color: Color,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .then(if (width != null) Modifier.size(width = width, height = height) else Modifier.height(height))
            .background(color, RoundedCornerShape(999.dp))
            .then(if (width == null) Modifier.fillMaxWidth() else Modifier),
    )
}

private fun String.icon(): ImageVector = when (this) {
    "Phone" -> Icons.Outlined.PhoneAndroid
    "Watch" -> Icons.Outlined.Watch
    "Car" -> Icons.Outlined.DirectionsCar
    else -> Icons.Outlined.CheckCircle
}

private fun NotesSourceFilterUi.icon(): ImageVector = when (key) {
    "phone" -> Icons.Outlined.PhoneAndroid
    "smart" -> Icons.Outlined.Watch
    "car" -> Icons.Outlined.DirectionsCar
    else -> Icons.Outlined.CheckCircle
}

@Preview(name = "Status Synced", showBackground = true, backgroundColor = 0xFFF9FAFB)
@Composable
private fun SyncedStatusChipPreview() {
    StatusChip(
        label = "Synced",
        background = Color(0xFF3A7D58),
        textColor = VoraColors.VoraWhite,
        icon = Icons.Outlined.CheckCircle,
    )
}

@Preview(name = "Status Pending", showBackground = true, backgroundColor = 0xFFF9FAFB)
@Composable
private fun PendingStatusChipPreview() {
    FilterChip(
        label = "Pending 2",
        selected = false,
        background = Color(0xFFEFF4F7),
        textColor = VoraColors.LogoInk,
        icon = Icons.Outlined.Sync,
        onClick = {},
    )
}

@Preview(name = "Status Needs Review", showBackground = true, backgroundColor = 0xFFF9FAFB)
@Composable
private fun NeedsReviewStatusChipPreview() {
    FilterChip(
        label = "Needs review",
        selected = false,
        background = Color(0xFFF9ECE8),
        textColor = Color(0xFFC95B4A),
        icon = Icons.Outlined.PriorityHigh,
        onClick = {},
    )
}

@Preview(name = "Status Source Phone", showBackground = true, backgroundColor = 0xFFF9FAFB)
@Composable
private fun SourcePhoneChipPreview() {
    StatusChip(
        label = "Phone",
        background = Color(0xFFEFF4F7),
        textColor = VoraColors.LogoInk,
        icon = Icons.Outlined.PhoneAndroid,
    )
}

@Preview(name = "Status Source Watch", showBackground = true, backgroundColor = 0xFFF9FAFB)
@Composable
private fun SourceWatchChipPreview() {
    StatusChip(
        label = "Watch",
        background = Color(0xFFEFF4F7),
        textColor = VoraColors.LogoInk,
        icon = Icons.Outlined.Watch,
    )
}

@Preview(name = "Status Source Car", showBackground = true, backgroundColor = 0xFFF9FAFB)
@Composable
private fun SourceCarChipPreview() {
    StatusChip(
        label = "Car",
        background = Color(0xFFEFF4F7),
        textColor = VoraColors.LogoInk,
        icon = Icons.Outlined.DirectionsCar,
    )
}

@Preview(name = "Loading Status Card", showBackground = true, backgroundColor = 0xFFF9FAFB)
@Composable
private fun LoadingStatusCardPreview() {
    NotesLoadingStatusCard(
        titleColor = VoraColors.LogoInk,
        subtitleColor = VoraColors.VoraMuted,
        background = VoraColors.VoraWhite,
        borderColor = Color(0xFFEDF2F5),
        iconTint = VoraColors.VoraMuted,
        accentColor = VoraColors.Tertiary,
        skeletonColor = Color(0xFFE3EDF2),
    )
}

@Preview(name = "Loading Memo Card", showBackground = true, backgroundColor = 0xFFF9FAFB)
@Composable
private fun LoadingMemoCardPreview() {
    LoadingMemoCard(
        background = VoraColors.VoraWhite,
        borderColor = Color(0xFFEDF2F5),
        lineColor = Color(0xFFD9EAF2),
        chipColor = Color(0xFFE9EDF3),
    )
}

@Preview(name = "Record Button", showBackground = true, backgroundColor = 0xFFF9FAFB)
@Composable
private fun RecordFabPreview() {
    RecordFab(
        background = Color(0xFFEF2B2A),
        textColor = VoraColors.VoraWhite,
        onClick = {},
    )
}

@Preview(name = "Empty State Card", showBackground = true, backgroundColor = 0xFFF9FAFB)
@Composable
private fun EmptyStateCardPreview() {
    NotesEmptyStateCard(
        titleColor = VoraColors.LogoInk,
        subtitleColor = VoraColors.VoraMuted,
        background = Color(0xFFF7F9FB),
        borderColor = Color(0xFFF0F3F6),
        iconColor = VoraColors.LogoInk.copy(alpha = 0.78f),
    )
}
