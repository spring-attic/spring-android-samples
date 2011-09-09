/*
 * Copyright 2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.android.showcase;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * Handles requests for the application file upload page.
 * 
 * @author Roy Clarkson
 */
@Controller
public class FileUploadController {

    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    /**
     * Simply selects the view to render by returning its name.
     */
    @RequestMapping(value = "fileupload", method = RequestMethod.GET)
    public String fileUpload() {
        return "fileupload";
    }

    /**
     * Accepts a POST request with multipart/form-data content
     * @param name the name of the file being uploaded
     * @param file the binary file
     * @return response message indicating success or failure
     */
    @RequestMapping(value = "postformdata", method = RequestMethod.POST, headers = "Content-Type=multipart/form-data")
    public @ResponseBody
    String handleFormUpload(@RequestParam("description") String description, @RequestParam("file") MultipartFile file) {

        if (!file.isEmpty()) {
            byte[] bytes = null;
            try {
                bytes = file.getBytes();
            } catch (IOException e) {
                logger.info("error processing uploaded file", e);
            }
            return "file upload received! Name:[" + description + "] Size:[" + bytes.length + "]";
        } else {
            return "file upload failed!";
        }
    }

}
