import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.junit.Assert.assertEquals;

public class TestAuthentication {
    @Test
    //Negativ - Wrong password
    public void testEveryAutorPermitP() throws IOException {
        HttpGet request = new HttpGet("http://localhost:8080/Project_Lina_Z_war_exploded/resources/service");

        final String auth = "lina" + ":" + "lina";
        final byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(StandardCharsets.ISO_8859_1));

        final String authHeader = "Basic " + new String(encodedAuth);
        request.setHeader(HttpHeaders.AUTHORIZATION, authHeader);

        HttpResponse httpResponse = HttpClientBuilder
                .create()
                .build()
                .execute(request);

        // Testen
        assertEquals(HttpStatus.SC_UNAUTHORIZED, httpResponse.getStatusLine().getStatusCode());
    }
    @Test
    //Negativ - Wrong User
    public void testEveryAutorPermitU() throws IOException {
        HttpGet request = new HttpGet("http://localhost:8080/Project_Lina_Z_war_exploded/resources/service");

        final String auth = "tina" + ":" + "z3a";
        final byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(StandardCharsets.ISO_8859_1));

        final String authHeader = "Basic " + new String(encodedAuth);
        request.setHeader(HttpHeaders.AUTHORIZATION, authHeader);

        HttpResponse httpResponse = HttpClientBuilder
                .create()
                .build()
                .execute(request);

        // Testen
        assertEquals(HttpStatus.SC_UNAUTHORIZED, httpResponse.getStatusLine().getStatusCode());
    }
}
