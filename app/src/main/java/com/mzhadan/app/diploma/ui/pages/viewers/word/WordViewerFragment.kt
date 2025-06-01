package com.mzhadan.app.diploma.ui.pages.viewers.word

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.mzhadan.app.diploma.databinding.FragmentWordViewerBinding
import com.mzhadan.app.reader.DocxWorker
import java.io.File

class WordViewerFragment : Fragment() {
    private lateinit var binding: FragmentWordViewerBinding
    private lateinit var webView: WebView
    private var path: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWordViewerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        path = arguments?.getString("path") ?: return
        binding.filenameTV.text = path ?: "Файл не найден"

        webView = WebView(requireContext()).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            settings.javaScriptEnabled = true
            webViewClient = WebViewClient()
        }

        binding.contentContainer.addView(webView)

        binding.btnSave.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
            val file = File(path!!)
            if (file.exists()) {
                file.delete()
            }
        }

        path?.let {
            try {
                val html = DocxWorker.convertDocxToHtml(it)
                webView.loadDataWithBaseURL(null, html, "text/html", "UTF-8", null)
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Ошибка отображения документа", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
