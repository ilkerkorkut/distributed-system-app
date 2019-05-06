package com.ilkerkorkut.model;

import com.ilkerkorkut.enums.NodeState;

import java.util.concurrent.atomic.AtomicInteger;

public class Node {

    private String id;
    private NodeState nodeState;
    private int port;
    private AtomicInteger term;
    private AtomicInteger voteCount;

    public Node(String id, NodeState nodeState, int port, AtomicInteger term, AtomicInteger voteCount) {
        this.id = id;
        this.nodeState = nodeState;
        this.port = port;
        this.term = term;
        this.voteCount = voteCount;
    }

    public Node() {
    }

    public static NodeBuilder builder() {
        return new NodeBuilder();
    }

    public String getId() {
        return this.id;
    }

    public synchronized NodeState getNodeState() {
        return this.nodeState;
    }

    public int getPort() {
        return this.port;
    }

    public AtomicInteger getTerm() {
        return this.term;
    }

    public AtomicInteger getVoteCount() {
        return this.voteCount;
    }

    public void setId(String id) {
        this.id = id;
    }

    public synchronized void setNodeState(NodeState nodeState) {
        this.nodeState = nodeState;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setTerm(AtomicInteger term) {
        this.term = term;
    }

    public void setVoteCount(AtomicInteger voteCount) {
        this.voteCount = voteCount;
    }

    public static class NodeBuilder {
        private String id;
        private NodeState nodeState;
        private int port;
        private AtomicInteger term;
        private AtomicInteger voteCount;

        NodeBuilder() {
        }

        public Node.NodeBuilder id(String id) {
            this.id = id;
            return this;
        }

        public Node.NodeBuilder nodeState(NodeState nodeState) {
            this.nodeState = nodeState;
            return this;
        }

        public Node.NodeBuilder port(int port) {
            this.port = port;
            return this;
        }

        public Node.NodeBuilder term(AtomicInteger term) {
            this.term = term;
            return this;
        }

        public Node.NodeBuilder voteCount(AtomicInteger voteCount) {
            this.voteCount = voteCount;
            return this;
        }

        public Node build() {
            return new Node(id, nodeState, port, term, voteCount);
        }

        public String toString() {
            return "Node.NodeBuilder(id=" + this.id + ", nodeState=" + this.nodeState + ", port=" + this.port + ", term=" + this.term + ", voteCount=" + this.voteCount + ")";
        }
    }
}
