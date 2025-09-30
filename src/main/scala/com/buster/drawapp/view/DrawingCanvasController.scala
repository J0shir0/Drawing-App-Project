package com.buster.drawapp.view

import application.DrawAppModel
import com.buster.drawapp.DrawApp.stage
import scalafx.Includes._
import scalafx.event.ActionEvent
import scalafx.scene.canvas._
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.control.{Alert, ColorPicker, TextField}
import scalafx.scene.image.WritableImage
import scalafx.scene.input._
import scalafx.scene.paint.Color
import scalafx.stage.FileChooser
import scalafxml.core.macros.sfxml

import java.awt.image.BufferedImage
import javax.imageio.ImageIO
import java.io.File
import java.io.IOException

@sfxml
class DrawingCanvasController(
                               private val canvas: Canvas,
                               private var colourChooser: ColorPicker,
                               private var bsize: TextField
                             ){

  val model: DrawAppModel = new DrawAppModel
  var brush: GraphicsContext = canvas.graphicsContext2D
  var brushSelected = false
  var eraserSelected = true

  init() // Call the initializer method

  def init(): Unit = {
    // Set up the graphics context
    brush = canvas.graphicsContext2D

    // Set ColorPicker's initial value to black
    colourChooser.value = Color.Black

    // Synchronize the model's color with the ColorPicker's initial value
    model.currentColour = colourChooser.value.value

    // Set the brush's initial fill color
    brush.setFill(model.currentColour)

    // Add listener to bsize TextField
    bsize.text.onChange { (_, _, _) =>
      updateBrushSize()
    }

    // Set initial brush size
    bsize.text = model.size.toString

    // Set up event handlers
    colourChooser.onAction = (event: ActionEvent) => handleColourChooser(event)
    canvas.onMouseDragged = (event: MouseEvent) => mouseDragged(event)

    // Set initial states
    brushSelected = true
    eraserSelected = false
  }

  def handleColourChooser(colourEvent: ActionEvent): Unit = { // handle the colour of the brush
    model.currentColour = colourChooser.value.value
    brush.setFill(model.currentColour)
  }

  def mouseDragged(event: MouseEvent): Unit = { // logic for drawing
    val size = model.size
    if (brushSelected && !eraserSelected) {
      brush.fillRoundRect(event.x, event.y, size, size, size, size)
      model.addPoint(event.x, event.y, size)
    } else if (eraserSelected && !brushSelected) {
      brush.clearRect(event.x, event.y, size, size)
    }
  }

  def updateBrushSize(): Unit = { // handles the brush size
    if (!bsize.text.value.isEmpty) {
      try {
        val size = bsize.text.value.toDouble
        model.size = size // Update the brush size in the model
      } catch {
        case _: NumberFormatException =>
          val alert = new Alert(AlertType.Warning) {
            initOwner(stage)
            title = "Invalid Brush Size"
            headerText = "Value Error"
            contentText = "Please enter a valid number for the brush size."
          }
          alert.showAndWait()

          // Optionally, reset the text field to a default value or clear it
          bsize.text = ""
      }
    }
  }


  def brushSelected(brushEvent: MouseEvent): Unit = { // When brush is selected, set the eraser to false so that only one is available to use
    brushSelected = true
    eraserSelected = false
  }

  def eraserSelected(eraserEvent: MouseEvent): Unit = {
    eraserSelected = true
    brushSelected = false
  }

  def clearCanvas(clearCanvasEvent: MouseEvent): Unit = { // logic for clearing the canvas
    brush.clearRect(0, 0, canvas.width.value, canvas.height.value)
  }

  def saveAsPng(): Unit = { // handles the file saving logic
    val fileChooser = new FileChooser {
      title = "Save Drawing as PNG"
      extensionFilters.add(new FileChooser.ExtensionFilter("PNG Files", "*.png"))
    }

    val file = fileChooser.showSaveDialog(canvas.getScene.getWindow)
    if (file != null) {
      val writableImage = new WritableImage(canvas.width.toInt, canvas.height.toInt)
      canvas.snapshot(null, writableImage)

      val bufferImage = new BufferedImage(canvas.width.toInt, canvas.height.toInt, BufferedImage.TYPE_INT_ARGB)
      val pixelReader = writableImage.pixelReader.get

      for {
        y <- 0 until canvas.height.toInt
        x <- 0 until canvas.width.toInt
      } {
        val argb = pixelReader.getArgb(x, y)
        bufferImage.setRGB(x, y, argb)
      }

      try {
        val outputFile = new File(file.getAbsolutePath)
        ImageIO.write(bufferImage, "png", outputFile)
        println(s"Image saved successfully to ${file.getAbsolutePath}")
      } catch {
        case ex: IOException =>
          println(s"Error saving image to ${file.getAbsolutePath}: ${ex.getMessage}")
          ex.printStackTrace()
      }
    }
  }
}