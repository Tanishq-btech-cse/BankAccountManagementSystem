package com.bank.management.system.generator;

import com.bank.management.system.annotation.TransectionId;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;
import java.util.Random;

/**
 * Custom Hibernate ID generator for generating positive transaction IDs.
 *
 * <p>This class implements {@link IdentifierGenerator} and is responsible
 * for automatically assigning a unique transaction ID to an entity
 * when it is persisted to the database.</p>
 *
 * <p>The generator produces random integer values and ensures that the
 * generated transaction ID is always positive.</p>
 *
 * <p><b>Generation Logic:</b>
 * <ul>
 *     <li>A random integer is generated</li>
 *     <li>If the number is negative, it is converted to positive</li>
 *     <li>If already positive, it is used directly</li>
 * </ul>
 * </p>
 *
 * <p><b>Note:</b> This approach is for demonstration purposes only.
 * In real-world banking systems, transaction IDs are generated using
 * more secure and structured techniques (e.g., UUIDs, timestamps,
 * or distributed ID generators).</p>
 *
 * Used with {@link TransectionId} annotation.
 *
 * @author Tanishq Mathpal
 * @version 1.1
 */
public class TransectionIdGenerator implements IdentifierGenerator {

    private final Random random = new Random();

    /**
     * Generates a unique positive transaction ID.
     *
     * @param session the Hibernate session
     * @param object  the entity for which the ID is being generated
     * @return a positive unique transaction ID
     */
    @Override
    public Serializable generate(SharedSessionContractImplementor session,
                                 Object object) {

        int id = random.nextInt();
        if (id < 0) {
            return -id;
        } else {
            return id;
        }
    }
}