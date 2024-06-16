package com.moayo.moayoeats.backend.domain.post.service.impl;

import com.moayo.moayoeats.backend.domain.post.dto.request.PostRequest;
import com.moayo.moayoeats.backend.domain.post.entity.CategoryEnum;
import com.moayo.moayoeats.backend.domain.post.entity.Post;
import com.moayo.moayoeats.backend.domain.post.entity.PostStatusEnum;
import com.moayo.moayoeats.backend.domain.post.repository.PostRepository;
import com.moayo.moayoeats.backend.domain.post.service.PostCreateService;
import com.moayo.moayoeats.backend.domain.user.entity.User;
import com.moayo.moayoeats.backend.domain.userpost.entity.UserPost;
import com.moayo.moayoeats.backend.domain.userpost.entity.UserPostRole;
import com.moayo.moayoeats.backend.domain.userpost.repository.UserPostRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PostCreateServiceImpl implements PostCreateService {

    private final PostRepository postRepository;
    private final UserPostRepository userPostRepository;

    @Override
    public void createPost(PostRequest postReq, User user) {
        //set deadline to hours and mins after now
        LocalDateTime deadline = getDeadline(postReq);

        //get latitude and longitude from the coordinate
        Double [] location = getAddress(postReq.address());

        Post post = Post.builder()
                .latitude(location[0])
                .longitude(location[1])
                .store(postReq.store())
                .deliveryCost(getIntFromString(postReq.deliveryCost()))
                .minPrice(getIntFromString(postReq.minPrice()))
                .deadline(deadline)
                .cuisine(postReq.category())
                .postStatus(PostStatusEnum.OPEN)
                .build();

        //save the post
        postRepository.save(post);

        //Build new relation between the post and the user
        UserPost userpost = UserPost.builder().user(user).post(post).role(UserPostRole.HOST)
            .build();

        //save the relation
        userPostRepository.save(userpost);
    }

    private int getIntFromString(String s) {
        int i = 0;
        if (!s.equals("")) {
            i = Integer.parseInt(s);
        }
        return i;
    }

    private Double[] getAddress(String address) {
        //remove useless parts
        address =  address.replaceAll("[^0-9.,]", "");
        String [] location =  address.split(",");

        //make longitude and latitude into Double
        Double [] result = {Double.valueOf(location[0]),Double.valueOf(location[1])};
        return result;
    }

    private LocalDateTime getDeadline(PostRequest postReq){
        return LocalDateTime.now()
            .plusMinutes(getIntFromString(postReq.deadlineMins()))
            .plusHours(getIntFromString(postReq.deadlineHours()));
    }
}
