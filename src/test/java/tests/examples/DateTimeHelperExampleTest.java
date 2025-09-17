package tests.examples;

import framework.utils.DateTimeHelper;
import framework.utils.LogUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Calendar;
import java.util.Date;

/**
 * Example test class demonstrating the use of DateTimeHelper for various date/time operations
 */
public class DateTimeHelperExampleTest {
    
    @Test(description = "Demonstrate using DateTimeHelper for hotel booking dates")
    public void testHotelDateCalculations() {
        LogUtils.logAction("DateTimeHelperExampleTest", "Testing hotel date calculations");
        
        // Example 1: Calculate days until next Friday
        int daysUntilFriday = DateTimeHelper.getDaysUntilNextDayOfWeek(Calendar.FRIDAY);
        LogUtils.logAction("DateTimeHelperExampleTest", "Days until next Friday: " + daysUntilFriday);
        Assert.assertTrue(daysUntilFriday >= 1 && daysUntilFriday <= 7, 
                "Days until Friday should be between 1 and 7");
        
        // Example 2: Get next Friday's date
        Date nextFriday = DateTimeHelper.getNextDayOfWeek(Calendar.FRIDAY);
        LogUtils.logAction("DateTimeHelperExampleTest", "Next Friday: " + DateTimeHelper.formatDate(nextFriday));
        
        // Verify it's actually a Friday
        Calendar cal = Calendar.getInstance();
        cal.setTime(nextFriday);
        Assert.assertEquals(cal.get(Calendar.DAY_OF_WEEK), Calendar.FRIDAY, 
                "The calculated date should be a Friday");
        
        // Example 3: Calculate a 3-night stay starting next Friday
        Date[] stayDates = DateTimeHelper.calculateHotelDates(daysUntilFriday, 3);
        Date checkInDate = stayDates[0];
        Date checkOutDate = stayDates[1];
        
        LogUtils.logAction("DateTimeHelperExampleTest", "3-night stay: " + 
                "Check-in: " + DateTimeHelper.formatDate(checkInDate) + ", " + 
                "Check-out: " + DateTimeHelper.formatDate(checkOutDate));
                
        // Verify stay duration
        int stayDuration = DateTimeHelper.getDaysBetween(checkInDate, checkOutDate);
        Assert.assertEquals(stayDuration, 3, "Stay duration should be 3 nights");
        
        // Example 4: Calculate a weekend stay (Friday to Sunday)
        Date[] weekendStay = DateTimeHelper.calculateWeekendStay(Calendar.FRIDAY, 2);
        LogUtils.logAction("DateTimeHelperExampleTest", "Weekend stay: " + 
                "Check-in: " + DateTimeHelper.formatDate(weekendStay[0]) + ", " + 
                "Check-out: " + DateTimeHelper.formatDate(weekendStay[1]));
                
        // Verify weekend check-in day
        Calendar weekendCal = Calendar.getInstance();
        weekendCal.setTime(weekendStay[0]);
        Assert.assertEquals(weekendCal.get(Calendar.DAY_OF_WEEK), Calendar.FRIDAY, 
                "Weekend should start on Friday");
                
        // Verify weekend duration
        int weekendDuration = DateTimeHelper.getDaysBetween(weekendStay[0], weekendStay[1]);
        Assert.assertEquals(weekendDuration, 2, "Weekend stay should be 2 nights");
        
        LogUtils.logSuccess("DateTimeHelperExampleTest", "All hotel date calculations verified successfully");
    }
}
