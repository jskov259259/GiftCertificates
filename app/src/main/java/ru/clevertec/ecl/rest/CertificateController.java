package ru.clevertec.ecl.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.ecl.model.GiftCertificate;
import ru.clevertec.ecl.service.CertificateService;

import java.util.List;

@RestController
public class CertificateController {

    private CertificateService certificateService;

    @Autowired
    public CertificateController(CertificateService certificateService) {
        this.certificateService = certificateService;
    }

    @GetMapping(value="/certificates", produces = "application/json")
    public ResponseEntity<List<GiftCertificate>> certificates() {

        List<GiftCertificate> certificates = certificateService.findAll();
        return new ResponseEntity<>(certificates, HttpStatus.OK);
    }

    @PostMapping(value="/certificates", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Long> createCertificate(@RequestBody GiftCertificate certificate) {

        Long id = certificateService.create(certificate);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @PutMapping(value = "/certificates/{id}", consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<Integer> updateCertificate(@PathVariable Integer id, @RequestBody GiftCertificate certificate) {

        certificate.setId(id);
        int result = certificateService.update(certificate);
        return new ResponseEntity(result, HttpStatus.OK);
    }
}
