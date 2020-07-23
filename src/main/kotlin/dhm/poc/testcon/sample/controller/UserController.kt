package dhm.poc.testcon.sample.controller

import dhm.poc.testcon.sample.model.User
import dhm.poc.testcon.sample.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping(UserController.URL)
class UserController(
    private val userService: UserService
) {
    
    companion object {
        const val URL = "v1/user"
    }
    
    @GetMapping
    fun getUsersList(): List<User> {
        return userService.list()
    }
    
    @PutMapping("/create/")
    @ResponseStatus(HttpStatus.OK)
    fun createUser(@RequestBody user: User) {
        userService.publish(user)
    }
    
    @DeleteMapping("/id")
    fun deleteUser(@PathVariable("{id}")id: UUID) {
        val user = userService.findById(id)
        if(user != null) {
            userService.delete(user)
        }
    }
    
}