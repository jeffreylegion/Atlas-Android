package com.layer.ui.message.choice;

import android.content.Context;
import android.databinding.Bindable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.layer.sdk.LayerClient;
import com.layer.sdk.messaging.MessagePart;
import com.layer.ui.BR;
import com.layer.ui.R;
import com.layer.ui.message.model.MessageModel;
import com.layer.ui.repository.MessageSenderRepository;
import com.layer.ui.message.response.ChoiceResponseModel;
import com.layer.ui.util.Log;
import com.layer.ui.util.json.AndroidFieldNamingStrategy;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ChoiceMessageModel extends MessageModel {
    public static final String MIME_TYPE = "application/vnd.layer.choice+json";

    private ChoiceMessageMetadata mMetadata;
    private ResponseSummary mResponseSummary;
    private List<String> mSelectedChoices;
    private Gson mGson;

    public ChoiceMessageModel(Context context, LayerClient layerClient) {
        super(context, layerClient);
        mGson = new GsonBuilder().setFieldNamingStrategy(new AndroidFieldNamingStrategy()).create();
        mSelectedChoices = new ArrayList<>();
    }

    @Override
    public Class<ChoiceMessageView> getRendererType() {
        return ChoiceMessageView.class;
    }

    @Override
    protected void parse(@NonNull MessagePart messagePart) {
        if (messagePart.equals(getRootMessagePart())) {
            JsonReader reader = new JsonReader(new InputStreamReader(messagePart.getDataStream()));
            mMetadata = mGson.fromJson(reader, ChoiceMessageMetadata.class);
            processSelections();
            notifyPropertyChanged(BR.choiceMessageMetadata);
        }
    }

    @Override
    protected void processResponseSummaryPart(@NonNull MessagePart responseSummaryPart) {
        JsonReader reader = new JsonReader(new InputStreamReader(responseSummaryPart.getDataStream()));
        mResponseSummary = mGson.fromJson(reader, ResponseSummary.class);
        processSelections();
        notifyPropertyChanged(BR.selectedChoices);
    }

    @Override
    protected boolean shouldDownloadContentIfNotReady(@NonNull MessagePart messagePart) {
        return true;
    }

    @Nullable
    @Override
    public String getTitle() {
        String title = getContext().getString(R.string.choice_message_model_default_title);
        if (mMetadata != null && mMetadata.getTitle() != null) {
            title = mMetadata.getTitle();
        }

        return title;
    }

    @Nullable
    @Override
    public String getDescription() {
        return null;
    }

    @Nullable
    @Override
    public String getFooter() {
        return null;
    }

    @Override
    public String getActionEvent() {
        return null;
    }

    @Override
    public int getBackgroundColor() {
        return R.color.transparent;
    }

    @Override
    public boolean getHasContent() {
        return mMetadata != null;
    }

    @Nullable
    @Bindable
    public ChoiceMessageMetadata getChoiceMessageMetadata() {
        return mMetadata;
    }

    @Bindable
    public List<String> getSelectedChoices() {
        return mSelectedChoices;
    }

    @Bindable
    public boolean getIsEnabledForMe() {
        if (getLayerClient().getAuthenticatedUser() == null || mMetadata == null)
            return false;
        String myUserID = getLayerClient().getAuthenticatedUser().getId().toString();
        if (mMetadata.getEnabledFor() != null) {
            return mMetadata.getEnabledFor().contains(myUserID);
        }

        return true;
    }

    private void processSelections() {
        mSelectedChoices.clear();
        if (mResponseSummary != null && mResponseSummary.hasData()) {
            for (Map.Entry<String, JsonObject> participantResponses : mResponseSummary.getParticipantData().entrySet()) {
                if (participantResponses.getValue().has(mMetadata.getResponseName())) {
                    String[] ids = participantResponses.getValue().get(mMetadata.getResponseName()).getAsString().split(",");
                    mSelectedChoices.addAll(Arrays.asList(ids));
                }
            }
        } else if (mMetadata.getPreselectedChoice() != null) {
            mSelectedChoices.add(mMetadata.getPreselectedChoice());
        }
    }

    void sendResponse(@NonNull ChoiceMetadata choice) {
        String userName = getIdentityFormatter().getDisplayName(getLayerClient().getAuthenticatedUser());
        String statusText;
        if (mMetadata.getLabel() == null) {
            statusText = getContext().getString(R.string.response_message_status_text,
                    userName, choice.getText());
        } else {
            statusText = getContext().getString(R.string.response_message_status_text_with_label,
                    userName, choice.getText(), mMetadata.getLabel());
        }

        if (getRootNodeId() == null) {
            if (Log.isLoggable(Log.ERROR)) {
                Log.e("Choice message does not have a node ID, cannot process response. "
                        + "Message ID: " + getMessage().getId());
            }
            throw new IllegalStateException("Choice message does not have a node ID, cannot "
                    + "process response. Message ID: " + getMessage().getId());
        }

        ChoiceResponseModel choiceResponseModel = new ChoiceResponseModel(getMessage().getId(), getRootNodeId());
        choiceResponseModel.setStatusText(statusText);
        choiceResponseModel.addChoice(mMetadata.getResponseName(), choice.getId());

        MessageSenderRepository messageSenderRepository = getMessageSenderRepository();
        messageSenderRepository.sendChoiceResponse(getMessage().getConversation(), choiceResponseModel);
    }

    public static class ResponseSummary {
        @SerializedName("participant_data")
        private Map<String, JsonObject> mParticipantData;

        public boolean hasData() {
            return mParticipantData != null && !mParticipantData.isEmpty();
        }

        public Map<String, JsonObject> getParticipantData() {
            return mParticipantData;
        }
    }
}
