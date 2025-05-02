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
import org.apache.poi.xwpf.usermodel.XWPFDocument
import java.io.File
import java.io.FileInputStream

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
        path = arguments?.getString("path")
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

        path?.let { loadDocxAsHtml(it) }
    }

    private fun loadDocxAsHtml(filePath: String) {
        try {
            val file = File(filePath)
            val inputStream = FileInputStream(file)
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
            inputStream.close()

            webView.loadDataWithBaseURL(null, htmlBuilder.toString(), "text/html", "UTF-8", null)

        } catch (e: Exception) {
            Toast.makeText(requireContext(), "Ошибка отображения документа", Toast.LENGTH_SHORT).show()
            e.printStackTrace()
        }
    }
}
