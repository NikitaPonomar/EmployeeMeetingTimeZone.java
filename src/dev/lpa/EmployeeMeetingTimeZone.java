package dev.lpa;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

public class EmployeeMeetingTimeZone {
    public record Person(String name, ZonedDateTime dateTime, Locale locale) {}

    public static void main(String[] args) {
// Searching appropriate time for an hour meeting for two persons with different time zones
        ZonedDateTime now = ZonedDateTime.now();
        Person firstPerson = new Person("Joe"
                , now.withZoneSameInstant(ZoneId.of("America/New_York"))
                , Locale.US);
        Person secondPerson = new Person("Nikita"
                , now.withZoneSameInstant(ZoneId.of("Australia/Sydney"))
                , Locale.forLanguageTag("en-AU"));

        ZonedDateTime firstDate = firstPerson.dateTime();
        ZonedDateTime secondDate = secondPerson.dateTime();

        for (int day = 1; day <= 10; day++) {
            System.out.println("current day is " + day);
            firstDate = firstDate.plusDays(1);
            secondDate = secondDate.plusDays(1);
            checkingWorkingHours(firstDate, secondDate, firstPerson, secondPerson);
            checkingWorkingHours(secondDate, firstDate, secondPerson, firstPerson);
        }


    }

    public static void checkingWorkingHours(ZonedDateTime date, ZonedDateTime anotherDate,
                                            Person person, Person anotherPerson) {
        var dtf =
                DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);

        for (int i = 7; i <= 20; i++) {
            date = date.withHour(i).withMinute(0).withSecond(0);
            anotherDate = date.withZoneSameInstant(ZoneId.of(String.valueOf(anotherDate.getZone())));
            ZoneId oneZone = date.getZone();
            ZoneId secondZone = date.getZone();
            if (anotherDate.getHour() >= 7 && anotherDate.getHour() <= 20) {
                // working hours in different time zones
               System.out.println(person.name() + " ["
                        + person.dateTime().getZone() + "] : "
                        + date.format(dtf.withLocale(person.locale())) + " <---> "
                        + anotherPerson.name() + " ["
                        + anotherPerson.dateTime().getZone() + "] : "
                        + anotherDate.format(dtf.withLocale(anotherPerson.locale())));
            }
        }
    }
}
