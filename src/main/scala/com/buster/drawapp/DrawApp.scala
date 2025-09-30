package com.buster.drawapp

import com.buster.drawapp.view.DrawingCanvasController
import javafx.{scene => jfxs}
import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafx.scene.image.Image
import scalafxml.core.{FXMLLoader, NoDependencyResolver}

object DrawApp extends JFXApp{
  var drawingCanvasController: Option[DrawingCanvasController#Controller] = None

  // locate the RootLayout resource
  val rootResource = getClass.getResource("view/RootLayout.fxml")

  // initialize loader object
  val loader = new FXMLLoader(rootResource, NoDependencyResolver)

  // load root layout from FXML file
  loader.load();

  // retrieve the root component BorderPane from the FXML file
  val roots = loader.getRoot[jfxs.layout.BorderPane]

  // initialize stage
  stage = new PrimaryStage {
    title = "Drawing App"
    icons += new Image(getClass.getResourceAsStream("/images/App-logo.png"))
    scene = new Scene {
      root = roots
      stylesheets = Seq(getClass.getResource("view/PaintTheme.css").toString)
    }
  }

  def showDrawingCanvas(): Unit = {
    val resource = getClass.getResource("view/DrawingCanvas.fxml")
    val loader = new FXMLLoader(resource, NoDependencyResolver)
    loader.load()
    drawingCanvasController = Option(loader.getController[DrawingCanvasController#Controller]())

    // retrieve root components
    val roots = loader.getRoot[jfxs.layout.AnchorPane]
    this.roots.setCenter(roots)
  }
  showDrawingCanvas()
}