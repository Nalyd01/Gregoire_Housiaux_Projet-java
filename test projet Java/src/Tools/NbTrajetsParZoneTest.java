package Tools;

import Business.NbTrajetsParZone;
import org.junit.Assert;

public class NbTrajetsParZoneTest {
    private int nbTrajetsAttendu;

    @org.junit.Before
    public void setUp() throws Exception{

    }

    @org.junit.Test
    public void getNbTrajets(){
        nbTrajetsAttendu = 10;
        Assert.assertEquals(nbTrajetsAttendu, NbTrajetsParZone.getNbTrajets());
    }
}
