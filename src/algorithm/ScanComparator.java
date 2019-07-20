package algorithm;
import bean.Task;
import common.Direct;

import java.util.Comparator;

/**
 * Created by LC on 19/07/2019 23:36
 */
public class ScanComparator implements Comparator<Task> {
    private final int initPos;
    private final Direct direct;

    public ScanComparator(int initPos, Direct direct) {
        this.initPos = initPos;
        this.direct = direct;
    }

    @Override
    public int compare(Task o1, Task o2) {
        int i = ifInitDirectIsUp(o1, o2);
        if (direct == Direct.UP) {
            return i;
        } else {
            return -i;
        }
    }

    private int ifInitDirectIsUp(Task o1, Task o2) {
        int i = biggerOrSmaller(o1, o2);
        Direct d1 = o1.getDirect();
        Direct d2 = o2.getDirect();
        if (d1 == d2) {
            if (d1 == Direct.UP) {
                if (o1.getFrom() > initPos) {
                    return i;
                } else {
                    return 1;
                }
            } else {
                if (o1.getFrom() > initPos) {
                    return -i;
                } else {
                    return 1;
                }
            }
        } else {
            if (d1 == Direct.UP) {
                if (o1.getFrom() > initPos) {
                    return -1;
                } else {
                    return 1;
                }
            } else {
                if (o1.getFrom() > initPos) {
                    return 1;
                } else {
                    return -1;
                }
            }
        }
    }

    private int biggerOrSmaller(Task o1, Task o2) {
        if (o1.getFrom() > o2.getFrom()) {
            return 1;
        } else if (o1.getFrom() < o2.getFrom()) {
            return -1;
        } else {
            return 0;
        }
    }
}
