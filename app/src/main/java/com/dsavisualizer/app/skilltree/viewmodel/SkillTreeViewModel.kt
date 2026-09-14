package com.dsavisualizer.app.skilltree.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.dsavisualizer.app.skilltree.model.SkillNode
import com.dsavisualizer.app.skilltree.model.SkillStatus
import com.dsavisualizer.app.skilltree.model.SkillTreeGraph
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class SkillTreeUiState(
    val nodes: List<SkillNode> = emptyList(),
    val selectedNode: SkillNode? = null,
    val completedCount: Int = 0,
    val totalCount: Int = 0,
    val masteryPercentage: Int = 0
)

class SkillTreeViewModel(application: Application) : AndroidViewModel(application) {

    private val _uiState = MutableStateFlow(SkillTreeUiState())
    val uiState: StateFlow<SkillTreeUiState> = _uiState.asStateFlow()

    init {
        refreshTree()
    }

    fun refreshTree() {
        val nodes = SkillTreeGraph.computeSkillTree(getApplication())
        val completed = nodes.count { it.status == SkillStatus.COMPLETED }
        val total = nodes.size
        val percentage = if (total > 0) (completed * 100) / total else 0

        _uiState.update { current ->
            current.copy(
                nodes = nodes,
                completedCount = completed,
                totalCount = total,
                masteryPercentage = percentage,
                selectedNode = current.selectedNode?.let { sel -> nodes.find { it.id == sel.id } }
            )
        }
    }

    fun selectNode(node: SkillNode) {
        _uiState.update { it.copy(selectedNode = node) }
    }

    fun dismissDetail() {
        _uiState.update { it.copy(selectedNode = null) }
    }
}
