package com.example.arplacement

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.MediaPlayer
import android.net.Uri
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.isGone
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.ar.core.Config
import io.github.sceneview.ar.ArSceneView
import io.github.sceneview.ar.node.ArModelNode
import io.github.sceneview.ar.node.AugmentedImageNode
import io.github.sceneview.ar.node.PlacementMode
import io.github.sceneview.material.setExternalTexture
import io.github.sceneview.math.Position
import io.github.sceneview.math.Rotation
import io.github.sceneview.node.VideoNode

class MainActivity : AppCompatActivity() {

    private lateinit var listView: ListView
    private lateinit var playButton: Button
    private val shapes = listOf("Cone", "Cube")
    private var selectedPosition: Int = -1  // -1 means nothing selected


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        listView = findViewById(R.id.shapeListView)
        playButton = findViewById(R.id.playButton)

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_single_choice, shapes)
        listView.adapter = adapter

        // Update selectedPosition when an item is clicked
        listView.setOnItemClickListener { _, _, position, _ ->
            selectedPosition = position
        }

        playButton.setOnClickListener {
            if (selectedPosition != -1) {
                val selectedShape = shapes[selectedPosition]
                val intent = Intent(this, ArActivity::class.java)
                intent.putExtra("shape", selectedShape)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Please select a shape first", Toast.LENGTH_SHORT).show()
            }
        }
    }
}