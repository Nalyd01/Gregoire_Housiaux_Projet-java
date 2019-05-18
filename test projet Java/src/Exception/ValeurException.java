package Exception;

public class ValeurException extends Exception {

    public String getMessage(){
        return "La valeur doit être supérieure à 0 !";
    }
}
