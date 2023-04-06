package ru.clevertec.ecl.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.clevertec.ecl.model.GiftCertificate;
import ru.clevertec.ecl.service.CertificateService;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/certificates")
public class CertificateController {

    private final CertificateService certificateService;

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<GiftCertificate>> findAll(@RequestParam(required=false) Map<String,String> filterParams) {
        List<GiftCertificate> certificates = certificateService.findAll(filterParams);
        return new ResponseEntity<>(certificates, HttpStatus.OK);
    }

    @GetMapping(value="/{id}", produces = "application/json")
    public ResponseEntity<GiftCertificate> findById(@PathVariable Long id) {
        GiftCertificate certificate = certificateService.findById(id);
        return new ResponseEntity<>(certificate, HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Long> create(@RequestBody GiftCertificate certificate) {
        Long id = certificateService.create(certificate);
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<Integer> update(@PathVariable Long id, @RequestBody GiftCertificate certificate) {
        certificate.setId(id);
        int result = certificateService.update(certificate);
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}", produces = {"application/json"})
    public ResponseEntity<Integer> delete(@PathVariable Integer id) {
        certificateService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
