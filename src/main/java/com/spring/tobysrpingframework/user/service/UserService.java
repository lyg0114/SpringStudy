package com.spring.tobysrpingframework.user.service;

import com.spring.tobysrpingframework.user.domain.User;

public interface UserService {

    void add(User user);
    void upgradeLevels();

}
