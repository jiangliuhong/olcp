package top.jiangliuhong.olcp.data.event;

import org.springframework.context.ApplicationEvent;

/**
 * groovy file change event
 */
public class GroovyFileChangeEvent extends ApplicationEvent {

    private final String folder;
    private final String name;

    private final String appName;

    public GroovyFileChangeEvent(Object source, String appName, String folder, String name) {
        super(source);
        this.appName = appName;
        this.folder = folder;
        this.name = name;
    }

    public String getAppName() {
        return this.appName;
    }

    public String getFolder() {
        return this.folder;
    }

    public String getName() {
        return this.name;
    }
}

