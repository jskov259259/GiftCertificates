package ru.clevertec.ecl.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.ecl.model.GiftCertificate;
import ru.clevertec.ecl.service.CertificateService;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@RestController
public class CertificateController {

    private CertificateService certificateService;

    @Autowired
    public CertificateController(CertificateService certificateService) {
        this.certificateService = certificateService;
    }

    @GetMapping(value="/certificates", produces = "application/json")
    public ResponseEntity<List<GiftCertificate>> certificates(@RequestParam(required=false) Map<String,String> filterParams) {

        List<GiftCertificate> certificates = certificateService.findAll(filterParams);
        return new ResponseEntity<>(certificates, HttpStatus.OK);
    }

    @GetMapping(value="/certificates/{id}", produces = "application/json")
    public ResponseEntity<GiftCertificate> certificateById(@PathVariable Long id) {

        GiftCertificate certificate = certificateService.findById(id);
        return new ResponseEntity<>(certificate, HttpStatus.OK);
    }

    @PostMapping(value="/certificates", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Long> createCertificate(@RequestBody GiftCertificate certificate) {

        Long id = certificateService.create(certificate);
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }

    @PutMapping(value = "/certificates/{id}", consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<Integer> updateCertificate(@PathVariable Long id, @RequestBody GiftCertificate certificate) {

        certificate.setId(id);
        int result = certificateService.update(certificate);
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @DeleteMapping(value = "/certificates/{id}", produces = {"application/json"})
    public ResponseEntity<Integer> deleteCertificate(@PathVariable Integer id) {

        int result = certificateService.delete(id);
        return new ResponseEntity(result, HttpStatus.OK);
    }
}
