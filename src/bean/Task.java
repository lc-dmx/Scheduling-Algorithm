package bean;

import common.Direct;

/**
 * Created by LC on 17/07/2019 14:51
 */
public class Task {
    private String name;
    private int from;
    private int to;

    public Task() {

    }

    public Task(String name, int from, int to) {
        this.name = name;
        this.from = from;
        this.to = to;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getTo() {
        return to;
    }

    public void setTo(int to) {
        this.to = to;
    }

    public Direct getDirect() {
        return from - to > 0 ? Direct.DOWN : Direct.UP;
    }

    @Override
    public String toString() {
        return String.format("%s %d->%d", name, from, to);
    }
}
