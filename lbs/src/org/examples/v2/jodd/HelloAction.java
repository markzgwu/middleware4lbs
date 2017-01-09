package org.examples.v2.jodd;

import jodd.madvoc.meta.*;

@MadvocAction
public class HelloAction {

    @In
    String name;

    @Out
    String value;

    @Action
    public String world() {
        System.out.println("HelloAction.world " + name);
        value = "Hello World!";
        return "ok";
    }
}