package br.com.zaya.engineering.nexo.monitoring;

import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

/** Fixed diagnostic codes only; never expose exception messages or supplied data. */
@Provider
public class MonitorBadRequestMapper implements ExceptionMapper<BadRequestException> {
    public Response toResponse(BadRequestException failure) {
        String code=switch(java.util.Objects.toString(failure.getMessage(),"")) {
            case "Event timestamp outside allowed window" -> "EVENT_TIME_WINDOW";
            case "Invalid bounded failure event" -> "INVALID_EVENT_FIELDS";
            default -> "INVALID_REQUEST";
        };
        return Response.status(400).header("X-Nexo-Rejection",code).entity(java.util.Map.of("error",code)).build();
    }
}
