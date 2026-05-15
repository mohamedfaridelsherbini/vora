package com.mohamedfaridelsherbini.vora.presentation.notes

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
                text = "12 memos · 18 min",
                color = subtitleColor,
                fontFamily = interFontFamily(),
                fontWeight = FontWeight.Medium,
                fontSize = 12.sp,
            )
            StatusChip("Synced", statusBackground, statusTextColor)
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
        Text(
            text = "Q",
            color = textColor.copy(alpha = 0.65f),
            fontFamily = interFontFamily(),
            fontWeight = FontWeight.SemiBold,
            fontSize = 12.sp,
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
    background: Color,
    textColor: Color,
) {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        FilterChip("All 12", true, background, textColor)
        FilterChip("Pending 2", false, background, textColor)
        FilterChip("Needs review", false, Color(0xFFF9ECE8), Color(0xFFC95B4A))
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
        Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
            StatusChip(memo.source, sourceBackground, sourceTextColor)
        }
    }
}

@Composable
internal fun RecordFab(
    modifier: Modifier = Modifier,
    background: Color,
    textColor: Color,
) {
    Box(
        modifier = modifier
            .size(64.dp)
            .background(background, CircleShape),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = "Rec",
            color = textColor,
            fontFamily = interFontFamily(),
            fontWeight = FontWeight.Bold,
            fontSize = 13.sp,
        )
    }
}

@Composable
private fun StatusChip(
    label: String,
    background: Color,
    textColor: Color,
) {
    Text(
        text = label,
        color = textColor,
        fontFamily = interFontFamily(),
        fontWeight = FontWeight.SemiBold,
        fontSize = 12.sp,
        modifier = Modifier
            .background(background, RoundedCornerShape(999.dp))
            .padding(horizontal = VoraSpacing.ChipHorizontal, vertical = VoraSpacing.ChipVertical),
    )
}

@Composable
private fun FilterChip(
    label: String,
    selected: Boolean,
    background: Color,
    textColor: Color,
) {
    Text(
        text = label,
        color = if (selected) VoraColors.LogoPaper else textColor,
        fontFamily = interFontFamily(),
        fontWeight = FontWeight.SemiBold,
        fontSize = 12.sp,
        modifier = Modifier
            .background(
                color = if (selected) VoraColors.LogoInk else background,
                shape = RoundedCornerShape(999.dp),
            )
            .padding(horizontal = VoraSpacing.ChipHorizontal, vertical = VoraSpacing.ChipVertical),
    )
}

@Preview(name = "Status Chip", showBackground = true, backgroundColor = 0xFFF9FAFB)
@Composable
private fun StatusChipPreview() {
    StatusChip(
        label = "Synced",
        background = Color(0xFF3A7D58),
        textColor = VoraColors.VoraWhite,
    )
}

@Preview(name = "Record Button", showBackground = true, backgroundColor = 0xFFF9FAFB)
@Composable
private fun RecordFabPreview() {
    RecordFab(
        background = Color(0xFFEF2B2A),
        textColor = VoraColors.VoraWhite,
    )
}
