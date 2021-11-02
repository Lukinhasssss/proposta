package br.com.lukinhasssss.proposta.controllers

import br.com.lukinhasssss.proposta.builder.saveProposalBuilder
import br.com.lukinhasssss.proposta.dto.request.NewProposalRequest
import br.com.lukinhasssss.proposta.repositories.ProposalRepository
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import org.hamcrest.Matchers.*
import org.hamcrest.core.IsEqual
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.test.context.ActiveProfiles
import java.math.BigDecimal

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
class NewProposalControllerTest {

    @LocalServerPort
    private var port = 0

    @Autowired
    lateinit var repository: ProposalRepository

    @BeforeEach
    internal fun setUp() { repository.deleteAll() }

    @Test
    internal fun `should return 201 and save proposal on database when request is valid`() {
        // Arrange
        val request = newProposalRequest

        // Act - Assert
        Given {
            port(port)
            contentType("application/json")
            body(request)
        } When {
            post("/v1/proposals")
        } Then {
            statusCode(201)
            header("Location", matchesPattern("http://localhost:$port/v1/proposals/([a-f0-9]{8}(-[a-f0-9]{4}){4}[a-f0-9]{8})"))
        }

        assertTrue(repository.findAll().size == 1)
    }
    
    @Test
    internal fun `should return 400 with an error message when name is not provided`() {
        // Arrange
        val request = newProposalRequest.copy(name = "")
    
        // Act - Assert
        Given {
            port(port)
            contentType("application/json")
            body(request)
        } When {
            post("/v1/proposals")
        } Then {
            statusCode(400)
            body("timestamp", notNullValue())
            body("status", IsEqual(400))
            body("path", IsEqual("/proposals"))
            body("messages.size()", IsEqual(1))
            body("messages.get(0).field", IsEqual("name"))
            body("messages.get(0).message", IsEqual("Required field"))
        }

        assertTrue(repository.findAll().size == 0)
    }

    @Test
    internal fun `should return 400 with an error message when name has more than 50 characteres`() {
        // Arrange
        val request = newProposalRequest.copy(name = "a".repeat(51))

        // Act - Assert
        Given {
            port(port)
            contentType("application/json")
            body(request)
        } When {
            post("/v1/proposals")
        } Then {
            statusCode(400)
            body("timestamp", notNullValue())
            body("status", IsEqual(400))
            body("path", IsEqual("/proposals"))
            body("messages.size()", IsEqual(1))
            body("messages.get(0).field", IsEqual("name"))
            body("messages.get(0).message", IsEqual("Must be no longer than 50 characters"))
        }

        assertTrue(repository.findAll().size == 0)
    }

    @Test
    internal fun `should return 400 with an error message when email is not provided`() {
        // Arrange
        val request = newProposalRequest.copy(email = "")

        // Act - Assert
        Given {
            port(port)
            contentType("application/json")
            body(request)
        } When {
            post("/v1/proposals")
        } Then {
            statusCode(400)
            body("timestamp", notNullValue())
            body("status", IsEqual(400))
            body("path", IsEqual("/proposals"))
            body("messages.size()", IsEqual(1))
            body("messages.get(0).field", IsEqual("email"))
            body("messages.get(0).message", IsEqual("Required field"))
        }

        assertTrue(repository.findAll().size == 0)
    }

    @Test
    internal fun `should return 400 with an error message when email is invalid`() {
        // Arrange
        val request = newProposalRequest.copy(email = "srgarnetn")

        // Act - Assert
        Given {
            port(port)
            contentType("application/json")
            body(request)
        } When {
            post("/v1/proposals")
        } Then {
            statusCode(400)
            body("timestamp", notNullValue())
            body("status", IsEqual(400))
            body("path", IsEqual("/proposals"))
            body("messages.size()", IsEqual(1))
            body("messages.get(0).field", IsEqual("email"))
            body("messages.get(0).message", IsEqual("Must be a valid email"))
        }

        assertTrue(repository.findAll().size == 0)
    }

    @Test
    internal fun `should return 400 with an error message when email has more than 50 characteres`() {
        // Arrange
        val request = newProposalRequest.copy(email = "a".repeat(51).plus("@gmail.com"))

        // Act - Assert
        Given {
            port(port)
            contentType("application/json")
            body(request)
        } When {
            post("/v1/proposals")
        } Then {
            statusCode(400)
            body("timestamp", notNullValue())
            body("status", IsEqual(400))
            body("path", IsEqual("/proposals"))
            body("messages.size()", IsEqual(1))
            body("messages.get(0).field", IsEqual("email"))
            body("messages.get(0).message", IsEqual("Must be no longer than 50 characters"))
        }

        assertTrue(repository.findAll().size == 0)
    }

    @Test
    internal fun `should return 400 with an error message when document (CPF) is invalid`() {
        // Arrange
        val request = newProposalRequest.copy(document = "12345678912")

        // Act - Assert
        Given {
            port(port)
            contentType("application/json")
            body(request)
        } When {
            post("/v1/proposals")
        } Then {
            statusCode(400)
            body("timestamp", notNullValue())
            body("status", IsEqual(400))
            body("path", IsEqual("/proposals"))
            body("messages.size()", IsEqual(1))
            body("messages.get(0).field", IsEqual("document"))
            body("messages.get(0).message", IsEqual("Must be a valid CPF/CNPJ"))
        }

        assertTrue(repository.findAll().size == 0)
    }

    @Test
    internal fun `should return 400 with an error message when document (CNPJ) is invalid`() {
        // Arrange
        val request = newProposalRequest.copy(document = "12345678000103")

        // Act - Assert
        Given {
            port(port)
            contentType("application/json")
            body(request)
        } When {
            post("/v1/proposals")
        } Then {
            statusCode(400)
            body("timestamp", notNullValue())
            body("status", IsEqual(400))
            body("path", IsEqual("/proposals"))
            body("messages.size()", IsEqual(1))
            body("messages.get(0).field", IsEqual("document"))
            body("messages.get(0).message", IsEqual("Must be a valid CPF/CNPJ"))
        }

        assertTrue(repository.findAll().size == 0)
    }

    @Test
    internal fun `should return 400 with an error message when document is valid but has special characters`() {
        // Arrange
        val request = newProposalRequest.copy(document = "515.825.820-17")

        // Act - Assert
        Given {
            port(port)
            contentType("application/json")
            body(request)
        } When {
            post("/v1/proposals")
        } Then {
            statusCode(400)
            body("timestamp", notNullValue())
            body("status", IsEqual(400))
            body("path", IsEqual("/proposals"))
            body("messages.size()", IsEqual(1))
            body("messages.get(0).field", IsEqual("document"))
            body("messages.get(0).message", IsEqual("Document must have only numbers"))
        }

        assertTrue(repository.findAll().size == 0)
    }

    @Test
    internal fun `should return 422 with an error message when already exists a proposal with provided document`() {
        // Arrange
        repository.save(saveProposalBuilder())
        val request = newProposalRequest.copy(email = "luffy@gmail.com")

        // Act - Assert
        Given {
            port(port)
            contentType("application/json")
            body(request)
        } When {
            post("/v1/proposals")
            post("/v1/proposals")
        } Then {
            statusCode(422)
            body("timestamp", notNullValue())
            body("status", IsEqual(422))
            body("path", IsEqual("/proposals"))
            body("message", hasEntry("field", "document"))
            body("message", hasEntry("message", "There is already a proposal with this document"))
        }

        assertTrue(repository.findAll().size == 1)
    }

    @Test
    internal fun `should return 400 with an error message when salary is negative or zero`() {
        // Arrange
        val request = newProposalRequest.copy(salary = BigDecimal("-400000.00"))

        // Act - Assert
        Given {
            port(port)
            contentType("application/json")
            body(request)
        } When {
            post("/v1/proposals")
        } Then {
            statusCode(400)
            body("timestamp", notNullValue())
            body("status", IsEqual(400))
            body("path", IsEqual("/proposals"))
            body("messages.size()", IsEqual(1))
            body("messages.get(0).field", IsEqual("salary"))
            body("messages.get(0).message", IsEqual("Salary cannot be zero or negative"))
        }

        assertTrue(repository.findAll().size == 0)
    }

    @Test
    internal fun `should return 400 with an error message when address is not provided`() {
        // Arrange
        val request = newProposalRequest.copy(address = "")

        // Act - Assert
        Given {
            port(port)
            contentType("application/json")
            body(request)
        } When {
            post("/v1/proposals")
        } Then {
            statusCode(400)
            body("timestamp", notNullValue())
            body("status", IsEqual(400))
            body("path", IsEqual("/proposals"))
            body("messages.size()", IsEqual(1))
            body("messages.get(0).field", IsEqual("address"))
            body("messages.get(0).message", IsEqual("Required field"))
        }

        assertTrue(repository.findAll().size == 0)
    }

    @Test
    internal fun `should return 400 with an error message when address has more than 150 characteres`() {
        // Arrange
        val request = newProposalRequest.copy(address = "a".repeat(151))

        // Act - Assert
        Given {
            port(port)
            contentType("application/json")
            body(request)
        } When {
            post("/v1/proposals")
        } Then {
            statusCode(400)
            body("timestamp", notNullValue())
            body("status", IsEqual(400))
            body("path", IsEqual("/proposals"))
            body("messages.size()", IsEqual(1))
            body("messages.get(0).field", IsEqual("address"))
            body("messages.get(0).message", IsEqual("Must be no longer than 150 characters"))
        }

        assertTrue(repository.findAll().size == 0)
    }

    private val newProposalRequest = NewProposalRequest(
        name = "Monkey D. Luffy",
        email = "mugiwara@gmail.com",
        document = "73854034091",
        salary = BigDecimal("400000.00"),
        address = "Dressrosa"
    )

}