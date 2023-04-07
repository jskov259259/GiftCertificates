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
import org.springframework.web.bind.annotation.RestController;
import ru.clevertec.ecl.model.Tag;
import ru.clevertec.ecl.service.TagService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/tags")
public class TagController {

    private TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<Tag>> findAll() {
        List<Tag> tags = tagService.findAll();
        return new ResponseEntity<>(tags, HttpStatus.OK);
    }

    @GetMapping(value="/{id}", produces = "application/json")
    public ResponseEntity<Tag> findById(@PathVariable Long id) {
        Tag tag = tagService.findById(id);
        return new ResponseEntity<>(tag, HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Long> create(@RequestBody Tag tag) {
        Long id = tagService.create(tag);
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<Integer> update(@PathVariable Long id, @RequestBody Tag tag) {
        tag.setId(id);
        int result = tagService.update(tag);
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}", produces = {"application/json"})
    public ResponseEntity<Integer> delete(@PathVariable Integer id) {
        tagService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}