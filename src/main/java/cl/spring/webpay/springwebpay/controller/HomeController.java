package cl.spring.webpay.springwebpay.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import cl.spring.webpay.springwebpay.Utilidades.Constantes;
import cl.spring.webpay.springwebpay.model.CrearTokenModel;
import cl.spring.webpay.springwebpay.model.RequestModel;
import cl.spring.webpay.springwebpay.model.ResponseModel;
import reactor.core.publisher.Mono;

import org.springframework.web.bind.annotation.GetMapping;


@Controller
@RequestMapping("/")
public class HomeController {

    @GetMapping("")
    public String home() {
        return "home";
    }
    
    @GetMapping("pagar")
    public String pagar(Model model) {
        /* 
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders(); 

        headers.set("Tbk-Api-Key-Id", Constantes.WEBPAY_CODIGO_COMERCIO);
        headers.set("Tbk-Api-Key-Secret", Constantes.WEBPAY_CODIGO_SECRETO);

        headers.setContentType(MediaType.APPLICATION_JSON);

        RequestModel post = new RequestModel("ordenCompra12345678", "sesion1234557545", 12000, "http://localhost:8080/respuesta");

        HttpEntity<RequestModel> request = new HttpEntity<>(post, headers);

        ResponseEntity<CrearTokenModel> response = restTemplate.postForEntity(Constantes.WEBPAY_URL, request, CrearTokenModel.class);

        */

        WebClient client = WebClient.builder().baseUrl(Constantes.WEBPAY_URL2).build();

        RequestModel requestModel = new RequestModel("ordenCompra54321", "sesion1234557545", 5000, "http://localhost:8080/respuesta");

        Mono<CrearTokenModel> res = client.post().uri(Constantes.WEBPAY_URI).bodyValue(requestModel)
                    .headers(httpheader -> {httpheader.set("Tbk-Api-Key-Id", Constantes.WEBPAY_CODIGO_COMERCIO);
                                            httpheader.set("Tbk-Api-Key-Secret", Constantes.WEBPAY_CODIGO_SECRETO);
                                            httpheader.setContentType(MediaType.APPLICATION_JSON);})
                    .retrieve().bodyToMono(CrearTokenModel.class);


        System.out.println("url= "+res.block().getUrl());
        System.out.println("token= "+res.block().getToken());
        model.addAttribute("response", res.block());
        return "pagar";
    }

    @GetMapping("respuesta")
    public String respuesta(Model model, @RequestParam("token_ws") String token_ws) {
       /* 
        System.out.println("metodo= "+Constantes.WEBPAY_URL+"/"+token_ws);
       
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();

        headers.set("Tbk-Api-Key-Id", Constantes.WEBPAY_CODIGO_COMERCIO);
        headers.set("Tbk-Api-Key-Secret", Constantes.WEBPAY_CODIGO_SECRETO);

        headers.setContentType(MediaType.APPLICATION_JSON);
        
        HttpEntity<String> entity = new HttpEntity<>(headers);
        
        ResponseEntity<ResponseModel> response = restTemplate.exchange(Constantes.WEBPAY_URL+"/"+token_ws, HttpMethod.PUT, entity, ResponseModel.class);

        ResponseModel respuesta = response.getBody();

        model.addAttribute("respuesta", respuesta);
        
*/
        
        WebClient client = WebClient.builder().baseUrl(Constantes.WEBPAY_URL2).build();

        System.out.println("url = "+Constantes.WEBPAY_URL2 + Constantes.WEBPAY_URI + "/" + token_ws);
        Mono<ResponseModel> respuesta = client.put().uri(Constantes.WEBPAY_URI+"/"+token_ws)
                                .headers(httpheader -> {httpheader.set("Tbk-Api-Key-Id", Constantes.WEBPAY_CODIGO_COMERCIO);
                                                        httpheader.set("Tbk-Api-Key-Secret", Constantes.WEBPAY_CODIGO_SECRETO);
                                                        httpheader.setContentType(MediaType.APPLICATION_JSON);})
                                .retrieve().bodyToMono(ResponseModel.class);

        model.addAttribute("respuesta", respuesta.block());
        
        model.addAttribute("token_ws", token_ws);
        return "respuesta";
    }

}
