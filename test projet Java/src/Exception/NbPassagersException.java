package Exception;

public class NbPassagersException extends Exception {

    public String getMessage(){
        return "Le nombre de passagers doit être compris entre 1 et 7";
    }
}
