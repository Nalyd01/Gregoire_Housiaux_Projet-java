package Tools;


import org.junit.Assert;
import Business.TacheMetier;
import Model.Trajet;
import java.sql.Timestamp;
import java.util.Calendar;

public class TacheMetierTest {
    private Trajet trajet;
    Calendar dateDepart;
    @org.junit.Before
    public void setUp() throws Exception {
        int heureDépart =4;
        int minuteDépart =0;
        int heureArrivé =11;
        int minuteArrivé =0;
        int nbKm = 120;
        int NbPassager = 3;
        boolean aEmbouteillage = false;

        dateDepart = Calendar.getInstance();
        dateDepart.set(Calendar.MINUTE, minuteDépart);
        dateDepart.set(Calendar.SECOND, 0);
        dateDepart.set(Calendar.HOUR_OF_DAY, heureDépart);

        Calendar dateArrive = Calendar.getInstance();
        dateArrive.set(Calendar.MINUTE, minuteArrivé);
        dateArrive.set(Calendar.SECOND, 0);
        dateArrive.set(Calendar.HOUR_OF_DAY, heureArrivé);

        if (heureDépart > heureArrivé){
            dateArrive.set(Calendar.DAY_OF_YEAR, dateArrive.get(Calendar.DAY_OF_YEAR)+1);
        }


        trajet = new Trajet(222222, nbKm, NbPassager, 123456,5330, "Maillen", 1, false, aEmbouteillage,
            new Timestamp(dateArrive.getTime().getTime()),
            new Timestamp(dateDepart.getTime().getTime()));
    }

    @org.junit.Test
    public void getCost() {
        double prixAttendu = 318.37;
        Assert.assertEquals(prixAttendu, TacheMetier.getCost(trajet), 0.01);
    }

    @org.junit.Test
    public void getIndex(){
        int indexAttendu = 4;
        Assert.assertEquals(indexAttendu, TacheMetier.getIndex(dateDepart.get(Calendar.HOUR_OF_DAY)));
    }
}