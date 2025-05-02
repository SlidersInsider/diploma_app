package com.mzhadan.app.diploma.ui.pages.viewers.word
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.mzhadan.app.diploma.databinding.FragmentWordViewerBinding
import org.apache.poi.xwpf.usermodel.XWPFDocument
import org.apache.poi.xwpf.usermodel.XWPFParagraph
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class WordViewerFragment : Fragment() {
    private lateinit var binding: FragmentWordViewerBinding
    private lateinit var editText: EditText

    private var isEditing = false
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
        binding.filenameTV.text = path

        editText = EditText(requireContext()).apply {
            layoutParams = FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            isFocusable = false
            isFocusableInTouchMode = false
            isSingleLine = false
            setPadding(16, 16, 16, 16)
        }
        binding.contentContainer.addView(editText)

        path?.let { readDocx(it) }

        binding.btnEdit.setOnClickListener {
            isEditing = !isEditing
            editText.isFocusable = isEditing
            editText.isFocusableInTouchMode = isEditing
            if (isEditing) editText.requestFocus()
        }

        binding.btnSave.setOnClickListener {
            if (isEditing && path != null) {
                saveDocx(path!!, editText.text.toString())
                Toast.makeText(requireContext(), "Файл сохранен!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Чтение документа с сохранением структуры
    private fun readDocx(path: String) {
        try {
            val file = File(path)
            val inputStream = FileInputStream(file)
            val document = XWPFDocument(inputStream)
            val paragraphs = document.paragraphs
            val text = StringBuilder()

            // Считывание текста и стилей
            for (paragraph in paragraphs) {
                text.append(readParagraph(paragraph))
                text.append("\n") // Добавляем перенос строки между параграфами
            }

            editText.setText(text.toString())
            inputStream.close()
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "Ошибка чтения файла", Toast.LENGTH_SHORT).show()
            e.printStackTrace()
        }
    }

    // Метод для чтения каждого параграфа с сохранением его стилей
    private fun readParagraph(paragraph: XWPFParagraph): String {
        val text = StringBuilder()

        // Проходим по всем "run" в параграфе (каждая часть текста с одинаковым стилем)
        for (run in paragraph.runs) {
            // Сохраняем текст с применением стилей
            val runText = run.text()
            if (run.isBold) {
                text.append("**$runText**") // Жирный текст (можно форматировать по-другому)
            } else if (run.isItalic) {
                text.append("*$runText*") // Курсив
            } else {
                text.append(runText)
            }
        }

        return text.toString()
    }

    // Сохранение документа с сохранением структуры
    private fun saveDocx(path: String, content: String) {
        try {
            val file = File(path)
            val outputStream = FileOutputStream(file)
            val document = XWPFDocument()

            // Разбиваем текст по параграфам и сохраняем каждый параграф с его стилями
            val lines = content.split("\n")
            for (line in lines) {
                val paragraph = document.createParagraph()
                val run = paragraph.createRun()

                // Применение стилей к сохраненному тексту
                when {
                    line.contains("**") -> { // Пример для жирного текста
                        run.isBold = true
                        run.setText(line.replace("**", ""))
                    }
                    line.contains("*") -> { // Пример для текста в курсе
                        run.isItalic = true
                        run.setText(line.replace("*", ""))
                    }
                    else -> run.setText(line)
                }
            }

            // Запись в файл
            document.write(outputStream)
            outputStream.close()
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "Ошибка сохранения файла", Toast.LENGTH_SHORT).show()
            e.printStackTrace()
        }
    }
}
