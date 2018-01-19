package com.layer.ui.message.response;


import android.net.Uri;
import android.support.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("WeakerAccess")
public class ChoiceResponseModel extends ResponseModel {
    private String mStatusText;

    public ChoiceResponseModel(@NonNull Uri messageIdToRespondTo,
            @NonNull String nodeIdToRespondTo) {
        super(messageIdToRespondTo, nodeIdToRespondTo, null);
    }

    public String getStatusText() {
        return mStatusText;
    }

    public void setStatusText(String statusText) {
        mStatusText = statusText;
    }

    public void addChoice(@NonNull String responseName, @NonNull String choiceId) {
        Map<Object, Object> participantData = getParticipantData();
        if (participantData == null) {
            participantData = new HashMap<>(1);
            setParticipantData(participantData);
        }
        participantData.put(responseName, choiceId);
    }
}
