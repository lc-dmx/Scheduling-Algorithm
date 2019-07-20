package algorithm;

import bean.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LC on 17/07/2019 15:40
 */
public class SSTF extends Executor {
    public SSTF(List<Task> tasks, int initPos) {
        super(tasks, initPos);
    }

    @Override
    public void dispatching() {
        int prev = getInitPos();
        List<Task> newTaskList = new ArrayList<>();

        for (int i = 0; i < getTaskList().size(); i++) {
            int min = Integer.MAX_VALUE;
            int shortIndex = 0;
            for (int j = 0; j < getTaskList().size(); j++) {
                Task task = getTaskList().get(j);
                if (task == null) continue;
                int curr = Math.abs(task.getFrom() - prev);
                if (min > curr) {
                    min = curr;
                    shortIndex = j;
                }
            }
            Task e = getTaskList().get(shortIndex);
            newTaskList.add(e);
            getTaskList().set(shortIndex, null);
            prev = e.getTo();
        }
        getTaskList().clear();
        getTaskList().addAll(newTaskList);
    }

    @Override
    public String scheduler() {
        return "2) 最短寻找楼层时间优先算法(SSTF):";
    }
}
