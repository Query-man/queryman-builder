/*
 *  Queryman. Java tools for working with queries of PostgreSQL database.
 *
 *  License: MIT License
 *  To see license follow by http://queryman.org/license.txt
 */
package org.queryman.builder.ast;

import org.queryman.builder.token.Token;

import java.util.LinkedList;
import java.util.List;

/**
 * Standard implementation of {@link Node}. This is used by {@link AbstractSyntaxTree}.
 *
 * @author Timur Shaidullin
 */
public final class NodeImpl implements Node, Cloneable {
    private NodeMetadata nodeName;
    private final List<Token> leaves = new LinkedList<>();
    private final List<Node>   nodes  = new LinkedList<>();

    private String separator = " ";

    public NodeImpl(NodeMetadata value) {
        this.nodeName = value;
    }

    @Override
    public Node addChildNode(Node node) {
        nodes.add(node);
        return this;
    }

    @Override
    public Node addLeaf(Token token) {
        leaves.add(token);
        return this;
    }

    @Override
    public NodeMetadata getNodeMetadata() {
        return nodeName;
    }

    @Override
    public NodeMetadata setNodeMetadata(NodeMetadata nodeMetadata) {
        nodeName = nodeMetadata;
        return nodeName;
    }

    @Override
    public void clear() {
        nodes.clear();
        leaves.clear();
    }

    @Override
    public List<Token> getLeaves() {
        return leaves;
    }

    @Override
    public List<Node> getNodes() {
        return nodes;
    }

    @Override
    public boolean isEmpty() {
        return count() == 0;
    }

    @Override
    public int count() {
        return nodes.size();
    }

    @Override
    public Node setDelimiter(String c) {
        separator = c;
        return this;
    }

    @Override
    public String getDelimiter() {
        return separator;
    }

    @Override
    public Node clone() throws CloneNotSupportedException {
        return (Node) super.clone();
    }
}
