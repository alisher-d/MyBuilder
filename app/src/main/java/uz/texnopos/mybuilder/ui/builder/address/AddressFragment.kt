package uz.texnopos.mybuilder.ui.builder.address

import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.gms.location.*
import uz.texnopos.mybuilder.*
import uz.texnopos.mybuilder.Constants.PERMISSION_ID
import uz.texnopos.mybuilder.R
import uz.texnopos.mybuilder.data.FirebaseHelper
import uz.texnopos.mybuilder.databinding.FragmentAddressBinding
import uz.texnopos.mybuilder.models.BuilderModel
import java.util.*


class AddressFragment : BaseFragment(R.layout.fragment_address) {
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var navController: NavController
    private lateinit var bind: FragmentAddressBinding
    private var currentAddress = BuilderModel.Address()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bind = FragmentAddressBinding.bind(view)
        navController = Navigation.findNavController(view)
        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireContext())
        bind.apply {
            val address = if (getAddress().isNotEmpty()) getAddress().split("\n")
            else listOf("", "", "")
            etCountryName.setText(address[0])
            etStateName.setText(address[1])
            etCityName.setText(address[2])
            currentLocation.onClick {
                getLastLocation()
            }
            btnSave.onClick {
                if (validate()) {
                    showProgress()
                    val myAddress = BuilderModel.Address()
                    myAddress.countryName = etCountryName.textToString()
                    myAddress.stateName = etStateName.textToString()
                    myAddress.cityName = etCityName.textToString()
                    FirebaseHelper().updateBuilderAddress(myAddress,
                        {
                            toast(it!!)
                            hideProgress()
                            navController.navigate(R.id.action_addressFragment_to_homeMainFragment)
                        },
                        {
                            toast(it!!)
                            hideProgress()
                        })
                }
            }

        }
    }

    private fun getLastLocation() {
        if (checkPermission()) {
            if (isGPSEnable()) {
                showProgress()
                fusedLocationProviderClient.lastLocation.addOnCompleteListener { task ->
                    hideProgress()
                    val location: Location? = task.result
                    if (location == null) {
                        newLocationData()
                    } else {
                        Log.d("Debug:", "Your Location long:" + location.longitude)
                        Log.d("Debug:", "Your Location lat:" + location.latitude)
                        //textView text there
                        val myAddress = addressName(location.latitude, location.longitude)
                        bind.etCityName.setText(myAddress.cityName)
                        bind.etStateName.setText(myAddress.stateName)
                        bind.etCountryName.setText(myAddress.countryName)
                    }
                }
            } else {
                startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
            }
        } else {
            requestPermission()
        }
    }

    private fun addressName(lat: Double, long: Double): BuilderModel.Address {
        val geoCoder = Geocoder(requireContext(), Locale.getDefault())
        val address = geoCoder.getFromLocation(lat, long, 3)
        currentAddress.lat = lat
        currentAddress.long = long
        currentAddress.cityName = address[0].subAdminArea
        currentAddress.countryName = address[0].countryName
        currentAddress.stateName = address[0].adminArea
        return currentAddress
    }

    private fun newLocationData() {
        locationRequest = LocationRequest.create().apply {
            priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
            interval = 0
            fastestInterval = 0
            numUpdates = 1
        }
        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireContext())
        if (checkPermission()) fusedLocationProviderClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.myLooper()!!
        )
    }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            val lastLocation = locationResult.lastLocation
            Log.d("Debug:", "your last last location: " + lastLocation.longitude.toString())
            val myAddress = addressName(lastLocation.latitude, lastLocation.longitude)
            bind.etCityName.setText(myAddress.cityName)
            bind.etStateName.setText(myAddress.stateName)
            bind.etCountryName.setText(myAddress.countryName)
        }
    }

    private fun checkPermission(): Boolean {
        return ActivityCompat.checkSelfPermission(
            requireContext(),
            android.Manifest.permission.ACCESS_COARSE_LOCATION
        ) ==
                PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(
                    requireContext(),
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                ) ==
                PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ), PERMISSION_ID
        )
    }

    private fun validate(): Boolean {
        return when {
            bind.etCountryName.checkIsEmpty() -> {
                bind.etCountryName.showError("Field Required")
                false
            }
            bind.etStateName.checkIsEmpty() -> {
                bind.etStateName.showError("Field Required")
                false
            }
            bind.etCityName.checkIsEmpty() -> {
                bind.etCityName.showError("Field Required")
                false
            }
            else -> true

        }
    }
}