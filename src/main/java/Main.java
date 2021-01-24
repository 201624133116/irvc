import com.wethura.irvc.Project;
import com.wethura.irvc.XmlUpdater;
import org.dom4j.DocumentException;

import java.io.File;
import java.io.IOException;

/**
 * @author wethura
 * @date 2021/1/18 下午1:19
 */
public class Main {
    public static void main(String[] args) throws IOException, DocumentException {
        Project.initial("UFS", null, null);
        new XmlUpdater().update(new File(Project.getWorkspace_path()));
    }
}
