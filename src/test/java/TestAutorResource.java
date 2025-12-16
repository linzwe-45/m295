import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;

public class TestAutorResource {
    //Tomcat/Webseite muss zuerst laufen bevor die Test gerunt werden

    @Test
    //Positiv
    public void testPing() throws Exception {
        HttpGet httpGet = new HttpGet("http://localhost:8080/Project_Lina_Z_war_exploded/resources/service/ping");

        HttpResponse httpResponse = HttpClientBuilder
                .create()
                .build()
                .execute(httpGet);
        String response = EntityUtils.toString(httpResponse.getEntity());
        // Testen
        assertEquals(HttpStatus.SC_OK, httpResponse.getStatusLine().getStatusCode());
        assertEquals("API is running", response);
    }


    @Test
    //Done
    public void testEveryAutor() throws IOException {
        HttpGet request = new HttpGet("http://localhost:8080/Project_Lina_Z_war_exploded/resources/service");

        final String auth = "lina" + ":" + "z3a";
        final byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(StandardCharsets.ISO_8859_1));

        final String authHeader = "Basic " + new String(encodedAuth);
        request.setHeader(HttpHeaders.AUTHORIZATION, authHeader);

        HttpResponse httpResponse = HttpClientBuilder
                .create()
                .build()
                .execute(request);

        String response = EntityUtils.toString(httpResponse.getEntity());

        // Testen
        assertEquals(HttpStatus.SC_OK, httpResponse.getStatusLine().getStatusCode());
        assertEquals("[{\"idAutor\":1,\"vorname\":\"Lina\",\"nachname\":\"Zweifel\",\"gebdatum\":[2004,5,3]," +
                "\"umsatz\":10.05,\"istAktiv\":true,\"buch\":{\"idBuch\":1,\"titel\":\"Linas Reise\"}}," +
                "{\"idAutor\":2,\"vorname\":\"Tina\",\"nachname\":\"Muster\",\"gebdatum\":[2000,8,15],\"umsatz\":15.50," +
                "\"istAktiv\":false,\"buch\":{\"idBuch\":2,\"titel\":\"Java lernen leicht gemacht\"}}," +
                "{\"idAutor\":3,\"vorname\":\"Tina\",\"nachname\":\"Zweifel\",\"gebdatum\":[2004,5,3],\"umsatz\":10.05," +
                "\"istAktiv\":false,\"buch\":{\"idBuch\":2,\"titel\":\"Java lernen leicht gemacht\"}}]", response);
    }


    @Test
    //Positiv
    public void testCountEveryAutor() throws Exception {
        HttpGet request = new HttpGet("http://localhost:8080/Project_Lina_Z_war_exploded/resources/service/counter");

        final String auth = "lina" + ":" + "z3a";
        final byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(StandardCharsets.ISO_8859_1));
        final String authHeader = "Basic " + new String(encodedAuth);
        request.setHeader(HttpHeaders.AUTHORIZATION, authHeader);
        HttpResponse httpResponse = HttpClientBuilder
                .create()
                .build()
                .execute(request);

        String response = EntityUtils.toString(httpResponse.getEntity());

        // Testen
        assertEquals(HttpStatus.SC_OK, httpResponse.getStatusLine().getStatusCode());
        assertEquals("3 Autoren wurden in der Liste gefunden.", response);
    }


    @Test
    //Positiv
    public void testFindAutorById() throws IOException {
        HttpGet request = new HttpGet("http://localhost:8080/Project_Lina_Z_war_exploded/resources/service/byId?id=1");

        final String auth = "lina" + ":" + "z3a";
        final byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(StandardCharsets.ISO_8859_1));
        final String authHeader = "Basic " + new String(encodedAuth);
        request.setHeader(HttpHeaders.AUTHORIZATION, authHeader);

        HttpResponse httpResponse = HttpClientBuilder
                .create()
                .build()
                .execute(request);

        String response = EntityUtils.toString(httpResponse.getEntity());

        // Testen
        assertEquals(HttpStatus.SC_OK, httpResponse.getStatusLine().getStatusCode());
        assertEquals("{\"idAutor\":1,\"vorname\":\"Lina\",\"nachname\":\"Zweifel\",\"gebdatum\":[2004,5,3]," +
                "\"umsatz\":10.05,\"istAktiv\":true,\"buch\":{\"idBuch\":1,\"titel\":\"Linas Reise\"}}", response);
    }
    @Test
    //Negativ
    public void testFindAutorByIdNeg() throws IOException {
        HttpGet request = new HttpGet("http://localhost:8080/Project_Lina_Z_war_exploded/resources/service/byId?id=5");

        final String auth = "lina" + ":" + "z3a";
        final byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(StandardCharsets.ISO_8859_1));
        final String authHeader = "Basic " + new String(encodedAuth);
        request.setHeader(HttpHeaders.AUTHORIZATION, authHeader);

        HttpResponse httpResponse = HttpClientBuilder
                .create()
                .build()
                .execute(request);

        String response = EntityUtils.toString(httpResponse.getEntity());

        // Testen
        assertEquals(HttpStatus.SC_NOT_FOUND, httpResponse.getStatusLine().getStatusCode());
    }


    @Test
    //Positiv
    public void testFindAutorByDate() throws IOException {
        HttpGet request = new HttpGet("http://localhost:8080/Project_Lina_Z_war_exploded/resources/service/byDatum?datum=2004-05-03");

        final String auth = "admin" + ":" + "1234";
        final byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(StandardCharsets.ISO_8859_1));
        final String authHeader = "Basic " + new String(encodedAuth);
        request.setHeader(HttpHeaders.AUTHORIZATION, authHeader);

        HttpResponse httpResponse = HttpClientBuilder
                .create()
                .build()
                .execute(request);

        String response = EntityUtils.toString(httpResponse.getEntity());

        // Testen
        assertEquals(HttpStatus.SC_OK, httpResponse.getStatusLine().getStatusCode());
        assertEquals("{\"idAutor\":1,\"vorname\":\"Lina\",\"nachname\":\"Zweifel\",\"gebdatum\":[2004,5,3]," +
                "\"umsatz\":10.05,\"istAktiv\":true,\"buch\":{\"idBuch\":1,\"titel\":\"Linas Reise\"}}", response);
    }
    @Test
    //Negativ
    public void testFindAutorByDateNeg() throws IOException {
        HttpGet request = new HttpGet("http://localhost:8080/Project_Lina_Z_war_exploded/resources/service/byDatum?datum=03.05.2004");

        final String auth = "admin" + ":" + "1234";
        final byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(StandardCharsets.ISO_8859_1));
        final String authHeader = "Basic " + new String(encodedAuth);
        request.setHeader(HttpHeaders.AUTHORIZATION, authHeader);

        HttpResponse httpResponse = HttpClientBuilder
                .create()
                .build()
                .execute(request);

        // Testen-> Ungültiges Datumformat
        assertEquals(HttpStatus.SC_BAD_REQUEST, httpResponse.getStatusLine().getStatusCode());
    }


    @Test
    //Positiv
    public void testUpdateAutor() throws IOException {
        final HttpPut httpPut = new HttpPut("http://localhost:8080/Project_Lina_Z_war_exploded/resources/service");

        final String auth = "admin" + ":" + "1234";
        final byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(StandardCharsets.ISO_8859_1));
        final String authHeader = "Basic " + new String(encodedAuth);
        httpPut.setHeader(HttpHeaders.AUTHORIZATION, authHeader);

        String json = "{\"idAutor\":1,\"vorname\":\"Lina\",\"nachname\":\"Zweifel\",\"gebdatum\":[2004,5,3]," +
                "\"umsatz\":10.05,\"istAktiv\":true}";
        StringEntity entity = new StringEntity(json);
        httpPut.setEntity(entity);
        httpPut.setHeader("Content-type", "application/json");

        HttpResponse httpResponse = HttpClientBuilder
                .create()
                .build()
                .execute(httpPut);
        //Testen
        assertEquals(HttpStatus.SC_OK, httpResponse.getStatusLine().getStatusCode());
    }
    @Test
    //negativ
    public void testUpdateAutorNeg() throws IOException {
        final HttpPut httpPut = new HttpPut("http://localhost:8080/Project_Lina_Z_war_exploded/resources/service");

        final String auth = "admin" + ":" + "1234";
        final byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(StandardCharsets.ISO_8859_1));
        final String authHeader = "Basic " + new String(encodedAuth);
        httpPut.setHeader(HttpHeaders.AUTHORIZATION, authHeader);

        String json = "{\"idAutor\":3,\"vorname\":\"Lina\",\"nachname\":\"Zweifel\",\"gebdatum\":[2004,5,3]," +
                "\"umsatz\":10.05,\"istAktiv\":true}";
        StringEntity entity = new StringEntity(json);
        httpPut.setEntity(entity);
        httpPut.setHeader("Content-type", "application/json");

        HttpResponse httpResponse = HttpClientBuilder
                .create()
                .build()
                .execute(httpPut);
        //Testen-> gibt nichts zum updaten
        assertEquals(HttpStatus.SC_BAD_REQUEST, httpResponse.getStatusLine().getStatusCode());
    }

    @Test
    //Positiv
    public void testDeleteAutor() throws IOException {
        final HttpDelete httpDelete = new HttpDelete("http://localhost:8080/Project_Lina_Z_war_exploded/resources/service/1");

        final String auth = "admin" + ":" + "1234";
        final byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(StandardCharsets.ISO_8859_1));
        final String authHeader = "Basic " + new String(encodedAuth);
        httpDelete.setHeader(HttpHeaders.AUTHORIZATION, authHeader);

        HttpResponse httpResponse = HttpClientBuilder
                .create()
                .build()
                .execute(httpDelete);
        String response = EntityUtils.toString(httpResponse.getEntity());
        // Testen
        assertEquals(HttpStatus.SC_OK, httpResponse.getStatusLine().getStatusCode());
        assertEquals("Autor 1 geloscht", response);
    }
    @Test
    //Negativ
    public void testDeleteAutorNeg() throws IOException {
        final HttpDelete httpDelete = new HttpDelete("http://localhost:8080/Project_Lina_Z_war_exploded/resources/service/3");

        final String auth = "admin" + ":" + "1234";
        final byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(StandardCharsets.ISO_8859_1));
        final String authHeader = "Basic " + new String(encodedAuth);
        httpDelete.setHeader(HttpHeaders.AUTHORIZATION, authHeader);

        HttpResponse httpResponse = HttpClientBuilder
                .create()
                .build()
                .execute(httpDelete);
        String response = EntityUtils.toString(httpResponse.getEntity());
        // Testen
        assertEquals(HttpStatus.SC_BAD_REQUEST, httpResponse.getStatusLine().getStatusCode());
    }


    @Test
    //Positiv
    public void testDeleteAll() throws IOException {
        final HttpDelete httpDelete = new HttpDelete("http://localhost:8080/Project_Lina_Z_war_exploded/resources/service");

        final String auth = "admin" + ":" + "1234";
        final byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(StandardCharsets.ISO_8859_1));
        final String authHeader = "Basic " + new String(encodedAuth);
        httpDelete.setHeader(HttpHeaders.AUTHORIZATION, authHeader);

        HttpResponse httpResponse = HttpClientBuilder
                .create()
                .build()
                .execute(httpDelete);
        // Testen
        assertEquals(HttpStatus.SC_OK, httpResponse.getStatusLine().getStatusCode());
    }


    @Test
    //Positiv
    public void testAddAutor() throws IOException {
        final HttpPost httpPost = new HttpPost("http://localhost:8080/Project_Lina_Z_war_exploded/resources/service");

        final String auth = "admin" + ":" + "1234";
        final byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(StandardCharsets.ISO_8859_1));
        final String authHeader = "Basic " + new String(encodedAuth);
        httpPost.setHeader(HttpHeaders.AUTHORIZATION, authHeader);

        String json = "{\"idAutor\":3,\"vorname\":\"Tina\",\"nachname\":\"Zweifel\",\"gebdatum\":\"2004-05-03\"," +
                "\"umsatz\":10.05,\"istAktiv\":false,\"buch\":{\"idBuch\":2}}"; //Als Modul m
        StringEntity entity = new StringEntity(json);
        httpPost.setEntity(entity);
        httpPost.setHeader("Content-type", "application/json");//Sagt was man sendet->JSON

        HttpResponse httpResponse = HttpClientBuilder
                .create()
                .build()
                .execute(httpPost);
        //Testen
        assertEquals(HttpStatus.SC_OK, httpResponse.getStatusLine().getStatusCode());
    }
    @Test
    //Negativ
    public void testAddAutorNeg() throws IOException {
        final HttpPost httpPost = new HttpPost("http://localhost:8080/Project_Lina_Z_war_exploded/resources/service");

        final String auth = "admin" + ":" + "1234";
        final byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(StandardCharsets.ISO_8859_1));
        final String authHeader = "Basic " + new String(encodedAuth);
        httpPost.setHeader(HttpHeaders.AUTHORIZATION, authHeader);

        String json = "{\"idAutor\":2,\"vorname\":\"Tina\",\"nachname\":\"Zweifel\",\"gebdatum\":[2004,5,3]," +
                "\"umsatz\":10.05,\"istAktiv\":false,\"idBuch\":2}"; //Als Modul m
        StringEntity entity = new StringEntity(json);
        httpPost.setEntity(entity);
        httpPost.setHeader("Content-type", "application/json");//Sagt was man sendet->JSON

        HttpResponse httpResponse = HttpClientBuilder
                .create()
                .build()
                .execute(httpPost);
        //Testen
        assertEquals(HttpStatus.SC_OK, httpResponse.getStatusLine().getStatusCode());
    }

    @Test
    //Positiv
    public void testAddAll() throws IOException {
        final HttpPost httpPost = new HttpPost("http://localhost:8080/Project_Lina_Z_war_exploded/resources/service/init");

        final String auth = "admin" + ":" + "1234";
        final byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(StandardCharsets.ISO_8859_1));
        final String authHeader = "Basic " + new String(encodedAuth);
        httpPost.setHeader(HttpHeaders.AUTHORIZATION, authHeader);

        httpPost.setHeader("Content-type", "application/json");//Sagt was man sendet->JSON

        HttpResponse httpResponse = HttpClientBuilder
                .create()
                .build()
                .execute(httpPost);
        //Testen
        assertEquals(HttpStatus.SC_OK, httpResponse.getStatusLine().getStatusCode());
    }


}
