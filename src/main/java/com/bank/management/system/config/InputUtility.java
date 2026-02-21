package com.bank.management.system.config;

import java.util.Scanner;

/**
 * Utility class responsible for managing console input operations.
 *
 * <p>This class provides a single shared {@link Scanner} instance
 * for reading input from {@link System#in} across the entire application.</p>
 *
 * <h2>Why a Shared Scanner?</h2>
 * <ul>
 *     <li>Prevents multiple {@code Scanner} instances on {@code System.in}</li>
 *     <li>Avoids resource leaks and input stream conflicts</li>
 *     <li>Ensures consistent input handling throughout the system</li>
 * </ul>
 *
 * <h2>Design Consideration</h2>
 * <p>The class is declared {@code abstract} to prevent instantiation,
 * as it serves purely as a static utility holder.</p>
 *
 * <h2>Usage Example</h2>
 * <pre>
 * {@code
 * String name = InputUtility.SC.nextLine();
 * int age = InputUtility.SC.nextInt();
 * }
 * </pre>
 *
 * <p><b>Note:</b> The Scanner should not be closed manually, as it
 * would also close {@code System.in}.</p>
 *
 * @author Tanishq Mathpal
 * @since 1.0
 */
public abstract class InputUtility {

    /**
     * Shared {@link Scanner} instance for reading console input.
     */
    public static final Scanner SC = new Scanner(System.in);
}