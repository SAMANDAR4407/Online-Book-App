package uz.gita.exam5bookapp.presentation.ui.description_screen

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultCaller
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker.PermissionResult
import androidx.core.content.PermissionChecker.checkSelfPermission
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import uz.gita.exam5bookapp.R
import uz.gita.exam5bookapp.data.model.common.BookData
import uz.gita.exam5bookapp.data.source.local.pref.PrefHelper
import uz.gita.exam5bookapp.databinding.DescriptionScreenBinding
import uz.gita.exam5bookapp.presentation.ui.description_screen.viewmodel.DescriptionViewModelImpl
import uz.gita.exam5bookapp.utils.checkPermissions
import java.io.File

class DescriptionScreen : Fragment(R.layout.description_screen) {

    private val binding: DescriptionScreenBinding by viewBinding()
    private val viewModel by viewModels<DescriptionViewModelImpl>()
    private val shared = PrefHelper.getInstance()
    private lateinit var data: BookData

    private var readPermission = false
    private var writePermission = false

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val book = arguments?.getStringArrayList("book")!!
        binding.apply {
            title.text = book[1]
            author.text = book[2]
            rating.rating = book[5].toFloat()
            Glide.with(binding.image).load(book[8]).into(binding.image)

            if (File(book[9]).exists()){
                btnDownload.visibility = View.GONE
            }
        }

        data = BookData(
            book[0].toInt(),
            book[1],
            book[2],
            book[3],
            book[4].toInt(),
            book[5].toInt(),
            book[6],
            book[7],
            book[8],
            book[9],
            book[10].toInt(),
            book[11].toBoolean(),
        )

        binding.readBtn.setOnClickListener {
            if (File(data.reference).exists()) {
                val action = DescriptionScreenDirections.actionDescriptionScreenToReadScreen(data)
                findNavController().navigate(action)
            } else {
                Toast.makeText(requireContext(), "Please, download the book first!", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
            Log.d("TTT", "onViewCreated: ${shared.totalPage}  ${shared.pageNumber}")
        }

        binding.btnDownload.setOnClickListener {
            storagePermission()
        }

        viewModel.fileDownloadedLiveData.observe(viewLifecycleOwner){
            binding.btnDownload.setImageResource(R.drawable.ic_downloaded)
            Toast.makeText(requireContext(), "File downloaded!", Toast.LENGTH_SHORT).show()
        }

        viewModel.errorDownloadLiveData.observe(viewLifecycleOwner){
            Toast.makeText(requireContext(), "Error!", Toast.LENGTH_SHORT).show()
            Log.d("TTT", "Error downloading file: $it")
        }

        binding.readProgress.text = if (shared.bookTitle!!.isNotBlank() && shared.bookTitle == data.title && shared.pageNumber!=1)
                ((shared.pageNumber*100/shared.totalPage).toString()+"%") else (data.lastPage.toString()+"%")

    }


    private fun storagePermission() {
        val hasReadingPermission = ContextCompat.checkSelfPermission(
            requireActivity(),
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
        val hasWritingPermission = ContextCompat.checkSelfPermission(
            requireActivity(),
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED



        val minSdk29 = Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q           // 29
        val minSdk33 = Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU    // 33

        readPermission = hasReadingPermission || minSdk33
        writePermission = hasWritingPermission || minSdk29

        val permissionList = mutableListOf<String>()
        if (!readPermission) permissionList.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        if (!writePermission) permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)

        if (permissionList.isNotEmpty()) storagePermissionLauncher.launch(permissionList.toTypedArray())
        else  {
            Toast.makeText(requireActivity(), "Start downloading", Toast.LENGTH_SHORT).show()
            viewModel.downloadBook(requireActivity(), data)
        }
    }

    private val storagePermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
        readPermission = it[Manifest.permission.READ_EXTERNAL_STORAGE] ?: readPermission
        writePermission = it[Manifest.permission.WRITE_EXTERNAL_STORAGE] ?: writePermission

        val permissionList = mutableListOf<String>()
        if (!readPermission) permissionList.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        if (!writePermission) permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (permissionList.isEmpty()) {
            Toast.makeText(requireActivity(), "Start downloading", Toast.LENGTH_SHORT).show()
            viewModel.downloadBook(requireActivity(), data)
        }
        else Toast.makeText(requireActivity(), "Permission is denied", Toast.LENGTH_SHORT).show()
    }


}