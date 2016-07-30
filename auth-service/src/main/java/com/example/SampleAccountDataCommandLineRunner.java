package com.example;

import com.example.user.Account;
import com.example.user.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Component
public class SampleAccountDataCommandLineRunner implements CommandLineRunner {

    private final AccountRepository accountRepository;

    @Autowired
    public SampleAccountDataCommandLineRunner(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public void run(String... strings) throws Exception {

        Stream.of("jlong,spring", "pwebb,boot", "dsyer,cloud")
                .map(t -> t.split(","))
                .forEach(tuple ->
                        accountRepository.save(new Account(
                                tuple[0],
                                tuple[1],
                                true
                        ))
                );
    }
}
