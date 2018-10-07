using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class PlayerController : MonoBehaviour {
    // velocidad base 
    public float speed = 25f;
    public float jumpDistance = 6.5f;
    public float jumpTime = 0.2f;
    public float jumpTimeremaining;
    public float maxSpeed = 5f;
    public bool isJumping = false;
    private Rigidbody2D rigidBodyPlayer;
    private Animator animator;
    private bool inGround;

    // Use this for initialization
    void Start () {

        rigidBodyPlayer = GetComponent<Rigidbody2D>();
        animator = GetComponent<Animator>();
        jumpTimeremaining = jumpTime;

	}

    // Update is called once per frame
    void Update() {
        animator.SetFloat("Speed", Mathf.Abs(rigidBodyPlayer.velocity.x));
        animator.SetBool("InGround", inGround);

        if (inGround)
        {
            jumpTimeremaining = jumpTime;
        }

        Jump();
        float h = Input.GetAxis("Horizontal");

    }

    void FixedUpdate()
    {
        //print(rigidBodyPlayer.velocity);
        // con esto obtenemos el valor en float en donde se esta presionando en horizontal izq = -1 der = 1 ninguno = 0
        float h = Input.GetAxis("Horizontal");

        // params 
        // ** Vector2.right = posicion a la que se hace fuerza con un vector (xValue,yValue) (1.0,0.0) = derecha, (-1.0,0.0) = izq
        rigidBodyPlayer.AddForce(Vector2.right * speed * h);
        if (h == 0)
        {
            rigidBodyPlayer.velocity = new Vector2(0, rigidBodyPlayer.velocity.y);
        }
        //rigidBodyPlayer.AddForce(Vector2.up * jumpSpeed * v);


        // flip de sprite al cambiar de posicion
        if (h < -0.1f || h > 0.1f) {
            transform.localScale = h > 0.1f ?  new Vector3(1f,1f,1f) : new Vector3(-1f, 1f, 1f);
        }




        // funcion para matener un valor (primer parametro) entre un rango numerico
        float limitedSpeed = Mathf.Clamp(rigidBodyPlayer.velocity.x, -maxSpeed, maxSpeed);

            rigidBodyPlayer.velocity = new Vector2(limitedSpeed, rigidBodyPlayer.velocity.y);

    }

    public bool InGround
    {
        get
        {
            return inGround;
        }

        set
        {
            inGround = value;
        }
    }
    

    // Salto progresivo BETA 
    // si los bug de colisiones de unity no me lo hacen cambiar otravez :v

    // esto hay que ejecutarlo en el update porque los Input.GetKey al parecer trabajan correctamente con el framerate
    private void Jump()
    {

        if(Input.GetKeyDown(KeyCode.UpArrow) && InGround)
        {
            rigidBodyPlayer.velocity = new Vector2(rigidBodyPlayer.velocity.x, 0f);
            rigidBodyPlayer.velocity = new Vector2(rigidBodyPlayer.velocity.x, jumpDistance);
            isJumping = true;
        }

        if (Input.GetKey(KeyCode.UpArrow) && isJumping)
        {
           if(jumpTimeremaining > 0)
            {
                rigidBodyPlayer.velocity = new Vector2(rigidBodyPlayer.velocity.x, jumpDistance);
                jumpTimeremaining -= Time.deltaTime;
            }
            else
            {
                isJumping = false;
            }
            
        }
        if(Input.GetKeyUp(KeyCode.UpArrow) && isJumping)
        {
            jumpTimeremaining = 0;
            isJumping = false;
        }

    }

   
    /// <summary>
    /// jaweno un summary a buen autocompletar de visual studio prrrr
    /// esto es para por si el personaje se sale de la camara reaparesca
    /// solo deberia servir para pruebas :3
    /// </summary>
    private void OnBecameInvisible()
    {
        transform.position = new Vector3(0f, 0f, 0f);
    }


}
