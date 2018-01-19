package com.layer.ui.message.response;


import android.net.Uri;
import android.support.annotation.Nullable;

import java.util.Map;

@SuppressWarnings({"unused", "WeakerAccess"})
public class ResponseModel {

    private Uri mMessageIdToRespondTo;
    private String mNodeIdToRespondTo;
    private Map<Object, Object> mParticipantData;


    public ResponseModel(@Nullable Uri messageIdToRespondTo, @Nullable String nodeIdToRespondTo,
            @Nullable Map<Object, Object> participantData) {
        mMessageIdToRespondTo = messageIdToRespondTo;
        mNodeIdToRespondTo = nodeIdToRespondTo;
        mParticipantData = participantData;
    }

    public Uri getMessageIdToRespondTo() {
        return mMessageIdToRespondTo;
    }

    public void setMessageIdToRespondTo(Uri messageIdToRespondTo) {
        mMessageIdToRespondTo = messageIdToRespondTo;
    }

    public String getNodeIdToRespondTo() {
        return mNodeIdToRespondTo;
    }

    public void setNodeIdToRespondTo(String nodeIdToRespondTo) {
        mNodeIdToRespondTo = nodeIdToRespondTo;
    }

    public Map<Object, Object> getParticipantData() {
        return mParticipantData;
    }

    public void setParticipantData(Map<Object, Object> participantData) {
        mParticipantData = participantData;
    }
}
