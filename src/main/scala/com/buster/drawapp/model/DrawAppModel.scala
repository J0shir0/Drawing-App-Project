package application

import com.buster.drawapp.model.FilledRectangle
import scala.collection.mutable.ArrayBuffer
import scalafx.scene.paint.Color
import java.io.Serializable

class DrawAppModel extends Serializable{

  // Initialize the variables
  var currentColour: Color = Color.Black // default colour is initialized to black
  var size: Double = 10.0 // default brush size is initialized to 10

  // Initialize the array buffer to store rectangles
  private val rectangles: ArrayBuffer[FilledRectangle] = ArrayBuffer.empty[FilledRectangle]

  // Method to add a point, represented by a rectangle, to the collection
  def addPoint(x: Double, y: Double, size: Double): Unit = {
    rectangles += FilledRectangle(currentColour, x, y, size)
  }
}