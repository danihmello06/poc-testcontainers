package dhm.poc.testcon.sample.repository

import dhm.poc.testcon.sample.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface UserRepository : JpaRepository<User, UUID> {
    
    fun findFirstById(userId: UUID): User?
    
}
