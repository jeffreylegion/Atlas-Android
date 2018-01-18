package com.layer.ui.message.response;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.layer.sdk.LayerClient;
import com.layer.sdk.messaging.Message;
import com.layer.sdk.messaging.MessagePart;
import com.layer.ui.message.MessageItemStatusViewModel;
import com.layer.ui.message.MessagePartUtils;
import com.layer.ui.message.status.StatusMetadata;
import com.layer.ui.util.json.AndroidFieldNamingStrategy;

public class ResponseMessageComposer {

    public Message buildResponseMessage(LayerClient layerClient,
            Message messageToRespondTo, String nodeIdToRespondTo, String choice, String status) {
        Gson gson = new GsonBuilder().setFieldNamingStrategy(new AndroidFieldNamingStrategy()).create();

        MessagePart rootPart = createRootPart(layerClient, messageToRespondTo, nodeIdToRespondTo,
                choice, gson);
        MessagePart statusPart = createStatusPart(layerClient, gson, status);

        return layerClient.newMessage(rootPart, statusPart);
    }

    private MessagePart createStatusPart(LayerClient layerClient, Gson gson, String status) {
        String statusMimeType = MessagePartUtils.getAsRoleWithParentId(
                MessageItemStatusViewModel.STATUS_ROOT_MIME_TYPE, "status", null, "root");
        StatusMetadata statusMetadata = new StatusMetadata();
        statusMetadata.setText(status);
        return layerClient.newMessagePart(statusMimeType, gson.toJson(statusMetadata).getBytes());
    }

    private MessagePart createRootPart(LayerClient layerClient, Message messageToRespondTo,
            String nodeIdToRespondTo, String choice, Gson gson) {
        String rootMimeTpe = MessagePartUtils.getAsRoleRoot(
                MessageItemStatusViewModel.RESPONSE_ROOT_MIME_TYPE);

        ResponseMetadata responseMetadata = createResponseMetadata(messageToRespondTo,
                nodeIdToRespondTo, choice);
        return layerClient.newMessagePart(rootMimeTpe, gson.toJson(responseMetadata).getBytes());
    }

    private ResponseMetadata createResponseMetadata(Message messageToRespondTo,
            String nodeIdToRespondTo, String choice) {
        ResponseMetadata responseMetadata = new ResponseMetadata();
        responseMetadata.setMessageIdInResponseTo(messageToRespondTo.getId());
        responseMetadata.setNodeIdInResponseTo(nodeIdToRespondTo);
        ResponseMetadata.ParticipantData participantData = new ResponseMetadata.ParticipantData();
        participantData.setSelection(choice);
        responseMetadata.setParticipantData(participantData);
        return responseMetadata;
    }
}
