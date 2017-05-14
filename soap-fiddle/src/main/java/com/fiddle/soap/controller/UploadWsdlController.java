package com.fiddle.soap.controller;

import java.net.MalformedURLException;
import java.nio.file.Path;

import org.reficio.ws.builder.core.Wsdl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fiddle.soap.model.SoapFiddleModel;
import com.fiddle.soap.storage.StorageService;


@Controller
public class UploadWsdlController {

    private final StorageService storageService;

    @Autowired
    public UploadWsdlController(StorageService storageService) {
        this.storageService = storageService;
        storageService.deleteAll();
        storageService.init();
    }
	
	@PostMapping("/uploadWsdl")
	public String handleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) throws MalformedURLException {

		storageService.store(file);
		Path loadedWsdlFilePath = storageService.load(file.getOriginalFilename());
		System.out.println(loadedWsdlFilePath.toAbsolutePath());
		redirectAttributes.addFlashAttribute("soapFiddleModel", new SoapFiddleModel(Wsdl.parse(loadedWsdlFilePath.toUri().toURL())));
		loadedWsdlFilePath.toFile().delete();
		return "redirect:/soapFiddle";
	}

}
