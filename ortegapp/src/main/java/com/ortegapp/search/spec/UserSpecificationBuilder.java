package com.ortegapp.search.spec;


import com.ortegapp.model.User;
import com.ortegapp.search.util.SearchCriteria;

import java.util.List;

public class UserSpecificationBuilder extends GenericSpecificationBuilder<User> {
    public UserSpecificationBuilder(List<SearchCriteria> params) {
        super(params, User.class);
    }



}