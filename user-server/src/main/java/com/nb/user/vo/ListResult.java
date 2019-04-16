package com.nb.user.vo;

/**
 * @description 列表响应对象
 * @author: fly
 * @date: 2018/11/21 14:59
 */
public class ListResult<E> {
    private E list;

    public ListResult(E list) {
        this.list = list;
    }

    public E getList() {
        return list;
    }

    public void setList(E list) {
        this.list = list;
    }

    public static <E> ListResult<E> build(E list) {
        return new ListResult<>(list);
    }

}
