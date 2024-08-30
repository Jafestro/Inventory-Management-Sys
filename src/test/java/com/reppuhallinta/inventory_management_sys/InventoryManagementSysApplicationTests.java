package com.reppuhallinta.inventory_management_sys;

import com.reppuhallinta.inventory_management_sys.model.User;
import com.reppuhallinta.inventory_management_sys.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class InventoryManagementSysApplicationTests {

    @Autowired
    private UserService userService;

    @Test
    void testDatabaseConnection() {
        // Create a new user
        User user = new User();
        user.setUsername("testuser");
        user.setFirstName("Test");
        user.setLastName("User");
        user.setPassword("password");
        user.setAccessLevel("1");

        // Save the user
        User savedUser = userService.createUser(user);

        // Verify the user was saved
        assertThat(savedUser.getId()).isNotNull();
        assertThat(userService.getUserById(savedUser.getId())).isNotNull();

        // Retrieve the user
        User retrievedUser = userService.getUserById(savedUser.getId());
        assertThat(retrievedUser).isNotNull();
        assertThat(retrievedUser.getUsername()).isEqualTo("testuser");

        // Update the user
        retrievedUser.setFirstName("Updated");
        User updatedUser = userService.updateUser(retrievedUser.getId(), retrievedUser);
        assertThat(updatedUser.getFirstName()).isEqualTo("Updated");

        // Delete the user
        userService.removeUser(updatedUser.getId());
        assertThat(userService.getUserById(updatedUser.getId())).isNull();
    }
}