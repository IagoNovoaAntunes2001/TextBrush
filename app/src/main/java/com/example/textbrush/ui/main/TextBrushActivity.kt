package com.example.textbrush.ui.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.textbrush.R
import com.example.textbrush.databinding.ActivityTextBrushBinding
import com.example.textbrush.ui.utils.CustomCanvasView

class TextBrushActivity : AppCompatActivity() {

    companion object {
        private const val EMPTY = ""
    }

    private lateinit var binding: ActivityTextBrushBinding
    private lateinit var canvasView: CustomCanvasView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTextBrushBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        canvasView = binding.canvasView
        setupListeners()
    }

    private fun setupListeners() {
        setupFontButton()
        setupSpaceButton()
        setupRotationButton()
        undoButton()
    }

    private fun setupFontButton() {
        binding.fontButton.setOnClickListener {
            binding.textInput.hint = getString(R.string.font_size)
            setupTextClick()
        }
    }

    private fun setupTextClick() = when {
        binding.textInput.isVisible -> showInput()
        else -> hideInput()
    }

    private fun showInput() {
        setupNewFontSize()
        binding.textInput.setText(EMPTY)
        binding.textInput.visibility = View.GONE
    }

    private fun hideInput() {
        binding.textInput.requestFocus()
        binding.textInput.visibility = View.VISIBLE
    }

    private fun setupSpaceButton() {
        binding.spaceButton.setOnClickListener {
            binding.textInput.hint = getString(R.string.spacing_button)
            updateSpacing()
        }
    }

    private fun updateSpacing() = when {
        binding.textInput.isVisible -> showSpaceInput()
        else -> hideSpaceInput()
    }

    private fun showSpaceInput() {
        setupNewSpace()
        binding.textInput.setText(EMPTY)
        binding.textInput.visibility = View.GONE
    }

    private fun hideSpaceInput() {
        binding.textInput.requestFocus()
        binding.textInput.visibility = View.VISIBLE
    }

    private fun setupNewSpace() {
        val spaceText = binding.textInput.text.toString()
        if (spaceText.isNotBlank()) {
            val space = spaceText.toFloat()
            canvasView.updateSpacing(space)
        }
    }

    private fun setupRotationButton() {
        binding.rotationButton.setOnClickListener {
            binding.textInput.hint = getString(R.string.rotation_button)
            updateRotation()
        }
    }

    private fun updateRotation() = when {
        binding.textInput.isVisible -> showRotationInput()
        else -> hideRotationInput()
    }

    private fun showRotationInput() {
        setupNewRotation()
        binding.textInput.setText(EMPTY)
        binding.textInput.visibility = View.GONE
    }

    private fun setupNewRotation() {
        val rotationText = binding.textInput.text.toString()
        if (rotationText.isNotBlank()) {
            val rotation = rotationText.toFloat()
            canvasView.updateRotation(rotation)
        }
    }

    private fun hideRotationInput() {
        binding.textInput.requestFocus()
        binding.textInput.visibility = View.VISIBLE
    }

    private fun setupNewFontSize() {
        val fontSizeText = binding.textInput.text.toString()
        if (fontSizeText.isNotBlank()) {
            val newFontSize = fontSizeText.toFloat()
            canvasView.updateFontSize(newFontSize)
        }
    }

    private fun undoButton() {
        binding.undoButton.setOnClickListener {
            canvasView.undoLastDrawnSegment()
        }
    }
}