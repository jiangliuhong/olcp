package top.jiangliuhong.olcp.engine.load;

import java.io.File;
import java.util.function.Consumer;

/**
 * list folder all file
 */
public class FolderAllReader {

    public void read(File folder, Consumer<File> fileConsumer) {
        if (folder != null && folder.isDirectory()) {
            File[] files = folder.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        read(file, fileConsumer);
                    } else {
                        fileConsumer.accept(file);
                    }
                }
            }
        }
    }
}
