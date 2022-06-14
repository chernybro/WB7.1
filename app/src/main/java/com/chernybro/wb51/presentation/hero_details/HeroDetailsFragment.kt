package com.chernybro.wb51.presentation.hero_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import coil.load
import com.chernybro.wb51.R
import com.chernybro.wb51.databinding.FragmentHeroDetailsBinding
import com.chernybro.wb51.presentation.BaseFragment
import com.chernybro.wb51.presentation.hero_list_screen.MainActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HeroDetailsFragment : Fragment(), BaseFragment {

    private var _binding: FragmentHeroDetailsBinding? = null
    private val binding get() = _binding!!

    companion object {
        const val KEY_HERO_ID = "KEY_HERO_ID"
        const val KEY_HERO_NAME = "KEY_HERO_NAME"
    }

    private val vm: HeroDetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHeroDetailsBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onStart() {
        super.onStart()
        (activity as MainActivity).setToolbarTitle(arguments?.getString(KEY_HERO_NAME, getFragmentTitle()) ?: getFragmentTitle())
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getInt(KEY_HERO_ID)?.let { vm.getHeroDetails(it) }

        vm.heroDetails.observe(viewLifecycleOwner) { hero ->
            binding.apply {
                heroName.text = hero.name
                banner.load(hero.imageUrl)

                tvHealth.text = hero.health.baseValue.toString() + " + " + hero.health.regen.toString()
                tvMana.text = hero.mana.baseValue.toString() + " + " + hero.mana.regen.toString()

                tvAttackMaxMin.text = hero.stats.attack.min.toString() + " + " + hero.stats.attack.max.toString()
                tvAttackRange.text = hero.stats.attack.range.toString()
                tvAttackRate.text = hero.stats.attack.rate.toString()

                tvDefencePercent.text = hero.stats.defence.magicResistant.toString()
                tvDefenceRate.text = hero.stats.defence.armor.toString()

                tvAgilityStats.text =
                    hero.stats.attributes.agility.base.toString() + " + " + hero.stats.attributes.agility.gain.toString()
                tvIntelligenceStats.text =
                    hero.stats.attributes.intelligence.base.toString() + " + " + hero.stats.attributes.intelligence.gain.toString()
                tvStrengthStats.text =
                    hero.stats.attributes.strength.base.toString() + " + " + hero.stats.attributes.strength.gain.toString()

                tvMobilitySpeed.text = hero.stats.mobility.speed.toString()

                tvAttackType.text = hero.stats.attack.type
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun getFragmentTitle(): String {
        return getString(R.string.hero_name)
    }
}