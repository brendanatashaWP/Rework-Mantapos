package project.blibli.mantapos.Beans_Model;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

public class UploadMenuImage {
    @NotNull
    private MultipartFile multipartFile;

    public MultipartFile getMultipartFile() {
        return multipartFile;
    }

    public void setMultipartFile(MultipartFile multipartFile) {
        this.multipartFile = multipartFile;
    }
}
