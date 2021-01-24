package com.wethura.irvc;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author wethura
 * @date 2021/1/17 下午9:48
 */
public class Node {
    private String name;

    private Type type;

    private String value;
    private List<Node> list;
    private Map<String, Node> map;

    public Node() {
        list = new LinkedList<>();
        map = new LinkedHashMap<>();
    }

    public static enum Type {
        /**
         * represent for map, string, list of json struct.
         */
        MAP,
        STRING,
        LIST
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<Node> getList() {
        return list;
    }

    public void setList(List<Node> list) {
        this.list = list;
    }

    public Map<String, Node> getMap() {
        return map;
    }

    public void setMap(Map<String, Node> map) {
        this.map = map;
    }
}
