package cl.spring.webpay.springwebpay.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import cl.spring.webpay.springwebpay.Utilidades.Constantes;
import cl.spring.webpay.springwebpay.model.CrearTokenModel;
import cl.spring.webpay.springwebpay.model.RequestModel;

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
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders(); 

        headers.set("Tbk-Api-Key-Id", Constantes.WEBPAY_CODIGO_COMERCIO);
        headers.set("Tbk-Api-Key-Secret", Constantes.WEBPAY_CODIGO_SECRETO);

        headers.setContentType(MediaType.APPLICATION_JSON);

        RequestModel post = new RequestModel("ordenCompra12345678", "sesion1234557545", 12000, "http://localhost:8080/respuesta");

        HttpEntity<RequestModel> request = new HttpEntity<>(post, headers);

        ResponseEntity<CrearTokenModel> response = restTemplate.postForEntity(Constantes.WEBPAY_URL, request, CrearTokenModel.class);

        model.addAttribute("response", response.getBody());
        return "pagar";
    }

    @GetMapping("respuesta")
    public String respuesta() {
        return "respuesta";
    }
}
