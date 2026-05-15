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
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
    background: Color,
    borderColor: Color,
    textColor: Color,
) {
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
            tint = textColor.copy(alpha = 0.65f),
            modifier = Modifier.size(16.dp),
        )
        Text(
            text = "Search transcripts",
            color = textColor.copy(alpha = 0.85f),
            fontFamily = interFontFamily(),
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
        )
    }
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
    onRename: () -> Unit,
    onDelete: () -> Unit,
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
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(14.dp), verticalAlignment = Alignment.CenterVertically) {
                MemoAction(
                    label = "Rename",
                    icon = Icons.Outlined.Edit,
                    tint = metaColor,
                    onClick = onRename,
                )
                MemoAction(
                    label = "Delete",
                    icon = Icons.Outlined.DeleteOutline,
                    tint = VoraColors.Danger,
                    onClick = onDelete,
                )
            }
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
private fun MemoAction(
    label: String,
    icon: ImageVector,
    tint: Color,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier.clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = tint,
            modifier = Modifier.size(14.dp),
        )
        Text(
            text = label,
            color = tint,
            fontFamily = interFontFamily(),
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp,
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
