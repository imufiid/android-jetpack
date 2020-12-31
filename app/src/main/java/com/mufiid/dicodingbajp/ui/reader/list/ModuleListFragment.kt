package com.mufiid.dicodingbajp.ui.reader.list

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.mufiid.dicodingbajp.data.ModuleEntity
import com.mufiid.dicodingbajp.databinding.FragmentModuleListBinding
import com.mufiid.dicodingbajp.ui.reader.CourseReaderActivity
import com.mufiid.dicodingbajp.ui.reader.CourseReaderCallback
import com.mufiid.dicodingbajp.ui.reader.CourseReaderViewModel
import com.mufiid.dicodingbajp.utils.DataDummy
import com.mufiid.dicodingbajp.viewmodel.ViewModelFactory


class ModuleListFragment : Fragment(), MyAdapterClickListener {

    private lateinit var fragmentModuleListBinding: FragmentModuleListBinding
    private lateinit var adapter: ModuleListAdapter
    private lateinit var courseReaderCallback: CourseReaderCallback
    private lateinit var viewModel: CourseReaderViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentModuleListBinding = FragmentModuleListBinding.inflate(inflater, container, false)
        return fragmentModuleListBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory = ViewModelFactory.getInstance(requireActivity())
        viewModel = ViewModelProvider(requireActivity(), factory)[CourseReaderViewModel::class.java]
        adapter = ModuleListAdapter(this)

        fragmentModuleListBinding.progressBar.visibility = View.VISIBLE
        viewModel.getModules().observe(viewLifecycleOwner, { modules ->
            fragmentModuleListBinding.progressBar.visibility = View.GONE
            populateRecyclerView(modules)
        })
    }

    private fun populateRecyclerView(modules: List<ModuleEntity>) {
        with(fragmentModuleListBinding) {
            progressBar.visibility = View.GONE
            adapter.setModules(modules)
            rvModule.layoutManager = LinearLayoutManager(context)
            rvModule.setHasFixedSize(true)
            rvModule.adapter = adapter
            val dividerItemDecoration = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
            rvModule.addItemDecoration(dividerItemDecoration)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        courseReaderCallback = context as CourseReaderActivity
    }

    companion object {
        val TAG: String = ModuleListFragment::class.java.simpleName
        fun newInstance() : ModuleListFragment = ModuleListFragment()
    }

    override fun onItemClicked(position: Int, moduleId: String) {
        courseReaderCallback.moveTo(position, moduleId)
        viewModel.setSelectedModule(moduleId)
    }
}