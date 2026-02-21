package com.bank.management.system.annotation;

import com.bank.management.system.generator.TransectionIdGenerator;
import org.hibernate.annotations.IdGeneratorType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Custom identifier annotation for generating unique transaction IDs.
 *
 * <p>This annotation integrates with Hibernate’s identifier generation
 * system using {@link IdGeneratorType} and delegates ID creation
 * to {@link TransectionIdGenerator}.</p>
 *
 * <p>When applied to a field, Hibernate automatically invokes the
 * custom generator during entity persistence to produce a
 * unique transaction identifier.</p>
 *
 * <h2>Purpose</h2>
 * <ul>
 *     <li>Provides domain-specific transaction ID generation</li>
 *     <li>Replaces default Hibernate identifier strategies</li>
 *     <li>Ensures uniqueness for transaction-related entities</li>
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
 *     Associates this annotation with {@link TransectionIdGenerator}.</li>
 * </ul>
 *
 * <h2>Example Usage</h2>
 * <pre>
 * {@code
 * @Id
 * @TransectionId
 * private Long transactionId;
 * }
 * </pre>
 *
 * @author Tanishq Mathpal
 * @since 1.0
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@IdGeneratorType(TransectionIdGenerator.class)
public @interface TransectionId {
}