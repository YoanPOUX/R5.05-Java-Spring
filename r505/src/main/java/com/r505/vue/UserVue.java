package com.r505.vue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.r505.controller.UserService;
import com.r505.modele.User;

@Controller
@RequestMapping(path="/user")
public class UserVue {
  @Autowired
  private UserService userService;

  @GetMapping(path="/all")
  public @ResponseBody Iterable<User> getAllUsers() {
    return userService.getAllUsers();
  }

  @GetMapping(path="/{id}")
  public @ResponseBody User getUserById(@PathVariable Integer id) {
      return userService.getUserById(id);
  }

  @PostMapping(path="/add")
  public @ResponseBody String addNewUser (@RequestBody User user) {
    userService.addUser(user.getUsername(), user.getPassword(), user.getIsModerator());
    return "Saved";
  }

  @PutMapping(path="/update")
  public @ResponseBody User updateUser(@RequestBody User user) {
      return userService.updateUser(user.getId(), user.getUsername(), user.getPassword(), user.getIsModerator());
  }

  @DeleteMapping(path="/delete/{id}") // DELETE /user/delete/{id}
  public @ResponseBody String deleteUser(@PathVariable Integer id) {
      userService.deleteUser(id);
      return "Deleted";
  }
}