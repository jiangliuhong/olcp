package top.jiangliuhong.olcp.data.event;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

/**
 * groovy file change event
 */
public class GroovyFileChangeEvent extends ApplicationEvent {

    private GroovyFileChange message;

    public GroovyFileChangeEvent(Object source, GroovyFileChange message) {
        super(source);
        this.message = message;
    }


    public GroovyFileChange getMessage() {
        return this.message;
    }

    public static GroovyFileChangeEventBuilder builder() {
        return new GroovyFileChangeEventBuilder();
    }

    @Getter
    @Setter
    public static class GroovyFileChange {
        private String appName;
        private String folder;
        private String name;
        private String oldFolder;
        private String oldName;
    }

    public static class GroovyFileChangeEventBuilder {
        private Object source;
        private final GroovyFileChange change = new GroovyFileChange();

        public GroovyFileChangeEvent build() {
            return new GroovyFileChangeEvent(this.source, this.change);
        }

        public GroovyFileChangeEventBuilder source(Object source) {
            this.source = source;
            return this;
        }

        public GroovyFileChangeEventBuilder appName(String appName) {
            this.change.setAppName(appName);
            return this;
        }

        public GroovyFileChangeEventBuilder file(String folder, String name) {
            this.change.setFolder(folder);
            this.change.setName(name);
            return this;
        }

        public GroovyFileChangeEventBuilder oldFile(String folder, String name) {
            this.change.setOldFolder(folder);
            this.change.setOldName(name);
            return this;
        }

    }

}

