/**
 *
 *  @author Partyka Jakub S18830
 *
 */

package S_PASSTIME_SERVER1;


import java.text.DecimalFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

public class Time {

    private static Locale locale = Locale.forLanguageTag("pl-PL");


    public static String passed(String timeFrom, String timeTo) {

        StringBuilder sb = new StringBuilder();
        Period period = null;

        long wszystkieDni;
        long godziny;
        long minuty;

        String wzor2 = "#.##";
        DecimalFormat decimalFormat = new DecimalFormat(wzor2);

        try {
            if (timeFrom.length() == 16) {

                ZonedDateTime lFrom = changeTime(timeFrom);
                ZonedDateTime lTo = changeTime(timeTo);

                godziny = ChronoUnit.HOURS.between(lFrom, lTo);
                minuty = ChronoUnit.MINUTES.between(lFrom, lTo);

                LocalDate ldFrom = lFrom.toLocalDate();
                LocalDate ldTo = lTo.toLocalDate();
                wszystkieDni = ChronoUnit.DAYS.between(ldFrom, ldTo);

                period = Period.between(ldFrom, ldTo);

                String wzor1 = "d MMMM yyyy (EEEE) 'godz.' HH':'mm";
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(wzor1, locale);

                sb.append("Od " + lFrom.format(dateTimeFormatter) + " do " + lTo.format(dateTimeFormatter) + "\n - mija: " + wszystkieDni);
                sb.append(showDays(wszystkieDni));
                sb.append(decimalFormat.format(wszystkieDni / 7.0).replaceAll(",", "."));
                sb.append("\n - godzin: " + godziny + ", minut: " + minuty);
                sb.append(mojaData(period));

            } else {

                LocalDate ldTo = LocalDate.parse(timeTo);
                LocalDate ldFrom = LocalDate.parse(timeFrom);
                wszystkieDni = ChronoUnit.DAYS.between(ldFrom, ldTo);
                period = Period.between(ldFrom, ldTo);

                String wzor1 = "d MMMM yyyy (EEEE)";
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(wzor1, locale);

                sb.append("Od " + ldFrom.format(dateTimeFormatter) + " do " + ldTo.format(dateTimeFormatter) + "\n - mija: " + wszystkieDni);
                sb.append(showDays(wszystkieDni));
                sb.append(decimalFormat.format(wszystkieDni / 7d).replaceAll(",", "."));
                sb.append(mojaData(period));
            }
        } catch (Exception ex) {
            return "*** " + ex;
        }
        String w = sb.toString();
        return w;
    }

    public static ZonedDateTime changeTime(String time) {
        return ZonedDateTime.of(LocalDateTime.parse(time), ZoneId.of("Europe/Warsaw"));
    }

    public static String mojaData(Period period)
    {
        StringBuilder s = new StringBuilder();
        int iloscDni = period.getDays();
        int iloscMiesiecy = period.getMonths();
        int iloscLat = period.getYears();

        if (iloscDni >= 1) {
            s.append("\n- kalendarzowo: ");
            if (iloscLat == 1) {
                s.append("1 rok, ");
            } else if (iloscLat > 1 && iloscLat <= 4) {
                s.append(iloscLat + " lata, ");
            } else if (iloscLat > 4) {
                s.append(iloscLat + " lat, ");
            }
            if (iloscMiesiecy == 1) {
                s.append("1 miesiąc, ");
            } else if (iloscMiesiecy > 1 && iloscMiesiecy <= 4) {
                s.append(iloscMiesiecy + " miesiące, ");
            } else if (iloscMiesiecy > 4) {
                s.append(iloscMiesiecy + " miesięcy, ");
            }
            if (iloscDni == 1) {
                s.append("1 dzień");
            } else {
                s.append(iloscDni + " dni");
            }
        }
        String w = s.toString();
        return w;

    }

    public static String showDays (long dni)
    {
        StringBuilder s = new StringBuilder();

        if (dni == 1)
            s.append(" dzień, tygodni ");
        else
            s.append(" dni, tygodni ");

        String w = s.toString();
        return w;

    }
}





