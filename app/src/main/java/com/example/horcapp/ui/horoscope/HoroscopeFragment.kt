package com.example.horcapp.ui.horoscope

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.horcapp.databinding.FragmentHoroscopeBinding
import com.example.horcapp.domain.Model.HoroscopeInfo.Aquarius
import com.example.horcapp.domain.Model.HoroscopeInfo.Aries
import com.example.horcapp.domain.Model.HoroscopeInfo.Cancer
import com.example.horcapp.domain.Model.HoroscopeInfo.Capricorn
import com.example.horcapp.domain.Model.HoroscopeInfo.Gemini
import com.example.horcapp.domain.Model.HoroscopeInfo.Leo
import com.example.horcapp.domain.Model.HoroscopeInfo.Libra
import com.example.horcapp.domain.Model.HoroscopeInfo.Pisces
import com.example.horcapp.domain.Model.HoroscopeInfo.Sagittarius
import com.example.horcapp.domain.Model.HoroscopeInfo.Scorpio
import com.example.horcapp.domain.Model.HoroscopeInfo.Taurus
import com.example.horcapp.domain.Model.HoroscopeInfo.Virgo
import com.example.horcapp.domain.Model.HoroscopeModel
import com.example.horcapp.ui.horoscope.Adapter.HoroscopeAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HoroscopeFragment : Fragment() {
    private val horoscopeViewModel by viewModels<HoroscopeViewModel>()

    private lateinit var horoscopeAdapter: HoroscopeAdapter
    private var _binding: FragmentHoroscopeBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        InitUI()
    }

    private fun InitUI() {
        initList()
        initUIState()
    }

    private fun initList() {
        horoscopeAdapter = HoroscopeAdapter(onItemSelected = {
            val type = when (it) {
                Aquarius -> HoroscopeModel.Aquarius
                Aries -> HoroscopeModel.Aries
                Cancer -> HoroscopeModel.Cancer
                Capricorn -> HoroscopeModel.Capricorn
                Gemini -> HoroscopeModel.Gemini
                Leo -> HoroscopeModel.Leo
                Libra -> HoroscopeModel.Libra
                Pisces -> HoroscopeModel.Pisces
                Sagittarius -> HoroscopeModel.Sagittarius
                Scorpio -> HoroscopeModel.Scorpio
                Taurus -> HoroscopeModel.Taurus
                Virgo -> HoroscopeModel.Virgo
            }
            type
            findNavController().navigate(
                HoroscopeFragmentDirections.actionHoroscopeFragmentToHoroscopeDetailActivity(type)
            )
        })

        binding.rvHoroscope.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = horoscopeAdapter
        }

    }

    private fun initUIState() {
        //Siempre que se quiera usar una corrutina en un fragment o activity usar lifecycle scope - destruye a la vez que el fragmento
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                horoscopeViewModel.horoscope.collect() {
                    //Cambios en horoscope
                    horoscopeAdapter.updateList(it)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHoroscopeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

}