package com.dsavisualizer.app.skilltree.ui

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dsavisualizer.app.skilltree.model.SkillNode
import com.dsavisualizer.app.skilltree.model.SkillStatus
import com.dsavisualizer.app.ui.theme.LocalDsaCustomColors
import com.dsavisualizer.app.ui.theme.Slate400
import com.dsavisualizer.app.ui.theme.Slate500
import com.dsavisualizer.app.ui.theme.Slate700
import com.dsavisualizer.app.ui.theme.Slate800

@Composable
fun SkillNodeItem(
    node: SkillNode,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val customColors = LocalDsaCustomColors.current

    // Pulsing animation for active/available nodes
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 1.0f,
        targetValue = 1.04f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale"
    )

    val (borderColor, bgColor, statusText, statusBadgeBg) = when (node.status) {
        SkillStatus.COMPLETED -> Quad(
            customColors.nodeCompleted,
            customColors.nodeCompleted.copy(alpha = 0.12f),
            "✓ Mastered",
            customColors.nodeCompleted.copy(alpha = 0.22f)
        )
        SkillStatus.AVAILABLE -> Quad(
            customColors.nodeAvailable,
            customColors.nodeAvailable.copy(alpha = 0.10f),
            "▶ Ready",
            customColors.nodeAvailable.copy(alpha = 0.20f)
        )
        SkillStatus.LOCKED -> Quad(
            Slate700,
            Slate800.copy(alpha = 0.40f),
            "🔒 Locked",
            Slate700.copy(alpha = 0.50f)
        )
    }

    val itemModifier = if (node.status == SkillStatus.AVAILABLE) {
        modifier.scale(pulseScale)
    } else {
        modifier
    }

    Box(
        modifier = itemModifier
            .width(160.dp)
            .shadow(
                elevation = if (node.status == SkillStatus.AVAILABLE) 10.dp else 4.dp,
                shape = RoundedCornerShape(18.dp),
                ambientColor = borderColor,
                spotColor = borderColor
            )
            .clip(RoundedCornerShape(18.dp))
            .background(MaterialTheme.colorScheme.surface)
            .border(
                width = if (node.status == SkillStatus.AVAILABLE) 2.dp else 1.dp,
                brush = Brush.verticalGradient(listOf(borderColor, borderColor.copy(alpha = 0.5f))),
                shape = RoundedCornerShape(18.dp)
            )
            .clickable(onClick = onClick)
            .padding(12.dp)
    ) {
        Column {
            // Status Pill & Tier Indicator
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(statusBadgeBg)
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = statusText,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (node.status == SkillStatus.LOCKED) Slate400 else borderColor
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = "T${node.tier}",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Black,
                    color = Slate500
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Node Title
            Text(
                text = node.title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = if (node.status == SkillStatus.LOCKED) Slate400 else MaterialTheme.colorScheme.onSurface,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Category Label
            Text(
                text = node.categoryTitle,
                fontSize = 11.sp,
                color = Slate500,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

private data class Quad<A, B, C, D>(val first: A, val second: B, val third: C, val fourth: D)
