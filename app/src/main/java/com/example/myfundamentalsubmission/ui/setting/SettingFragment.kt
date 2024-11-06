package com.example.myfundamentalsubmission.ui.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.example.myfundamentalsubmission.databinding.FragmentSettingBinding
import com.example.myfundamentalsubmission.ui.ViewModelFactory
import com.example.myfundamentalsubmission.worker.DailyReminderWorker
import java.util.concurrent.TimeUnit

class SettingFragment : Fragment() {
    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding

    private lateinit var workManager: WorkManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        workManager = WorkManager.getInstance(requireActivity().application)

        val switchTheme = binding?.switchTheme
        val switchDailyReminder = binding?.switchDailyReminder

        val pref = SettingPreferences.getInstance(requireActivity().application)
        val settingViewModel = ViewModelProvider(this, ViewModelFactory.getInstance(requireActivity().application)).get(
            SettingViewModel::class.java
        )

        settingViewModel.getThemeSettings().observe(viewLifecycleOwner) { isDarkModeActive ->
            AppCompatDelegate.setDefaultNightMode(
                if (isDarkModeActive) AppCompatDelegate.MODE_NIGHT_YES
                else AppCompatDelegate.MODE_NIGHT_NO
            )
            switchTheme?.isChecked = isDarkModeActive
        }

        switchTheme?.setOnCheckedChangeListener { _, isChecked ->
            settingViewModel.saveThemeSetting(isChecked)
        }

        settingViewModel.getDailyReminderSetting().observe(viewLifecycleOwner) { isReminderActive ->
            switchDailyReminder?.isChecked = isReminderActive
            if (isReminderActive) {
                startDailyReminder()
            } else {
                cancelDailyReminder()
            }
        }

        switchDailyReminder?.setOnCheckedChangeListener { _, isChecked ->
            settingViewModel.saveDailyReminderSetting(isChecked)
            if (isChecked) {
                startDailyReminder()
            } else {
                cancelDailyReminder()
            }
        }
    }

    private fun startDailyReminder() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val dailyWorkRequest = PeriodicWorkRequest.Builder(DailyReminderWorker::class.java, 24, TimeUnit.HOURS)
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(requireContext()).enqueueUniquePeriodicWork(
            "DailyReminderTask",
            ExistingPeriodicWorkPolicy.KEEP,
            dailyWorkRequest
        )
    }

    private fun cancelDailyReminder() {
        WorkManager.getInstance(requireContext()).cancelUniqueWork("DailyReminderTask")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
