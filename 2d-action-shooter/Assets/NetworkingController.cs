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

using System;
using System.Collections.Generic;
using UnityEngine;
using WebSocketSharp;

/**
 * @author L0G1C (David B) <a
 *         href=https://github.com/Binary-L0G1C/java-unity-websocket-connector>
 *         https://github.com/Binary-L0G1C/java-unity-websocket-connector </a>
 */
public class NetworkingController {

	private WebSocket _websocket;
	private Dictionary<String, HashSet<EventHandler<MessageEventArgs>>> onMessageFunctions = new Dictionary<String, HashSet<EventHandler<MessageEventArgs>>>();

	public NetworkingController() {
		Debug.Log("Networking class initiated!");
		//connectManually();
	}

	public void connect(string ip, string user) {
		Debug.Log("Arg ip: " + ip);
		Debug.Log("Arg user: " + user);

		if (_websocket != null && _websocket.IsAlive) {
			Debug.LogWarning("Websocket is already connected!");
			return;
		}

		Debug.Log("Creating websocket");
		string serverAddress = "ws://" + ip + ":8080";
		_websocket = new WebSocket(serverAddress + "/unitysocket");
		_websocket.Log.Level = LogLevel.Trace;
		_websocket.Log.File = "D:\\somelog";

		_websocket.SetCredentials(user, null, false);

		_websocket.OnMessage += (sender, e) => {
			Debug.Log("Server says: " + e.Data);

			String responseType = ServerResponse.CreateFromJSON(e.Data).type;
			if (onMessageFunctions.ContainsKey(responseType)) {
				foreach (EventHandler<MessageEventArgs> eventHandler in onMessageFunctions[responseType]) {
					eventHandler.BeginInvoke(sender, e, EndAsyncEvent, null);
				}
			}
		};

		_websocket.OnOpen += (sender, e) => {
			Debug.Log("Socket Open");
		};

		_websocket.OnError += (sender, e) => {
			Debug.Log("Error " + e.Message);
			Debug.Log("Exception " + e.Exception);
		};

		_websocket.OnClose += (sender, e) => {
			Debug.Log("Close Reason: " + e.Reason);
			Debug.Log("Close Code: " + e.Code);
			Debug.Log("Close Clean? " + e.WasClean);
		};

		Debug.Log("Connecting...");
		_websocket.Connect();
		//ws.ConnectAsync();
		Debug.Log("Connection isAlive : " + _websocket.IsAlive);
		Debug.Log("Connection Status : " + _websocket.ReadyState);
	}


	public void startListenOnMessage(EventHandler<MessageEventArgs> funct, String responseType) {
		if (!onMessageFunctions.ContainsKey(responseType)) {
			onMessageFunctions[responseType] = new HashSet<EventHandler<MessageEventArgs>>();
		}
		onMessageFunctions[responseType].Add(funct);
	}

	public void stopListenOnMessage(EventHandler<MessageEventArgs> funct, String responseType) {
		if (onMessageFunctions.ContainsKey(responseType)) {
			onMessageFunctions[responseType].Remove(funct);
		}
	}

	public bool isConnected() {
		return _websocket.IsAlive;
	}

	public void sendRequest(ClientRequest request) {
		String json = JsonUtility.ToJson(request);

		if (_websocket != null) {
			Debug.Log("Sending request of type " + request.type + ": " + json);
			_websocket.Send(json);
		}
	}

	/**
	 * Helper method to just connect to the server manually
	 */
	private void connectManually() {
		Debug.Log("Connecting Manually...");
		connect("127.0.0.1", "CatgirlLover");
		Debug.Log("Finished Manual Connection.");
	}

	// TODO: can this be handled better?
	private void EndAsyncEvent(IAsyncResult iar) {
		var ar = (System.Runtime.Remoting.Messaging.AsyncResult)iar;
		var invokedMethod = (EventHandler)ar.AsyncDelegate;

		try {
			invokedMethod.EndInvoke(iar);
		} catch {
			// Handle any exceptions that were thrown by the invoked method
			Debug.Log("An event listener went kaboom!");
		}
	}

	public class ServerResponse {
		public string type;

		public static ServerResponse CreateFromJSON(String jsonString) {
			return JsonUtility.FromJson<ServerResponse>(jsonString);
		}
	}
}
