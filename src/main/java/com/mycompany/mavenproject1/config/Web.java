/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject1.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author stepa
 */
@Configuration
@ComponentScan(basePackageClasses = com.mycompany.mavenproject1.handler.web.Package.class)
public class Web {
}
