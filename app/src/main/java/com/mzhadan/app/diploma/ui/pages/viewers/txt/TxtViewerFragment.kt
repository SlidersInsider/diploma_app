package com.mzhadan.app.diploma.ui.pages.viewers.txt

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.mzhadan.app.diploma.databinding.FragmentTxtViewerBinding
import com.mzhadan.app.reader.TxtWorker
import java.io.File
import java.io.IOException

class TxtViewerFragment : Fragment() {
    private lateinit var binding: FragmentTxtViewerBinding
    private lateinit var editText: EditText

    private var isEditing = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTxtViewerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val path = arguments?.getString("path") ?: return
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

        try {
            editText.setText(TxtWorker.readFromFile(path))
        } catch (e: IOException) {
            Toast.makeText(requireContext(), "Ошибка чтения файла", Toast.LENGTH_SHORT).show()
        }

        binding.btnEdit.setOnClickListener {
            isEditing = !isEditing
            editText.isFocusable = isEditing
            editText.isFocusableInTouchMode = isEditing
            if (isEditing) editText.requestFocus()
        }

        binding.btnSave.setOnClickListener {
            try {
                TxtWorker.writeToFile(path, editText.text.toString())
                Toast.makeText(requireContext(), "Файл сохранен!", Toast.LENGTH_SHORT).show()
                requireActivity().supportFragmentManager.popBackStack()
                val file = File(path)
                if (file.exists()) {
                    file.delete()
                }

            } catch (e: IOException) {
                Toast.makeText(requireContext(), "Ошибка сохранения файла", Toast.LENGTH_SHORT).show()
            }
        }
    }

}