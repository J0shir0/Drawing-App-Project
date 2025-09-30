package com.buster.drawapp.model

import scalafx.scene.paint.Color
import java.io.Serializable

// Case class for immutability
@SerialVersionUID(100L)
case class FilledRectangle(colour: Color, x: Double, y: Double, size: Double) extends Serializable