package app.appworks.school.publisher.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import app.appworks.school.publisher.MainActivity
import app.appworks.school.publisher.MainViewModel
import app.appworks.school.publisher.NavigationDirections
import app.appworks.school.publisher.databinding.FragmentHomeBinding
import app.appworks.school.publisher.ext.getVmFactory
import app.appworks.school.publisher.util.Logger

/**
 * Created by Wayne Chen on 2020-01-15.
 */
class HomeFragment : Fragment() {

    /**
     * Lazily initialize our [HomeViewModel].
     */
    private val viewModel by viewModels<HomeViewModel> { getVmFactory() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        viewModel.getLiveArticlesResult()

        binding.recyclerHome.layoutManager = LinearLayoutManager(context)
        binding.recyclerHome.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        binding.recyclerHome.adapter = HomeAdapter(HomeAdapter.OnClickListener {
            Logger.d("click, it=$it")
            viewModel.delete(it)
        })

        viewModel.navigationToPublish.observe(this, Observer {
            it?.let {
                findNavController().navigate(NavigationDirections.navigationToPublishDialog(it))
                viewModel.onPublishNavigated()
            }
        })

        binding.layoutSwipeRefreshHome.setOnRefreshListener {
            viewModel.refresh()
        }

        viewModel.refreshStatus.observe(this, Observer {
            it?.let {
                binding.layoutSwipeRefreshHome.isRefreshing = it
            }
        })

        ViewModelProviders.of(activity!!).get(MainViewModel::class.java).apply {
            refresh.observe(this@HomeFragment, Observer {
                it?.let {
                    viewModel.refresh()
                    onRefreshed()
                }
            })
        }

        viewModel.liveArticles.observe(this, Observer {
            Logger.d("viewModel.liveArticles.observe, it=$it")
            it?.let {
                binding.viewModel = viewModel
            }
        })

        return binding.root
    }
}