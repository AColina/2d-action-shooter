using System;
using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using WebSocketSharp;

public class MultiPlayerController : MonoBehaviour {
	
	private static readonly String responseName = "Move";
	private string _playerName;
	
	// Use this for initialization
	void Start () {
	
	}

	public void init(string playerName)
	{
		Debug.Log("int : "+_playerName);
		_playerName = playerName;
		NetworkingController networking = GameMaster.getNetworkingController();

		networking.startListenOnMessage(movePlayer,_playerName+ responseName);
		//networking.requestFriendsList();
		//requestFriendsList(networking);
	}
	// Update is called once per frame
	void Update () {
		
	}

	private void OnDestroy()
	{
		NetworkingController networking = GameMaster.getNetworkingController();
		networking.stopListenOnMessage(movePlayer, _playerName+ responseName);
	}

	private void movePlayer(object sender, MessageEventArgs e) {
		
	}
	
	public class MoveOtherPlayerResponse {
		public String type;
		

		public static MoveOtherPlayerResponse CreateFromJSON(string jsonString) {
			return JsonUtility.FromJson<MoveOtherPlayerResponse>(jsonString);
		}
	}
}
