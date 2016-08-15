package webserver;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class FileUploadController {

	String dir = System.getProperty("user.dir");
	String ROOT  =  dir.substring(0, dir.lastIndexOf("backend")) +
			"videos/uploaded/";
	

	@RequestMapping(method = RequestMethod.POST, value = "/postFile")
	@ResponseBody
	public String handleFileUpload(@RequestParam("file") MultipartFile file,
								   RedirectAttributes redirectAttributes) {
		
		String message = "";
				
		if (!file.isEmpty()) {
			try {
				Files.copy(file.getInputStream(), Paths.get(ROOT, file.getOriginalFilename()));
				message=  "Se subio " + file.getOriginalFilename() + " de forma exitosa!";
			} catch (IOException|RuntimeException e) {
				message = "Hubo un problema subiendo " + file.getOriginalFilename() + " => " + e.getMessage();
			}
		} else {
			message = "No se pudo subir " + file.getOriginalFilename() + " porque el archivo es vacio";
		}
	
		return message;
	}

}