package com.layer.ui.message.response;


import android.content.Context;

import com.layer.sdk.LayerClient;
import com.layer.sdk.messaging.Message;
import com.layer.ui.message.messagetypes.MessageSender;
import com.layer.ui.response.ChoiceResponse;

// TODO Need to be able to extend this class and customize the responses
public class ResponseSender extends MessageSender {

    public ResponseSender(Context context, LayerClient layerClient) {
        super(context, layerClient);
    }

    public boolean sendChoiceResponse(ChoiceResponse choiceResponse) {
        Message message = new ResponseMessageComposer().buildResponseMessage(getLayerClient(), choiceResponse);
        return send(message);
    }
}
