package org.blacksmith;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
@SpringBootApplication
public class Main {
    // TODO : Change the model class to remove mongodb
    // TODO : Try to tunnel this program
    // TODO : Create a frontend
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}