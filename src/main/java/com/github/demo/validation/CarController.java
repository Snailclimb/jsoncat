package com.github.demo.validation;

import com.github.jsoncat.annotation.springmvc.PostMapping;
import com.github.jsoncat.annotation.springmvc.RequestBody;
import com.github.jsoncat.annotation.springmvc.RestController;
import com.github.jsoncat.annotation.validation.Validated;

import javax.validation.Valid;

@Validated
@RestController("/cars")
public class CarController {
    @PostMapping
    public CarDto create(@RequestBody @Valid CarDto carDto) {
        return carDto;
    }
}
