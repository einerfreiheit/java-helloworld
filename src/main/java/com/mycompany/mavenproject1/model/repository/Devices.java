/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject1.model.repository;

import com.mycompany.mavenproject1.model.Device;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author stepa
 */
public interface Devices extends JpaRepository<Device, Long> {
}
