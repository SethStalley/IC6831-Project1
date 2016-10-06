package webserver;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static java.nio.file.StandardCopyOption.*;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import team_identifier.Reader;


@Controller
public class FileUploadController {

  String dir = System.getProperty("user.dir");
  String ROOT = dir.substring(0, dir.lastIndexOf("backend"))
      + "backend/complete/src/main/resources/public/videos";


  @RequestMapping(method = RequestMethod.POST, value = "/postFile")
  @ResponseBody
  public String handleFileUpload(@RequestParam("file") MultipartFile file,
      RedirectAttributes redirectAttributes) {

    String message = "";

    if (!file.isEmpty()) {
      try {

        Files.copy(file.getInputStream(), Paths.get(ROOT, file.getOriginalFilename()),
            REPLACE_EXISTING);
        message = "Se subio " + file.getOriginalFilename() + " de forma exitosa!";

        // Process video for testing
        proccessVideo(ROOT + '/' + file.getOriginalFilename());
      } catch (FileAlreadyExistsException e) {
        message = "Archivo ya existe, cambie el nombre de archivo!";
      } catch (IOException | RuntimeException e) {
        e.printStackTrace();
        message =
            "Hubo un problema subiendo " + file.getOriginalFilename() + " => " + e.getMessage();
      }
    } else {
      message = "No se pudo subir " + file.getOriginalFilename() + " porque el archivo es vacio";
    }
    System.out.println(message);
    return message;
  }


  private void proccessVideo(String videoPath) {
    try {
      Reader.readVideo(videoPath);
      Reader.addMask();
      Reader.normalize();

    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

  }

  private void hola() {

  }

}
