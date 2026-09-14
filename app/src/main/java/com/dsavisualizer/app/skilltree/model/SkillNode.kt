package com.dsavisualizer.app.skilltree.model

enum class SkillStatus {
    LOCKED,
    AVAILABLE,
    COMPLETED
}

data class SkillNode(
    val id: String,
    val title: String,
    val categoryId: String,
    val techniqueId: String? = null,
    val description: String,
    val tier: Int, // 1 to 5
    val prerequisiteIds: List<String> = emptyList(),
    val status: SkillStatus = SkillStatus.LOCKED,
    val categoryTitle: String = "",
    val iconName: String = "code"
)
