package com.dsavisualizer.app.skilltree.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dsavisualizer.app.skilltree.model.SkillNode
import com.dsavisualizer.app.skilltree.model.SkillStatus
import com.dsavisualizer.app.ui.theme.LocalDsaCustomColors
import com.dsavisualizer.app.ui.theme.Slate300
import com.dsavisualizer.app.ui.theme.Slate400
import com.dsavisualizer.app.ui.theme.Slate500
import com.dsavisualizer.app.ui.theme.Slate700
import com.dsavisualizer.app.ui.theme.Slate800

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SkillDetailBottomSheet(
    node: SkillNode,
    allNodes: List<SkillNode>,
    onDismiss: () -> Unit,
    onLaunchVisualizer: (SkillNode) -> Unit,
    sheetState: SheetState
) {
    val customColors = LocalDsaCustomColors.current

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .padding(bottom = 32.dp)
        ) {
            // Header with Tier and Category
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(
                            when (node.status) {
                                SkillStatus.COMPLETED -> customColors.nodeCompleted.copy(alpha = 0.2f)
                                SkillStatus.AVAILABLE -> customColors.nodeAvailable.copy(alpha = 0.2f)
                                SkillStatus.LOCKED -> Slate800
                            }
                        )
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "Tier ${node.tier}",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = when (node.status) {
                            SkillStatus.COMPLETED -> customColors.nodeCompleted
                            SkillStatus.AVAILABLE -> customColors.nodeAvailable
                            SkillStatus.LOCKED -> Slate400
                        }
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = node.categoryTitle,
                    fontSize = 12.sp,
                    color = Slate400,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Node Title
            Text(
                text = node.title,
                fontSize = 22.sp,
                fontWeight = FontWeight.Black,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Description Card
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                    .border(1.dp, customColors.surfaceStroke, RoundedCornerShape(14.dp))
                    .padding(14.dp)
            ) {
                Text(
                    text = node.description,
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            // Prerequisites Section
            if (node.prerequisiteIds.isNotEmpty()) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Prerequisites",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(8.dp))

                Column {
                    for (prereqId in node.prerequisiteIds) {
                        val prereq = allNodes.find { it.id == prereqId }
                        val isDone = prereq?.status == SkillStatus.COMPLETED
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(vertical = 3.dp)
                        ) {
                            Text(
                                text = if (isDone) "✓" else "○",
                                color = if (isDone) customColors.nodeCompleted else Slate500,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = prereq?.title ?: prereqId,
                                fontSize = 13.sp,
                                color = if (isDone) MaterialTheme.colorScheme.onSurface else Slate500,
                                fontWeight = if (isDone) FontWeight.Medium else FontWeight.Normal
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Action Button
            when (node.status) {
                SkillStatus.COMPLETED, SkillStatus.AVAILABLE -> {
                    Button(
                        onClick = {
                            onDismiss()
                            onLaunchVisualizer(node)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (node.status == SkillStatus.COMPLETED) customColors.nodeCompleted else customColors.nodeAvailable,
                            contentColor = Color.White
                        )
                    ) {
                        Text(
                            text = if (node.status == SkillStatus.COMPLETED) "Review & Visualize Topic" else "Launch Visualizer & Learn",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                SkillStatus.LOCKED -> {
                    OutlinedButton(
                        onClick = { },
                        enabled = false,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        shape = RoundedCornerShape(14.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, Slate700)
                    ) {
                        Text(
                            text = "🔒 Complete Prerequisites to Unlock",
                            fontSize = 14.sp,
                            color = Slate500,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }
    }
}
