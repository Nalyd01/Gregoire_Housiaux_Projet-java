package Exception;

public class ListException extends Exception {

    public String getMessage(){
        return "La liste doit contenir au moins une valeur";
    }
}
