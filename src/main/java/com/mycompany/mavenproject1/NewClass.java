/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 *
 * @author stepa
 */
@SpringBootApplication
@ComponentScan(basePackageClasses = {com.mycompany.mavenproject1.config.Package.class})
public class NewClass {
    public static void main(String[] args) {
        SpringApplication.run(NewClass.class, args);
    }
}
