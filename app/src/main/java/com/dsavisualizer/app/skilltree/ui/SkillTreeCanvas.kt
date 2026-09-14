package com.dsavisualizer.app.skilltree.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
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
import com.dsavisualizer.app.ui.theme.Slate400
import com.dsavisualizer.app.ui.theme.Slate500
import com.dsavisualizer.app.ui.theme.Slate700
import com.dsavisualizer.app.ui.theme.Slate800

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SkillTreeCanvas(
    nodes: List<SkillNode>,
    onNodeClick: (SkillNode) -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    val customColors = LocalDsaCustomColors.current

    val tierTitles = mapOf(
        1 to "Tier 1: Foundations",
        2 to "Tier 2: Linear Structures & Search",
        3 to "Tier 3: Hierarchical Structures & Hashing",
        4 to "Tier 4: Networks & Graph Algorithms",
        5 to "Tier 5: Advanced Optimization & Balance"
    )

    val nodesByTier = nodes.groupBy { it.tier }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(horizontal = 16.dp, vertical = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Interactive Legend Bar
        TreeLegendBar()

        Spacer(modifier = Modifier.height(24.dp))

        // Render each tier
        for (tier in 1..5) {
            val tierNodes = nodesByTier[tier] ?: emptyList()
            if (tierNodes.isEmpty()) continue

            // Tier Header Badge
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f))
                    .border(1.dp, customColors.surfaceStroke, RoundedCornerShape(20.dp))
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(
                    text = tierTitles[tier] ?: "Tier $tier",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Tier Nodes in FlowRow for responsive wrapping
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalArrangement = Arrangement.spacedBy(14.dp),
                maxItemsInEachRow = 2
            ) {
                for (node in tierNodes) {
                    Box(modifier = Modifier.padding(horizontal = 6.dp)) {
                        SkillNodeItem(
                            node = node,
                            onClick = { onNodeClick(node) }
                        )
                    }
                }
            }

            // Connector arrow between tiers (except after last tier)
            if (tier < 5) {
                Spacer(modifier = Modifier.height(16.dp))
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(
                        modifier = Modifier
                            .width(2.dp)
                            .height(20.dp)
                            .background(customColors.surfaceStroke)
                    )
                    Text(
                        text = "▼",
                        fontSize = 10.sp,
                        color = Slate500
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }

        Spacer(modifier = Modifier.height(40.dp))
    }
}

@Composable
private fun TreeLegendBar() {
    val customColors = LocalDsaCustomColors.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(MaterialTheme.colorScheme.surface)
            .border(1.dp, customColors.surfaceStroke, RoundedCornerShape(14.dp))
            .padding(vertical = 10.dp, horizontal = 14.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        LegendItem(color = customColors.nodeCompleted, label = "Mastered")
        LegendItem(color = customColors.nodeAvailable, label = "Available")
        LegendItem(color = Slate500, label = "Locked")
    }
}

@Composable
private fun LegendItem(color: Color, label: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(10.dp)
                .clip(CircleShape)
                .background(color)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = label,
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontWeight = FontWeight.Medium
        )
    }
}
