package com.frametrap.app;

public class Move {
    private String move_name;
    private int startup;
    private int active;
    private int recovery;
    private int on_block;
    private String move_type;

    public String getMove_name() {
        return move_name;
    }

    public void setMove_name(String move_name) {
        this.move_name = move_name;
    }

    public int getStartup() {
        return startup;
    }

    public void setStartup(int startup) {
        this.startup = startup;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public int getRecovery() {
        return recovery;
    }

    public void setRecovery(int recovery) {
        this.recovery = recovery;
    }

    public int getOn_block() {
        return on_block;
    }

    public void setOn_block(int on_block) {
        this.on_block = on_block;
    }

    public String getMove_type() {
        return move_type;
    }

    public void setMove_type(String move_type) {
        this.move_type = move_type;
    }
}
