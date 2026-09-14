package com.dsavisualizer.app.skilltree.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dsavisualizer.app.R
import com.dsavisualizer.app.skilltree.model.SkillNode
import com.dsavisualizer.app.skilltree.viewmodel.SkillTreeViewModel
import com.dsavisualizer.app.ui.theme.LocalDsaCustomColors
import com.dsavisualizer.app.ui.theme.Slate400
import com.dsavisualizer.app.ui.theme.Slate500

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SkillTreeScreen(
    viewModel: SkillTreeViewModel,
    onNavigateBack: () -> Unit,
    onLaunchVisualizer: (SkillNode) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val customColors = LocalDsaCustomColors.current
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "DSA Skill Tree",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "${uiState.completedCount} of ${uiState.totalCount} Mastered (${uiState.masteryPercentage}%)",
                            fontSize = 12.sp,
                            color = customColors.cyberSky,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Text(
                            text = "←",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Mastery Progress Bar
            LinearProgressIndicator(
                progress = {
                    if (uiState.totalCount > 0) uiState.completedCount.toFloat() / uiState.totalCount.toFloat() else 0f
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(4.dp),
                color = customColors.cyberEmerald,
                trackColor = MaterialTheme.colorScheme.surfaceVariant
            )

            // Skill Tree Canvas
            SkillTreeCanvas(
                nodes = uiState.nodes,
                onNodeClick = { viewModel.selectNode(it) }
            )
        }

        // Bottom Sheet for Selected Node Detail
        uiState.selectedNode?.let { node ->
            SkillDetailBottomSheet(
                node = node,
                allNodes = uiState.nodes,
                onDismiss = { viewModel.dismissDetail() },
                onLaunchVisualizer = onLaunchVisualizer,
                sheetState = sheetState
            )
        }
    }
}
