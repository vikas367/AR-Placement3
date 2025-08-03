package com.example.arplacement

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isGone
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.ar.core.Config
import io.github.sceneview.ar.ArSceneView
import io.github.sceneview.ar.node.ArModelNode
import io.github.sceneview.ar.node.PlacementMode
import io.github.sceneview.math.Position
import android.view.MotionEvent
import com.google.ar.core.Plane



class ArActivity : AppCompatActivity() {

    private lateinit var sceneView: ArSceneView
    private lateinit var placeButton: ExtendedFloatingActionButton
    private lateinit var modelNode: ArModelNode

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_ar)
        val shape = intent.getStringExtra("shape") ?: "Cone"

        sceneView = findViewById<ArSceneView>(R.id.sceneView).apply {
            this.lightEstimationMode = Config.LightEstimationMode.DISABLED
            this.planeFindingMode = Config.PlaneFindingMode.HORIZONTAL
            this.depthEnabled = true



        }

        placeButton = findViewById(R.id.place)
        placeButton.setOnClickListener {
            placeModel()
        }

        val glbFile = when (shape.lowercase()) {
            "cone" -> "models/road_cone.glb"
            "cube" -> "models/cube.glb"
            else -> "models/road_cone.glb"  // default/fallback
        }

        modelNode = ArModelNode(sceneView.engine, PlacementMode.INSTANT).apply {
            loadModelGlbAsync(
                glbFileLocation = glbFile,
                scaleToUnits = 1f,
                centerOrigin = Position(-0.5f)
            ) {
                sceneView.planeRenderer.isVisible = true
            }

            onAnchorChanged = {
                placeButton.isGone = it != null
            }
        }

        sceneView.addChild(modelNode)


    }

    private fun placeModel() {
        modelNode.anchor()
        sceneView.planeRenderer.isVisible = true
    }

    }
