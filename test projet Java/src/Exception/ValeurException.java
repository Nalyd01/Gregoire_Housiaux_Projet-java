package Exception;

public class ValeurException extends Exception {
    private int wrongValue;

    public ValeurException(int wrongValue){
        this.wrongValue = wrongValue;
    }

    public String getMessage(){
        return "La valeur doit être supérieure à 0 !";
    }
}
