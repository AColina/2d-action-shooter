using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class CheckGround : MonoBehaviour {

    private PlayerController playerController;
	// Use this for initialization
	void Start () {
        playerController = GetComponentInParent<PlayerController>();
	}


    void OnTriggerEnter2D(Collider2D collider)
    {
        Debug.Log(collider.gameObject.name);
        playerController.InGround = collider.gameObject.tag == "Ground";
    }

    void OnTriggerExit2D(Collider2D collider)
    {
        playerController.InGround = !(collider.gameObject.tag == "Ground");
    }

    void OnTriggerStay2D(Collider2D collider)
    {
        playerController.InGround = collider.gameObject.tag == "Ground";
    }

    //private void OnCollisionStay2D(Collision2D collision)
    //{
    //    playerController.InGround = collision.gameObject.tag == "Ground";
    //}
}
