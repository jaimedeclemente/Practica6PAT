package com.icai.practicas.controller;

import com.icai.practicas.controller.ProcessController.DataRequest;
import com.icai.practicas.controller.ProcessController.DataResponse;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.assertj.core.api.BDDAssertions.then;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProcessControllerTest {
    
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void processDataTestOK() {

        //Given
        String address = "http://localhost:" + port + "/api/v1/process-step1";

        //Request
        DataRequest example = new DataRequest("Jaime de Clemente", "02568420X", "626855391"); //Estos datos están bien
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<ProcessController.DataRequest> request = new HttpEntity<>(example, headers); //Creamos un request con los datos que le hemos metido antes

        //When
		ResponseEntity<DataResponse> result = this.restTemplate.postForEntity(address, request, DataResponse.class); //Cuando hacemos el request nos sale este resultado

        //Then
		String expectedResult = "OK"; //Si todo hubiese ido bien, nos debería salir este resultado...
		DataResponse expectedResponse = new DataResponse(expectedResult); //... y devolvernos esta respuesta

        then(result.getStatusCode()).isEqualTo(HttpStatus.OK); //Siempre devuelve OK, cambia el mensaje
        then(result.getBody().result()).isEqualTo(expectedResult); //Miramos que el resultado sea coherente con lo que creíamos
		then(result.getBody()).isEqualTo(expectedResponse); //Miramos que la respuesta es la que creíamos
    }

    @Test
    public void processDataTestKO() {

        //Given
        String address = "http://localhost:" + port + "/api/v1/process-step1";

        //Request
        DataRequest example = new DataRequest("Jaime de Clemente", "02568", "6268x5391"); //Estos datos no están bien
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<ProcessController.DataRequest> request = new HttpEntity<>(example, headers); //Creamos un request con los datos que hemos metido antes

        //When
        ResponseEntity<DataResponse> result = this.restTemplate.postForEntity(address, request, DataResponse.class); //Al hacer el request sale este resultado
		

		//Then
		String expectedResult = "KO"; //Supuestamente el resultado que nos debería salir es este...
		DataResponse expectedResponse = new DataResponse(expectedResult); //...junto con esta respuesta

        then(result.getStatusCode()).isEqualTo(HttpStatus.OK); //Siempre devuelve OK, cambia el mensaje que devuelve
        then(result.getBody().result()).isEqualTo(expectedResult); //Comprobamos que el resultado obtenido es igual al que creíamos que iba a salir
		then(result.getBody()).isEqualTo(expectedResponse); //Comprobamos también la respuesta
    }

    @Test
    public void processDataLegacyTestOK() {
        //Given
        String address = "http://localhost:" + port + "/api/v1/process-step1-legacy";

        //Request
        MultiValueMap<String, String> example = new LinkedMultiValueMap<String, String>(); //Creo un mapa donde meteré los valores que irán en el Body
        example.add("fullName", "Jaime de Clemente");
        example.add("dni", "02568420X");
        example.add("telefono", "626855391"); //Todos los datos tienen el formato correcto, por lo que deberían funcionar
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(example, headers); 

        //When
        ResponseEntity<String> result = this.restTemplate.postForEntity(address, request, String.class); //Cuando metemos lo que hemos definido sale este resultado

		//Then
		String expectedResult = ResponseHTMLGenerator.message1; //El message1 es lo que se genera cuando ha ido OK

        then(result.getStatusCode()).isEqualTo(HttpStatus.OK); //ProcessController siempre genera OK, cambia el mensaje que da
        then(result.getBody()).isEqualTo(expectedResult); //Miramos que lo que creemos que va a salir (message1) es igual que lo que sale (result)
    }

    @Test
    public void processDataLegacyTestKO() {
        //Given
        String address = "http://localhost:" + port + "/api/v1/process-step1-legacy";

        //Request
        MultiValueMap<String, String> example = new LinkedMultiValueMap<String, String>();
        example.add("fullName", "Jaime de Clemente");
        example.add("dni", "028420-");
        example.add("telefono", "6268z5391"); //Los datos no tienen el formato correcto así que no debería funcionar
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(example, headers);

        //When
        ResponseEntity<String> result = this.restTemplate.postForEntity(address, request, String.class); //Cuando metemos lo que hemos definido sale este resultado

		//Then
		String expectedResult = ResponseHTMLGenerator.message2; //El message2 es lo que se genera cuando ha ido mal

        then(result.getStatusCode()).isEqualTo(HttpStatus.OK); //ProcessController siempre genera OK, cambia el mensaje que da
        then(result.getBody()).isEqualTo(expectedResult); //Miramos que lo que creemos que va a salir (message2) es igual que lo que sale (result)
    }
}
