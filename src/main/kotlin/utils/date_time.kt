package utils


import android.os.Build
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.reflect.jvm.internal.impl.descriptors.Visibilities.Local

object DateTimeUtils {
    /**
     * [uiDateFormat] which format date in Thursday, 07-Jun-2020
     * */
    const val uiDateFormat = "EEEE, dd-MMM-yyyy"
    const val uiDateFormat_ = "EEE, dd-MMM-yyyy"
    const val ddmmyyyy = "dd-MM-yyyy_hh_mm"
    const val mmddyyyy = "MM-dd-yyyy"
    const val FORMAT_EE_DD_MMM_YYYY = "EE, dd-MMM-yyyy"
    const val FORMAT_DD_MMM_YYYY = "dd-MMM-yyyy"
    const val FORMAT_DD_MMM_YYYY_HH_12HRS = "dd-MMM-yyyy hh:mm a"
    const val FORMAT_DD_MM_YYYY_HH_12HRS = "dd/MM/yyyy hh:mm a"
    const val FORMAT_MM_DD_YYYY_HH_12HRS = "MM/dd/yyyy hh:mm:ss a"
    const val FORMAT_DD_MMM_YYYY_HH_mm_ss_12HRS = "dd-MMM-yyyy hh:mm:ss a"
    const val FORMAT_DD_MMM_YYYY_HH = "dd-MMM-yyyy hh:mm"

    const val FORMAT_EEE_DD_MMM = "EEE, dd-MMM"
    const val FORMAT_DD_MMMM = "dd-MMMM"
    const val FORMAT_DD_MMM = "dd-MMM"
    const val FORMAT_HH_MM_A = "hh:mm a"
    const val FORMAT_HH_MM_A_DD_MMM_YYYY = "HH:mm a | dd-MMM-yyyy"
    const val FORMAT_HH_MM_A_12_HRS = "hh:mm a"
    const val FORMAT_DD_MMM_HH_MM_A = "dd-MMM, HH:mm a"
    const val slash_mmddyyyy = "MM/dd/yyyy"
    const val slash_ddmmyyyy = "dd/MM/yyyy"
    const val FORMAT_YEAR = "yy"

    const val FORMAT_MMM_YYYY = "MMM, yyyy"
    const val FORMAT_YYYY_MM_DD = "yyyyMMdd"
    const val FORMAT_DD_MM_YY = "dd/MM/yy"
    const val FORMAT_YYYY_DD_MM = "yyyy-dd-MM"
    const val FORMAT_YYYY_mm_DD = "yyyy-MM-dd"
    const val FORMAT_dd_MM_yyyy_HH_mm_ss = "dd/MM/yyyy HH:mm:ss"

    /**
     * Change today's date in [uiDateFormat]
     *
     * @return string with today's date in [uiDateFormat]]
     * */
    const val serverDateFormat = "yyyy-MM-dd'T'HH:mm:ss"
    const val FORMAT_yyyy_mm_dd = "yyyy-MM-dd"


    fun epochTime(): Long = System.currentTimeMillis() / 1000

    fun isTodayEpoch(epochTime: Long): Boolean {
        val now = Calendar.getInstance()
        val calendar = Calendar.getInstance().apply {
            timeInMillis = epochTime * 1000     // eppoch time in millies
        }
        return now.get(Calendar.YEAR) == calendar.get(Calendar.YEAR) &&
                now.get(Calendar.MONTH) == calendar.get(Calendar.MONTH) &&
                now.get(Calendar.DAY_OF_MONTH) == calendar.get(Calendar.DAY_OF_MONTH)
    }


    fun formatterFrom(pattern: String): SimpleDateFormat = SimpleDateFormat(pattern)

    fun dayDiffFromCurrentTo(start: String?): Long {
        start?.let {
            val myFormat = SimpleDateFormat(serverDateFormat)
            try {
                val calendar = Calendar.getInstance()
                val finalDate = myFormat.parse(start)
                val diff = calendar.timeInMillis - (finalDate?.time ?: 0)
                TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS).let {
                    println("date $start days $it")
                    return it
                }
            } catch (e: ParseException) {
                e.printStackTrace()
                return 0
            }
        } ?: return 0
    }



    fun isDayChanged(appTimeZone: TimeZone, startDayMillis: Long): Boolean {
        val currentCalendar = Calendar.getInstance(appTimeZone)
        currentCalendar.timeInMillis = System.currentTimeMillis()

        val startDayCalendar = Calendar.getInstance(appTimeZone)
        startDayCalendar.timeInMillis = startDayMillis

        if (startDayMillis > 0) {
            return when {
                currentCalendar[Calendar.YEAR] != startDayCalendar[Calendar.YEAR] -> true
                currentCalendar[Calendar.MONTH] != startDayCalendar[Calendar.MONTH] -> true
                currentCalendar[Calendar.DAY_OF_MONTH] != startDayCalendar[Calendar.DAY_OF_MONTH] -> true
                else -> false
            }
        }
        return false
    }

    fun dayDifference(recent: Calendar, old: Calendar): Long {
        return (trimTimePart(
            recent
        ).timeInMillis - trimTimePart(
            old
        ).timeInMillis) / (1000 * 60 * 60 * 24)
    }

    private fun trimTimePart(dateValue: Calendar): Calendar {
        dateValue.set(Calendar.HOUR_OF_DAY, 0)
        dateValue.set(Calendar.MINUTE, 0)
        dateValue.set(Calendar.SECOND, 0)
        dateValue.set(Calendar.MILLISECOND, 0)
        return dateValue
    }

    fun stringFormatToDate(date: String, format: String): Date {
        return SimpleDateFormat(format, Locale.ENGLISH).parse(date) ?: Date()
    }

    fun dateToStringFormat(date: Date, format: String): String {
        val sdf = SimpleDateFormat(format, Locale.ENGLISH)
        return sdf.format(date)
    }



    fun getDateStringFromString(
        dateStr: String?,
        currentDateFormat: String,
        reqDateFormat: String
    ): String {
        if (dateStr == null) return ""
        val format = SimpleDateFormat(currentDateFormat, Locale.ENGLISH)
        var date: Date? = null
        try {
            date = format.parse(dateStr)
            val ddFor = SimpleDateFormat(reqDateFormat, Locale.ENGLISH)
            return ddFor.format(date!!)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return ""
    }

    fun getNextDateAndMonthString(
        dateStr: String?,
        currentDateFormat: String
    ): String {
        if (dateStr == null) return ""
        var returnString = ""
        val calendar = Calendar.getInstance()
        try {
            val format = SimpleDateFormat(currentDateFormat, Locale.ENGLISH)
            var date: Date? = null
            date = format.parse(dateStr)
            date.let {
                calendar.time = it
                calendar.add(Calendar.DATE, 1)
                val monthName = SimpleDateFormat("MMMM", Locale.ENGLISH).format(calendar.time)
                returnString = "${calendar.get(Calendar.DAY_OF_MONTH)} ${monthName}"
            }
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return returnString
    }


    @Synchronized
    fun targetTimeIsBefore(timeTargetInHHMM: String?, achieved: Long): Boolean {
        //target will be like 09:30 pm
        timeTargetInHHMM?.let {
            val sdf = SimpleDateFormat(FORMAT_DD_MMM_YYYY, Locale.ENGLISH)
            val ddMMM = sdf.format(Date()) //dd-MMM
            println("targetTimeIsBefore Target date $ddMMM")

            val sdfddMMHH = SimpleDateFormat(FORMAT_DD_MMM_YYYY_HH_12HRS, Locale.ENGLISH)
            sdfddMMHH.parse("$ddMMM $timeTargetInHHMM")?.let {
                println("targetTimeIsBefore Target date $it")
                println("targetTimeIsBefore Achieved date ${Date(achieved)}")
                return achieved != 0L && achieved < it.time
            }
        }
        return false
    }

    fun formatConversionToString(date: String, currentFormat: String, toFormat: String): String {
        try {
            return dateToStringFormat(
                stringFormatToDate(
                    date,
                    currentFormat,
                ), toFormat
            )
        } catch (e: java.lang.Exception) {
            return ""
            e.printStackTrace()
        }
    }

    fun previousDay(date: String, indices: Int): String {
        val dateCalender = Calendar.getInstance()
        dateCalender.time = stringFormatToDate(date, serverDateFormat)
        dateCalender.add(Calendar.DATE, -Math.abs(indices))
        return dateToStringFormat(dateCalender.time, serverDateFormat)
    }

    fun isYesterDay(date: String): Boolean {
        val currentCalendar = Calendar.getInstance()
        currentCalendar.add(Calendar.DATE, -1)
        val dateCalender = Calendar.getInstance()
        dateCalender.time = stringFormatToDate(date, serverDateFormat)
        val dayDiff = dayDifference(currentCalendar, dateCalender)
        return dayDiff == 0L
    }


    fun minusDaysFromToday(days: Int): String {
        val dateFormat: DateFormat = SimpleDateFormat(FORMAT_DD_MMM_YYYY, Locale.ENGLISH)
        val cal = Calendar.getInstance()
        cal.add(Calendar.DATE, days)
        return dateFormat.format(cal.time)
    }

    fun isMonthDifferenceN(dateToCompare: String, dateFormat: String, N: Int): Boolean {
        val cal = Calendar.getInstance()
        val currentMonth = cal.get(Calendar.MONTH)
        val monthToCompareWith = stringFormatToDate(dateToCompare, dateFormat).month
        return (currentMonth - monthToCompareWith == N)
    }

    fun getDateOfCurrentMonth(): Int {
        val cal = Calendar.getInstance()
        return cal.get(Calendar.DATE)
    }

    fun getNameOfCurrentMonth(): String {
        val cal = Calendar.getInstance()
        return SimpleDateFormat("MMM").format(cal.time)
    }

    fun getStartAndEndDateOfPrevWeek(
        currentWeekNo: Int,
    ): Pair<String, String> {
        val cal = Calendar.getInstance()
        val sdf = SimpleDateFormat(slash_mmddyyyy)
        var lastWeekNo = -1
        var currentMonthNo = cal.get(Calendar.MONTH) + 1
        var currentYearNo = cal.get(Calendar.YEAR)
        if (currentWeekNo == 1 && currentMonthNo == 1) {
            lastWeekNo = 4
            currentMonthNo = 12
            currentYearNo -= 1
        } else if (currentWeekNo == 1) {
            lastWeekNo = 4
            currentMonthNo -= 1
        } else {
            lastWeekNo = currentWeekNo - 1
        }
        cal.clear()
        cal.set(Calendar.YEAR, currentYearNo);
        cal.set(Calendar.MONTH, currentMonthNo - 1);
        cal.set(Calendar.WEEK_OF_MONTH, lastWeekNo);
        cal.add(Calendar.DATE, 1)
        val startDate = sdf.format(cal.time)
        val daysToAdd = if (lastWeekNo == 4) cal.getActualMaximum(Calendar.DAY_OF_MONTH) - 28 else 0
        cal.add(Calendar.DAY_OF_YEAR, daysToAdd + 6)
        val endDate = sdf.format(cal.time)
        return Pair(startDate, endDate)
    }

    fun isBothDateSame(firstDate: String, secondDate: String, format: String): Boolean {
        return stringFormatToDate(firstDate,format) == stringFormatToDate(secondDate,format)
    }

    fun getCurrentTime() : String{ // return time in 10:30 AM format
        val currentTime = System.currentTimeMillis()
        val sdf = SimpleDateFormat("hh:mm a", Locale.getDefault())
        return sdf.format(Date(currentTime)).replace("am", "AM").replace("pm", "PM")
    }

    fun isDifferenceGreaterOrEqual24Hours(epochTime1: Long, epochTime2: Long): Boolean {
        val millisecondsIn24Hours = 24 * 60 * 60 * 1000 // 24 hours in milliseconds
        val difference = Math.abs(epochTime1 - epochTime2)
        return difference >= millisecondsIn24Hours
    }
}