package Tools;

import Business.TripCost;
import Model.Trajet;
import org.junit.Assert;
import java.sql.Timestamp;
import java.util.Calendar;

public class TripCostTest {
    private Trajet trajet;
    private Calendar dateDépart, dateArrivée;
    private int heureDépart, heureArrivée, minuteDépart, minuteArrivée, nbKm, nbPassagers, indexAttendu;
    private boolean aEmbouteillage;
    private double prixAttendu;

    @org.junit.Before
    public void setUp() throws Exception {
        heureDépart = 4;
        minuteDépart = 0;
        heureArrivée = 11;
        minuteArrivée = 0;
        nbKm = 120;
        nbPassagers = 3;
        aEmbouteillage = false;

        dateDépart = Calendar.getInstance();
        dateDépart.set(Calendar.MINUTE, minuteDépart);
        dateDépart.set(Calendar.SECOND, 0);
        dateDépart.set(Calendar.HOUR_OF_DAY, heureDépart);

        dateArrivée = Calendar.getInstance();
        dateArrivée.set(Calendar.MINUTE, minuteArrivée);
        dateArrivée.set(Calendar.SECOND, 0);
        dateArrivée.set(Calendar.HOUR_OF_DAY, heureArrivée);

        if (heureDépart > heureArrivée){
            dateArrivée.set(Calendar.DAY_OF_YEAR, dateArrivée.get(Calendar.DAY_OF_YEAR)+1);
        }

        trajet = new Trajet(222222, nbKm, nbPassagers, 123456,5330, "Maillen", 1, false, aEmbouteillage,
            new Timestamp(dateArrivée.getTime().getTime()),
            new Timestamp(dateDépart.getTime().getTime()));
    }

    @org.junit.Test
    public void getCost() {
        prixAttendu = 318.37;
        Assert.assertEquals(prixAttendu, TripCost.getCost(trajet), 0.01);
    }

    @org.junit.Test
    public void getIndex(){
        indexAttendu = 4;
        Assert.assertEquals(indexAttendu, TripCost.getIndex(dateDépart.get(Calendar.HOUR_OF_DAY)));
    }
}