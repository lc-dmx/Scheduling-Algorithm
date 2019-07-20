package algorithm;
import bean.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LC on 17/07/2019 15:01
 */
public abstract class Executor {
    private final List<Task> taskList;
    private final int initPos;

    public Executor(List<Task> tasks, int initPos) {
        this.taskList = new ArrayList<>(tasks);
        this.initPos = initPos;
    }

    public List<Task> getTaskList() {
        return taskList;
    }

    public int getInitPos() {
        return initPos;
    }

    public abstract void dispatching();

    public abstract String scheduler();

    public void exec() {
        System.out.println(scheduler());
        System.out.format("电梯当前位于第%s层，对如下乘客进行服务：\n", getInitPos());

        for (Task task : getTaskList()) {
            System.out.format("%d->%d \n", task.getFrom(), task.getTo());
        }

        System.out.println("\n请求次序     服务乘客    电梯移动楼层数");

        dispatching();

        int totalTime = 0;
        int prev = getInitPos();
        for (int i = 0; i < getTaskList().size(); i++) {
            Task task = getTaskList().get(i);
            int span = Math.abs(task.getFrom() - prev);
            totalTime += span;

            int dist = Math.abs(task.getFrom() - task.getTo());
            totalTime += dist;

            prev = task.getTo();

            System.out.format("   %d     %s:%2d->%2d       %2d\n", i + 1, task.getName(), task.getFrom(),
                    task.getTo(), span + dist);
        }

        System.out.println("总移动距离: " + totalTime);
        double ave = totalTime / 1.0 / getTaskList().size();
        System.out.format("平均每次服务的距离: %.1f\n", ave);
    }
}
