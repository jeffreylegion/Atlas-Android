package com.layer.ui.message.response;


import android.content.Context;
import android.text.TextUtils;

import com.layer.sdk.LayerClient;
import com.layer.sdk.messaging.Message;
import com.layer.sdk.messaging.MessagePart;
import com.layer.ui.message.messagetypes.MessageSender;
import com.layer.ui.message.status.StatusMessageComposer;

import java.util.UUID;

/**
 * Creates and sends message responses for different types of messages
 */
public class ResponseSender extends MessageSender {

    public ResponseSender(Context context, LayerClient layerClient) {
        super(context, layerClient);
    }

    /**
     * Send a response to a choice message. If the {@link ChoiceResponseModel} contains a non-null
     * status text then a status message will be added to the message's parts.
     *
     * @param choiceResponse model for populating the response
     * @return result of {@link MessageSender#send(Message)}
     */
    public boolean sendChoiceResponse(ChoiceResponseModel choiceResponse) {
        LayerClient layerClient = getLayerClient();
        MessagePart responsePart = new ResponseMessagePartComposer()
                .buildResponseMessagePart(layerClient, choiceResponse);
        Message message;
        if (TextUtils.isEmpty(choiceResponse.getStatusText())) {
            message = layerClient.newMessage(responsePart);
        } else {
            UUID rootPartId = UUID.fromString(responsePart.getId().getLastPathSegment());
            MessagePart statusPart = new StatusMessageComposer()
                    .buildStatusMessagePart(layerClient, choiceResponse.getStatusText(), rootPartId);
            message = layerClient.newMessage(responsePart, statusPart);
        }
        return send(message);
    }
}
