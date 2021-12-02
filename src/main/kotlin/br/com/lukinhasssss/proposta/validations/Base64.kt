package br.com.lukinhasssss.proposta.validations

import java.util.Base64.getDecoder
import javax.validation.Constraint
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext
import javax.validation.Payload
import kotlin.annotation.AnnotationRetention.RUNTIME
import kotlin.annotation.AnnotationTarget.FIELD
import kotlin.reflect.KClass

@MustBeDocumented
@Target(FIELD)
@Retention(RUNTIME)
@Constraint(validatedBy = [Base64Validator::class])
annotation class Base64(

    val domainClass: String,
    val fieldName: String,
    val message: String = "There is already a {domainClass} with this {fieldName}",
    val groups: Array<KClass<Any>> = [],
    val payload: Array<KClass<Payload>> = []

)

class Base64Validator : ConstraintValidator<Base64, Any> {

    private lateinit var domainClass: String
    private lateinit var fieldName: String

    override fun initialize(constraintAnnotation: Base64) {
        domainClass = constraintAnnotation.domainClass
        fieldName = constraintAnnotation.fieldName
    }

    override fun isValid(value: Any?, context: ConstraintValidatorContext?): Boolean {

        val base64Decoder = getDecoder()

        return try {
            base64Decoder.decode(value as String)
            true
        } catch (exception: Exception) {
            false
        }
    }

}