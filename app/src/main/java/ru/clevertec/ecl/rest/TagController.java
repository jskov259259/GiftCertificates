package ru.clevertec.ecl.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.clevertec.ecl.model.GiftCertificate;
import ru.clevertec.ecl.model.Tag;
import ru.clevertec.ecl.service.TagService;

import java.util.List;

@RestController
public class TagController {

    private TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping(value="/tags", produces = "application/json")
    public ResponseEntity<List<Tag>> tags() {

        List<Tag> tags = tagService.findAll();
        return new ResponseEntity<>(tags, HttpStatus.OK);
    }

    @PostMapping(value="/tags", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Long> createTag(@RequestBody Tag tag) {

        Long id = tagService.create(tag);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }
}
