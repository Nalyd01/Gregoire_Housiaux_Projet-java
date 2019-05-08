public class IdException extends Exception {
    private int wrongValue;

    public IdException(int wrongValue){
        this.wrongValue = wrongValue;
    }

    public String getMessage(){
        return "La valeur entrée doit être composée de 6 chiffres.";
    }
}
