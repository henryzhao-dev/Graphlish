package com.qimiao.graphlishproject.Service;

import com.qimiao.graphlishproject.Service.Interface.ImageUrlResolver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class R2ImageUrlResolver implements ImageUrlResolver {


    //from yml property
    @Value("${r2.public-domain}")
    private String publicDomain;


    @Override
    public String resolve(String imagePath) {

        //if imagePath, return null
        if (imagePath == null || imagePath.isBlank()){//isBlank determine whether String have actual content
            return null;                              //isEmpty determine whether the length is 0
        }

        //prevent repeated "/"
        if (publicDomain.endsWith("/")) {
            return publicDomain + imagePath;
        }

        return publicDomain + "/" + imagePath;

    }
}
