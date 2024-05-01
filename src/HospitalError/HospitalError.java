package HospitalError;

public class HospitalError extends Exception {
    public Object errorObject;

    public HospitalError() {
        super();
    }

    public HospitalError(String errorMessage, Object errorObject) {
        super(errorMessage);
        this.errorObject = errorObject;
    }
}
