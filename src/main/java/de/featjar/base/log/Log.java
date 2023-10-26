/*
 * Copyright (C) 2023 FeatJAR-Development-Team
 *
 * This file is part of FeatJAR-base.
 *
 * base is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3.0 of the License,
 * or (at your option) any later version.
 *
 * base is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with base. If not, see <https://www.gnu.org/licenses/>.
 *
 * See <https://github.com/FeatureIDE/FeatJAR-base> for further information.
 */
package de.featjar.base.log;

import de.featjar.base.data.Problem;
import de.featjar.base.data.Result;
import java.util.Arrays;
import java.util.List;

/**
 * Logs messages to standard output and files. Formats log messages with
 * {@link IFormatter formatters}. TODO: instead of logging values directly, only
 * pass suppliers that are called if some log target is configured. this saves
 * time for creating log strings
 *
 * @author Sebastian Krieter
 * @author Elias Kuiter
 */
public interface Log {
    /**
     * Logging verbosity. Each verbosity (save for {@link Verbosity#NONE}) defines a
     * type of message that can be logged. In addition, defines a log level that
     * includes all log messages of the message type and all types above.
     */
    public enum Verbosity {
        /**
         * Indicates that no messages should be logged.
         */
        NONE,
        /**
         * Regular message. For explicit console output.
         */
        MESSAGE,
        /**
         * Error message. Typically used to log critical exceptions and errors.
         */
        ERROR,
        /**
         * Warning message. Typically used to log non-critical warnings.
         */
        WARNING,
        /**
         * Info message. Typically used to log high-level information.
         */
        INFO,
        /**
         * Debug message. Typically used to log low-level information.
         */
        DEBUG,
        /**
         * Progress message. Typically used to signal progress in long-running jobs.
         */
        PROGRESS,
        /**
         * Indicates that all messages should be logged.
         */
        ALL;

        public static boolean isValid(String verbosityString) {
            String[] verbosities = new String[] {"none", "error", "warning", "info", "debug", "progress"};
            return Arrays.asList(verbosities).contains(verbosityString);
        }

        public static Result<Verbosity> of(String verbosityString) {
            if (!isValid(verbosityString)) return Result.empty();
            return Result.of(Verbosity.valueOf(verbosityString.toUpperCase()));
        }
    }

    /**
     * Logs a problem.
     *
     * @param problem the problem
     */
    default void problem(Problem problem) {
        switch (problem.getSeverity()) {
            case ERROR:
                error(problem.getException());
                break;
            case WARNING:
                warning(problem.getException());
                break;
        }
    }

    /**
     * Logs problems.
     *
     * @param problems the problems
     */
    default void problems(List<Problem> problems) {
        for (Problem problem : problems) {
            problem(problem);
        }
    }

    /**
     * Logs an error message.
     *
     * @param message the error message
     */
    default void error(String message) {
        println(message, Verbosity.ERROR);
    }

    /**
     * Logs a {@link String#format(String, Object...) formatted} error message.
     *
     * @param formatMessage the message with format specifiers
     * @param elements      the arguments for the format specifiers in the format
     *                      message
     */
    default void error(String formatMessage, Object... elements) {
        error(String.format(formatMessage, elements));
    }

    /**
     * Logs an error message.
     *
     * @param error the error object
     */
    default void error(Throwable error) {
        println(error, false);
    }

    /**
     * Logs a warning message.
     *
     * @param message the warning message
     */
    default void warning(String message) {
        println(message, Verbosity.WARNING);
    }

    /**
     * Logs a {@link String#format(String, Object...) formatted} warning message.
     *
     * @param formatMessage the message with format specifiers
     * @param elements      the arguments for the format specifiers in the format
     *                      message
     */
    default void warning(String formatMessage, Object... elements) {
        warning(String.format(formatMessage, elements));
    }

    /**
     * Logs a warning message.
     *
     * @param warning the warning object
     */
    default void warning(Throwable warning) {
        println(warning, true);
    }

    /**
     * Logs an info message.
     *
     * @param message the message
     */
    default void info(String message) {
        println(message, Verbosity.INFO);
    }

    /**
     * Logs a {@link String#format(String, Object...) formatted} info message.
     *
     * @param formatMessage the message with format specifiers
     * @param elements      the arguments for the format specifiers in the format
     *                      message
     */
    default void info(String formatMessage, Object... elements) {
        //        info(String.format(formatMessage, elements));
    }

    /**
     * Logs an info message.
     *
     * @param messageObject the message object
     */
    default void info(Object messageObject) {
        //        info(String.valueOf(messageObject));
    }

    /**
     * Logs a regular message.
     *
     * @param message the message
     */
    default void message(String message) {
        println(message, Verbosity.MESSAGE);
    }

    /**
     * Logs a {@link String#format(String, Object...) formatted} regular message.
     *
     * @param formatMessage the message with format specifiers
     * @param elements      the arguments for the format specifiers in the format
     *                      message
     */
    default void message(String formatMessage, Object... elements) {
        message(String.format(formatMessage, elements));
    }

    /**
     * Logs a regular message.
     *
     * @param messageObject the message object
     */
    default void message(Object messageObject) {
        message(String.valueOf(messageObject));
    }

    /**
     * Logs a debug message.
     *
     * @param message the message
     */
    default void debug(String message) {
        println(message, Verbosity.DEBUG);
    }

    /**
     * Logs a {@link String#format(String, Object...) formatted} debug message.
     *
     * @param formatMessage the message with format specifiers
     * @param elements      the arguments for the format specifiers in the format
     *                      message
     */
    default void debug(String formatMessage, Object... elements) {
        //        debug(String.format(formatMessage, elements));
    }

    /**
     * Logs a debug message.
     *
     * @param messageObject the message object
     */
    default void debug(Object messageObject) {
        //        debug(String.valueOf(messageObject));
    }

    /**
     * Logs a progress message.
     *
     * @param message the message
     */
    default void progress(String message) {
        println(message, Verbosity.PROGRESS);
    }

    /**
     * Logs a {@link String#format(String, Object...) formatted} progress message.
     *
     * @param formatMessage the message with format specifiers
     * @param elements      the arguments for the format specifiers in the format
     *                      message
     */
    default void progress(String formatMessage, Object... elements) {
        progress(String.format(formatMessage, elements));
    }

    /**
     * Logs a progress message.
     *
     * @param messageObject the message object
     */
    default void progress(Object messageObject) {
        progress(String.valueOf(messageObject));
    }

    /**
     * Logs a message.
     *
     * @param message   the message
     * @param verbosity the verbosities
     */
    default void log(String message, Verbosity verbosity) {
        println(message, verbosity);
    }

    void println(String message, Verbosity verbosity);

    void println(Throwable error, boolean isWarning);
}
