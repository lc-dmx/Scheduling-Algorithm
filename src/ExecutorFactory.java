import bean.Task;
import common.Direct;
import common.Type;
import algorithm.Executor;
import algorithm.FCFS;
import algorithm.SSTF;
import algorithm.SCAN;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by LC on 17/07/2019 15:40
 */
public class ExecutorFactory {
    private static final int INIT_POS = 1;
    private static final int MIN_FLOOR = 1;
    private static final int MAX_FLOOR = 20;
    private static final Direct INIT_DIRECT = Direct.UP;
    private static final List<Task> TASKS = Collections.unmodifiableList(new LinkedList<Task>() {
        {
            add(new Task("乘客1", 4, 7));
            add(new Task("乘客2", 8, 10));
            add(new Task("乘客3", 7, 8));
            add(new Task("乘客4", 9, 2));
            add(new Task("乘客5", 10, 7));
            add(new Task("乘客6", 3, 5));

        }
    });

    private Executor create(Type type) {
        switch (type) {
            case FCFS:
                return new FCFS(TASKS, INIT_POS);
            case SSTF:
                return new SSTF(TASKS, INIT_POS);
            case SCAN:
                return new SCAN(TASKS, INIT_POS, INIT_DIRECT, MIN_FLOOR, MAX_FLOOR);
            default:
                throw new IllegalArgumentException("不存在这样的调度的算法");
        }
    }

    public static void main(String[] args) {
        Executor fcfs = new ExecutorFactory().create(Type.FCFS);
        fcfs.exec();
        System.out.println("\n-----------------------------------------------\n");

        Executor sstf = new ExecutorFactory().create(Type.SSTF);
        sstf.exec();
        System.out.println("\n-----------------------------------------------\n");

        Executor scan = new ExecutorFactory().create(Type.SCAN);
        scan.exec();
    }
}
