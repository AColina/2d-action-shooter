using System;
using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using WebSocketSharp;

public class InitController : MonoBehaviour
{
	private static readonly String responseName = "PLAYERLIST";
	public GameObject playerPrefact;

	public GameObject multiPlayerPrefact;
	// Use this for initialization
	void Start ()
	{
		NetworkingController networking = GameMaster.getNetworkingController();
		Instantiate(playerPrefact, new Vector3(-8,-1,0),Quaternion.identity);
		networking.startListenOnMessage(createPlayerServer,responseName);
		networking.sendRequest(new PlayerListRequest());
	}
	
	// Update is called once per frame
	void Update () {
		
	}

	private void createPlayerServer(object sender, MessageEventArgs e)
	{
		Debug.Log("reciviendo respuesta");
		PlayerListResponse response = PlayerListResponse.CreateFromJSON(e.Data);
		
		foreach (var user in response.users)
		{ UnityThread.executeInUpdate(() =>
			{
				GameObject p=Instantiate(multiPlayerPrefact, new Vector3(-8,-1,0),Quaternion.identity);
				MultiPlayerController con=p.GetComponent<MultiPlayerController>();
				con.init(user);
			});
			
		}
		NetworkingController networking = GameMaster.getNetworkingController();
		networking.stopListenOnMessage(createPlayerServer,responseName);
	}

	private void Awake()
	{
		UnityThread.initUnityThread();
	}

	public class PlayerListRequest : ClientRequest {


		public PlayerListRequest() {
			type = "PLAYERLIST";
		}
	}
	public class PlayerListResponse {
		public string type;
		public List<string> users;

		public static PlayerListResponse CreateFromJSON(string jsonString) {
			return JsonUtility.FromJson<PlayerListResponse>(jsonString);
		}
	}
}
