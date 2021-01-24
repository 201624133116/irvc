package com.wethura.irvc;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * This class for reading the config of resources/config.yml
 *
 * @author wethura
 * @date 2021/1/18 上午11:43
 */
public class Config {

    private static String KEY_PROJECT          = "project";
    private static String KEY_PROJECT_PATH     = "project_path";
    private static String KEY_ENVIRONMENT_PATH = "environment_path";

    private String name;
    private String projectPath;
    private String environmentPath;

    private static Map<String, Config> configMap = new LinkedHashMap<>();

    static {
        readConfig();
    }

    public static void readConfig() {
        List<Node> configs = YamlReader.read(YamlReader.class.getClassLoader().getResourceAsStream("config.yml")).getList();

        if (configs == null) {
            return;
        }

        configs.forEach(node -> {
            final Config config = new Config();
            config.setName(node.getMap().get(KEY_PROJECT).getValue());
            config.setProjectPath(node.getMap().get(KEY_PROJECT_PATH).getValue());
            config.setEnvironmentPath(node.getMap().get(KEY_ENVIRONMENT_PATH).getValue());

            configMap.put(config.getName(), config);
        });

        System.out.println("config initial succeed!");
    }

    /**
     * the array can not be opt, only for read
     */
    public static List<Project> getProjectsAsList() {
        return Arrays.asList(Config.configMap.values().toArray(new Project[0]));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public void setProjectPath(String projectPath) {
        this.projectPath = projectPath;
    }

    public String getProjectPath() {
        return projectPath;
    }

    public String getEnvironmentPath() {
        return environmentPath;
    }

    public void setEnvironmentPath(String environmentPath) {
        this.environmentPath = environmentPath;
    }

    public static Map<String, Config> getConfigMap() {
        return configMap;
    }

    public static void setConfigMap(Map<String, Config> configMap) {
        Config.configMap = configMap;
    }
}
