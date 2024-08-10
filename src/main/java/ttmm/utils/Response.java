package ttmm.utils;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Response {
    private String message="Data fetched successfully";
    private Object data="";
    private int status=200;
    private boolean success=true;

    public Response(String message, Object data, int status, boolean success) {
        this.message = message;
        this.data = data;
        this.status = status;
        this.success = success;
    }

    public Response(String message, int status, boolean success) {
        this.message = message;
        this.status = status;
        this.success = success;
    }
}
