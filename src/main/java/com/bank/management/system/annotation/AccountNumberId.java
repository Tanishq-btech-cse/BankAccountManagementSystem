package com.bank.management.system.annotation;

import com.bank.management.system.generator.AccountNumberGenerator;
import com.bank.management.system.entity.Account;
import org.hibernate.annotations.IdGeneratorType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Custom identifier annotation for generating unique account numbers
 * in the {@link Account} entity.
 *
 * <p>This annotation integrates with Hibernate's identifier generation
 * mechanism using {@link IdGeneratorType} and delegates the ID generation
 * process to {@link AccountNumberGenerator}.</p>
 *
 * <p>When applied to a field, Hibernate automatically invokes the
 * custom generator to produce a unique account number during
 * entity persistence.</p>
 *
 * <h2>Purpose</h2>
 * <ul>
 *     <li>Replaces default Hibernate ID generation strategies</li>
 *     <li>Provides domain-specific account number generation</li>
 *     <li>Ensures uniqueness and consistency across accounts</li>
 * </ul>
 *
 * <h2>Meta-Annotations</h2>
 * <ul>
 *     <li><b>{@link Target}</b>: {@link ElementType#FIELD} —
 *     Can only be applied to fields.</li>
 *
 *     <li><b>{@link Retention}</b>: {@link RetentionPolicy#RUNTIME} —
 *     Available at runtime for Hibernate processing.</li>
 *
 *     <li><b>{@link IdGeneratorType}</b> —
 *     Links this annotation to {@link AccountNumberGenerator}.</li>
 * </ul>
 *
 * <h2>Example Usage</h2>
 * <pre>
 * {@code
 * @Id
 * @AccountNumberId
 * private Long accountNumber;
 * }
 * </pre>
 *
 * @author Tanishq Mathpal
 * @since 1.0
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@IdGeneratorType(AccountNumberGenerator.class)
public @interface AccountNumberId {
}