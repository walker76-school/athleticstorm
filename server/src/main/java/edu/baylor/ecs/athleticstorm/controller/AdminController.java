/*
 * Filename: AdminController.java
 * Author: Andrew Walker
 * Date Last Modified: 4/26/2020
 */

package edu.baylor.ecs.athleticstorm.controller;

import edu.baylor.ecs.athleticstorm.startUp.DataPopulator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for Admin data
 *
 * @author Andrew Walker
 */
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    DataPopulator dataPopulator;

    /**
     * Refreshes the DB
     */
    @PostMapping("/refresh")
    @PreAuthorize("hasRole('ADMIN')")
    public void authenticateUser() {
        dataPopulator.refresh();
    }
}
