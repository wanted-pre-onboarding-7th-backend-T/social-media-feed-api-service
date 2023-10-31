package com.wanted.external.info;

import com.wanted.common.exception.CommonException;
import com.wanted.content.enums.SnsType;
import com.wanted.external.info.impl.FacebookApiInfo;
import com.wanted.external.info.impl.InstagramApiInfo;
import com.wanted.external.info.impl.ThreadApiInfo;
import com.wanted.external.info.impl.TwitterApiInfo;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class SnsApiInfoFactory {

    private final InstagramApiInfo instagramApiInfo;
    private final TwitterApiInfo twitterApiInfo;
    private final FacebookApiInfo facebookApiInfo;
    private final ThreadApiInfo threadApiInfo;

    public SnsApiInfo getSnsApiInfo(SnsType type) {
        if (type == SnsType.INSTAGRAM) {
            return instagramApiInfo;
        }
        if (type == SnsType.TWITTER) {
            return twitterApiInfo;
        }
        if (type == SnsType.FACEBOOK) {
            return facebookApiInfo;
        }
        if (type == SnsType.THREAD) {
            return threadApiInfo;
        }
        throw new CommonException(HttpStatus.BAD_REQUEST, "지원하지 않는 SNS 입니다.");
    }
}
