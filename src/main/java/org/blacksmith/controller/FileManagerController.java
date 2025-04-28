package org.blacksmith.controller;

import jakarta.annotation.Resource;
import org.blacksmith.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
public class FileManagerController {

    @Autowired
    private FileStorageService fileStorageService;
    private static final Logger log = Logger.getLogger(FileManagerController.class.getName());

    @PostMapping("/upload-file")
    public boolean uploadFile(@RequestParam("file")MultipartFile file){
        try{
            fileStorageService.saveFile(file);
            return true;
        }catch (IOException e){
            log.log(Level.SEVERE, "Exception during upload",e);
        }
        return false;
    }

    @GetMapping("/getMessage")
    public String getMessage(){
        return "LOL FOOO";
    }


    @PostMapping("/{ruleId}/runs")
    public ResponseEntity<?> runRule(@PathVariable String ruleId, @RequestHeader("authorization") String authorization) {
        if (!authorization.startsWith("Bearer ")) {
            return ResponseEntity.status(401).body(Map.of("error", "Unauthorized"));
        }

        return ResponseEntity.ok(Map.of(
                "message", "Rule execution started successfully!",
                "ruleId", ruleId,
                "status", "running"
        ));
    }

//    @GetMapping
//    public ResponseEntity<Resource> downloadFile(@RequestParam("file") String filename){
//        try {
//            var fileToDownload = fileStorageService.getDownloadFile(filename);
//            return ResponseEntity.ok().contentLength();
//        } catch (Exception e) {
//            return ResponseEntity.notFound().build();
//        }
//    }
}
