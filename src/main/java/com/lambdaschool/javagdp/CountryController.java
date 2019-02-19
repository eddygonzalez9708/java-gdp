package com.lambdaschool.javagdp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
public class CountryController {
    private final CountryRepository countryrepos;
    private final RabbitTemplate rt;

    public CountryController(CountryRepository countryrepos, RabbitTemplate rt) {
        this.countryrepos = countryrepos;
        this.rt = rt;
    }

    // /names -
    // return using the JSON format all of the countries alphabetized by name

    @GetMapping("/names")
    public List<Country> all() {
        return countryrepos.findAll();
    }
}
