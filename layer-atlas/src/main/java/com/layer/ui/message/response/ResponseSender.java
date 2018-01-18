package com.layer.ui.message.response;


import android.content.Context;

import com.layer.sdk.LayerClient;
import com.layer.sdk.messaging.Message;
import com.layer.ui.message.messagetypes.MessageSender;

public class ResponseSender extends MessageSender {

    private ResponseMessageComposer mComposer = new ResponseMessageComposer();

    public ResponseSender(Context context, LayerClient layerClient) {
        super(context, layerClient);
    }

    public boolean requestSend(Message messageToRespondTo, String nodeIdToRespondTo, String choice, String status) {
        Message message = mComposer.buildResponseMessage(getLayerClient(), messageToRespondTo,
                nodeIdToRespondTo, choice, status);
        return send(message);
    }
}
