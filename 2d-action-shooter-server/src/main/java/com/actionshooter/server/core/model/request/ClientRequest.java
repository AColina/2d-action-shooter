/*
 * Copyright 2017 L0G1C (David B) - logiclodge.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.actionshooter.server.core.model.request;

import com.actionshooter.server.model.request.PlayerListRequest;
import com.actionshooter.server.model.request.PlayerMoveRequest;
import com.actionshooter.server.model.request.PlayerNameRequest;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * @author Angel Colina
 * @version 1.0
 */
@JsonTypeInfo( //
        use = JsonTypeInfo.Id.NAME, //
        include = JsonTypeInfo.As.PROPERTY, //
        property = "type", //
        // defaultImpl = Event.class, //
        visible = true)
@JsonSubTypes({ //
        @JsonSubTypes.Type(value = PlayerMoveRequest.class, name = "PLAYERMOVE"), //
        @JsonSubTypes.Type(value = PlayerNameRequest.class, name = "PLAYERNAME"), //
        @JsonSubTypes.Type(value = PlayerListRequest.class, name = "PLAYERLIST"), //
})
public abstract class ClientRequest {

    private RequestType type;

    public RequestType getType() {
        return type;
    }

    public void setType(RequestType type) {
        this.type = type;
    }
}