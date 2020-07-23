package dhm.poc.testcon.sample.model

import org.hibernate.annotations.Type
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table
data class User (
    
    @Id
    @Type(type="uuid-char")
    val id: UUID = UUID.randomUUID(),
    @Column
    val email: String,
    @Column
    val password: String,
    @Column
    val name: String

)