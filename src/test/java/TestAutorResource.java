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
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.junit.Assert.assertEquals;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestAutorResource {

    @Test
    //Positiv
    public void test02Ping() throws Exception {
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
    //Positiv
    public void test03EveryAutor() throws IOException {
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
                "\"istAktiv\":false,\"buch\":{\"idBuch\":2,\"titel\":\"Java lernen leicht gemacht\"}}]", response);
    }


    @Test
    //Positiv
    public void test04CountEveryAutor() throws Exception {
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
        assertEquals("2 Autoren wurden in der Liste gefunden.", response);
    }


    @Test
    //Positiv
    public void test05FindAutorById() throws IOException {
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
    //Negativ -> Autor existiert nicht
    public void test06FindAutorByIdNeg() throws IOException {
        HttpGet request = new HttpGet("http://localhost:8080/Project_Lina_Z_war_exploded/resources/service/byId?id=7");

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
    public void test07FindAutorByDate() throws IOException {
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
        //assertEquals(HttpStatus.SC_OK, httpResponse.getStatusLine().getStatusCode());
        assertEquals("{\"idAutor\":1,\"vorname\":\"Lina\",\"nachname\":\"Zweifel\",\"gebdatum\":[2004,5,3]," +
                "\"umsatz\":10.05,\"istAktiv\":true,\"buch\":{\"idBuch\":1,\"titel\":\"Linas Reise\"}}", response);
    }
    @Test
    //Negativ
    public void test08FindAutorByDateNeg() throws IOException {
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
    public void test09UpdateAutor() throws IOException {
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
    //negativ -> Autor existiert nicht
    public void test10UpdateAutorNeg() throws IOException {
        final HttpPut httpPut = new HttpPut("http://localhost:8080/Project_Lina_Z_war_exploded/resources/service");

        final String auth = "admin" + ":" + "1234";
        final byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(StandardCharsets.ISO_8859_1));
        final String authHeader = "Basic " + new String(encodedAuth);
        httpPut.setHeader(HttpHeaders.AUTHORIZATION, authHeader);

        String json = "{\"idAutor\":7,\"vorname\":\"Lina\",\"nachname\":\"Zweifel\",\"gebdatum\":[2004,5,3]," +
                "\"umsatz\":10.05,\"istAktiv\":true}";
        StringEntity entity = new StringEntity(json);
        httpPut.setEntity(entity);
        httpPut.setHeader("Content-type", "application/json");

        HttpResponse httpResponse = HttpClientBuilder
                .create()
                .build()
                .execute(httpPut);
        //Testen-> gibt nichts zum updaten
        assertEquals(HttpStatus.SC_NOT_FOUND, httpResponse.getStatusLine().getStatusCode());
    }


    @Test
    //Positiv
    public void test13DeleteAutor() throws IOException {
        final HttpDelete httpDelete = new HttpDelete("http://localhost:8080/Project_Lina_Z_war_exploded/resources/service/8");

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
        assertEquals("Autor 8 geloscht", response);
    }
    @Test
    //Negativ -> Autor existiert nicht
    public void test14DeleteAutorNeg() throws IOException {
        final HttpDelete httpDelete = new HttpDelete("http://localhost:8080/Project_Lina_Z_war_exploded/resources/service/10");

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
        assertEquals(HttpStatus.SC_NOT_FOUND, httpResponse.getStatusLine().getStatusCode());
    }


    @Test
    //Positiv
    public void test20DeleteAll() throws IOException {
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
    public void test11AddAutor() throws IOException {
        final HttpPost httpPost = new HttpPost("http://localhost:8080/Project_Lina_Z_war_exploded/resources/service");

        final String auth = "admin" + ":" + "1234";
        final byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(StandardCharsets.ISO_8859_1));
        final String authHeader = "Basic " + new String(encodedAuth);
        httpPost.setHeader(HttpHeaders.AUTHORIZATION, authHeader);

        String json = "{\"idAutor\":8,\"vorname\":\"Tina\",\"nachname\":\"Zweifel\",\"gebdatum\":\"2004-05-03\"," +
                "\"umsatz\":10.05,\"istAktiv\":false,\"buch\":{\"idBuch\":2}}";
        StringEntity entity = new StringEntity(json);
        httpPost.setEntity(entity);
        httpPost.setHeader("Content-type", "application/json");
        HttpResponse httpResponse = HttpClientBuilder
                .create()
                .build()
                .execute(httpPost);
        //Testen
        assertEquals(HttpStatus.SC_OK, httpResponse.getStatusLine().getStatusCode());
    }
    @Test
    //Positiv -> mit null
    public void test11AddAutor2() throws IOException {
        final HttpPost httpPost = new HttpPost("http://localhost:8080/Project_Lina_Z_war_exploded/resources/service");

        final String auth = "admin" + ":" + "1234";
        final byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(StandardCharsets.ISO_8859_1));
        final String authHeader = "Basic " + new String(encodedAuth);
        httpPost.setHeader(HttpHeaders.AUTHORIZATION, authHeader);

        String json = "{\"idAutor\":9,\"vorname\":null,\"nachname\":null,\"gebdatum\":null," +
                "\"umsatz\":null,\"istAktiv\":null,\"buch\":{\"idBuch\":2}}";
        StringEntity entity = new StringEntity(json);
        httpPost.setEntity(entity);
        httpPost.setHeader("Content-type", "application/json");
        HttpResponse httpResponse = HttpClientBuilder
                .create()
                .build()
                .execute(httpPost);
        //Testen
        assertEquals(HttpStatus.SC_OK, httpResponse.getStatusLine().getStatusCode());
    }
    @Test
    //Negativ -> Gibt schon einen Eintrag mit idAutor= 2
    public void test12AddAutorNeg() throws IOException {
        final HttpPost httpPost = new HttpPost("http://localhost:8080/Project_Lina_Z_war_exploded/resources/service");

        final String auth = "admin" + ":" + "1234";
        final byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(StandardCharsets.ISO_8859_1));
        final String authHeader = "Basic " + new String(encodedAuth);
        httpPost.setHeader(HttpHeaders.AUTHORIZATION, authHeader);

        String json = "{\"idAutor\":2,\"vorname\":\"Sina\",\"nachname\":\"Zweifel\",\"gebdatum\":null," +
                "\"umsatz\":10.05,\"istAktiv\":false,\"buch\":{\"idBuch\":2}}";
        StringEntity entity = new StringEntity(json);
        httpPost.setEntity(entity);
        httpPost.setHeader("Content-type", "application/json");

        HttpResponse httpResponse = HttpClientBuilder
                .create()
                .build()
                .execute(httpPost);
        //Testen
        assertEquals(HttpStatus.SC_CONFLICT, httpResponse.getStatusLine().getStatusCode());
    }
    @Test
    //Randbedingungen -> Datum liegt in Zukunft
    public void test12AddAutorRand1() throws IOException {
        final HttpPost httpPost = new HttpPost("http://localhost:8080/Project_Lina_Z_war_exploded/resources/service");

        final String auth = "admin" + ":" + "1234";
        final byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(StandardCharsets.ISO_8859_1));
        final String authHeader = "Basic " + new String(encodedAuth);
        httpPost.setHeader(HttpHeaders.AUTHORIZATION, authHeader);

        String json = "{\"idAutor\":5,\"vorname\":\"Sina\",\"nachname\":\"Zweifel\",\"gebdatum\":[2026,5,3]," +
                "\"umsatz\":10.05,\"istAktiv\":false,\"buch\":{\"idBuch\":2}}";
        StringEntity entity = new StringEntity(json);
        httpPost.setEntity(entity);
        httpPost.setHeader("Content-type", "application/json");

        HttpResponse httpResponse = HttpClientBuilder
                .create()
                .build()
                .execute(httpPost);
        //Testen
        assertEquals(HttpStatus.SC_BAD_REQUEST, httpResponse.getStatusLine().getStatusCode());
    }
    @Test
    //Randbedingungen -> Vorname ist länger als 25 Zeichen
    public void test12AddAutorRand2() throws IOException {
        final HttpPost httpPost = new HttpPost("http://localhost:8080/Project_Lina_Z_war_exploded/resources/service");

        final String auth = "admin" + ":" + "1234";
        final byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(StandardCharsets.ISO_8859_1));
        final String authHeader = "Basic " + new String(encodedAuth);
        httpPost.setHeader(HttpHeaders.AUTHORIZATION, authHeader);

        String json = "{\"idAutor\":5,\"vorname\":\"Sinatinalinabinaninarinamina\",\"nachname\":\"Zweifel\",\"gebdatum\":[2004,5,3]," +
                "\"umsatz\":10.05,\"istAktiv\":false,\"buch\":{\"idBuch\":2}}";
        StringEntity entity = new StringEntity(json);
        httpPost.setEntity(entity);
        httpPost.setHeader("Content-type", "application/json");

        HttpResponse httpResponse = HttpClientBuilder
                .create()
                .build()
                .execute(httpPost);
        //Testen
        assertEquals(HttpStatus.SC_BAD_REQUEST, httpResponse.getStatusLine().getStatusCode());
    }
    @Test
    //Randbedingungen -> umsatz ist negativ
    public void test12AddAutorRand3() throws IOException {
        final HttpPost httpPost = new HttpPost("http://localhost:8080/Project_Lina_Z_war_exploded/resources/service");

        final String auth = "admin" + ":" + "1234";
        final byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(StandardCharsets.ISO_8859_1));
        final String authHeader = "Basic " + new String(encodedAuth);
        httpPost.setHeader(HttpHeaders.AUTHORIZATION, authHeader);

        String json = "{\"idAutor\":5,\"vorname\":\"Sina\",\"nachname\":\"Zweifel\",\"gebdatum\":[2026,5,3]," +
                "\"umsatz\":-10.05,\"istAktiv\":false,\"buch\":{\"idBuch\":2}}";
        StringEntity entity = new StringEntity(json);
        httpPost.setEntity(entity);
        httpPost.setHeader("Content-type", "application/json");

        HttpResponse httpResponse = HttpClientBuilder
                .create()
                .build()
                .execute(httpPost);
        //Testen
        assertEquals(HttpStatus.SC_BAD_REQUEST, httpResponse.getStatusLine().getStatusCode());
    }


    @Test
    //Positiv -> Funktioniert nur wenn beide Tabellen leer sind/ nicht existieren
    public void test01AddAll() throws IOException {
        final HttpPost httpPost = new HttpPost("http://localhost:8080/Project_Lina_Z_war_exploded/resources/service/init");

        final String auth = "admin" + ":" + "1234";
        final byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(StandardCharsets.ISO_8859_1));
        final String authHeader = "Basic " + new String(encodedAuth);
        httpPost.setHeader(HttpHeaders.AUTHORIZATION, authHeader);

        httpPost.setHeader("Content-type", "application/json");

        HttpResponse httpResponse = HttpClientBuilder
                .create()
                .build()
                .execute(httpPost);
        //Testen
        assertEquals(HttpStatus.SC_OK, httpResponse.getStatusLine().getStatusCode());
    }


}
