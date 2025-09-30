package com.buster.drawapp.model

trait ContactInfo {
  val name: Option[String]
  val contactEmail: String
  val contactPhone: String

  def getContactInfo(): String = {
    name match {
      case Some(n) => s"Name: $n\nEmail: $contactEmail\nPhone No: $contactPhone"
      case None => s"Email: $contactEmail\nPhone No: $contactPhone"
    }
  }
}