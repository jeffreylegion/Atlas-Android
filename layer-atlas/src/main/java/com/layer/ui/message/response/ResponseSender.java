package com.layer.ui.message.response;


import android.content.Context;
import android.text.TextUtils;

import com.layer.sdk.LayerClient;
import com.layer.sdk.messaging.Message;
import com.layer.sdk.messaging.MessagePart;
import com.layer.ui.message.messagetypes.MessageSender;
import com.layer.ui.message.status.StatusMessageComposer;

public class ResponseSender extends MessageSender {

    public ResponseSender(Context context, LayerClient layerClient) {
        super(context, layerClient);
    }

    public boolean sendChoiceResponse(ChoiceResponseModel choiceResponse) {
        MessagePart responsePart = new ResponseMessagePartComposer()
                .buildResponseMessagePart(getLayerClient(), choiceResponse);
        Message message;
        if (TextUtils.isEmpty(choiceResponse.getStatusText())) {
            message = getLayerClient().newMessage(responsePart);
        } else {
            MessagePart statusPart = new StatusMessageComposer()
                    .buildStatusMessagePart(getLayerClient(), choiceResponse.getStatusText());
            message = getLayerClient().newMessage(responsePart, statusPart);
        }
        return send(message);
    }
}
