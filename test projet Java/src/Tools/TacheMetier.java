package Tools;

import java.util.Calendar;

public class TacheMetier {

    public static int[] heuresPallier = new int[] {10,14,18,22, 6};

    public static final double PKm6_10 = 2.5; // PKm = Prix au Km
    public static final double PKm10_14 = 2;
    public static final double PKm14_18 = 2.8;
    public static final double PKm18_22 = 2.2;
    public static final double PKm22_6 = 2.3;

    public static final double PPC6_10 = 2.5; //PPC = Prix de Prise en Charge
    public static final double PPC10_14 = 2.5;
    public static final double PPC14_18 = 2.5;
    public static final double PPC18_22 = 2.5;
    public static final double PPC22_6 = 2.5;

    public static final double PCS6_10 = 3;// PCS = Prix par Client Supplémentaire
    public static final double PCS10_14 = 2.5;
    public static final double PCS14_18 = 3.2;
    public static final double PCS18_22 = 2.75;
    public static final double PCS22_6 = 3.5;


    public static double getCost(Trajet trajet){
        Calendar dateDépart = Calendar.getInstance();
        dateDépart.setTimeInMillis(trajet.getHeureDepart().getTime());
        Calendar dateArrivé = Calendar.getInstance();
        dateArrivé.setTimeInMillis(trajet.getHeureArrivee().getTime());

        double  nbKm[] = new double[5]; // 0 -> 6-10h, 1 -> 10-14h, 2 -> 14-18h, 3 ->  18-22h, 4 -> 22-06h
        double cost = 0;
        int indexHour = getIndex(dateDépart.get(Calendar.HOUR));

        switch (indexHour){
            case 0 : cost += PPC6_10 + PCS6_10 * trajet.getNbPassagers()-1;
                break;
            case 1 : cost += PPC10_14 + PCS10_14 * trajet.getNbPassagers()-1;
                break;
            case 2 : cost += PPC14_18 + PCS14_18 * trajet.getNbPassagers()-1;
                break;
            case 3 : cost += PPC18_22 + PCS18_22 * trajet.getNbPassagers()-1;
                break;
            case 4 : cost += PPC22_6 + PCS22_6 * trajet.getNbPassagers()-1;
                break;
        }

        Calendar timeToCompare = ((Calendar) dateDépart.clone());
        timeToCompare.set(Calendar.MINUTE, 0);
        timeToCompare.set(Calendar.SECOND, 0);
        if(dateArrivé.get(Calendar.HOUR) >= 22){
            timeToCompare.set(Calendar.DAY_OF_YEAR, timeToCompare.get(Calendar.DAY_OF_YEAR)+1);
        }

        for (int i =0; i < 6 ; i++) {
            timeToCompare.set(Calendar.HOUR, heuresPallier[indexHour]);
            if (dateArrivé.after(timeToCompare)) {
                nbKm[indexHour] = (double)(timeToCompare.getTimeInMillis() - dateDépart.getTimeInMillis()) / (double)( trajet.getHeureArrivee().getTime()-trajet.getHeureDepart().getTime()) * trajet.getNbKm();
            }
            else {
                nbKm[indexHour] = (double) (dateArrivé.getTimeInMillis()-dateDépart.getTimeInMillis()) / (double) (trajet.getHeureArrivee().getTime()-trajet.getHeureDepart().getTime()) * trajet.getNbKm();
                break;
            }
            indexHour++;
            dateDépart =(Calendar)timeToCompare.clone();
            if (indexHour >= 5) {
                indexHour = 0;
            }
            if (indexHour == 4){
                timeToCompare.set(Calendar.DAY_OF_YEAR, timeToCompare.get(Calendar.DAY_OF_YEAR)+1);
            }
        }


        cost += nbKm[0] * PKm6_10;
        cost += nbKm[1] * PKm10_14;
        cost += nbKm[2] * PKm14_18;
        cost += nbKm[3] * PKm18_22;
        cost += nbKm[4] * PKm22_6;

        cost += (trajet.getaEuEmbouteillage()? 3 : 0);

        return cost;
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
