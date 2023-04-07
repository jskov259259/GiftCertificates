package ru.clevertec.ecl.controller;

import lombok.RequiredArgsConstructor;
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
import java.util.Optional;

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
        Optional<GiftCertificate> certificate = certificateService.findById(id);

        if (certificate.isPresent()) {
            return new ResponseEntity<>(certificate.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Long> create(@RequestBody GiftCertificate certificate) {
        Long id = certificateService.save(certificate);
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<Long> update(@PathVariable Long id, @RequestBody GiftCertificate certificate) {
        Optional<GiftCertificate> certificateData = certificateService.findById(id);

        if (certificateData.isPresent()) {
            GiftCertificate newCertificate = certificateData.get();
            newCertificate.setName(certificate.getName());
            newCertificate.setDescription(certificate.getDescription());
            newCertificate.setPrice(certificate.getPrice());
            newCertificate.setDuration(certificate.getDuration());
            return new ResponseEntity<>(certificateService.save(newCertificate), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "/{id}", produces = {"application/json"})
    public ResponseEntity<Integer> delete(@PathVariable Integer id) {
        certificateService.deleteById(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
