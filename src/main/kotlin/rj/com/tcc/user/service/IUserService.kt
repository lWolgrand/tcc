package rj.com.tcc.user.service

import rj.com.tcc.user.model.User

interface  IUserService {
    fun get(): List<User>
    fun create(user: User): User
    fun update(id: String, name: String?, surname: String?, email: String?, birthdate: String?): User
    fun delete(id: String): Boolean
}