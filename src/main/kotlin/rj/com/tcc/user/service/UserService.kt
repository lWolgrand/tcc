package rj.com.tcc.user.service

import org.springframework.stereotype.Service
import rj.com.tcc.user.model.User
import rj.com.tcc.user.repository.UserRepository

@Service
class UserService(private val repository: UserRepository): IUserService {

    override fun get(): List<User> {
        return repository.get()
    }

    override fun create(user: User): User {
         return repository.create(user)
    }

    override fun update(id: String, name: String?, surname: String?, email: String?, birthdate: String?): User {
        return repository.update(id, name, surname, email, birthdate)
    }

    override fun delete(id: String): Boolean {
        return repository.delete(id)
    }



}