package Business;

import Model.Trajet;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;

public class TripCost {
    private static int[] heuresPallier = new int[] {10, 14, 18, 22, 6};

    private static final double PKm6_10 = 2.5; // PKm = Prix au Km
    private static final double PKm10_14 = 2;
    private static final double PKm14_18 = 2.8;
    private static final double PKm18_22 = 2.2;
    private static final double PKm22_6 = 3;

    private static final double PPC6_10 = 2.4; //PPC = Prix de Prise en Charge
    private static final double PPC10_14 = 1.8;
    private static final double PPC14_18 = 2.6;
    private static final double PPC18_22 = 2;
    private static final double PPC22_6 = 2.8;

    private static final double PCS6_10 = 3;// PCS = Prix par Client Supplémentaire
    private static final double PCS10_14 = 2.5;
    private static final double PCS14_18 = 3.2;
    private static final double PCS18_22 = 2.75;
    private static final double PCS22_6 = 3.5;

    private static Calendar dateDépart, dateArrivée, timeToCompare;
    private static double[] nbKm;
    private static double cost, newCost;
    private static int indexHour;
    private static BigDecimal costToFormat;

    public static double getCost(Trajet trajet){
        dateDépart = Calendar.getInstance();
        dateDépart.setTimeInMillis(trajet.getHeureDepart().getTime());

        dateArrivée = Calendar.getInstance();
        dateArrivée.setTimeInMillis(trajet.getHeureArrivee().getTime());

        nbKm = new double[5]; // 0 -> 6-10h, 1 -> 10-14h, 2 -> 14-18h, 3 ->  18-22h, 4 -> 22-06h
        cost = 0;
        indexHour = getIndex(dateDépart.get(Calendar.HOUR_OF_DAY));

        switch (indexHour){
            case 0 : cost += PPC6_10 + (PCS6_10 * (trajet.getNbPassagers()-1));
                break;
            case 1 : cost += PPC10_14 + (PCS10_14 * (trajet.getNbPassagers()-1));
                break;
            case 2 : cost += PPC14_18 + (PCS14_18* (trajet.getNbPassagers()-1));
                break;
            case 3 : cost += PPC18_22 + (PCS18_22 * (trajet.getNbPassagers()-1));
                break;
            case 4 : cost += PPC22_6 + (PCS22_6 * (trajet.getNbPassagers()-1));
                break;
        }

        timeToCompare = ((Calendar) dateDépart.clone());
        timeToCompare.set(Calendar.MINUTE, 0);
        timeToCompare.set(Calendar.SECOND, 0);

        if(dateDépart.get(Calendar.HOUR_OF_DAY) >= 22){
            timeToCompare.set(Calendar.DAY_OF_YEAR, timeToCompare.get(Calendar.DAY_OF_YEAR)+1);
        }

        for (int i =0; i < 6 ; i++) {
            timeToCompare.set(Calendar.HOUR_OF_DAY, heuresPallier[indexHour]);
            if (dateArrivée.after(timeToCompare)) {
                nbKm[indexHour] = (double)(timeToCompare.getTimeInMillis() - dateDépart.getTimeInMillis()) / (double)( trajet.getHeureArrivee().getTime()-trajet.getHeureDepart().getTime()) * trajet.getNbKm();
            }
            else {
                nbKm[indexHour] = (double) (dateArrivée.getTimeInMillis()-dateDépart.getTimeInMillis()) / (double) (trajet.getHeureArrivee().getTime()-trajet.getHeureDepart().getTime()) * trajet.getNbKm();
                break;
            }
            indexHour++;
            dateDépart =(Calendar)timeToCompare.clone();

            if (indexHour == 4){
                timeToCompare.set(Calendar.DAY_OF_YEAR, timeToCompare.get(Calendar.DAY_OF_YEAR)+1);
            }
            if (indexHour >= 5){
                indexHour = 0;
            }
        }

        cost += nbKm[0] * PKm6_10;
        cost += nbKm[1] * PKm10_14;
        cost += nbKm[2] * PKm14_18;
        cost += nbKm[3] * PKm18_22;
        cost += nbKm[4] * PKm22_6;

        if(trajet.getaEuEmbouteillage() != null) {
            cost += (trajet.getaEuEmbouteillage() ? 3 : 0);
        }
        
        costToFormat = new BigDecimal(cost).setScale(2, RoundingMode.HALF_UP);
        newCost = costToFormat.doubleValue();

        return newCost;
    }

    public static int getIndex(int heure) {
        if (heure >= 6 && heure < 10)
            return 0;
        else if (heure >= 10 && heure < 14)
            return  1;
        else if(heure >= 14 && heure < 18)
            return 2;
        else if (heure >= 18 && heure < 22)
            return 3;
        else
            return 4;
    }


}
