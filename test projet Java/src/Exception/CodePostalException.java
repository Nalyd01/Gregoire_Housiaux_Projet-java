package Exception;

public class CodePostalException extends Exception {
    private int wrongCodePostal;

    public  CodePostalException(int wrongCodePostal){
        this.wrongCodePostal = wrongCodePostal;
    }

    public String getMessage(){
        return "Le code postal " + wrongCodePostal + " n'est pas un code postal belge. " +
                "\nVeuillez entrez un code postal entre 1000 et 9992";
    }
}
