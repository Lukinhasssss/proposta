package br.com.lukinhasssss.proposta.validations

import org.hibernate.validator.constraints.CompositionType
import org.hibernate.validator.constraints.ConstraintComposition
import org.hibernate.validator.constraints.br.CNPJ
import org.hibernate.validator.constraints.br.CPF
import javax.validation.Constraint
import javax.validation.Payload
import kotlin.annotation.AnnotationRetention.RUNTIME
import kotlin.annotation.AnnotationTarget.FIELD
import kotlin.reflect.KClass

@Target(FIELD)
@Retention(RUNTIME)
@ConstraintComposition(CompositionType.OR)
@CPF @CNPJ
@Constraint(validatedBy = [])
annotation class ValidDocument(
    val message: String = "Must be a valid CPF/CNPJ",
    val groups: Array<KClass<Any>> = [],
    val payload: Array<KClass<Payload>> = []
)