package ru.clevertec.ecl.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.clevertec.ecl.service.CertificateService;

@RestController
public class HelloController {

    private CertificateService certificateService;

    @Autowired
    public HelloController(CertificateService certificateService) {
        this.certificateService = certificateService;
    }

    @GetMapping(value="/hello")
    public ResponseEntity<String> getHelloMessage() {
        System.out.println(certificateService.getHello());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
