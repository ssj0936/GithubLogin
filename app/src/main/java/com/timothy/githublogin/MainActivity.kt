package com.timothy.githublogin

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.google.android.material.snackbar.Snackbar
import com.timothy.githublogin.databinding.ActivityMainBinding
import com.timothy.githublogin.model.Status
import com.timothy.githublogin.utils.GITHUB_CLIENT_ID
import com.timothy.githublogin.utils.GITHUB_REDIRECT_URL_HOST
import com.timothy.githublogin.utils.GITHUB_REDIRECT_URL_SCHEMA
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity @Inject constructor(): AppCompatActivity() {
    private val viewmodel:MainViewModel by viewModels()
    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewmodel.userResponse.observe(this){
            if(it is Status.TokenExpired){
                Timber.d("token expired: need to request token")
                getCodeFromGithub()
            }else {
                Timber.d("userResponse:${it.data.toString()}")
                it.data?.let {
                    with(binding){
                        signinAccount.setText(it.login)
                        email.setText(it.email)
                        name.setText(it.name)
                        location.setText(it.location)
                    }
                }
                Snackbar.make(binding.root, "Loading data from github", Snackbar.LENGTH_SHORT).show()
            }
        }

        binding.oauthGithub.setOnClickListener {
            viewmodel.getAuth()
        }

        binding.button.setOnClickListener {
            Snackbar.make(binding.root, "just a fake button, click the github button", Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun getCodeFromGithub(){
        Intent(
            Intent.ACTION_VIEW,
            Uri.parse("https://github.com/login/oauth/authorize?client_id=%s&scope=user&redirect_uri=%s://%s".format(
                GITHUB_CLIENT_ID,
                GITHUB_REDIRECT_URL_SCHEMA,
                GITHUB_REDIRECT_URL_HOST,
            ))
        ).let {
            startActivity(it)
        }
    }

    override fun onNewIntent(i: Intent?) {
        super.onNewIntent(i)
        i?.let {intent->
            if(intent.scheme == GITHUB_REDIRECT_URL_SCHEMA) {
                val code = intent.data?.getQueryParameter("code")?.let {
                    Timber.d("code:$it")
                    viewmodel.getAuth(it)
                }
            }
        }
    }
}