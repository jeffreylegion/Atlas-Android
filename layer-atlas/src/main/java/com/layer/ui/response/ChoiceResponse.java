package com.layer.ui.response;


import android.net.Uri;
import android.support.annotation.NonNull;

public interface ChoiceResponse {

    Uri getMessageIdToRespondTo();

    String getNodeIdToRespondTo();

    String getResponseName();

    String getChoiceId();

    String getChoiceText();

    class ChoiceResponseBuilder {
        private Uri mMessageIdToRespondTo;
        private String mNodeIdToRespondTo;
        private String mResponseName;
        private String mChoiceId;
        private String mChoiceText;

        @NonNull
        public ChoiceResponseBuilder setMessageIdToRespondTo(Uri messageIdToRespondTo) {
            mMessageIdToRespondTo = messageIdToRespondTo;
            return this;
        }

        public ChoiceResponseBuilder setNodeIdToRespondTo(String nodeIdToRespondTo) {
            mNodeIdToRespondTo = nodeIdToRespondTo;
            return this;
        }

        public ChoiceResponseBuilder setResponseName(String responseName) {
            mResponseName = responseName;
            return this;
        }

        public ChoiceResponseBuilder setChoiceId(String choiceId) {
            mChoiceId = choiceId;
            return this;
        }

        public ChoiceResponseBuilder setChoiceText(String choiceText) {
            mChoiceText = choiceText;
            return this;
        }

        public ChoiceResponse build() {

            // TODO Validation

            return new ChoiceResponse() {
                @Override
                public Uri getMessageIdToRespondTo() {
                    return mMessageIdToRespondTo;
                }

                @Override
                public String getNodeIdToRespondTo() {
                    return mNodeIdToRespondTo;
                }

                @Override
                public String getResponseName() {
                    return mResponseName;
                }

                @Override
                public String getChoiceId() {
                    return mChoiceId;
                }

                @Override
                public String getChoiceText() {
                    return mChoiceText;
                }
            };
        }
    }
}
