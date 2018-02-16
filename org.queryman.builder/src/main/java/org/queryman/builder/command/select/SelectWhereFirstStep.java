/*
 *  Queryman. Java tools for working with queries of PostgreSQL database.
 *
 *  License: MIT License.
 *  To see license follow by http://queryman.org/license.txt
 */
package org.queryman.builder.command.select;

import org.queryman.builder.Query;
import org.queryman.builder.command.Conditions;
import org.queryman.builder.token.Expression;
import org.queryman.builder.token.Operator;

/**
 * @author Timur Shaidullin
 */
public interface SelectWhereFirstStep extends SelectGroupByStep {
    SelectWhereStep where(String left, String operator, String right);

    SelectWhereStep where(Expression left, Operator operator, Expression right);

    SelectWhereStep where(Expression field, Operator operator, Query query);

    SelectWhereStep where(Conditions conditions);

    SelectWhereStep whereExists(Query query);

    SelectWhereStep whereBetween(String field, String value1, String value2);

    SelectWhereStep whereBetween(Expression field, Expression value1, Expression value2);
}