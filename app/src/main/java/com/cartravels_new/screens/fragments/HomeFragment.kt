package com.cartravels_new.screens.fragments


import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.cartravels_new.R
import com.cartravels_new.databinding.FragmentHomeBinding
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationListener
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import dilipsuthar.saathi.utils.mToast
import java.util.*

/**
 * A simple [Fragment] subclass.
 *
 */
class HomeFragment : Fragment(), OnMapReadyCallback,
		GoogleApiClient.ConnectionCallbacks,
		GoogleApiClient.OnConnectionFailedListener,
		LocationListener {

	companion object {
		const val TAG: String = "HomeFragmentDebug"
		const val FINE_LOCATION: String = Manifest.permission.ACCESS_FINE_LOCATION
		const val COARSE_LOCATION: String = Manifest.permission.ACCESS_COARSE_LOCATION
		const val LOCATION_PERMISSION_RC = 1234
		const val DEFAULT_ZOOM: Float = 15.5F
	}

	private lateinit var binding: FragmentHomeBinding
	private var distance: Float = 0F

	// Google Map
	private lateinit var mMap: GoogleMap
	private lateinit var mapFragment: SupportMapFragment
	private lateinit var mLocationRequest: LocationRequest
	private var mGoogleApiClient: GoogleApiClient? = null
	private lateinit var latLng: LatLng
	private lateinit var startLatLng: LatLng

	private var geoCoder: Geocoder? = null  // For getting address from location
	private var currentLocation: Location? = null
	private lateinit var locationManager: LocationManager
	private var isGPSEnabled = false
	private var isNetworkEnabled = false
	private var isLocationPermissionGranted = false

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		// Inflate the layout for this fragment
		binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
		initComponent()
		checkLocationAndDataService()

		return binding.mapLl;
	}

	override fun onResume() {
		super.onResume()
		initComponent()
		if (mGoogleApiClient != null)
			mGoogleApiClient!!.connect()
	}


	private fun initComponent() {
		binding.fabLocateMe.setOnClickListener {
			mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,
					DEFAULT_ZOOM
			))
		}

	}

	private fun initMap() {
		Log.d(TAG, "initMap: Map init successfully!!")
		mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
		mapFragment.getMapAsync(this)
	}

	private fun checkLocationAndDataService() {
		Log.d(TAG, "checkLocationAndDataService: called")
		locationManager = activity!!.getSystemService(Context.LOCATION_SERVICE) as LocationManager
		isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
		isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)

		if (!isGPSEnabled) {
			Log.d(TAG, "checkLocationAndDataService: condition false, openDialog")
			val dialog = AlertDialog.Builder(activity!!)
			dialog.setTitle("GPS Setting")
			dialog.setMessage("GPS is not enabled. Do you want to go to setting menu?")
			dialog.setCancelable(false)
			dialog.setPositiveButton("SETTINGS") { _, _ ->
				activity!!.startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
			}
			dialog.setNegativeButton("CANCEL") { _, _ ->

			}
			dialog.show()
		} else {
			getLocationPermission()
		}

		checkAgainLocationAndDataService()
	}
	private fun bitmapDescriptorFromVector(context: Context, vectorResId: Int): BitmapDescriptor? {
		return ContextCompat.getDrawable(context, vectorResId)?.run {
			setBounds(0, 0, intrinsicWidth, intrinsicHeight)
			val bitmap = Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, Bitmap.Config.ARGB_8888)
			draw(Canvas(bitmap))
			BitmapDescriptorFactory.fromBitmap(bitmap)
		}
	}
	private fun checkAgainLocationAndDataService() {
		if (isNetworkEnabled && isGPSEnabled)
			getLocationPermission()
	}

	private fun getLocationPermission() {
		Log.d(TAG, "getLocationPermission: called")
		val permissions = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)

		if (ActivityCompat.checkSelfPermission(activity!!,
						FINE_LOCATION
				) == PackageManager.PERMISSION_GRANTED) {
			if (ActivityCompat.checkSelfPermission(activity!!,
							COARSE_LOCATION
					) == PackageManager.PERMISSION_GRANTED) {
				isLocationPermissionGranted = true
				// Initialize mMap
				initMap()
			} else
				ActivityCompat.requestPermissions(activity!!, permissions,
						LOCATION_PERMISSION_RC
				)
		} else
			ActivityCompat.requestPermissions(activity!!, permissions,
					LOCATION_PERMISSION_RC
			)
	}

	override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
		Log.d(TAG, "onRequestPermissionResult: called")
		isLocationPermissionGranted = false

		when (requestCode) {
			LOCATION_PERMISSION_RC -> {
				if (grantResults.isNotEmpty()) {
					for (i in grantResults.indices) {
						if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
							mToast.showToast(activity!!, "Permission denied", Toast.LENGTH_SHORT)
							isLocationPermissionGranted = false
							return
						}
					}
					mToast.showToast(activity!!, "Permission granted", Toast.LENGTH_SHORT)
					isLocationPermissionGranted = true
					initMap()
				}
			}
		}
	}

	private fun getAddress(location: Location?) {
		location!!.let {
			var addresses: List<Address> = emptyList()

			addresses = geoCoder!!.getFromLocation(location.latitude, location.longitude, 1)

			val address = addresses[0].getAddressLine(0)
			val city = addresses[0].locality
			val state = addresses[0].adminArea
			val countryCode = addresses[0].countryCode
		}
	}

	private fun addNearbyMarker(latLng: LatLng) {
		var lat = latLng.latitude
		var lng = latLng.longitude
		val counter = floatArrayOf(0.001f, -0.002f, 0.003f, -0.0028f, -0.0054f)
		var bikeNum = 2019000

		for (i in 0 until counter.size) {
			lat = lat.plus(counter[i])
			lng = lng.plus(counter[i])
			bikeNum += 1

			val ltlg = LatLng(lat, lng)
			when (i) {
				0 -> mMap.addMarker(
						MarkerOptions().position(ltlg).title("$bikeNum").icon(
								bitmapDescriptorFromVector(activity!!, R.drawable.ic_about)
						)
				)
				1 -> mMap.addMarker(
						MarkerOptions().position(ltlg).title("$bikeNum").icon(
								bitmapDescriptorFromVector(activity!!, R.drawable.ic_about)
						)
				)
				2 -> mMap.addMarker(
						MarkerOptions().position(ltlg).title("$bikeNum").icon(
								bitmapDescriptorFromVector(activity!!, R.drawable.ic_about)
						)
				)
				3 -> mMap.addMarker(
						MarkerOptions().position(ltlg).title("$bikeNum").icon(
								bitmapDescriptorFromVector(activity!!, R.drawable.ic_about)
						)
				)
				4 -> mMap.addMarker(
						MarkerOptions().position(ltlg).title("$bikeNum").icon(
								bitmapDescriptorFromVector(activity!!, R.drawable.ic_about)
						)
				)

			}
		}
	}

	override fun onMapReady(googleMap: GoogleMap?) {
		mToast.showToast(activity!!, "Map is ready", Toast.LENGTH_SHORT)
		mMap = googleMap!!
//        mMap.clear()

		// Customize the style of Google Map
		setCustomTheme(mMap)


		// call getDeviceLocation
		try {
			if (isLocationPermissionGranted) {
//                getDeviceLocation()

				mMap.isMyLocationEnabled = true
				mMap.isBuildingsEnabled = true
				mMap.isTrafficEnabled = false
				mMap.uiSettings.isZoomControlsEnabled = false
				mMap.uiSettings.isMyLocationButtonEnabled = false

				// For getting address of location
				geoCoder = Geocoder(activity!!, Locale.getDefault())    // For address
				buildGoogleApiClient()
			} else {
				mMap.isMyLocationEnabled = false
				getLocationPermission()
			}
		} catch (e: SecurityException) {

		}
	}

	private fun buildGoogleApiClient() {
		Log.d(TAG, "buildGoogleApiClient: called")
		mGoogleApiClient = GoogleApiClient.Builder(context!!)
				.addConnectionCallbacks(this)
				.addOnConnectionFailedListener(this)
				.addApi(LocationServices.API)
				.build()

		if (mGoogleApiClient != null)
			mGoogleApiClient!!.connect()
	}

	override fun onConnected(bundle: Bundle?) {
		Log.d(TAG, "onConnected: called")

		if (mGoogleApiClient!!.isConnected) {

			try {

				val mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient)
				if (mLastLocation != null) {
					mMap.clear()
					latLng = LatLng(mLastLocation.latitude, mLastLocation.longitude)
					startLatLng = latLng
					getAddress(mLastLocation)
					mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,
							DEFAULT_ZOOM
					))
					addNearbyMarker(latLng)
				}

				mLocationRequest = LocationRequest()
				mLocationRequest.interval = 5000
				mLocationRequest.fastestInterval = 3000
				mLocationRequest.priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY

				LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this)


			} catch (e: SecurityException) {

			}

		}
	}

	override fun onConnectionSuspended(p0: Int) {
		Log.d(TAG, "onConnectionSuspend: called")
	}

	override fun onConnectionFailed(p0: ConnectionResult) {
		Log.d(TAG, "onConnectionFailed: called")
	}

	@SuppressLint("SetTextI18n")
	override fun onLocationChanged(location: Location?) {
		/*if (mCurrLocation != null) {
			mCurrLocation.remove()
		}*/
		if (location != null) {
			currentLocation = location
			//mToast.showToast(context!!, "Location changed", Toast.LENGTH_SHORT)
			latLng = LatLng(location.latitude, location.longitude)
			//mCurrLocation = mMap.addMarker(MarkerOptions().position(latLng).title("You'r here").icon(BitmapDescriptorFactory.fromResource(R.drawable.pin_x64)))
			getAddress(location)
			mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,
					DEFAULT_ZOOM
			))
		}
	}

	fun decodePolyline(encoded: String): List<LatLng> {

		val poly = ArrayList<LatLng>()
		var index = 0
		val len = encoded.length
		var lat = 0
		var lng = 0

		while (index < len) {
			var b: Int
			var shift = 0
			var result = 0
			do {
				b = encoded[index++].toInt() - 63
				result = result or (b and 0x1f shl shift)
				shift += 5
			} while (b >= 0x20)
			val dlat = if (result and 1 != 0) (result shr 1).inv() else result shr 1
			lat += dlat

			shift = 0
			result = 0
			do {
				b = encoded[index++].toInt() - 63
				result = result or (b and 0x1f shl shift)
				shift += 5
			} while (b >= 0x20)
			val dlng = if (result and 1 != 0) (result shr 1).inv() else result shr 1
			lng += dlng

			val latLng = LatLng((lat.toDouble() / 1E5), (lng.toDouble() / 1E5))
			poly.add(latLng)
		}

		return poly
	}
//----------------------------------------------------------------------------------------------------------------------

	private fun setCustomTheme(map: GoogleMap) {

		/*val hour = getClockTime()

		when (hour) {
			in 0..6 -> map.setMapStyle(MapStyleOptions.loadRawResourceStyle(activity, R.raw.night))
			in 7..18 -> map.setMapStyle(MapStyleOptions.loadRawResourceStyle(activity, R.raw.uber_style))
			in 19..24 -> map.setMapStyle(MapStyleOptions.loadRawResourceStyle(activity, R.raw.night))
		}*/

		mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(activity, R.raw.uber_style))
	}

}
