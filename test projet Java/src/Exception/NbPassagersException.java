package Exception;

public class NbPassagersException extends Exception {

    public String getMessage(){
        return "Le nombre de passgers doit être compris entre 1 et 7";
    }
}
