package com.wethura.irvc;

import com.wethura.irvc.utils.Strings;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

/**
 * @author wethura
 * @date 2021/1/19 上午3:03
 */
public class Project {

    private static String DEFAULT_ENVIRONMENT = "local";
    private static String DEFAULT_TAG         = "latest";

    private static String environment_path;
    private static String workspace_path;


    private static Map<String, Project> projects = new LinkedHashMap<>();

    private List<Env> envs;

    /**
     * environmentName, tagName can be null, (local, latest) will be the default value.
     */
    public static Map<String, Project> initial(String projectName, String environmentName, String tagName) throws IOException {

        environmentName = Strings.getOrDefault(environmentName, DEFAULT_ENVIRONMENT);
        tagName = Strings.getOrDefault(tagName, DEFAULT_TAG);

        final Config config = Config.getConfigMap().get(projectName.toUpperCase());

        setEnvironment_path(config.getEnvironmentPath() + "/" + environmentName + "/" + tagName + ".yml");
        setWorkspace_path(config.getProjectPath() + "/.idea/workspace.xml");

        try (FileInputStream inputStream = new FileInputStream(getEnvironment_path())) {
            final Node root = YamlReader.read(inputStream);

            root.getMap().entrySet().forEach(entry -> {
                final Project project = new Project();
                entry.getValue().getMap().entrySet().forEach(envEntry -> {
                    project.getEnvs().add(new Env(envEntry.getKey(), envEntry.getValue().getValue()));
                });

                projects.put(entry.getKey(), project);
            });
        }
        return null;
    }

    public List<Project> getProjectsAsList() {
        return Arrays.asList(projects.values().toArray(new Project[0]));
    }

    public static class Env {
        private String name;
        private String value;

        public Env(String name, String value) {
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    public List<Env> getEnvs() {
        if (envs == null) {
            envs = new LinkedList<>();
        }
        return envs;
    }

    public void setEnvs(List<Env> envs) {
        this.envs = envs;
    }

    public static Map<String, Project> getProjects() {
        return projects;
    }

    public static void setProjects(Map<String, Project> projects) {
        Project.projects = projects;
    }

    public static String getEnvironment_path() {
        return environment_path;
    }

    public static void setEnvironment_path(String environment_path) {
        Project.environment_path = environment_path;
    }

    public static String getWorkspace_path() {
        return workspace_path;
    }

    public static void setWorkspace_path(String workspace_path) {
        Project.workspace_path = workspace_path;
    }
}
