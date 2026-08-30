package exceptions;

public class PropertiesNotFound extends RuntimeException {
    public PropertiesNotFound(){
        super("Arquivo db.properties não encontrado em resources!");
    }

    public PropertiesNotFound(String message) {
        super(message);
    }
}
