package com.nicat.socailmedia.util;

import com.nicat.socailmedia.dto.response.ApiMessage;

import java.time.LocalDateTime;

public interface ApiBuilder {

    default ApiMessage generateOkay(){
        ApiMessage apiMessage = new ApiMessage();
        apiMessage.setDate(LocalDateTime.now());
        apiMessage.setMessage("API call finished successfully");
        return apiMessage;
    }

    default ApiMessage generateNoContent(){
        ApiMessage apiMessage = new ApiMessage();
        apiMessage.setDate(LocalDateTime.now());
        apiMessage.setMessage("Resource deleted successfully");
        return apiMessage;
    }

    default ApiMessage generateAccepted(){
        ApiMessage apiMessage = new ApiMessage();
        apiMessage.setDate(LocalDateTime.now());
        apiMessage.setMessage("Changes accepted successfully");
        return apiMessage;
    }
}
