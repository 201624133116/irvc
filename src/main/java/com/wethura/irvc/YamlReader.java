package com.wethura.irvc;

import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * read value from Yaml file to update workspace file.
 * @author wethura
 * @date 2021/1/17 下午9:26
 */
public class YamlReader {

    private static void readDfs(Object object, Node parent) {
        if (object instanceof Map) {
            parent.setType(Node.Type.MAP);

            Map<String, Object> map = (Map) object;

            map.entrySet().forEach(entry -> {
                final Node node = new Node();
                node.setName(entry.getKey());
                parent.getMap().put(entry.getKey(), node);

                readDfs(entry.getValue(), node);
            });

        } else if (object instanceof String) {
            parent.setType(Node.Type.STRING);

            parent.setValue((String) object);
        } else if (object instanceof List) {
            parent.setType(Node.Type.LIST);

            final List list = (List) object;
            list.forEach(e -> {
                final Node node = new Node();
                parent.getList().add(node);

                readDfs(e, node);
            });
        }
    }

    public static Node read(InputStream inputStream) {
        final Object yamlObject = new Yaml().load(inputStream);

        Node node = new Node();
        readDfs(yamlObject, node);

        return node;
    }
}
