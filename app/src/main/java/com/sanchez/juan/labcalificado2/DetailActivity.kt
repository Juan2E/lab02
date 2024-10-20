package com.sanchez.juan.labcalificado2
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.sanchez.juan.labcalificado2.databinding.ActivityDetailBinding


class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val NAME_KEY = "NAME_KEY"
    private val PHONE_KEY = "PHONE_KEY"
    private val PRODUCTS_KEY = "PRODUCTS_KEY"
    private val MAP_KEY = "MAP_KEY"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        showInformation(intent.extras)
        observeButtons(intent.extras)
    }

    private fun showInformation(bundle: Bundle?) {
        if (bundle != null) {
            val name = bundle.getString(NAME_KEY)
            val phone = bundle.getString(PHONE_KEY)
            val products = bundle.getString(PRODUCTS_KEY)
            val map = bundle.getString(MAP_KEY)

            binding.tvName.text = "Nombre completo: $name"
            binding.tvPhone.text = "Número telefónico: $phone"
            binding.tvproducts.text = "Productos: $products"
            binding.tvmap.text = "Dirección: $map"
        }
    }

    private fun observeButtons(bundle: Bundle?) {
        binding.imbWsp.setOnClickListener {
            goWsp(bundle)
        }
        binding.imbDial.setOnClickListener {
            goDial(bundle)
        }
        binding.imbSms.setOnClickListener {
            goSms(bundle)
        }
        binding.imbMaps.setOnClickListener {
            goMaps(bundle)
        }
    }
    private fun goWsp(bundle: Bundle?) {
        if (bundle != null) {
            val name = bundle.getString(NAME_KEY) ?: ""
            val products = bundle.getString(PRODUCTS_KEY) ?: ""
            val map = bundle.getString(MAP_KEY) ?: ""
            val phone = "+51${bundle.getString(PHONE_KEY)}"
            val message = "Hola $name, Tus productos: $products están en camino a la dirección: $map"

            val intentWsp = Intent().apply {
                action = Intent.ACTION_VIEW
                data = Uri.parse("https://wa.me/$phone?text=$message")
            }

            startActivity(intentWsp)
        }
    }
    private fun goDial(bundle: Bundle?) {
        val phone = bundle?.getString(PHONE_KEY)

        val intentDial = Intent().apply {
            action = Intent.ACTION_DIAL
            data = Uri.parse("tel:$phone")
        }
        startActivity(intentDial)
    }
    private fun goSms(bundle: Bundle?) {
        val phone = bundle?.getString(PHONE_KEY)
        val message = "Hola te he agregado a mi lista de contactos"

        val intentSms = Intent().apply {
            action = Intent.ACTION_SENDTO
            data = Uri.parse("smsto:$phone")
            putExtra("sms_body", message)
        }
        startActivity(intentSms)
    }
    private fun goMaps(bundle: Bundle?) {
        val map = bundle?.getString(MAP_KEY) ?: return
        val uri = Uri.parse("geo:0,0?q=$map")
        val intentMaps = Intent(Intent.ACTION_VIEW, uri)
        intentMaps.setPackage("com.google.android.apps.maps")
        startActivity(intentMaps)
    }
}

