package com.geeks.noteApp.auth

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.geeks.noteApp.R
import com.geeks.noteApp.databinding.FragmentAuthBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthCredential
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth

class AuthFragment : Fragment() {

    private lateinit var binding: FragmentAuthBinding

    private lateinit var auth: FirebaseAuth
    private lateinit var googleClient: GoogleSignInClient

    private val signInLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){result->
        if(result.resultCode == Activity.RESULT_OK){
            val data = result.data
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)

            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account.idToken)

            }catch (e: ApiException){
                updateUi(user = null)
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String?) {

        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener{task->
            if(task.isSuccessful){
                val user = auth.currentUser
                val name = user?.displayName
                val email = user?.email
                val photoUrl = user?.photoUrl
                Log.d("ololo", "firebaseAuthWithGoogle: Name -> ${name}\n Email -> ${email}\n")
                updateUi(user)
            }
        }

    }

    private fun updateUi(user: FirebaseUser?) {

        if(user!=null){
            findNavController().navigate(R.id.mainNoteFragment)
        }else{
            Toast.makeText(requireContext(), "Ошибка", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAuthBinding.inflate(inflater, container, false)
        auth = Firebase.auth
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(
            getString(
                R.string.default_web_client
            )
        ).requestEmail().build()
        googleClient = GoogleSignIn.getClient(requireContext(), gso)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
            setupListener()

    }

    private fun setupListener() {
        binding.btnSign.setOnClickListener{
            signInLauncher.launch(googleClient.signInIntent)
        }
    }

}