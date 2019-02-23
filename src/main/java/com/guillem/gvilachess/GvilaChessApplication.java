package com.guillem.gvilachess;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;


@SpringBootApplication
public class GvilaChessApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(GvilaChessApplication.class).run(args);
    }

}
