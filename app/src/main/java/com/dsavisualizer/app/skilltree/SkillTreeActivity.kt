package com.dsavisualizer.app.skilltree

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.dsavisualizer.app.CategoryActivity
import com.dsavisualizer.app.TechniqueDetailActivity
import com.dsavisualizer.app.skilltree.model.SkillNode
import com.dsavisualizer.app.skilltree.ui.SkillTreeScreen
import com.dsavisualizer.app.skilltree.viewmodel.SkillTreeViewModel
import com.dsavisualizer.app.ui.theme.DsaTheme

class SkillTreeActivity : ComponentActivity() {

    private val viewModel: SkillTreeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            DsaTheme {
                SkillTreeScreen(
                    viewModel = viewModel,
                    onNavigateBack = { finish() },
                    onLaunchVisualizer = { node -> launchVisualizer(node) }
                )
            }
        }
    }

    override fun onResume() {
        super.onResume()
        // Refresh tree so newly visited topics immediately update node status
        viewModel.refreshTree()
    }

    private fun launchVisualizer(node: SkillNode) {
        if (!node.techniqueId.isNullOrBlank()) {
            val intent = Intent(this, TechniqueDetailActivity::class.java).apply {
                putExtra(TechniqueDetailActivity.EXTRA_TECHNIQUE_ID, node.techniqueId)
            }
            startActivity(intent)
        } else {
            val intent = Intent(this, CategoryActivity::class.java).apply {
                putExtra(CategoryActivity.EXTRA_CATEGORY_ID, node.categoryId)
                putExtra(CategoryActivity.EXTRA_CATEGORY_TITLE, node.categoryTitle)
            }
            startActivity(intent)
        }
    }
}
