package com.mohamedfaridelsherbini.vora.notes.presentation.components

import android.content.res.Configuration
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
import androidx.compose.material.icons.outlined.DirectionsCar
import androidx.compose.material.icons.outlined.KeyboardVoice
import androidx.compose.material.icons.outlined.PhoneAndroid
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
import com.mohamedfaridelsherbini.vora.notes.presentation.state.NoteListItemUi
import com.mohamedfaridelsherbini.vora.presentation.theme.VoraColors
import com.mohamedfaridelsherbini.vora.presentation.theme.VoraSpacing
import com.mohamedfaridelsherbini.vora.presentation.theme.voraTypography

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
            style = voraTypography().titleMedium.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
            ),
        )
        Text(
            text = "Tap record to capture your first thought.",
            color = subtitleColor,
            style = voraTypography().bodyMedium.copy(
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
            ),
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
                style = voraTypography().titleSmall.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                ),
            )
            Text(
                text = "Checking local files before cloud sync.",
                color = subtitleColor,
                style = voraTypography().bodySmall.copy(
                    fontWeight = FontWeight.Medium,
                    fontSize = 12.sp,
                ),
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
                    style = voraTypography().headlineSmall.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                    ),
                )
                Text(
                    text = memo.subtitle,
                    color = metaColor,
                    style = voraTypography().bodySmall.copy(
                        fontWeight = FontWeight.Medium,
                        fontSize = 12.sp,
                    ),
                )
            }
            Text(
                text = memo.time,
                color = metaColor,
                style = voraTypography().bodySmall.copy(
                    fontWeight = FontWeight.Medium,
                    fontSize = 12.sp,
                ),
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            SourceChip(
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
private fun SourceChip(
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
            style = voraTypography().labelMedium.copy(
                fontWeight = FontWeight.SemiBold,
                fontSize = 12.sp,
            ),
        )
    }
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

@Preview(name = "Loading Status Card Dark", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, backgroundColor = 0xFF111827)
@Composable
private fun LoadingStatusCardPreviewDark() {
    NotesLoadingStatusCard(
        titleColor = VoraColors.LogoPaper,
        subtitleColor = VoraColors.VoraMuted.copy(alpha = 0.72f),
        background = Color(0xFF161F2D),
        borderColor = Color(0xFF202C3D),
        iconTint = Color(0xFFB8C1CD),
        accentColor = Color(0xFF1D2736),
        skeletonColor = Color(0xFF24314F),
    )
}

@Preview(name = "Loading Memo Card Dark", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, backgroundColor = 0xFF111827)
@Composable
private fun LoadingMemoCardPreviewDark() {
    LoadingMemoCard(
        background = Color(0xFF161F2D),
        borderColor = Color(0xFF202C3D),
        lineColor = Color(0xFF24314F),
        chipColor = Color(0xFF1D2736),
    )
}

@Preview(name = "Record Button Dark", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, backgroundColor = 0xFF111827)
@Composable
private fun RecordFabPreviewDark() {
    RecordFab(
        background = Color(0xFFEF2B2A),
        textColor = VoraColors.VoraWhite,
        onClick = {},
    )
}

@Preview(name = "Empty State Card Dark", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, backgroundColor = 0xFF111827)
@Composable
private fun EmptyStateCardPreviewDark() {
    NotesEmptyStateCard(
        titleColor = VoraColors.LogoPaper,
        subtitleColor = Color(0xFFB8C1CD),
        background = Color(0xFF161F2D),
        borderColor = Color(0xFF202C3D),
        iconColor = Color(0xFFB8C1CD),
    )
}
