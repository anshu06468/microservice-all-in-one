package com.microservice.card.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.microservice.card.config.CardsServiceConfig;
import com.microservice.card.model.Cards;
import com.microservice.card.model.Customer;
import com.microservice.card.model.Properties;
import com.microservice.card.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CardsController {

    @Autowired
    private CardRepository cardRepository;
    
    @Autowired
    private CardsServiceConfig cardsServiceConfig;

    @PostMapping("/myCards")
    public List<Cards> getCardsDetails(@RequestBody Customer customer){
        List<Cards> cards = cardRepository.findByCustomerId(customer.getCustomerId());

        if(cards != null) {
            return cards;
        }else{
            return null;
        }
    }

    @GetMapping("/card/properties")
    public String getPropertyDetails() throws JsonProcessingException {
        ObjectWriter objectWriter=new ObjectMapper().writer().withDefaultPrettyPrinter();
        Properties properties=new Properties(cardsServiceConfig.getMsg(),
                cardsServiceConfig.getBuildVersion(),
                cardsServiceConfig.getMailDetails(),cardsServiceConfig.getActiveBranches());
        String jsonStr=objectWriter.writeValueAsString(properties);
        return jsonStr;
    }
}
