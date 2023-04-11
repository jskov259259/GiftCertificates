package ru.clevertec.ecl.util;

import ru.clevertec.ecl.dto.GiftCertificateDto;
import ru.clevertec.ecl.dto.OrderDto;
import ru.clevertec.ecl.dto.TagDto;
import ru.clevertec.ecl.dto.UserDto;
import ru.clevertec.ecl.model.GiftCertificate;
import ru.clevertec.ecl.model.Order;
import ru.clevertec.ecl.model.Tag;
import ru.clevertec.ecl.model.User;

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

    public static GiftCertificate getCertificate() {
        return getCertificates().get(0);
    }

    public static GiftCertificateDto getCertificateDto() {
        return GiftCertificateDto.builder().id(1L).name("Certificate1").build();
    }

    public static List<Tag> getTags() {
        List<Tag> tags = new ArrayList<>();
        tags.add(new Tag(1L, "Tag1"));
        tags.add(new Tag(2L, "Tag2"));
        return tags;
    }

    public static Tag getTag() {
        return getTags().get(0);
    }

    public static TagDto getTagDto() {
        return new TagDto(1L, "Tag1");
    }

    public static List<User> getUsers() {
        List<User> users = new ArrayList<>();
        users.add(User.builder().id(1L).name("User1").build());
        users.add(User.builder().id(2L).name("User2").build());
        users.add(User.builder().id(3L).name("User3").build());
        return users;
    }

    public static User getUser() {
        return getUsers().get(0);
    }

    public static UserDto getUserDto() {
        return UserDto.builder().id(1L).name("User1").build();
    }

    public static List<Order> getOrders() {
        List<Order> orders = new ArrayList<>();
        orders.add(Order.builder().id(1L).certificate(getCertificates().get(0))
                .user(getUsers().get(0)).build());
        orders.add(Order.builder().id(2L).certificate(getCertificates().get(1))
                .user(getUsers().get(1)).build());
        orders.add(Order.builder().id(3L).certificate(getCertificates().get(2))
                .user(getUsers().get(2)).build());
        return orders;
    }

    public static Order getOrder() {
        return getOrders().get(0);
    }

    public static OrderDto getOrderDto() {
        return OrderDto.builder().id(1L).certificateId(1L).userId(1L).build();
    }
}