package com.bank.management.system.generator;

import com.bank.management.system.annotation.AccountNumberId;
import com.bank.management.system.entity.Account;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;
import java.util.Random;

/**
 * Custom Hibernate identifier generator responsible for
 * generating structured account numbers for the
 * Bank Management System.
 *
 * <p>This class implements {@link IdentifierGenerator} and is
 * triggered automatically when persisting an {@link Account}
 * entity annotated with {@link AccountNumberId}.</p>
 *
 * <h2>Generation Strategy</h2>
 * <ul>
 *     <li>Uses a fixed bank code prefix</li>
 *     <li>Appends an 8-digit random numeric value</li>
 *     <li>Returns the final result as a {@link Long}</li>
 * </ul>
 *
 * <p>The generated account number follows the structure:</p>
 *
 * <pre>
 * [BankCode][8-digit-random-number]
 * Example: 870512345678
 * </pre>
 *
 * <h2>Design Notes</h2>
 * <ul>
 *     <li>Ensures positive numeric values</li>
 *     <li>Provides simple structured formatting</li>
 *     <li>Suitable for demonstration and academic purposes</li>
 * </ul>
 *
 * <p><b>Important:</b> In production banking systems, account numbers
 * are typically generated using deterministic algorithms that may
 * include branch codes, validation checks (e.g., checksum),
 * and collision prevention strategies.</p>
 *
 * @author Tanishq Mathpal
 * @since 1.1
 */
public class AccountNumberGenerator implements IdentifierGenerator {

    /**
     * Generates a unique positive account number.
     *
     * @param session the Hibernate session context
     * @param object  the entity for which the ID is being generated
     * @return a positive account number as {@link Serializable}
     */
    @Override
    public Serializable generate(
            SharedSessionContractImplementor session,
            Object object) {

        String bankCode = "8705"; // your bank code
        int randomPart = 10000000 + new Random().nextInt(90000000);
        String accountNumber = bankCode + randomPart;
        return Long.parseLong(accountNumber);
    }
}