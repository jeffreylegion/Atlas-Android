package com.layer.ui.message.response;


import android.net.Uri;
import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

public class ResponseMetadata {

    @SerializedName("response_to")
    private Uri mMessageIdInResponseTo;

    @SerializedName("response_to_node_id")
    private String mNodeIdInResponseTo;

    @SerializedName("participant_data")
    private ParticipantData mParticipantData;

    @Nullable
    public Uri getMessageIdInResponseTo() {
        return mMessageIdInResponseTo;
    }

    public void setMessageIdInResponseTo(@Nullable Uri messageIdInResponseTo) {
        mMessageIdInResponseTo = messageIdInResponseTo;
    }

    @Nullable
    public String getNodeIdInResponseTo() {
        return mNodeIdInResponseTo;
    }

    public void setNodeIdInResponseTo(@Nullable String nodeIdInResponseTo) {
        mNodeIdInResponseTo = nodeIdInResponseTo;
    }

    @Nullable
    public ParticipantData getParticipantData() {
        return mParticipantData;
    }

    public void setParticipantData(@Nullable ParticipantData participantData) {
        mParticipantData = participantData;
    }

    public static class ParticipantData {

        @SerializedName("my_selection")
        private String mSelection;

        @Nullable
        public String getSelection() {
            return mSelection;
        }

        public void setSelection(@Nullable String selection) {
            mSelection = selection;
        }
    }
}
