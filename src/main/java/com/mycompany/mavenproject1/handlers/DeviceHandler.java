/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject1.handlers;

import com.mycompany.mavenproject1.model.Device;
import com.mycompany.mavenproject1.model.repository.Devices;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author stepa
 */
@RestController
@RequestMapping(path = "/dev")
public class DeviceHandler {

    @Autowired
    Devices devices;

    @RequestMapping(method = RequestMethod.GET, path = "/")
    public List<Device> list() {
        return devices.findAll();
    }
    
    @RequestMapping(method = RequestMethod.GET, path = "/add/")
    public Device add(@RequestParam String name) {
        Device d = new Device();
        d.setName(name);
        return devices.save(d);
    }

}
