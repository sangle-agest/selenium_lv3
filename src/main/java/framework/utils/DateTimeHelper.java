package framework.utils;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;

/**
 * Helper class for date and time operations
 * Provides methods for common date calculations and formatting
 */
public class DateTimeHelper {
    
    private static final SimpleDateFormat CALENDAR_DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy");
    
    /**
     * Calculate days until next occurrence of the specified day of week
     * @param dayOfWeek The target day of week (use Calendar constants: Calendar.MONDAY, Calendar.TUESDAY, etc.)
     * @return Number of days until the next occurrence of the specified day
     */
    public static int getDaysUntilNextDayOfWeek(int dayOfWeek) {
        Calendar today = Calendar.getInstance();
        int currentDayOfWeek = today.get(Calendar.DAY_OF_WEEK);
        
        // Calculate days until next occurrence
        int daysUntil = (dayOfWeek - currentDayOfWeek + 7) % 7;
        
        // If current day is the target day, we want next week's occurrence
        if (daysUntil == 0) {
            daysUntil = 7;
        }
        
        LogUtils.logAction("DateTimeHelper", "Days until next " + getDayName(dayOfWeek) + ": " + daysUntil);
        return daysUntil;
    }
    
    /**
     * Get the next occurrence of the specified day of week
     * @param dayOfWeek The target day of week (use Calendar constants: Calendar.MONDAY, Calendar.TUESDAY, etc.)
     * @return Date object representing the next occurrence of the specified day
     */
    public static Date getNextDayOfWeek(int dayOfWeek) {
        Calendar calendar = Calendar.getInstance();
        int daysUntil = getDaysUntilNextDayOfWeek(dayOfWeek);
        
        calendar.add(Calendar.DAY_OF_YEAR, daysUntil);
        return calendar.getTime();
    }
    
    /**
     * Get a future date by adding days to the current date
     * @param daysToAdd Number of days to add to the current date
     * @return Date object representing the future date
     */
    public static Date getFutureDate(int daysToAdd) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, daysToAdd);
        return calendar.getTime();
    }
    
    /**
     * Get a future date by adding days to a specified base date
     * @param baseDate The starting date
     * @param daysToAdd Number of days to add to the base date
     * @return Date object representing the future date
     */
    public static Date getFutureDate(Date baseDate, int daysToAdd) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(baseDate);
        calendar.add(Calendar.DAY_OF_YEAR, daysToAdd);
        return calendar.getTime();
    }
    
    /**
     * Format a date using the default format (MM/dd/yyyy)
     * @param date The date to format
     * @return Formatted date string
     */
    public static String formatDate(Date date) {
        return CALENDAR_DATE_FORMAT.format(date);
    }
    
    /**
     * Format a date using a specified format
     * @param date The date to format
     * @param format The date format pattern
     * @return Formatted date string
     */
    public static String formatDate(Date date, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }
    
    /**
     * Get the name of a day of week
     * @param dayOfWeek The day of week (use Calendar constants: Calendar.MONDAY, Calendar.TUESDAY, etc.)
     * @return Name of the day
     */
    public static String getDayName(int dayOfWeek) {
        switch (dayOfWeek) {
            case Calendar.SUNDAY: return "Sunday";
            case Calendar.MONDAY: return "Monday";
            case Calendar.TUESDAY: return "Tuesday";
            case Calendar.WEDNESDAY: return "Wednesday";
            case Calendar.THURSDAY: return "Thursday";
            case Calendar.FRIDAY: return "Friday";
            case Calendar.SATURDAY: return "Saturday";
            default: return "Unknown";
        }
    }
    
    /**
     * Get the current date
     * @return Current date as a Date object
     */
    public static Date getCurrentDate() {
        return Calendar.getInstance().getTime();
    }
    
    /**
     * Calculate the difference in days between two dates
     * @param date1 First date
     * @param date2 Second date
     * @return Difference in days (absolute value)
     */
    public static int getDaysBetween(Date date1, Date date2) {
        long diffInMillis = Math.abs(date2.getTime() - date1.getTime());
        return (int) (diffInMillis / (1000 * 60 * 60 * 24));
    }
    
    /**
     * Java 8 implementation for next day of week calculation
     * @param dayOfWeek The target day (use DayOfWeek enum: DayOfWeek.MONDAY, DayOfWeek.TUESDAY, etc.)
     * @return LocalDate representing the next occurrence of the specified day
     */
    public static LocalDate getNextDayOfWeekJava8(DayOfWeek dayOfWeek) {
        LocalDate today = LocalDate.now();
        LocalDate next = today.with(TemporalAdjusters.nextOrSame(dayOfWeek));
        
        // If today is the target day, get next week's occurrence
        if (next.isEqual(today)) {
            next = today.with(TemporalAdjusters.next(dayOfWeek));
        }
        
        return next;
    }
    
    /**
     * Convert LocalDate to Date
     * @param localDate The LocalDate to convert
     * @return Date object
     */
    public static Date toDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
    
    /**
     * Convert Date to LocalDate
     * @param date The Date to convert
     * @return LocalDate object
     */
    public static LocalDate toLocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }
    
    /**
     * Calculate check-in and check-out dates for hotel booking
     * @param checkInOffsetDays Days from today for check-in
     * @param stayNights Number of nights to stay
     * @return Array with two Date objects: [0] = check-in date, [1] = check-out date
     */
    public static Date[] calculateHotelDates(int checkInOffsetDays, int stayNights) {
        Date[] dates = new Date[2];
        
        // Calculate check-in date
        Date checkInDate = getFutureDate(checkInOffsetDays);
        dates[0] = checkInDate;
        
        // Calculate check-out date (check-in + nights)
        Date checkOutDate = getFutureDate(checkInDate, stayNights);
        dates[1] = checkOutDate;
        
        LogUtils.logAction("DateTimeHelper", "Hotel booking dates calculated: " + 
                "Check-in: " + formatDate(checkInDate) + ", Check-out: " + formatDate(checkOutDate));
                
        return dates;
    }
    
    /**
     * Calculate check-in and check-out dates for a weekend stay
     * @param weekendStartDay Day to start the weekend (Calendar.FRIDAY or Calendar.SATURDAY)
     * @param weekendNights Number of nights to stay
     * @return Array with two Date objects: [0] = check-in date, [1] = check-out date
     */
    public static Date[] calculateWeekendStay(int weekendStartDay, int weekendNights) {
        if (weekendStartDay != Calendar.FRIDAY && weekendStartDay != Calendar.SATURDAY) {
            throw new IllegalArgumentException("Weekend start day must be Calendar.FRIDAY or Calendar.SATURDAY");
        }
        
        Date[] dates = new Date[2];
        
        // Get next occurrence of the weekend start day
        Date checkInDate = getNextDayOfWeek(weekendStartDay);
        dates[0] = checkInDate;
        
        // Calculate check-out date
        Date checkOutDate = getFutureDate(checkInDate, weekendNights);
        dates[1] = checkOutDate;
        
        LogUtils.logAction("DateTimeHelper", "Weekend stay dates calculated: " + 
                "Check-in: " + formatDate(checkInDate) + " (" + getDayName(weekendStartDay) + "), " + 
                "Check-out: " + formatDate(checkOutDate));
                
        return dates;
    }
}
