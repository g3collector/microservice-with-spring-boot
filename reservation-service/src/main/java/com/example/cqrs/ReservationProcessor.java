package com.example.cqrs;

import com.example.reservation.Reservation;
import com.example.reservation.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;

@MessageEndpoint
public class ReservationProcessor {

    @ServiceActivator(inputChannel = Sink.INPUT)
    public void acceptNewReservation(Message<String> msg) {
        this.reservationRepository.save(new Reservation(msg.getPayload()));
    }

    private final ReservationRepository reservationRepository;

    @Autowired
    public ReservationProcessor(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }
}
