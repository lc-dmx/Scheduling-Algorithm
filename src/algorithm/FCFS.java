package algorithm;
import bean.Task;
import java.util.List;

/**
 * Created by LC on 17/07/2019 14:56
 */
public class FCFS extends Executor {
    public FCFS(List<Task> tasks, int initPos) {
        super(tasks, initPos);
    }

    @Override
    public void dispatching() {

    }

    @Override
    public String scheduler() {
        return "1) 先来先服务算法(FCFS):";
    }

}
