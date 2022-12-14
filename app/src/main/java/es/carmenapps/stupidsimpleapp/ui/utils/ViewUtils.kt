package es.carmenapps.stupidsimpleapp.ui.utils

import android.app.DatePickerDialog
import android.content.Context
import android.view.View
import android.widget.EditText
import java.text.SimpleDateFormat
import java.util.*


fun View?.hide() {
  this?.visibility = View.GONE
}

fun View?.show() {
  this?.visibility = View.VISIBLE
}

fun View?.setVisible(condition: Boolean) {
  if (condition) {
    this.show()

  } else {
    this.hide()
  }
}

fun EditText.transformIntoDatePicker(context: Context?, format: String, maxDate: Date? = null) {
  isFocusableInTouchMode = false
  isClickable = true
  isFocusable = false

  val myCalendar = Calendar.getInstance()
  val datePickerOnDataSetListener =
    DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
      myCalendar.set(Calendar.YEAR, year)
      myCalendar.set(Calendar.MONTH, monthOfYear)
      myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
      val sdf = SimpleDateFormat(format, Locale.getDefault())
      setText(sdf.format(myCalendar.time))
    }

  setOnClickListener {
    if (context != null) {
      DatePickerDialog(
        context, datePickerOnDataSetListener, myCalendar
          .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
        myCalendar.get(Calendar.DAY_OF_MONTH)
      ).run {
        maxDate?.time?.also { datePicker.maxDate = it }
        show()
      }
    }
  }
}