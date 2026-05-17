package com.mohamedfaridelsherbini.vora.notes.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.DirectionsCar
import androidx.compose.material.icons.outlined.PhoneAndroid
import androidx.compose.material.icons.outlined.PriorityHigh
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Sync
import androidx.compose.material.icons.outlined.Watch
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mohamedfaridelsherbini.vora.notes.presentation.state.NotesSourceFilterUi
import com.mohamedfaridelsherbini.vora.presentation.theme.VoraColors
import com.mohamedfaridelsherbini.vora.presentation.theme.VoraSpacing
import com.mohamedfaridelsherbini.vora.presentation.theme.interFontFamily

@Composable
internal fun NotesHeader(
    titleColor: Color,
    subtitleColor: Color,
    summaryText: String,
    statusLabel: String,
    statusBackground: Color,
    statusTextColor: Color,
) {
    androidx.compose.foundation.layout.Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
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
    BasicTextField(
        value = query,
        onValueChange = onQueryChange,
        textStyle = androidx.compose.ui.text.TextStyle(
            color = textColor.copy(alpha = 0.85f),
            fontFamily = interFontFamily(),
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
        ),
        singleLine = true,
        cursorBrush = SolidColor(textColor),
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
                        modifier = Modifier.size(16.dp),
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Close,
                            contentDescription = "Clear search",
                            tint = iconColor.copy(alpha = 0.65f),
                            modifier = Modifier.size(16.dp),
                        )
                    }
                }
            }
        },
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
