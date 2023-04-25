package com.example.qrscannerapp

import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.qrscannerapp.databinding.FragmentHomeBinding
import com.google.zxing.Result
import me.dm7.barcodescanner.zxing.ZXingScannerView
import me.dm7.barcodescanner.zxing.ZXingScannerView.ResultHandler



class HomeFragment : Fragment(), ResultHandler {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var scannerView : ZXingScannerView
    private var isScanningEnabled = true
    private var list = ArrayList<String>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater)
        scannerView = ZXingScannerView(requireContext())
        initializeQRScanner()
        onClicks()
        list.add("Viraj")
        list.add("Aman")
        list.add("Devajit")
        return binding.root
    }

    private fun onClicks() {
        binding.ivFlash.setOnClickListener {
            if(it.isSelected){
                offFlashLight()
            }
            else{
              onFlashLight()

            }
        }
    }

    private fun onFlashLight() {
        binding.ivFlash.isSelected = true
        scannerView.flash = true
    }

    private fun offFlashLight() {
        binding.ivFlash.isSelected = false
        scannerView.flash = false
    }

    private fun initializeQRScanner() {
        scannerView.apply {
            setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.colorTranslucent))
            setBorderColor(ContextCompat.getColor(requireContext(),R.color.blue1))
            setLaserColor(ContextCompat.getColor(requireContext(),R.color.blue1))
            setBorderStrokeWidth(10)
            setAutoFocus(true)
            setSquareViewFinder(true)
            setResultHandler(this@HomeFragment)
            binding.containerScanner.addView(scannerView)
            startQRCamera()

        }

    }

    private fun startQRCamera(){
        scannerView.startCamera()
    }

    override fun onResume() {
        super.onResume()
        scannerView.setResultHandler(this)
        scannerView.startCamera()
    }
    override fun onPause() {
        super.onPause()
        scannerView.stopCamera()
    }
    override fun onDestroy() {
        super.onDestroy()
        scannerView.stopCamera()
    }

    override fun handleResult(rawResult: Result?) {

        if(list.contains<String>(rawResult.toString())){
            Toast.makeText(requireContext(),"Present",Toast.LENGTH_SHORT).show()
        }
        else{
            Toast.makeText(requireContext(),"Absent",Toast.LENGTH_SHORT).show()
        }

        scannerView.resumeCameraPreview(this)
//        if (isScanningEnabled) { // check flag variable before handling result
//            isScanningEnabled = false // set flag variable to false
//            val vibrator = requireContext().getSystemService(Vibrator::class.java)
//            vibrator?.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
//            Toast.makeText(requireContext(), rawResult?.text, Toast.LENGTH_SHORT).show()
//            scannerView.resumeCameraPreview(this)
//        }

    }

}