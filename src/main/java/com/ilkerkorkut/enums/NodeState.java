package com.ilkerkorkut.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum NodeState {

    FOLLOWER(0),
    CANDIDATE(1),
    LEADER(2);

    @Getter
    private int state;
}
