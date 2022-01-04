package com.example.comptest

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.comptest.databinding.DialogCustomDatePickerBinding
import com.example.comptest.ui.MonthFragment
import java.text.SimpleDateFormat
import java.util.*

class CustomDatePickerDialog(private val fragmentActivity: FragmentActivity, private val listener: DatePickerDialog.OnDateSetListener?,
                             year: Int, month: Int, dayOfMonth: Int)
    : AlertDialog(fragmentActivity), DialogInterface.OnClickListener{

    private var calendar: Calendar = Calendar.getInstance()

    private lateinit var binding: DialogCustomDatePickerBinding

    init {
        calendar.set(year, month, dayOfMonth)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogCustomDatePickerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.monthsViewPager.adapter = CalendarViewPagerAdapter(fragmentActivity, calendar, listener)
        binding.monthsViewPager.setCurrentItem(600, false)
    }

    override fun onClick(p0: DialogInterface?, p1: Int) {
        TODO("Not yet implemented")
    }

    class CalendarViewPagerAdapter(private val fragmentActivity: FragmentActivity, private val calendar: Calendar, private val listener: DatePickerDialog.OnDateSetListener?)
        : FragmentStateAdapter(fragmentActivity), OnDateChangeListener {

        private val MIDDLE_OF_ALL_MONTHS = 600

        private val actualDateCheckCalendar = Calendar.getInstance()

        private var selectedYear = actualDateCheckCalendar.get(Calendar.YEAR)
        private var selectedMonth = actualDateCheckCalendar.get(Calendar.MONTH)
        private var selectedDayOfMonth = actualDateCheckCalendar.get(Calendar.DAY_OF_MONTH)

        override fun getItemCount() = 12 * 100   // Count of all months

        override fun createFragment(position: Int): Fragment {

            val scrolledToPosition = MIDDLE_OF_ALL_MONTHS - position

            val monthToShowCalendar = setMonth(scrolledToPosition)

            val today = actualDateCheckCalendar.get(Calendar.DAY_OF_MONTH)

            val monthStartsAtDay = monthToShowCalendar.getFirstDayNameOfFirstWeek()
            val daysInMonth = monthToShowCalendar.getActualMaximum(Calendar.DAY_OF_MONTH)

            val monthFragment = if (monthToShowCalendar.get(Calendar.YEAR) == actualDateCheckCalendar.get(Calendar.YEAR) &&     // Today selection
                monthToShowCalendar.get(Calendar.MONTH) == actualDateCheckCalendar.get(Calendar.MONTH)
                    ) MonthFragment(monthStartsAtDay, daysInMonth, today)
            else MonthFragment(monthStartsAtDay, daysInMonth)

            if(selectedYear == monthToShowCalendar.get(Calendar.YEAR)                                   // User date selection
                && selectedMonth == monthToShowCalendar.get(Calendar.MONTH)){
                    monthFragment.year = monthToShowCalendar.get(Calendar.YEAR)
                    monthFragment.month = monthToShowCalendar.get(Calendar.MONTH)
                    monthFragment.selectedDay = selectedDayOfMonth
            }
            else {
                monthFragment.year = monthToShowCalendar.get(Calendar.YEAR)
                monthFragment.month = monthToShowCalendar.get(Calendar.MONTH)
                monthFragment.selectedDay = 0
            }

            monthFragment.setOnDateChangeListener(this)

            return monthFragment
        }

        private fun Calendar.getFirstDayNameOfFirstWeek(): Int {
            val simpleDateFormat = SimpleDateFormat("u")

            val checkCalendar = Calendar.getInstance()
            checkCalendar.set(this.get(Calendar.YEAR), this.get(Calendar.MONTH), 1)

            return simpleDateFormat.format(checkCalendar.time).toInt()
        }

        private fun setMonth(diff: Int): Calendar{

            var year = calendar.get(Calendar.YEAR)
            var month = calendar.get(Calendar.MONTH)
            val day = 1

            when {
                month - diff == 12 -> {
                    month = Calendar.JANUARY
                    year++
                }
                month - diff == -1 -> {
                    month = Calendar.DECEMBER
                    year--
                }
                else -> {
                    month -= diff
                }
            }

            val setMonthCalendar = Calendar.getInstance()
            setMonthCalendar.set(year, month, day)

            return setMonthCalendar
        }

        override fun onChangeDate(changedYear: Int, changedMonth: Int, changedDayOfMonth: Int) {
            selectedYear = changedYear
            selectedMonth = changedMonth
            selectedDayOfMonth = changedDayOfMonth
        }
    }
}