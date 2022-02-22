package com.example.agecalculator

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.DatePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener {
    var cal = Calendar.getInstance()
    var bYear: Int = 0
    var bMonth: Int = 0
    var bDay: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Log.d("TEST", this::class.java.name + ".age")

        /*val formatter = SimpleDateFormat("dd/MM/yyyy")
        val birthDate = formatter.parse(birthDate.text.toString())*/

        var calMin = Calendar.getInstance()
        calMin.set(1980, 0, 1)

        var mToast = Toast.makeText(this, "Please, insert a date!", Toast.LENGTH_SHORT)

        birthDateTv.setOnClickListener{
            showDatePickerDialog(calMin)
        }

        calculateAgeBtn.setOnClickListener{
            var age = calculateAge(bDay, bMonth, bYear)

            // This first if check if today is your birthdate
            if(bDay == Calendar.getInstance().get(Calendar.DAY_OF_MONTH) && bMonth == Calendar.getInstance().get(Calendar.MONTH) + 1){
                if(bYear == Calendar.getInstance().get(Calendar.YEAR)){
                    sendIntent(age, -1)
                }else{
                    sendIntent(age, 1)
                }
            }else{
                // If the age is -1 that means the user didn't pick up a date yet
                if(age == -1){
                    mToast.cancel()
                    mToast = Toast.makeText(this, "Please, insert a date!", Toast.LENGTH_SHORT)
                    mToast.show()
                }
                else{
                    sendIntent(age, 0)
                }
            }
        }
    }


    /**
     * This function create and send the intent to the "ActualAge" activity
     *
     * @param age -> This is the age day of user
     * @param birthdate -> This indicate if is user's birthday or if the user is born today
     */
    fun sendIntent(age: Int, birthdate: Int){
        val intent = Intent(this, ActualAge::class.java).apply{
            putExtra("AGE", age)
            putExtra("BIRTHDATE", birthdate)
        }

        startActivity(intent)
    }


    /**
     * This function will be executed every time the user will select a new date from the DatePickerDialog pop-up
     *
     * @param view -> This is the DatePickerDialog this function has to check change on
     * @param year -> This is the year user selected in the DatePickerDialog pop-up
     * @param month -> This is the month user selected in the DatePickerDialog pop-up
     * @param dayOfMonth -> This is the day of month user selected in the DatePickerDialog pop-up
     */
    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        /* This three lines changes the Calendar variable "cal" so when user will generate a new DatePickerDialog pop-up
        him will see the last date he picked up
         */
        cal.set(Calendar.YEAR, year)
        cal.set(Calendar.MONTH, month)
        cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

        bDay = dayOfMonth
        bMonth = month + 1 // This +1 is because months of the DatePickerDialog starts with 0
        bYear = year

        val selectedDay = bDay.toString().padStart(2,'0')
        val selectedMonth = bMonth.toString().padStart(2,'0')
        val selectedYear = bYear.toString()

        birthDateTv.setText("$selectedDay/$selectedMonth/$selectedYear")
    }


    /**
     * This function show a DatePickerDialog pop-up
     *
     * @param calMin -> This is the minumum date you can pick on this DatePickerDialog
     */
    fun showDatePickerDialog(calMin: Calendar){
        Locale.setDefault(Locale.ITALY)

        val dpd: DatePickerDialog = DatePickerDialog(this, this,
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH))

        dpd.datePicker.maxDate = Date().time
        dpd.datePicker.minDate = calMin.timeInMillis
        dpd.show()
    }


    /**
     * This function calculate the age of user based on his date of birth and current date
     *
     * @param bDay -> This is the birthdate day of user
     * @param bMonth -> This is the birthdate month of user
     * @param bYear -> This is the birthdate yera of user
     */
    fun calculateAge(bDay: Int, bMonth: Int, bYear: Int): Int{
        val cYear = Calendar.getInstance().get(Calendar.YEAR)
        val cMonth = Calendar.getInstance().get(Calendar.MONTH) + 1
        val cDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)

        var age: Int = 0

        if(!birthDateTv.text.isEmpty()){
            if(bMonth > cMonth){
                age = (cYear - bYear) - 1
            }else{
                if(bMonth == cMonth){
                    if(bDay > cDay){
                        age = (cYear - bYear) - 1
                    }else{
                        age = cYear - bYear
                    }
                }else{
                    age = cYear - bYear
                }
            }
        }else{
            return -1
        }

        return age
    }
}