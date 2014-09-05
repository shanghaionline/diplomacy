package diplomacy.action;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletContext;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/fileupload")
public class UploadAct implements ServletContextAware {
    private ServletContext servletContext;

    @RequestMapping("/upload")
    @ResponseBody
    String upload(MultipartFile file) {
        String uri = String.format("/upload/%d%s",
                System.currentTimeMillis(), getFileSuffix(file.getOriginalFilename()));
        String path = servletContext.getRealPath(uri);
        String ret = "";
        try {
            file.transferTo(new File(path));
            ret = uri;
        } catch (IllegalStateException | IOException e) {
            e.printStackTrace();
        }
        return ret;
    }

    private String getFileSuffix(String filename) {
        int e = filename.lastIndexOf(".");
        if (e == -1) {
            return "";
        }
        return filename.substring(e);
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }
}
