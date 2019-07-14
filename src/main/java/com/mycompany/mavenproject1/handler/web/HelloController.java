/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject1.handler.web;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 *
 * @author stepa
 */
@RestController
public class HelloController {

    private String message = "Greeting!";

    @RequestMapping("/")
    public String index() {
        return message;
    }

}