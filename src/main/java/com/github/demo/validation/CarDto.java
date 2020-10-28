package com.github.demo.validation;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CarDto {
    @NotNull
    private String name;
}
