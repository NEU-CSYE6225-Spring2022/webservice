package com.neu.csye6225.springdemo.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.neu.csye6225.springdemo.model.ProfilePic;
import com.neu.csye6225.springdemo.model.User;
import com.neu.csye6225.springdemo.repository.ProfilePicRepository;
import com.neu.csye6225.springdemo.repository.UserRepository;
import com.neu.csye6225.springdemo.service.UserProfilePicService;
import com.sun.xml.bind.v2.TODO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Service("UserProfilePicServiceImpl")
public class UserProfilePicServiceImpl implements UserProfilePicService {

    private static final Logger logger = LogManager.getLogger(UserProfilePicServiceImpl.class);

    private ProfilePicRepository profilePicRepository;

    private UserRepository userRepository;

    private AmazonS3 amazonS3;

//    @Value("${aws.s3.bucket}")
    @Value("${bucketName}")
    private String bucketName;

    public UserProfilePicServiceImpl(ProfilePicRepository profilePicRepository, UserRepository userRepository, AmazonS3 amazonS3) {
        this.profilePicRepository = profilePicRepository;
        this.userRepository = userRepository;
        this.amazonS3 = amazonS3;
    }

    @Override
    public ProfilePic getUserProfilePic() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByUsername(username);
        ProfilePic profilePic = profilePicRepository.findByUser_id(user.getId());
        if(profilePic==null) {

            logger.info("Found no profile pic for the User: "+ user.getUsername());
            profilePic = new ProfilePic(null, user.getId(), null, user.getId());
            profilePic.setUpload_date(null);
        }
        return profilePic;
    }

    @Override
    public void deleteProfilePic() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByUsername(username);
        ProfilePic profilePic = profilePicRepository.findByUser_id(user.getId());
        if(profilePic!=null) {
            logger.info("Found profile pic for the User: "+ user.getUsername());
            deleteExistingProfilePic(profilePic);
        }
        profilePicRepository.deleteByUser_id(user.getId());
    }

    @Override
    public ProfilePic uploadUserProfilePic(InputStream inputStream, String fileName) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByUsername(username);
        ProfilePic profilePic = profilePicRepository.findByUser_id(user.getId());
        if(profilePic==null) {

            logger.info("Found no profile pic for the User: "+ user.getUsername() + ". So creating a new One");
            profilePic = new ProfilePic(fileName, user.getId(), bucketName+"/"+saveAndGetS3Path(inputStream, fileName, user) , user.getId());
            profilePic = profilePicRepository.save(profilePic);
            return profilePic;
        }
        deleteExistingProfilePic(profilePic);
        //Updating existing database entry
        profilePic.setFile_name(fileName);
        profilePic.setUrl(bucketName+"/"+saveAndGetS3Path(inputStream, fileName,user));
        profilePic = profilePicRepository.save(profilePic);
        return profilePic;
    }

    private String saveAndGetS3Path(InputStream inputStream, String fileName, User user) {

        logger.info("Saving profile pic to S3 for the User: "+ user.getUsername());
        Map<String, String> map = new HashMap<>();
        map.put("user_id", user.getId());
        map.put("fileName", fileName);
        String key = user.getId()+"/"+fileName;
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setUserMetadata(map);
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, inputStream, objectMetadata);
        amazonS3.putObject(putObjectRequest);
        return key;
    }

    private void deleteExistingProfilePic(ProfilePic profilePic) {

        logger.info("Deleting profile pic for the User: "+ profilePic.getUser_id());
        String path = profilePic.getUrl();
        if(path==null || path.isEmpty() ) {
            logger.info(" No pic is stored in S3 ");
            return;
        }
        if(amazonS3.doesObjectExist(bucketName, path.substring(1+bucketName.length()))){
            amazonS3.deleteObject(bucketName, path.substring(1+bucketName.length()));
        }
    }
}
