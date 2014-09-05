package diplomacy.vo;

import java.util.List;

public class PagerBean<T> {
    private List<T> list;
    private long allCount;
    private int start;
    private int size;

    public int getPage() {
        int ret = (int) allCount / size + ((int) allCount % size != 0 ? 1 : 0);
        return ret == 0 ? 1 : ret;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public long getAllCount() {
        return allCount;
    }

    public void setAllCount(long allCount) {
        this.allCount = allCount;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
