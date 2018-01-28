/*
 *  Queryman. Java tools for working with queries of PostgreSQL database.
 *
 *  License: MIT License
 *  To see license follow by http://queryman.org/license.txt
 */
package org.queryman.builder.ast;

import java.util.Deque;
import java.util.LinkedList;

/**
 * @author Timur Shaidullin
 */
final class TreeFormatter {
    String treeToString(Node node) {
        Deque<String> list = new LinkedList<>();
        if (node.getNodeMetadata().getName().length() > 0) {
            list.add(node.getNodeMetadata().getName());
        }

        if (node.getLeaves().size() > 0) {
            list.add(String.join(node.getDelimiter(), node.getLeaves()));
        }

        if (!node.isEmpty()) {
            for (Node n : node.getNodes()) {
                list.add(treeToString(n));
            }
        }

        return new Pipeline(node.getNodeMetadata())
           .process(list);
    }

    private class Pipeline {
        private final NodeMetadata metadata;

        private Pipeline(NodeMetadata metadata) {
            this.metadata = metadata;
        }

        private String process(Deque<String> list) {
            list = parentheses(list);

            return String.join(" ", list);
        }

        private Deque<String> parentheses(Deque<String> list) {
            if (metadata.hasParentheses()) {
                Deque<String> newList = new LinkedList<>();
                newList.add("(" + String.join(" ", list) + ")");

                return newList;
            }

            return list;
        }
    }
}