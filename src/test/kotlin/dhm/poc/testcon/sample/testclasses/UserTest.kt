package dhm.poc.testcon.sample.testclasses

import dhm.poc.testcon.sample.model.User
import dhm.poc.testcon.sample.repository.UserRepository
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.context.jdbc.SqlGroup
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.util.UUID

@SpringBootTest
class UserTest : ApplicationTest() {
	
	@Autowired
	private lateinit var userRepository: UserRepository
	
	@Test
	@SqlGroup(
		value = [
			Sql("/insert_data.sql"),
			Sql("/delete_data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
		]
	)
	fun `should save user successfully`() {
		val userId = UUID.randomUUID()
		val user = User(
			id = userId,
			email = "email@gmail.com",
			password = "12345",
			name = "Joao da silva"
		)
		
		this.sendPut("/v1/user/create/", user)
			.andExpect(MockMvcResultMatchers.status().isOk)
			.andReturn()
		
	}
	
	@Test
	fun `should get userlist`() {
		this.sendGet<List<User>>("/v1/user")
			.andExpect(MockMvcResultMatchers.status().isOk)
			.andReturn()
			
	}

}
