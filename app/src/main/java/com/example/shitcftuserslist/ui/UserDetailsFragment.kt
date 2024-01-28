package com.example.shitcftuserslist.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.example.shitcftuserslist.R
import com.example.shitcftuserslist.databinding.FragmentUserDetailsBinding
import com.example.shitcftuserslist.model.ResultsViewModel
import com.example.shitcftuserslist.room.ResultsApplication
import com.example.shitcftuserslist.room.User
import com.squareup.picasso.Picasso
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

class UserDetailsFragment : Fragment() {
    private val navigationArgs: UserDetailsFragmentArgs by navArgs()

    private var _binding: FragmentUserDetailsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ResultsViewModel by activityViewModels {
        ResultsViewModel.ResultViewModelFactory(
            (activity?.application as ResultsApplication).database.userDao()
        )
    }

    private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUserDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = navigationArgs.userId
        viewModel.getUserById(id).observe(this.viewLifecycleOwner) {selectedUser ->
            user = selectedUser
            bind(user)
        }

        binding.userDetailsCell.setOnClickListener {
            val cellNumber = user.cell
            val callIntent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel: $cellNumber")
            }
            startActivity(callIntent)
        }

        binding.userDetailsPhone.setOnClickListener {
            val phoneNumber = user.phone
            val callIntent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel: $phoneNumber")
            }
            startActivity(callIntent)
        }

        binding.userDetailsEmail.setOnClickListener {
            val email = user.email
            val callIntent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("mailto: $email")
            }
            startActivity(callIntent)
        }

        binding.userAddressCV.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            val addressUri = Uri.parse("geo:0,0")
                .buildUpon()
                .appendQueryParameter("q", user.street).build()
            intent.setData(addressUri)
            startActivity(intent)
        }
    }

    private fun bind(user: User) {
        val dob = OffsetDateTime.parse(user.dob, DateTimeFormatter.ISO_DATE_TIME)
        val formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy HH:mm:ss")
        binding.apply {
            Picasso.get().load(user.image).into(userImage)
            userDetailsName.text = user.name
            userDetailsCell.text = getString(R.string.cell_number, user.cell)
            userDetailsPhone.text = getString(R.string.user_phone, user.phone)
            userDetailsEmail.text = getString(R.string.user_email, user.email)
            userDetailsStreet.text = getString(R.string.user_street, user.street)
            userDetailsCity.text = getString(R.string.user_city, user.city)
            userDetailsState.text = getString(R.string.user_state, user.state)
            userDetailsCountry.text = getString(R.string.user_country, user.country)
            userDetailsDob.text = getString(R.string.user_dob, dob.format(formatter))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}