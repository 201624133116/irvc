package com.wethura.irvc;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.dom4j.tree.DefaultAttribute;
import org.dom4j.tree.DefaultElement;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Class for update workspace file.
 *
 * @author wethura
 * @date 2021/1/20 上午1:42
 */
public class XmlUpdater {

    private static String ATTR_NAME = "name";

    public void update(File file) throws IOException, DocumentException {
        final SAXReader reader   = new SAXReader();
        Document        document = null;
        try (final FileInputStream fileInputStream = new FileInputStream(file)) {
            document = reader.read(fileInputStream);
            Element root = document.getRootElement();

            root.elements().forEach(component -> {
                if (component.attributes().stream().anyMatch(attribute -> attribute.getValue().equals("RunManager"))) {
                    List<Element> configurations = component.elements();
                    configurations.stream()
                            .filter(tag -> tag.getName().equals("configuration") && null != tag.attribute("name"))
                            .forEach(configuration -> {

                                final Project project = Project.getProjects().get(configuration.attribute(ATTR_NAME).getValue());

                                if (project == null) {
                                    // configurations.remove(configuration);
                                    return;
                                }
                                final List<Project.Env> envs = project.getEnvs();

                                List<Element> confs = configuration.elements();
                                confs.forEach(conf -> {
                                    if (conf.getName().equals("envs")) {
                                        // delete all env properties
                                        conf.elements().forEach(env -> conf.elements().remove(env));
                                        // add all env properties from module config.
                                        envs.forEach(env -> {
                                            final DefaultElement element = new DefaultElement("env");

                                            element.add(new DefaultAttribute("name", env.getName()));
                                            element.add(new DefaultAttribute("value", env.getValue()));

                                            conf.elements().add(element);
                                        });
                                    }
                                });
                            });
                }
            });
        }


        try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            // format
            OutputFormat format = OutputFormat.createPrettyPrint();
            format.setEncoding(document.getXMLEncoding());

            final XMLWriter writer = new XMLWriter(fileOutputStream);
            writer.write(document);
            writer.close();
        }

    }
}
