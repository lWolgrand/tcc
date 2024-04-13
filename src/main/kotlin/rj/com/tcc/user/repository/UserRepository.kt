package rj.com.tcc.user.repository

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import rj.com.tcc.user.model.User
import java.time.LocalDateTime
import java.util.logging.Logger

@Repository
class UserRepository(private val db: JdbcTemplate): IUserRepository {

    override fun get(): List<User> {
        val sql = "SELECT * FROM users"
        Logger.getLogger(UserRepository::class.java.name).info("Repository - Executando SQL: $sql")

        try {
            val userList = db.query(sql) { response, _ ->
                User(
                    response.getString("id"),
                    response.getString("name"),
                    response.getString("surname"),
                    response.getString("email"),
                    response.getString("birthdate"),
                    response.getTimestamp("created_at").toLocalDateTime(),
                    response.getTimestamp("updated_at").toLocalDateTime()
                )
            }
            return userList
        } catch (e: Exception) {
            Logger.getLogger(UserRepository::class.java.name).warning("Falha ao obter dados: ${e.message}")
            throw e
        }
    }

    override fun create(user: User): User {

        val sql = "INSERT INTO users (id, name, surname, email, birthdate, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, ?)"
        Logger.getLogger(UserRepository::class.java.name).info("Repository/CREATE - Executando SQL: $sql")


        try {
            db.update(sql,
                user.id,
                user.name,
                user.surname,
                user.email,
                user.birthdate,
                user.created_at,
                user.updated_at
            )
        } catch (e: Exception) {
            Logger.getLogger(UserRepository::class.java.name).warning("Falha na criação de usuário: ${e.message}")
            throw e
        }

        return user
    }

    override fun update(id: String, name: String?, surname: String?, email: String?, birthdate: String?): User {
        val sql = "UPDATE users SET name = COALESCE(?, name), surname = COALESCE(?, surname), email = COALESCE(?, email), birthdate = COALESCE(?, birthdate), updated_at = ? WHERE id = ?"
        val user = "SELECT * FROM users WHERE id = ?"
        Logger.getLogger(UserRepository::class.java.name).info("Repository/UPDATE - Executando SQL: $sql")


        try {

            db.update(sql,
                name,
                surname,
                email,
                birthdate,
                LocalDateTime.now(),
                id
            )

            val updatedUser = db.queryForObject(user, arrayOf(id)) { rs, _ ->
                User(
                    id = rs.getString("id"),
                    name = rs.getString("name"),
                    surname = rs.getString("surname"),
                    email = rs.getString("email"),
                    birthdate = rs.getString("birthdate"),
                    created_at = rs.getTimestamp("created_at").toLocalDateTime(),
                    updated_at = rs.getTimestamp("updated_at").toLocalDateTime()
                )
            }

            return updatedUser ?: throw Exception("Usuário não encontrado.")
        } catch (e: Exception) {
            Logger.getLogger(UserRepository::class.java.name).warning("Falha ao atualizar ${e.message}")
            throw e
        }
    }

    override fun delete(id: String): Boolean {
        val sql = "DELETE FROM users WHERE id = ?"
        Logger.getLogger(UserRepository::class.java.name).info("Repository/DELETE - Executando SQL: $sql")

        return try {
            val rowsAffected = db.update(sql, id)
            if (rowsAffected == 0) {
    //                Logger.getLogger(UserRepository::class.java.name).warning("Usuário não encontrado")
                false
            } else {
    //                Logger.getLogger(UserRepository::class.java.name).info("Usuário deletado com sucesso")
                true
            }
        } catch (e: Exception) {
    //            Logger.getLogger(UserRepository::class.java.name).warning("Error $id: ${e.message}")
            throw e
        }
    }


}