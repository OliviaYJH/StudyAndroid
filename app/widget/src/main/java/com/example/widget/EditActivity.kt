package com.example.widget

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.isVisible
import com.example.widget.databinding.ActivityEditBinding

class EditActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getMessage()
        createSpinner()
        createCalendar()
        checkCheckbox()
        saveData()
    }

    private fun getMessage() {
        val message = intent.getStringExtra("intentMessage") ?: "없음" // elvis operator
        Log.d("intentMessage", message)
    }

    // spinner
    private fun createSpinner() {
        binding.bloodTypeSpinner.adapter = ArrayAdapter.createFromResource(
            this,
            R.array.blood_types,
            android.R.layout.simple_list_item_1
        )
    }

    // calendar
    private fun createCalendar() {
        binding.birthdateLayer.setOnClickListener {
            val listener = OnDateSetListener { date, year, month, dayOfMonth ->
                binding.birthdateValueTextView.text = "$year-${month.inc()}-$dayOfMonth"
            }

            // datePicker 사용
            DatePickerDialog(
                this,
                listener,
                2000, 1, 1
            ).show()
        }
    }

    // checkbox
    private fun checkCheckbox() {
        binding.warningEditText.isVisible = binding.warningCheckBox.isChecked

        binding.warningCheckBox.setOnCheckedChangeListener { buttonView, isChecked ->
            binding.warningEditText.isVisible = isChecked
        }
    }

    // save
    private fun saveData() {
        binding.saveButton.setOnClickListener {
            /*
            val editor = getSharedPreferences("userInformation", Context.MODE_PRIVATE).edit() // 생성한 앱에서만 작동 가능

            editor.putString("name", binding.nameEditText.text.toString())
            editor.putString("bloodType", )
            editor.putString("emergencyContact", binding.emergencyContactEditText.text.toString())
            editor.putString("birthDate", binding.birthdateTextView.text.toString())
            editor.putString("warning", )

            editor.apply()*/

            // ScopeFunction 사용 시
            with(getSharedPreferences(USER_INFORMATION, Context.MODE_PRIVATE).edit()) {

                putString(NAME, binding.nameEditText.text.toString())
                putString(BLOOD_TYPE, getBloodType())
                putString(EMERGENCY_CONTACT, binding.emergencyContactEditText.text.toString())
                putString(BIRTHDATE, binding.birthdateTextView.text.toString())
                putString(WARNING, getWarning())

                apply()
            }

            Toast.makeText(this, "저장을 완료했습니다", Toast.LENGTH_SHORT).show()
        }

        finish()
    }

    private fun getBloodType(): String {
        val bloodAlphabet = binding.bloodTypeSpinner.selectedItem.toString()
        val bloodSign = if (binding.bloodTypePlus.isChecked) "+" else "-"
        return "$bloodSign$bloodAlphabet"
    }

    private fun getWarning(): String =
        if (binding.warningCheckBox.isChecked) binding.warningEditText.text.toString() else ""

}