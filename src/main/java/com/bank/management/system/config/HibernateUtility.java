package com.bank.management.system.config;

import com.bank.management.system.entity.Account;
import com.bank.management.system.entity.History;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Utility class responsible for configuring and providing access
 * to the Hibernate {@link SessionFactory}.
 *
 * <p>This class initializes a single {@code SessionFactory} instance
 * at application startup using the Hibernate configuration file
 * {@code account_bank.xml} along with the annotated entity classes
 * {@link Account} and {@link History}.</p>
 *
 * <h2>Design Pattern</h2>
 * <p>The {@code SessionFactory} is implemented using the
 * <b>Singleton Pattern</b> because:</p>
 * <ul>
 *     <li>Creation of a SessionFactory is expensive</li>
 *     <li>It is thread-safe and intended to be shared</li>
 *     <li>Only one instance should exist per database configuration</li>
 * </ul>
 *
 * <h2>Initialization</h2>
 * <p>The SessionFactory is created inside a static initializer block,
 * ensuring it is initialized once when the class is loaded.</p>
 *
 * <h2>Usage</h2>
 * <pre>
 * {@code
 * Session session = HibernateUtility
 *                      .getSessionFactory()
 *                      .openSession();
 * }
 * </pre>
 *
 * <p>This class cannot be instantiated since it only provides
 * static utility methods and configuration logic.</p>
 *
 * @author Tanishq Mathpal
 * @since 1.0
 */
public abstract class HibernateUtility {

    /**
     * Singleton {@link SessionFactory} instance used to create
     * Hibernate {@code Session} objects.
     */
    private static final SessionFactory sessionFactory;

    static {
        try {
            sessionFactory = new Configuration()
                    .addAnnotatedClasses(Account.class, History.class)
                    .configure("account_bank.xml")
                    .buildSessionFactory();
        } catch (Exception ex) {
            throw new ExceptionInInitializerError(
                    "Failed to initialize Hibernate SessionFactory: " + ex.getMessage()
            );
        }
    }

    /**
     * Returns the singleton {@link SessionFactory} instance.
     *
     * @return the shared SessionFactory
     */
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}