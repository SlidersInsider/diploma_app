package com.mzhadan.app.diploma.ui.pages.viewers.pdf

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.mzhadan.app.diploma.databinding.FragmentPdfViewerBinding
import com.mzhadan.app.reader.PdfWorker
import java.io.File

class PdfViewerFragment : Fragment() {

    private lateinit var binding: FragmentPdfViewerBinding
    private var pdfRendererManager: PdfWorker? = null
    private var pageIndex = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPdfViewerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val path = arguments?.getString("path") ?: return
        binding.filenameTV.text = path

        try {
            val file = File(path)
            pdfRendererManager = PdfWorker(requireContext(), file)
            showPage(pageIndex)
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "Ошибка открытия PDF", Toast.LENGTH_SHORT).show()
        }

        binding.btnNextPage.setOnClickListener {
            pdfRendererManager?.let {
                if (pageIndex < it.pageCount - 1) {
                    pageIndex++
                    showPage(pageIndex)
                }
            }
        }

        binding.btnBackPage.setOnClickListener {
            if (pageIndex > 0) {
                pageIndex--
                showPage(pageIndex)
            }
        }

        binding.btnSave.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
            val file = File(path!!)
            if (file.exists()) {
                file.delete()
            }
        }
    }

    private fun showPage(index: Int) {
        pdfRendererManager?.renderPage(index)?.let { bitmap ->
            binding.contentContainer.setImageBitmap(bitmap)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        pdfRendererManager?.close()
    }
}
