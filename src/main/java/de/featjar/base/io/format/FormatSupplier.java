/*
 * Copyright (C) 2022 Sebastian Krieter, Elias Kuiter
 *
 * This file is part of util.
 *
 * util is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3.0 of the License,
 * or (at your option) any later version.
 *
 * util is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with util. If not, see <https://www.gnu.org/licenses/>.
 *
 * See <https://github.com/FeatureIDE/FeatJAR-util> for further information.
 */
package de.featjar.base.io.format;

import de.featjar.base.data.Result;
import de.featjar.base.io.InputHeader;

/**
 * Decides on a format for a given file content and file path.
 *
 * @author Sebastian Krieter
 */
@FunctionalInterface
public interface FormatSupplier<T> {
    /**
     * {@return a Constant format}
     *
     * @param format the format
     * @param <T> the type of the parsed object
     */
    static <T> FormatSupplier<T> of(Format<T> format) {
        return inputHeader -> Result.of(format);
    }

    /**
     * {@return a format fitting the given input header}
     *
     * @param inputHeader the input header
     */
    Result<Format<T>> getFormat(InputHeader inputHeader);
}