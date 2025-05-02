package com.mzhadan.app.diploma.ui.pages.viewers.pdf

import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
import android.os.Bundle
import android.os.ParcelFileDescriptor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.mzhadan.app.diploma.databinding.FragmentPdfViewerBinding
import java.io.File

class PdfViewerFragment : Fragment() {

    private lateinit var binding: FragmentPdfViewerBinding
    private var pdfRenderer: PdfRenderer? = null
    private var currentPage: PdfRenderer.Page? = null
    private var fileDescriptor: ParcelFileDescriptor? = null
    private var pageIndex = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPdfViewerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val path = arguments?.getString("path")
        binding.filenameTV.text = path

        if (path != null) {
            openRenderer(File(path))
            showPage(pageIndex)
        }

        binding.btnSave.setOnClickListener {
            if (pdfRenderer != null && pageIndex < pdfRenderer!!.pageCount - 1) {
                pageIndex++
                showPage(pageIndex)
            }
        }

        binding.btnEdit.setOnClickListener {
            if (pdfRenderer != null && pageIndex > 0) {
                pageIndex--
                showPage(pageIndex)
            }
        }
    }

    private fun openRenderer(file: File) {
        try {
            fileDescriptor = ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY)
            fileDescriptor?.let {
                pdfRenderer = PdfRenderer(it)
            }
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "Ошибка открытия PDF", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showPage(index: Int) {
        currentPage?.close()
        pdfRenderer?.let { renderer ->
            if (index < 0 || index >= renderer.pageCount) return
            currentPage = renderer.openPage(index)
            val width = resources.displayMetrics.densityDpi / 72 * currentPage!!.width
            val height = resources.displayMetrics.densityDpi / 72 * currentPage!!.height
            val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            currentPage!!.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
            binding.contentContainer.setImageBitmap(bitmap)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        currentPage?.close()
        pdfRenderer?.close()
        fileDescriptor?.close()
    }
}
