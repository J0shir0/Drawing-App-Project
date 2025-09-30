package com.buster.drawapp.model

class SupportContactInfo(val contactEmail: String,
                         val contactPhone: String
                        ) extends ContactInfo {

  val name: Option[String] = None

  override def getContactInfo(): String = {
    s"Technical Support\nEmail: $contactEmail\nPhone No: $contactPhone"
  }
}