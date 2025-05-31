package com.mzhadan.app.reader

import org.apache.poi.xwpf.usermodel.XWPFDocument
import java.io.File
import java.io.FileInputStream

object DocxWorker {

    @Throws(Exception::class)
    fun convertDocxToHtml(filePath: String): String {
        val file = File(filePath)
        FileInputStream(file).use { inputStream ->
            val document = XWPFDocument(inputStream)

            val htmlBuilder = StringBuilder()
            htmlBuilder.append("<html><body style='padding:16px;'>")

            for (paragraph in document.paragraphs) {
                htmlBuilder.append("<p>")
                for (run in paragraph.runs) {
                    val text = run.text() ?: continue
                    val style = StringBuilder()

                    if (run.isBold) style.append("font-weight:bold;")
                    if (run.isItalic) style.append("font-style:italic;")
                    if (run.underline.name != "NONE") style.append("text-decoration:underline;")
                    if (run.fontSize > 0) style.append("font-size:${run.fontSize}px;")
                    if (run.fontFamily != null) style.append("font-family:${run.fontFamily};")

                    htmlBuilder.append("<span style='$style'>$text</span>")
                }
                htmlBuilder.append("</p>")
            }

            htmlBuilder.append("</body></html>")
            return htmlBuilder.toString()
        }
    }
}
