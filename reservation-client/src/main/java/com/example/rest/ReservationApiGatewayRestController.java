package com.example.rest;

import com.example.cqrs.ReservationChannels;
import com.example.rest.reservation.Reservation;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpMethod;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/reservations")
class ReservationApiGatewayRestController {



   private final RestTemplate restTemplate;

   @Autowired
   public ReservationApiGatewayRestController(RestTemplate restTemplate, ReservationChannels channels) {
      this.restTemplate = restTemplate;
      this.channel =channels.output();
   }


   public Collection<String> fallback() {
      return new ArrayList<>();
   }

   @HystrixCommand(fallbackMethod = "fallback")
   @RequestMapping(method = RequestMethod.GET, value = "/names")
   public Collection<String> names() {
      return this.restTemplate.exchange("http://reservation-service/reservations", //service name
              HttpMethod.GET,
              null,
              new ParameterizedTypeReference<Resources<Reservation>>() {
              })
              .getBody()
              .getContent()
              .stream()
              .map(Reservation::getReservationName)
              .collect(Collectors.toList());
   }


   private final MessageChannel channel;

   @RequestMapping(method = RequestMethod.POST)
   public void write(@RequestBody Reservation reservation) {
      Message<String> msg =
              MessageBuilder
                      .withPayload(reservation.getReservationName())
                      .build();
      this.channel.send(msg);
   }
}
