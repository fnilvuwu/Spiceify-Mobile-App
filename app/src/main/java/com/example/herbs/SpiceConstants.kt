package com.example.herbs

object SpiceConstants {
    val classes = arrayOf(
        "Andaliman",
        "Cengkeh",
        "Daun Salam",
        "Jahe",
        "Kemiri",
        "Kencur",
        "Ketumbar",
        "Lengkuas",
        "daunjeruk",
        "kayumanis",
        "kunyit",
        "pala",
    )

    val classesText = arrayOf(
        "Andaliman",
        "Cengkeh",
        "Daun Salam",
        "Jahe",
        "Kemiri",
        "Kencur",
        "Ketumbar",
        "Lengkuas",
        "Daun Jeruk",
        "Kayu Manis",
        "Kunyit",
        "Pala",
    )

    val classToTextMap: Map<String, String> = classes.zip(classesText).toMap()
}