package ru.vladikadiroff.pagination.ui.activities

import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.core.view.updatePadding
import androidx.navigation.findNavController
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback
import dagger.hilt.android.AndroidEntryPoint
import ru.vladikadiroff.pagination.R
import ru.vladikadiroff.pagination.databinding.ActivityMainBinding
import ru.vladikadiroff.pagination.utils.extensions.doOnApplyWindowInsets

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onSupportNavigateUp() = findNavController(R.id.navHost).navigateUp()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initToolbar()
    }

    private fun initToolbar() {
        setSupportActionBar(binding.toolbar)
        binding.appBar.doOnApplyWindowInsets { toolbar, insets, _ ->
            toolbar.updatePadding(
                top = insets.getInsets(WindowInsetsCompat.Type.statusBars()).top
            )
            insets
        }
    }

    fun showToolbar(show: Boolean = true) {
        binding.appBar.isVisible = show
    }

}