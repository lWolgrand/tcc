package rj.com.tcc.user.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import rj.com.tcc.user.model.User
import rj.com.tcc.user.service.UserService

@RestController
@RequestMapping("/user")
class UserController(
    private val service: UserService
) {

    @GetMapping()
    fun get(): ResponseEntity<List<User>> {
       return ResponseEntity.ok(service.get())
    }

    @PostMapping("create")
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody user: User): User{
        return service.create(user)
    }

    @PutMapping("update/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun update(@PathVariable id: String, @RequestBody data: User): User{
        val name = data.name
        val surname = data.surname
        val email = data.email
        val birthdate = data.birthdate
        return service.update(id, name, surname, email, birthdate)
    }

    @DeleteMapping("delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun delete(@PathVariable id: String): ResponseEntity<String> {
        val success = service.delete(id)
        return if (success) {
            ResponseEntity.ok("Sucesso")
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado")
        }
    }
}


