package comm.servlet;

import org.apache.commons.digester.Digester;
import javax.servlet.UnavailableException;
import java.io.File;
import java.io.FilenameFilter;
import org.apache.struts.config.ModuleConfig;
import javax.servlet.ServletException;

public class ActionServlet extends org.apache.struts.action.ActionServlet {
    public ActionServlet() {
    }

    public ModuleConfig initModuleConfig(String prefix, String paths) throws
            ServletException {

        String path1 = getServletContext().getRealPath("/");
        File f = new File(path1 + "/WEB-INF/");
        File[] fs = f.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                if (name.startsWith("struts-") && name.endsWith(".xml") &&
                    !name.equals("struts-config.xml")) {
                    return true;
                } else {
                    return false;
                }
            }
        });
        StringBuffer buf = new StringBuffer(paths);
        for (File ff : fs) {
            String filePath = ff.getAbsolutePath();
            String tmpPath = filePath.substring(path1.length());
            if (tmpPath.charAt(0) != '/') {
                tmpPath = "/" + tmpPath;
            }
            if (tmpPath.length() > 1) {
                if (buf.length() > 0) {
                    buf.append(",");
                }
                buf.append(tmpPath);
            }
        }

        System.out.println("structs config path : "+buf.toString());
        return super.initModuleConfig(prefix, buf.toString());

    }


}
