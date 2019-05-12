package Tools;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class TacheMetier {

    public static int[] heuresPallier = new int[] {6,10,14,18,22};

    public static double getCost(Trajet trajet){
        Calendar dateDépart = Calendar.getInstance();
        dateDépart.setTimeInMillis(trajet.getHeureDepart().getTime());
        Calendar dateArrivé = Calendar.getInstance();
        dateArrivé.setTimeInMillis(trajet.getHeureArrivee().getTime());

        double  nbKm[] = new double[5]; // 0 -> 6-10h, 1 -> 10-14h, 2 -> 14-18h, 3 ->  18-22h, 4 -> 22-06h
        //double kmByMillisecond = trajet.getNbKm() / (dateArrivé.getTime() - dateDépart.getTime());

        for(int heure : heuresPallier){
            //if(dateDépart.a)
        }
        return 5;

    }
}
