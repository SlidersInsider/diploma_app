package com.mzhadan.app.reader

import android.content.Context
import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
import android.os.ParcelFileDescriptor
import java.io.Closeable
import java.io.File

class PdfWorker(private val context: Context, private val file: File) : Closeable {

    private var fileDescriptor: ParcelFileDescriptor? = null
    private var pdfRenderer: PdfRenderer? = null
    private var currentPage: PdfRenderer.Page? = null

    val pageCount: Int
        get() = pdfRenderer?.pageCount ?: 0

    init {
        openRenderer()
    }

    private fun openRenderer() {
        fileDescriptor = ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY)
        pdfRenderer = PdfRenderer(fileDescriptor!!)
    }

    fun renderPage(index: Int): Bitmap? {
        currentPage?.close()

        val renderer = pdfRenderer ?: return null
        if (index < 0 || index >= renderer.pageCount) return null

        currentPage = renderer.openPage(index)

        val dpi = context.resources.displayMetrics.densityDpi
        val width = dpi / 72 * currentPage!!.width
        val height = dpi / 72 * currentPage!!.height

        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        currentPage!!.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
        return bitmap
    }

    override fun close() {
        currentPage?.close()
        pdfRenderer?.close()
        fileDescriptor?.close()
    }
}
