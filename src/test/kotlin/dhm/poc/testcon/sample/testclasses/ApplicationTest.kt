package dhm.poc.testcon.sample.testclasses

import com.google.gson.Gson
import org.junit.Before
import org.junit.jupiter.api.BeforeEach
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.http.MediaType
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import org.testcontainers.containers.PostgreSQLContainer

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ContextConfiguration(initializers = [ApplicationTest.Initializer::class])
abstract class ApplicationTest {
    
    @Autowired
    protected lateinit var context: WebApplicationContext
    
    lateinit var mockMvcBuilder: MockMvc
    
    val gson = Gson()
    
    @BeforeEach
    fun setUp() {
        this.mockMvcBuilder = MockMvcBuilders
            .webAppContextSetup(this.context)
            .build()
    }
    
    companion object {
        lateinit var postgreSQLContainer: PostgreSQLContainer<*>
    }
    
    class Initializer : ApplicationContextInitializer<ConfigurableApplicationContext> {
        init {
            postgreSQLContainer = PostgreSQLContainer<Nothing>()
                .withExposedPorts(6379)
        }
        override fun initialize(configurableApplicationContext: ConfigurableApplicationContext) {
            postgreSQLContainer.start()
            TestPropertyValues.of(
                "spring.datasource.url=" + postgreSQLContainer.getJdbcUrl(),
                "spring.datasource.username=" + postgreSQLContainer.getUsername(),
                "spring.datasource.password=" + postgreSQLContainer.getPassword()
            ).applyTo(configurableApplicationContext.environment)
        }
    }
    
    protected fun <T> sendPost(uri: String, content: T): ResultActions {
        return mockMvcBuilder.perform(
            MockMvcRequestBuilders
                .post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(content))
        )
    }
    
    protected fun <T> sendPut(uri: String, content: T): ResultActions {
        return mockMvcBuilder.perform(
            MockMvcRequestBuilders
                .put(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(content))
        )
    }
    
    protected fun <T> sendGet(uri: String): ResultActions {
        return mockMvcBuilder.perform(
            MockMvcRequestBuilders
                .get(uri)
                .contentType(MediaType.APPLICATION_JSON)
        )
    }
    
}