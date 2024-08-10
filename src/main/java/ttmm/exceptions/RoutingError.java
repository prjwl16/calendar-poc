package ttmm.exceptions;


import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class RoutingError extends RuntimeException{
    private int statusCode=400;

    public RoutingError(String message, int statusCode){
        super(message);
    }

    public RoutingError(String message, int statusCode, Throwable cause){
        super(message, cause);
    }

    public RoutingError(int statusCode){
        this.statusCode=statusCode;
    }

    public RoutingError(String message){
        super(message);
    }

}
