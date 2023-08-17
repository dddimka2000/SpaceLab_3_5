package org.example.dto;

import jakarta.validation.Valid;
import lombok.Data;

import jakarta.validation.constraints.*;

import java.util.List;

@Data
public class OrderTableDTO {

    @NotBlank(message = "Пустой  логин")
    String login;
    @Size(max = 450, message = "Максимум 450 символов")
    String comment;
    Boolean status;
    List<String> products;
    @Valid
    @Size(min = 1, message = "Минимум 1 товар должен быть в заказе")
    List<String> quantity;

}
