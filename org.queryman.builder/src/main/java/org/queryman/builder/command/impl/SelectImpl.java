/*
 *  Queryman. Java tools for working with queries of PostgreSQL database.
 *
 *  License: MIT License.
 *  To see license follow by http://queryman.org/license.txt
 */
package org.queryman.builder.command.impl;

import org.queryman.builder.AbstractQuery;
import org.queryman.builder.Select;
import org.queryman.builder.Statements;
import org.queryman.builder.Where;
import org.queryman.builder.ast.AbstractSyntaxTree;
import org.queryman.builder.command.select.SelectFinalStep;
import org.queryman.builder.command.select.SelectFromManySteps;
import org.queryman.builder.command.select.SelectFromStep;
import org.queryman.builder.command.select.SelectWhereStep;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.queryman.builder.ast.NodeMetadata.FROM;
import static org.queryman.builder.ast.NodeMetadata.SELECT;
import static org.queryman.builder.ast.NodeMetadata.WHERE;

/**
 * @author Timur Shaidullin
 */
public class SelectImpl extends AbstractQuery implements
    SelectFromStep,
    SelectFromManySteps,
    SelectWhereStep,
    SelectFinalStep,
    Select {

    private final String[] columnsSelected;
    private final List<String> from   = new LinkedList<>();
    private final List<Where>  wheres = new LinkedList<>();


    public SelectImpl(
       AbstractSyntaxTree ast,
       String... columnsSelected
    ) {
        super(ast);
        this.columnsSelected = columnsSelected;
    }

    @Override
    public void assemble(AbstractSyntaxTree tree) {
        tree.startNode(SELECT, ", ")
           .addLeaves(columnsSelected);

        if (!from.isEmpty()) {
            tree.startNode(FROM, ", ")
               .addLeaves(from)
               .endNode();
        }

        if (!wheres.isEmpty()) {
            tree.startNode(WHERE);

            for (Where where : wheres) {
                if (where.getToken() == null) {
                    tree.addLeaves(where.getLeftValue(), where.getOperator(), where.getRightValue());
                } else {
                    tree.startNode(where.getToken())
                        .addLeaves(where.getLeftValue(), where.getOperator(), where.getRightValue())
                        .endNode();
                }
            }

            tree.endNode();
        }

        tree.endNode();
    }

    //--
    // FROM API
    //--

    @Override
    public SelectImpl from(String... tables) {
        from.clear();
        from.addAll(List.of(tables));
        return this;
    }

    //--
    // WHERE API
    //--

    @Override
    public SelectImpl where(String left, String operator, String right) {
        wheres.clear();
        wheres.add(Statements.where(left, operator, right));
        return this;
    }

    @Override
    public SelectWhereStep where(Where... wheres) {
        this.wheres.clear();
        this.wheres.addAll(Arrays.asList(wheres));
        return this;
    }

    @Override
    public SelectImpl andWhere(String left, String operator, String right) {
        wheres.add(Statements.andWhere(left, operator, right));
        return this;
    }

    @Override
    public SelectImpl orWhere(String left, String operator, String right) {
        wheres.add(Statements.orWhere(left, operator, right));
        return this;
    }
}
