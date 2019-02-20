package com.lambdaschool.javagdp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
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

    // ### GET

    // /names -
    // return using the JSON format all of the countries alphabetized by name

    @GetMapping("/names")
    public List<Country> all() {
        return countryrepos.findAll();
    }

    // /economy -
    // return using the JSON format all of the countries sorted from most to least GDP

    @GetMapping("/economy")
    public List<Country> sortedGdps() {
        List<Country> sortedList = countryrepos.findAll();
        sortedList.sort(Comparator.comparingLong(Country::getGdp).reversed());
        return sortedList;
    }

    // /total -
    // return the sum of all GDPs using the JSON format with country name being returned as Total

    @GetMapping("/total")
    public ObjectNode sumGdps() {
        List<Country> countries = countryrepos.findAll();
        Long total = 0L;

        for (Country c : countries) {
            total += c.getGdp();
        }

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode totalGdps = mapper.createObjectNode();
        totalGdps.put("id", 0);
        totalGdps.put("country", total);

        return totalGdps;
    }

    // /gdp/{country name} -
    // return using the JSON format the record for that country.
    // Must be spelled as in the data!
    // Log that someone looked up this country

    @GetMapping("/gdp/{name}")
    public ObjectNode getname() {
        List<Country> countries = countryrepos.findAll();
        CountryLog message = new CountryLog("Checked Total GDPs");
        rt.convertAndSend(JavaGdpApplication.QUEUE_NAME, message.toString());
    }

    // ### POST
    // /gdp - loads the data from the provided JSON file

    @PostMapping("/gdp")
    public List<Country> gdp(@RequestBody List<Country> countries) {
        return countryrepos.saveAll(countries);
    }
}
