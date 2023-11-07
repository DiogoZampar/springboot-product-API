package com.example.springboot.DTOs;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;




public record ProductRecordDTO(@NotBlank String name, @NotNull @PositiveOrZero BigDecimal value){


    

}