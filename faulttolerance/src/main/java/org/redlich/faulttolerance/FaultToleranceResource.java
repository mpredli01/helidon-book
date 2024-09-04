package org.redlich.faulttolerance;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Retry;

@Path("/ft")
public class FaultToleranceResource {

    private static int retry;

    @Fallback(fallbackMethod = "fallbackMethod")
    @Path("/fallback/{success}")
    @GET
    public Response fallbackHandler(@PathParam("success") String success) {
        if (!Boolean.parseBoolean(success)) {
            terminate();
            }
        return reactive();
        }

    @Retry(maxRetries = 2)
    @Path("/retry")
    @GET
    public Response retryHandler() {
        if (++retry < 2)  {
            terminate();
            }
        String response = String.format("Number of failures: %s", retry);
        retry = 0;
        return Response.ok(response).build();
        }

    private void terminate() {
        throw new RuntimeException("Failure");
        }

    private Response fallbackMethod(String success) {
        return Response.ok("Fallback endpoint reached").build();
        }

    private Response reactive() {
        return Response.ok(blocking()).build();
        }

    private String blocking() {
        try {
            Thread.sleep(5000);
            }
        catch (InterruptedException ignored) {
            }
        return "Blocked for 5 seconds";
        }
    }
