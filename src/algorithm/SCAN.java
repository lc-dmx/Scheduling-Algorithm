package algorithm;

import bean.Task;
import common.Direct;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LC on 17/07/2019 15:40
 */
public class SCAN extends Executor {
    private Direct direct;
    private int min;
    private int max;

    public SCAN(List<Task> tasks, int initPos, Direct direct, int min, int max)
    {
        super(tasks, initPos);
        this.direct = direct;
        this.min = min;
        this.max = max;
    }

    public Direct getDirect() {
        return direct;
    }

    public void setDirect(Direct direct) {
        this.direct = direct;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    @Override
    public void dispatching() {
        List<Task> newTaskList = new ArrayList<>(getTaskList());
        newTaskList.sort(new ScanComparator(getInitPos(), direct));
        getTaskList().clear();
        getTaskList().addAll(newTaskList);
    }

    @Override
    public String scheduler() {
        return "3) 扫描算法(SCAN):";
    }

    @Override
    public void exec() {
        System.out.println(scheduler());
        System.out.format("电梯当前位于第%s层，对如下乘客进行服务：\n", getInitPos());

        for (Task task : getTaskList()) {
            System.out.format("%d->%d \n", task.getFrom(), task.getTo());
        }

        System.out.println("\n请求次序     服务乘客    电梯移动楼层数");

        dispatching();

        int totalTime = 0;
        for (int i = 0; i < getTaskList().size(); i++) {
            Task task = getTaskList().get(i);
            int dist = Math.abs(task.getFrom() - task.getTo());
            totalTime += dist;

            System.out.format("   %d     %s:%2d->%2d       -\n", i + 1, task.getName(), task.getFrom(),
                    task.getTo());
        }

        totalTime = (max - min) * 2;
        System.out.println("总移动距离: " + totalTime);
        double ave = totalTime / 1.0 / getTaskList().size();
        System.out.format("平均每次服务的距离: %.1f\n", ave);
    }
}
