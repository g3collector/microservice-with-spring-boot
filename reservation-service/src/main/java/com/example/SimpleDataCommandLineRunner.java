package com.example;

import com.example.reservation.Reservation;
import com.example.reservation.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Component
public class SimpleDataCommandLineRunner implements CommandLineRunner {

    private ReservationRepository reservationRepository;

    @Autowired
    public SimpleDataCommandLineRunner(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Stream.of("takuma", "michiyo", "fumiyuki", "yousuke")
                .forEach(s -> reservationRepository.save(new Reservation(s)));

        reservationRepository.findAll().forEach(System.out::println);
    }
}
