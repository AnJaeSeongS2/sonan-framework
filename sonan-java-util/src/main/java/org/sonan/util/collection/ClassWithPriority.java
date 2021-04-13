package org.sonan.util.collection;

/**
 * 객체가 가진 priority값으로 정렬되는 객체이다.
 *
 * Created by Jaeseong on 2021/04/11
 * Git Hub : https://github.com/AnJaeSeongS2
 */
public class ClassWithPriority implements Comparable<ClassWithPriority> {
    protected int priority;
    public ClassWithPriority(int priority) {
        this.priority = priority;
    }

    @Override
    public int compareTo(ClassWithPriority o) {
        return (priority < o.priority) ? -1 : ((priority == o.priority) ? 0 : 1);
    }
}
