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
import java.util.Optional;

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
        Optional<Tag> tag = tagService.findById(id);

        if (tag.isPresent()) {
            return new ResponseEntity<>(tag.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Long> create(@RequestBody Tag tag) {
        Long id = tagService.save(tag);
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<Long> update(@PathVariable Long id, @RequestBody Tag tag) {
        Optional<Tag> tagData = tagService.findById(id);

        if (tagData.isPresent()) {
            Tag newTag = tagData.get();
            newTag.setName(tag.getName());
            return new ResponseEntity<>(tagService.save(newTag), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "/{id}", produces = {"application/json"})
    public ResponseEntity<Integer> delete(@PathVariable Integer id) {
        tagService.deleteById(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}