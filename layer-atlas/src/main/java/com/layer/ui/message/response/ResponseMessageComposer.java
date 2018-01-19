package com.layer.ui.message.response;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.layer.sdk.LayerClient;
import com.layer.sdk.messaging.Message;
import com.layer.sdk.messaging.MessagePart;
import com.layer.ui.message.MessageItemStatusViewModel;
import com.layer.ui.message.MessagePartUtils;
import com.layer.ui.message.status.StatusMetadata;
import com.layer.ui.response.ChoiceResponse;
import com.layer.ui.util.json.AndroidFieldNamingStrategy;

import java.util.HashMap;
import java.util.Map;

// TODO Move to response package?
public class ResponseMessageComposer {

    public Message buildResponseMessage(LayerClient layerClient, ChoiceResponse choiceResponse) {
        Gson gson = new GsonBuilder().setFieldNamingStrategy(new AndroidFieldNamingStrategy()).create();

        MessagePart rootPart = createRootPart(layerClient, choiceResponse, gson);
        if (choiceResponse.getStatusText() != null) {
            MessagePart statusPart = createStatusPart(layerClient, gson, choiceResponse.getStatusText());
            return layerClient.newMessage(rootPart, statusPart);
        } else {
            return layerClient.newMessage(rootPart);
        }
    }

    // TODO Move this out into a status composer
    private MessagePart createStatusPart(LayerClient layerClient, Gson gson, String status) {
        String statusMimeType = MessagePartUtils.getAsRoleWithParentId(
                MessageItemStatusViewModel.STATUS_ROOT_MIME_TYPE, "status", null, "root");
        StatusMetadata statusMetadata = new StatusMetadata();
        statusMetadata.setText(status);
        return layerClient.newMessagePart(statusMimeType, gson.toJson(statusMetadata).getBytes());
    }

    private MessagePart createRootPart(LayerClient layerClient, ChoiceResponse choiceResponse, Gson gson) {
        String rootMimeTpe = MessagePartUtils.getAsRoleRoot(
                MessageItemStatusViewModel.RESPONSE_ROOT_MIME_TYPE);

        ResponseMetadata responseMetadata = createResponseMetadata(choiceResponse);
        return layerClient.newMessagePart(rootMimeTpe, gson.toJson(responseMetadata).getBytes());
    }

    private ResponseMetadata createResponseMetadata(ChoiceResponse choiceResponse) {
        ResponseMetadata responseMetadata = new ResponseMetadata();
        responseMetadata.setMessageIdToRespondTo(choiceResponse.getMessageIdToRespondTo().toString());
        responseMetadata.setNodeIdToRespondTo(choiceResponse.getNodeIdToRespondTo());
        Map<Object, Object> participantData = new HashMap<>(1);
        participantData.put(choiceResponse.getResponseName(), choiceResponse.getChoiceId());

        responseMetadata.setParticipantData(participantData);
        return responseMetadata;
    }
}
