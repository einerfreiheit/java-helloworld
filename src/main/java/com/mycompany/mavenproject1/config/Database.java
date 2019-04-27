/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject1.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 *
 * @author stepa
 */
@Configuration
@ComponentScan(basePackageClasses = com.mycompany.mavenproject1.model.Package.class)
@EnableJpaRepositories(basePackageClasses = com.mycompany.mavenproject1.model.repository.Package.class)
public class Database {
    
}
