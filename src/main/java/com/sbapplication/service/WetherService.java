package com.sbapplication.service;

import com.sbapplication.api.response.WetherResponse;

public interface WetherService {


    public WetherResponse getWether(String city);
}
