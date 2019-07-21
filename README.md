# Scheduling Algorithm

In computing, scheduling is the method by which work is assigned to resources that complete the work. The work may be virtual 
computation elements such as threads, processes or data flows, which are in turn scheduled onto hardware resources such as processors, 
network links or expansion cards.

举个小例子，每天早上，那些差5分钟就迟到的程序员，在等电梯时，一般会做两件事：

第一，在心里骂电梯慢；

第二，在心里暗算着电梯调度如何优化；

以下是本文将涉及到的调度算法:

传统电梯调度方法:

[FCFS算法](#先来先服务算法)

[SSTF算法](#最短寻找楼层时间优先算法)

[SCAN算法](#扫描算法)

[LOOK算法](#LOOK算法)

[SAFT算法](#SAFT算法)

实时电梯调度方法:

[EDF算法](#最早截止期优先调度算法)

[SCAN-EDF算法](#SCAN-EDF算法)

[PI算法](#PI算法)

[FD-SCAN算法](#FD-SCAN算法)

# 先来先服务算法

(FCFS, First Come First Serve)

Idea: 根据乘客请求乘坐电梯的先后次序进行调度。

Advantages: 公平、简单，每个乘客的请求都能依次得到处理，不会出现某一乘客的请求长期得不到满足的情况。

Disadvantages: 负荷较大情况下，性能急剧下降。

代码:

```
public class FCFS {
    public static void exec(List<Task> taskList, int initPos) {
        System.out.println("1)先来先服务算法（FCFS）:");
        System.out.format("电梯当前位于第%s层, 对如下乘客进行服务:\n", initPos);
        for (Task task : taskList) {
            System.out.format("%d->%d ", task.from, task.to);
        }
        System.out.println("\n请求次序     服务乘客    电梯移动楼层数");

        int totalTime = 0;
        int prev = initPos;
        for (int i = 0; i < taskList.size(); i++) {
            Task task = taskList.get(i);
            int abs = Math.abs(task.from - prev);
            totalTime += abs;
            int d = Math.abs(task.from - task.to);
            totalTime += d;
            prev = task.to;
            System.out.format("   %d     %s:%2d->%2d       %2d\n", i, task.name, task.from, task.to, abs + d);
        }
        System.out.println("总移动距离: " + totalTime);
        double ave = totalTime / 1.0 / taskList.size();
        System.out.format("平均每次服务的距离: %.1f\n", ave);
    }
}
```

代码简单。任务执行只需按请求队列的顺序执行即可。

![FCFS](https://github.com/lc-dmx/Scheduling-Algorithm/blob/master/img/Capture1.PNG)

# 最短寻找楼层时间优先算法

(SSTF, Shortest Seek Time First)

Idea: 此算法选择下一个服务对象的原则是最短寻找楼层的时间。

Advantages: 注重电梯寻找楼层的优化。请求队列中距当前能够最先到达的楼层的请求信号就是下一个服务对象。

Disadvantages: 队列中的某些请求可能长时间得不到响应，出现所谓的“饿死”现象。(处于中间楼层的请求会被快速响应，但处于底层和上层的请求，会被长时间搁置。)

代码: 

```
public class SSTF {
    public static void exec(List<Task> taskList, int initPos) {
        System.out.println("1)先来先服务算法（FCFS）:");
        System.out.format("电梯当前位于第%s层, 对如下乘客进行服务:\n", initPos);
        for (Task task : taskList) {
            System.out.format("%d->%d ", task.from, task.to);
        }
        System.out.println("\n请求次序     服务乘客    电梯移动楼层数");

      	//---------------------调度开始-----------------------
        int prev = initPos;
        List<Task> visit = new ArrayList<>();
        for (int i = 0; i < taskList.size(); i++) {
            int min = Integer.MAX_VALUE;
            int shortIndex = 0;
            for (int j = 0; j < taskList.size(); j++) {
                Task task = taskList.get(j);
                if (task == null) continue;
                int curr = Math.abs(task.from - prev);
                if (min > curr) {
                    min = curr;
                    shortIndex = j;
                }
            }
            Task e = taskList.get(shortIndex);
            visit.add(e);
            taskList.set(shortIndex, null);
            prev = e.to;
        }
        taskList.clear();
        taskList.addAll(visit);
      	//---------------------调度结束-----------------------      

        int totalTime = 0;
        int prev = initPos;
        for (int i = 0; i < taskList.size(); i++) {
            Task task = taskList.get(i);
            int abs = Math.abs(task.from - prev);
            totalTime += abs;
            int d = Math.abs(task.from - task.to);
            totalTime += d;
            prev = task.to;
            System.out.format("   %d     %s:%2d->%2d       %2d\n", i, task.name, task.from, task.to, abs + d);
        }
        System.out.println("总移动距离: " + totalTime);
        double ave = totalTime / 1.0 / taskList.size();
        System.out.format("平均每次服务的距离: %.1f\n", ave);
    }
}
```

与FCFS算法相比，SSTF算法会先对请求队列进行排序，每次都会优先处理距离最近的请求。

![SSTF](https://github.com/lc-dmx/Scheduling-Algorithm/blob/master/img/Capture2.PNG)

# 扫描算法

(SCAN)

Idea: 一种按照楼层顺序依次服务请求，它让电梯在最底层和最顶层之间连续往返运行，在运行过程中响应处在于电梯运行方向相同的各楼层上的请求。

Advantages: 进行寻找楼层的优化，效率比较高。较好地解决了电梯移动的问题，在这个算法中，每个电梯响应乘客请求使乘客获得服务的次序是由其发出请求的乘客的位置与当前电梯位置之间的距离来决定的。

Disadvantages: 扫描算法的平均响应时间比最短寻找楼层时间优先算法长。

代码:

```
public class SCAN {
     System.out.println("3)扫描算法（SCAN）:");
        System.out.format("电梯当前位于第%s层, 对如下乘客进行服务:\n", initPos);
        for (Task task : taskList) {
            System.out.format("%d->%d ", task.from, task.to);
        }
        System.out.println("\n请求次序     服务乘客    电梯移动楼层数");
		//---------------------调度开始-----------------------
        List<Task> visit = new ArrayList<>(taskList);
        visit.sort(new ScanComparator(initPos, direct));
        taskList.clear();
        taskList.addAll(visit);
		//---------------------调度结束-----------------------
        int totalTime = 0;
        for (int i = 0; i < taskList.size(); i++) {
            Task task = taskList.get(i);
            int d = Math.abs(task.from - task.to);
            totalTime += d;
            System.out.format("   %d     %s:%2d->%2d       -\n", i, task.name, task.from, task.to);
        }
        totalTime = (max - min) * 2;
        System.out.println("总移动距离: " + totalTime);
        double ave = totalTime / 1.0 / taskList.size();
        System.out.format("平均每次服务的距离: %.1f\n", ave);
}

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

    public int ifInitDirectIsUp(Task o1, Task o2) {
        int i = biggerOrSmaller(o1, o2);
        Direct d1 = o1.getDirect();
        Direct d2 = o2.getDirect();
        if (d1 == d2) {
            if (d1 == Direct.UP) {
                if (o1.from > initPos) {
                    return i;
                } else {
                    return 1;
                }
            } else {
                if (o1.from > initPos) {
                    return -i;
                } else {
                    return 1;
                }
            }
        } else {
            if (d1 == Direct.UP) {
                if (o1.from > initPos) {
                    return -1;
                } else {
                    return 1;
                }
            } else {
                if (o1.from > initPos) {
                    return 1;
                } else {
                    return -1;
                }
            }
        }
    }


    public int biggerOrSmaller(Task o1, Task o2) {
        if (o1.from > o2.from) {
            return 1;
        } else if (o1.from < o2.from) {
            return -1;
        } else {
            return 0;
        }
    }
}
```

扫描算法的响应时间方差比最短寻找楼层时间优先算法小，从统计学角度来讲，扫描算法要比最短寻找楼层时间优先算法稳定。

![SCAN](https://github.com/lc-dmx/Scheduling-Algorithm/blob/master/img/Capture3.PNG)

# LOOK算法

LOOK 算法是扫描算法（SCAN）的一种改进。对LOOK算法而言，电梯同样在最底层和最顶层之间运行。

但当 LOOK 算法发现电梯所移动的方向上不再有请求时立即改变运行方向，而扫描算法则需要移动到最底层或者最顶层时才改变运行方向。

# SAFT算法

(SAFT, Shortest Access Time First)

与 SSTF 算法的思想类似，唯一的区别就是 SATF 算法将 SSTF 算法中的寻找楼层时间改成了访问时间。

这是因为电梯技术发展到今天，寻找楼层的时间已经有了很大地改进，但是电梯的运行当中等待乘客上梯时间却不是人为可以控制。

SATF 算法考虑到了电梯运行过程中乘客上梯时间的影响。

# 最早截止期优先调度算法

(EDF, Earliest Deadline First)

Idea: 是最简单的实时电梯调度算法，与FCFS调度算法类似。它响应请求队列中时限最早的请求，是其它实时电梯调度算法性能衡量的基准和特例。

缺点是造成电梯任意地寻找楼层，导致极低的电梯吞吐率。

# SCAN-EDF算法

是SCAN算法和EDF算法相结合的产物。SCAN-EDF算法先按照EDF算法选择请求列队中哪一个是下一个服务对象，而对于具有相同时限的请求，则按照SCAN算法服务每一个请求。它的效率取决于有相同deadline 的数目，因而效率是有限的。

# PI算法

(PI, Priority Inversion)

将请求队列中的请求分成两个优先级，它首先保证高优先级队列中的请求得到及时响应，在高优先级队列为空的情况下在相应地优先级队列中的请求。

# FD-SCAN算法

(FD-SCAN, Feasible Deadline SCAN)

首先从请求队列中找出时限最早、从当前位置开始移动又可以买足其时限要求的请求，作为下一次SCAN的方向。并在电梯所在楼层向该请求信号运行的过程中响应处在与电梯运行方向相同且电梯可以经过的请求信号。这种算法忽略了用SCAN算法相应其它请求的开销，因此并不能确保服务对象时限最终得到满足。
