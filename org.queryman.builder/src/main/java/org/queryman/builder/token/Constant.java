/*
 *  Queryman. Java tools for working with queries of PostgreSQL database.
 *
 *  License: MIT License
 *  To see license follow by http://queryman.org/license.txt
 */
package org.queryman.builder.token;

/**
 * @author Timur Shaidullin
 */
public class Constant extends AbstractToken {
    public Constant(String name) {
        super(name);
    }

    @Override
    public String getName() {
        return "'" + name + "'";
    }
}