package app.appworks.school.publisher.publish

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import app.appworks.school.publisher.MainActivity
import app.appworks.school.publisher.MainViewModel
import app.appworks.school.publisher.R
import app.appworks.school.publisher.databinding.DialogPublishBinding
import app.appworks.school.publisher.ext.getVmFactory
import app.appworks.school.publisher.home.HomeFragment
import app.appworks.school.publisher.home.HomeViewModel
import app.appworks.school.publisher.util.Logger

/**
 * Created by Wayne Chen on 2020-01-15.
 */
class PublishDialog : AppCompatDialogFragment() {

    /**
     * Lazily initialize our [PublishViewModel].
     */
    private val viewModel by viewModels<PublishViewModel> { getVmFactory(PublishDialogArgs.fromBundle(arguments!!).author) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.PublishDialog)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        val binding = DialogPublishBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        viewModel.leave.observe(this, Observer {
            it?.let { needRefresh ->
                if (needRefresh) {
                    ViewModelProviders.of(activity!!).get(MainViewModel::class.java).apply {
                        refresh()
                    }
                }
                findNavController().navigateUp()
                viewModel.onLeft()
            }
        })

        return binding.root
    }
}
