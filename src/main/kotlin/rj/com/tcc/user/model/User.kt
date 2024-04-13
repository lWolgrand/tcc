package rj.com.tcc.user.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "users")
data class User(
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    val id: String?,
    val name: String?,
    val surname: String?,
    val email: String?,
    val birthdate: String?,
    val created_at: LocalDateTime = LocalDateTime.now(),
    val updated_at: LocalDateTime = LocalDateTime.now()
)