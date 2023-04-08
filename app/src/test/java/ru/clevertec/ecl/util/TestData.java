package ru.clevertec.ecl.util;

import ru.clevertec.ecl.model.GiftCertificate;
import ru.clevertec.ecl.model.Tag;

import java.util.ArrayList;
import java.util.List;

public class TestData {

    public static List<GiftCertificate> getCertificates() {
        List<GiftCertificate> certificates = new ArrayList<>();
        certificates.add(GiftCertificate.builder().id(1L).name("Certificate1").build());
        certificates.add(GiftCertificate.builder().id(2L).name("Certificate2").build());
        certificates.add(GiftCertificate.builder().id(3L).name("Certificate3").build());
        return certificates;
    }

    public static List<Tag> getTags() {
        List<Tag> tags = new ArrayList<>();
        tags.add(new Tag(1L, "Tag1"));
        tags.add(new Tag(2L, "Tag2"));
        return tags;
    }
}
