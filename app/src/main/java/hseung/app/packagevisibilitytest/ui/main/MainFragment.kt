package hseung.app.packagevisibilitytest.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import hseung.app.packagevisibilitytest.R
import hseung.app.packagevisibilitytest.databinding.MainFragmentBinding
import hseung.app.packagevisibilitytest.domain.LandUsecase

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: MainFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.main_fragment, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, MainViewModelFactory(context!!.packageManager, LandUsecase(context!!))).get(MainViewModel::class.java)

        binding.viewModel = viewModel
        viewModel.showSnackbar.observe(this.viewLifecycleOwner, Observer {
            showSnackbar(it)
        })

    }

    fun showSnackbar(text: String) {
        activity?.let {
            Snackbar.make(activity!!.findViewById(android.R.id.content), text, Snackbar.LENGTH_SHORT).show();
        }
    }
}