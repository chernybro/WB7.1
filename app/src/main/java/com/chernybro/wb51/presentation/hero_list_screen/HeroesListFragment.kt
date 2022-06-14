package com.chernybro.wb51.presentation.hero_list_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.chernybro.wb51.R
import com.chernybro.wb51.databinding.FragmentHeroesListBinding
import com.chernybro.wb51.domain.models.HeroItem
import com.chernybro.wb51.presentation.BaseFragment
import com.chernybro.wb51.presentation.hero_details.HeroDetailsFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HeroesListFragment : Fragment(), BaseFragment {

    private var _binding: FragmentHeroesListBinding? = null
    private val binding get() = _binding!!

    private val adapter = HeroesListAdapter()
    private var swipeRefreshLayout: SwipeRefreshLayout? = null

    private val vm: HeroesListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter.attachClickHandler(object :HeroClickHandler {
            override fun onItemClick(item: HeroItem) {
                val bundle = Bundle()
                bundle.putInt(HeroDetailsFragment.KEY_HERO_ID, item.id)
                bundle.putString(HeroDetailsFragment.KEY_HERO_NAME, item.name)
                findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment, bundle)
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHeroesListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            this.container.adapter = adapter
            swipeRefreshLayout = swipeRefreshView
        }
        swipeRefreshLayout?.setOnRefreshListener { vm.fetchHeroes() }

        configureObserving()
    }

    override fun onStart() {
        super.onStart()
        (activity as MainActivity).setToolbarTitle(getFragmentTitle())
    }


    private fun configureObserving(){
        vm.items.observe(viewLifecycleOwner) { items ->
            adapter.setData(items)
            swipeRefreshLayout?.isRefreshing = false
        }

        vm.errorMessage.observe(viewLifecycleOwner) { error ->
            Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun getFragmentTitle(): String {
        return getString(R.string.dota_heroes_label)
    }
}