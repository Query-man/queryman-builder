/*
 *  Queryman. Java tools for working with queries of PostgreSQL database.
 *
 *  License: MIT License
 *  To see license follow by http://queryman.org/license.txt
 */
package org.queryman.builder.token;

import org.queryman.builder.ast.AbstractSyntaxTree;

/**
 * @author Timur Shaidullin
 */
public class Keyword extends AbstractToken {

    public Keyword(String name) {
        super(name);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void assemble(AbstractSyntaxTree tree) {

    }
}
