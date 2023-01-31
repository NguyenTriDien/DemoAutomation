package DateTime;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;


public class DateDifference {
    static String startDate = "20170228";
    static String closeDate = "20211224";
    static int monthsToAdd = 1;

    static LocalDate start = LocalDate.parse(startDate, DateTimeFormatter.ofPattern("yyyyMMdd"));
    static LocalDate end = LocalDate.parse(closeDate, DateTimeFormatter.ofPattern("yyyyMMdd"));
    public static void main(String[] args){
        caculatoStartDateToCloseDate();
        caculatoStartDateUptoMoth();

    }
    public static void caculatoStartDateToCloseDate(){
        long totalDays = ChronoUnit.DAYS.between(start, end);
        System.out.println("Total days between " + start + " and " + end + " is: " + totalDays);
    }
    public static void caculatoStartDateUptoMoth(){
        LocalDate end = start.plusMonths(monthsToAdd);
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        String strDate = dateFormat.format(java.sql.Date.valueOf(end));
        LocalDate convertedDate = LocalDate.parse(strDate, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        convertedDate = convertedDate.withDayOfMonth(
                convertedDate.getMonth().length(convertedDate.isLeapYear()));
        String result = convertedDate.toString().replace("-","");
        System.out.println("Close date after adding " + monthsToAdd + " month(s) to " + start + " is: " + result);
    }
    }