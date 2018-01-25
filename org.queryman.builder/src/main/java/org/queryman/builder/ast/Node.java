/*
 *  Queryman. Java tools for working with queries of PostgreSQL database.
 *
 *  License: MIT License.
 *  To see license follow by http://queryman.org/license.txt
 */
package org.queryman.builder.ast;

import java.util.List;

/**
 * Element of tree.
 *
 * @author Timur Shaidullin
 */
interface Node extends Cloneable {
    /**
     * Add children node.
     */
    Node addChildNode(Node node);

    /**
     * Add leaf.
     */
    Node addLeaf(String value);

    /**
     * Get leaves.
     */
    List<String> getLeaves();

    /**
     * Get children nodes.
     */
    List<Node> getNodes();

    /**
     * Return true if {@code this} does not contain other nodes.
     */
    boolean isEmpty();

    /**
     * Leaves is separated by delimiter.
     */
    Node setDelimiter(String c);

    /**
     * Leaves is separated by delimiter.
     */
    String getDelimiter();

    /**
     * Each node must named.
     * Note. Maybe it will contain other node metadata.
     */
    String getNodeName();

    /**
     * Delete all nodes and leaves.
     */
    void clear();
}
