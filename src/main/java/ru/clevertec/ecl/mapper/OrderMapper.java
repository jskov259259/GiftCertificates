package ru.clevertec.ecl.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.clevertec.ecl.dto.OrderDto;
import ru.clevertec.ecl.model.Order;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(target = "userId", expression = "java(order.getUser().getId())")
    @Mapping(target = "certificateId", expression = "java(order.getCertificate().getId())")
    OrderDto orderToDto(Order order);
}
