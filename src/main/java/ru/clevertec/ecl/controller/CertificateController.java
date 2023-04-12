package ru.clevertec.ecl.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.clevertec.ecl.dto.GiftCertificateDto;
import ru.clevertec.ecl.service.CertificateService;

import java.math.BigDecimal;
import java.util.List;

import static ru.clevertec.ecl.controller.config.Constants.DEFAULT_PAGE_NO;
import static ru.clevertec.ecl.controller.config.Constants.DEFAULT_PAGE_SIZE;
import static ru.clevertec.ecl.controller.config.Constants.DEFAULT_SORT_BY;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/certificates")
public class CertificateController {

    private final CertificateService certificateService;

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<GiftCertificateDto>> findAll(
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(defaultValue = DEFAULT_PAGE_NO) Integer pageNo,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) Integer pageSize,
            @RequestParam(defaultValue = DEFAULT_SORT_BY) String sortBy) {
        List<GiftCertificateDto> certificates = certificateService.findAll(search, pageNo, pageSize, sortBy);
        return new ResponseEntity<>(certificates, HttpStatus.OK);
    }

    @GetMapping(value="/{id}", produces = "application/json")
    public ResponseEntity<GiftCertificateDto> findById(@PathVariable Long id) {
        GiftCertificateDto certificate = certificateService.findById(id);
        return new ResponseEntity<>(certificate, HttpStatus.OK);
    }

    @GetMapping(value="/tags", consumes = "application/json", produces = "application/json")
    public ResponseEntity<List<GiftCertificateDto>> findAllByTagNames(
            @RequestBody List<String> tagNames,
            @RequestParam(defaultValue = DEFAULT_PAGE_NO) Integer pageNo,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) Integer pageSize,
            @RequestParam(defaultValue = DEFAULT_SORT_BY) String sortBy) {
        List<GiftCertificateDto> certificates = certificateService
                .findAllByTagNames(pageNo, pageSize, sortBy, tagNames);
        return new ResponseEntity<>(certificates, HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<GiftCertificateDto> create(@RequestBody @Valid GiftCertificateDto certificateDto) {
        GiftCertificateDto createdGiftCertificateDto = certificateService.save(certificateDto);
        return new ResponseEntity<>(createdGiftCertificateDto, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<GiftCertificateDto> update(@PathVariable Long id,
                                                     @RequestBody @Valid GiftCertificateDto certificateDto) {
        certificateDto.setId(id);
        GiftCertificateDto certificate = certificateService.update(certificateDto);
        return new ResponseEntity<>(certificate, HttpStatus.OK);
    }

    @PatchMapping("/{id}/price")
    public ResponseEntity<?> updatePrice(@PathVariable Long id, @RequestBody BigDecimal price) {
        certificateService.updatePrice(id, price);
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}", produces = {"application/json"})
    public ResponseEntity<?> delete(@PathVariable Long id) {
        certificateService.deleteById(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
