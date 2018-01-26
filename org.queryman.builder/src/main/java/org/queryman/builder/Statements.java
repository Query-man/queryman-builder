/*
 *  Queryman. Java tools for working with queries of PostgreSQL database.
 *
 *  License: MIT License.
 *  To see license follow by http://queryman.org/license.txt
 */
package org.queryman.builder;

import org.queryman.builder.command.impl.WhereImpl;

import static org.queryman.builder.command.impl.WhereImpl.AND;
import static org.queryman.builder.command.impl.WhereImpl.OR;

/**
 * @author Timur Shaidullin
 */
public class Statements {
    public static Where where(String leftValue, String operator, String rightValue) {
        return new WhereImpl(leftValue, operator, rightValue);
    }

    public static Where andWhere(String leftValue, String operator, String rightValue) {
        return new WhereImpl(AND, leftValue, operator, rightValue);
    }

    public static Where orWhere(String leftValue, String operator, String rightValue) {
        return new WhereImpl(OR, leftValue, operator, rightValue);
    }
}