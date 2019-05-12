package Tools;

import java.sql.Timestamp;
import java.util.Date;
import java.util.GregorianCalendar;

public class TacheMetier {


    public static double getCost(Trajet trajet){
        Timestamp dateDépart = trajet.getHeureDepart();

        Date dateArrivé = trajet.getHeureArrivee();

        double duréeMili = dateArrivé.getTime()- dateArrivé.getTime();
        double nbKm6_10, nbH10_14, nbH;
        return 45;
    }
}
