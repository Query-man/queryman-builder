/*
 *  Queryman. Java tools for working with queries of PostgreSQL database.
 *
 *  License: MIT License
 *  To see license follow by http://queryman.org/license.txt
 */
package org.queryman.builder.command.impl;

import org.queryman.builder.Query;
import org.queryman.builder.ast.AbstractSyntaxTree;
import org.queryman.builder.ast.Node;
import org.queryman.builder.ast.NodeImpl;
import org.queryman.builder.ast.NodeMetadata;
import org.queryman.builder.command.Conditions;
import org.queryman.builder.token.Expression;
import org.queryman.builder.token.Operator;

import static org.queryman.builder.PostgreSQL.asName;
import static org.queryman.builder.PostgreSQL.condition;
import static org.queryman.builder.PostgreSQL.conditionExists;
import static org.queryman.builder.PostgreSQL.getTree;
import static org.queryman.builder.PostgreSQL.operator;
import static org.queryman.builder.ast.NodesMetadata.AND;
import static org.queryman.builder.ast.NodesMetadata.AND_NOT;
import static org.queryman.builder.ast.NodesMetadata.OR;
import static org.queryman.builder.ast.NodesMetadata.OR_NOT;

/**
 * @author Timur Shaidullin
 */
public final class ConditionsImpl implements
   Conditions {

    private Node node;

    public ConditionsImpl(Expression leftValue, NodeMetadata metadata, Expression rightValue) {
        node = new NodeImpl(metadata)
           .addLeaf(leftValue)
           .addLeaf(rightValue);
    }

    /**
     * Ordinarily this constructor is used by <code>BETWEEN .. AND ..</code>
     * clause.
     * <p>
     * Where:
     *
     * @param metadata   - {@code BETWEEN} node metadata.
     * @param field      - field of between. Example: <code>id BETWEEN ..</code>
     * @param conditions - condition of between. Example: <code>id BETWEEN 1 AND 20</code>
     */
    public ConditionsImpl(NodeMetadata metadata, Expression field, Conditions conditions) {
        node = new NodeImpl(metadata)
           .addLeaf(field)
           .addChildNode(conditions.getNode());
    }

    public ConditionsImpl(Conditions conditions) {
        node = rebuildNodeMetadata(conditions.getNode(), false);
    }

    /**
     * It is used
     *
     * @param query
     */
    public ConditionsImpl(NodeMetadata metadata, Query query) {
        node = new NodeImpl(metadata)
           .addChildNode(queryToNode(query));
    }

    public ConditionsImpl(Expression field, NodeMetadata metadata, Query query) {
        node = new NodeImpl(metadata)
           .addLeaf(field)
           .addChildNode(queryToNode(query));
    }

    /**
     * The {@code query} is turned into node.
     */
    private Node queryToNode(Query query) {
        AbstractSyntaxTree tree = getTree();
        query.assemble(tree);
        return rebuildNodeMetadata(tree.getRootNode(), true);
    }

    /**
     * Instantiate a new {@link #node} with {@code metadata}, that contains
     * the previous {@link #node} value as first child node and the {@code conditions}
     * as second child node.
     * <p>
     * The second node is derived out of {@code conditions} and its metadata
     * is rebuilt if it is needed.
     *
     * @see #rebuildNodeMetadata(Node, boolean)
     */
    private void rebuildNode(NodeMetadata metadata, Node node1) {
        node = new NodeImpl(metadata)
           .addChildNode(node)
           .addChildNode(rebuildNodeMetadata(node1, false));
    }

    /**
     * If the node has two child nodes or {@code force} is true,
     * a {@code metadata} parentheses is set up.
     */
    private Node rebuildNodeMetadata(Node node1, boolean force) {
        if (force || node1.count() == 2) {
            NodeMetadata nodeMetadata = node1.getNodeMetadata().setParentheses(true);
            node1.setNodeMetadata(nodeMetadata);
        }

        return node1;
    }

    //----
    // API
    //----

    @Override
    public final Conditions and(String leftValue, String operator, String rightValue) {
        and(asName(leftValue), operator(operator), asName(rightValue));
        return this;
    }

    @Override
    public final Conditions and(Expression leftField, Operator operator, Expression rightField) {
        and(condition(leftField, operator, rightField));
        return this;
    }

    @Override
    public final Conditions and(Conditions conditions) {
        rebuildNode(AND, conditions.getNode());
        return this;
    }

    @Override
    public Conditions and(Expression field, Operator operator, Query query) {
        and(condition(field, operator, query));
        return this;
    }

    @Override
    public final Conditions andExists(Query query) {
        and(conditionExists(query));
        return this;
    }

    @Override
    public final Conditions andNot(String leftValue, String operator, String rightValue) {
        andNot(asName(leftValue), operator(operator), asName(rightValue));
        return this;
    }

    @Override
    public final Conditions andNot(Expression leftField, Operator operator, Expression rightField) {
        andNot(condition(leftField, operator, rightField));
        return this;
    }

    @Override
    public final Conditions andNot(Conditions conditions) {
        rebuildNode(AND_NOT, conditions.getNode());
        return this;
    }

    @Override
    public final Conditions andNot(Expression field, Operator operator, Query query) {
        andNot(condition(field, operator, query));
        return this;
    }

    @Override
    public final Conditions andNotExists(Query query) {
        andNot(conditionExists(query));
        return this;
    }

    @Override
    public final Conditions or(String leftValue, String operator, String rightValue) {
        or(asName(leftValue), operator(operator), asName(rightValue));
        return this;
    }

    @Override
    public final Conditions or(Expression leftField, Operator operator, Expression rightField) {
        or(condition(leftField, operator, rightField));

        return this;
    }

    @Override
    public final Conditions or(Conditions conditions) {
        rebuildNode(OR, conditions.getNode());
        return this;
    }

    @Override
    public final Conditions orExists(Query query) {
        or(conditionExists(query));
        return this;
    }

    @Override
    public final Conditions or(Expression field, Operator operator, Query query) {
        or(condition(field, operator, query));
        return this;
    }

    @Override
    public final Conditions orNot(String leftValue, String operator, String rightValue) {
        orNot(asName(leftValue), operator(operator), asName(rightValue));
        return this;
    }

    @Override
    public final Conditions orNot(Expression leftField, Operator operator, Expression rightField) {
        orNot(condition(leftField, operator, rightField));
        return this;
    }

    @Override
    public final Conditions orNot(Conditions conditions) {
        rebuildNode(OR_NOT, conditions.getNode());
        return this;
    }

    @Override
    public final Conditions orNot(Expression field, Operator operator, Query query) {
        orNot(condition(field, operator, query));
        return this;
    }

    @Override
    public final Conditions orNotExists(Query query) {
        orNot(conditionExists(query));
        return this;
    }

    @Override
    public Node getNode() {
        return node;
    }

    @Override
    public void assemble(AbstractSyntaxTree tree) {
        Node node2;

        try {
            node2 = ((NodeImpl) node).clone();

            if (node2.count() == 2) {
                NodeMetadata nodeMetadata = node2.getNodeMetadata().setParentheses(true);
                node2.setNodeMetadata(nodeMetadata);
            }

        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        tree.addChildNode(node);
    }
}
