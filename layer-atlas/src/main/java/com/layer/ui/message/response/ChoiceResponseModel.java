package com.layer.ui.message.response;


import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * Model class for creating a choice response message.
 */
@SuppressWarnings("WeakerAccess")
public class ChoiceResponseModel extends ResponseModel {
    private String mStatusText;

    public ChoiceResponseModel(@NonNull Uri messageIdToRespondTo,
            @NonNull String nodeIdToRespondTo) {
        super(messageIdToRespondTo, nodeIdToRespondTo, null);
    }

    /**
     * @return text to be displayed in the status message, null if not set
     */
    @Nullable
    public String getStatusText() {
        return mStatusText;
    }

    /**
     * Sets text that will be displayed in a status message.
     *
     * @param statusText text to display in the status message
     */
    public void setStatusText(@Nullable String statusText) {
        mStatusText = statusText;
    }

    /**
     * Add a choice to the participant data map
     *
     * @param responseName key for the map entry
     * @param choiceId value for the map entry
     */
    public void addChoice(@NonNull String responseName, @NonNull String choiceId) {
        Map<Object, Object> participantData = getParticipantData();
        if (participantData == null) {
            participantData = new HashMap<>(1);
            setParticipantData(participantData);
        }
        participantData.put(responseName, choiceId);
    }
}
