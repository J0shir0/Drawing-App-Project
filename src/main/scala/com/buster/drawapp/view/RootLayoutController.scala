package com.buster.drawapp.view

import com.buster.drawapp.DrawApp
import com.buster.drawapp.model.{ContactInfo, DeveloperContactInfo, SupportContactInfo}
import scalafx.scene.control.Alert
import scalafx.scene.control.Alert.AlertType
import scalafxml.core.macros.sfxml

@sfxml
class RootLayoutController {
  def handleSave(): Unit = {
    DrawApp.drawingCanvasController match { // handle the invoking of the method to save the image
      case Some(x) => x.saveAsPng()

      case None => println("Error saving the file")
    }
  }

  def handleAbout(): Unit = { // handle the display of the contact information
    val contacts: List[ContactInfo] = List(
      new DeveloperContactInfo(Some("Joshua Gnow"), "joshuagnow2019@gmail.com", "012-345-6789"),
      new SupportContactInfo("techsupport@help.com", "1800-123-456")
    )

    val contactInfo = contacts.map(_.getContactInfo()).mkString("\n\n")
    new Alert(AlertType.Information) {
      initOwner(DrawApp.stage)
      title = "About"
      headerText = "Application Information"
      contentText = contactInfo
    }.showAndWait()
  }
}