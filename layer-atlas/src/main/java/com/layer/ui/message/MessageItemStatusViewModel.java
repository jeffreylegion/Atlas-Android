package com.layer.ui.message;


import android.content.Context;
import android.databinding.Bindable;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.layer.sdk.messaging.Message;
import com.layer.sdk.messaging.MessagePart;
import com.layer.ui.viewmodel.ItemViewModel;

public class MessageItemStatusViewModel extends ItemViewModel<Message> {
    public static final String STATUS_ROOT_MIME_TYPE = "application/vnd.layer.status+json";
    public static final String RESPONSE_ROOT_MIME_TYPE = "application/vnd.layer.response+json";
    private static final String RESPONSE_TEXT_MIME_TYPE = "application/vnd.layer.text+json";

    private CharSequence mText;
    private boolean mVisible;
    private final JsonParser mJsonParser;

    public MessageItemStatusViewModel(Context context) {
        super(context, null);
        mJsonParser = new JsonParser();
    }

    public void update() {
        Message message = getItem();
        MessagePart statusPart = null;
        MessagePart responseTextPart = null;
        for (MessagePart part : message.getMessageParts()) {
            String mimeType = MessagePartUtils.getMimeType(part);
            if (mimeType == null) {
                continue;
            }
            switch (mimeType) {
                case STATUS_ROOT_MIME_TYPE:
                    statusPart = part;
                    break;
                case RESPONSE_TEXT_MIME_TYPE:
                    responseTextPart = part;
                    break;
            }
        }

        if (statusPart != null) {
            mVisible = true;
            // TODO Handle with AND-1114
        } else if (responseTextPart != null) {
            mVisible = true;
            String data = new String(responseTextPart.getData());
            JsonObject jsonObject = mJsonParser.parse(data).getAsJsonObject();
            mText = jsonObject.has("text") ? jsonObject.get("text").getAsString() : null;
        } else {
            mVisible = false;
        }

    }

    @Bindable
    public CharSequence getText() {
        return mText;
    }

    @Bindable
    public boolean isVisible() {
        return mVisible;
    }
}
