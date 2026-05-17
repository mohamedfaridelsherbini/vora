package com.mohamedfaridelsherbini.vora.notes.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
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
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.mohamedfaridelsherbini.vora.notes.presentation.state.NoteListItemUi
import com.mohamedfaridelsherbini.vora.presentation.theme.VoraColors
import com.mohamedfaridelsherbini.vora.presentation.theme.interFontFamily

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
                    .background(Color(0xFFE0E5EA), CircleShape),
            )
        },
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .padding(bottom = 32.dp),
        ) {
            Text(
                text = "Rename memo",
                fontFamily = interFontFamily(),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = if (isDark) Color.White else VoraColors.LogoInk,
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Recorded ${memo.time} · ${memo.source} · ${memo.subtitle}",
                fontFamily = interFontFamily(),
                fontWeight = FontWeight.Medium,
                fontSize = 12.sp,
                color = VoraColors.VoraMuted,
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
                                modifier = Modifier.size(16.dp),
                            )
                        }
                    }
                },
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = "Names appear in the list and in transcripts.",
                    fontFamily = interFontFamily(),
                    fontSize = 12.sp,
                    color = VoraColors.VoraMuted,
                )
                Text(
                    text = "${fieldValue.text.length} / 80",
                    fontFamily = interFontFamily(),
                    fontSize = 12.sp,
                    color = VoraColors.VoraMuted,
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp)
                        .background(
                            if (isDark) Color(0xFF1D2633) else Color.White,
                            RoundedCornerShape(12.dp),
                        )
                        .border(
                            1.dp,
                            if (isDark) Color(0xFF24314F) else Color(0xFFE7EDF3),
                            RoundedCornerShape(12.dp),
                        )
                        .clickable(onClick = onDismiss),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = "Cancel",
                        fontFamily = interFontFamily(),
                        fontWeight = FontWeight.Medium,
                        color = VoraColors.VoraMuted,
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
                    contentAlignment = Alignment.Center,
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        if (isDark && canSave) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = null,
                                tint = saveFg,
                                modifier = Modifier.size(16.dp),
                            )
                            Spacer(Modifier.size(8.dp))
                        }
                        Text(
                            text = "Save",
                            fontFamily = interFontFamily(),
                            fontWeight = FontWeight.SemiBold,
                            color = saveFg,
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
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(
                            if (isDark) Color(0xFF3B1A1A) else Color(0xFFFDE8E8),
                            CircleShape,
                        ),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null,
                        tint = VoraColors.Danger,
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Delete this memo?",
                    fontFamily = interFontFamily(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = if (isDark) Color.White else VoraColors.LogoInk,
                    textAlign = TextAlign.Center,
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "${memo.title} · ${memo.subtitle} will be permanently removed from this device. This can't be undone.",
                    fontFamily = interFontFamily(),
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp,
                    color = VoraColors.VoraMuted,
                    textAlign = TextAlign.Center,
                )

                Spacer(modifier = Modifier.height(24.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .background(VoraColors.Danger, RoundedCornerShape(12.dp))
                        .clickable(onClick = onConfirm),
                    contentAlignment = Alignment.Center,
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        if (isDark) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(16.dp),
                            )
                            Spacer(Modifier.size(8.dp))
                        }
                        Text(
                            text = "Delete memo",
                            fontFamily = interFontFamily(),
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White,
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .clickable(
                            onClick = onDismiss,
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                        ),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = "Cancel",
                        fontFamily = interFontFamily(),
                        fontWeight = FontWeight.Medium,
                        color = if (isDark) Color.White else Color(0xFF1A1D20),
                    )
                }
            }
        }
    }
}
