package com.example.notificationmanager.fragments.detailsFragments

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.notificationmanager.R
import com.example.notificationmanager.ViewModels.DetailActivityViewModel
import com.example.notificationmanager.utils.Utils


class ShortBreakDetailsBackgroundFragment :
    Fragment(R.layout.fragment_short_break_details_background) {

    private val detailActivityViewModel: DetailActivityViewModel by viewModels()
    private var ruleId: Int? = null
    private lateinit var countdownTextView: TextView
    private lateinit var countDownTimer: CountDownTimer
    private var leftTimeInMillis: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            ruleId = it.getInt(BUNDLE_RULE_ID)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        countdownTextView = view.findViewById(R.id.countdown_textView)


        ruleId?.let { detailActivityViewModel.getDetoxRuleEntity(it) }
            ?.observe(viewLifecycleOwner, Observer { detoxRule ->

                leftTimeInMillis = detoxRule.ruleBreakTimeEndTimestamp - Utils.getCurrentTime()

                if (leftTimeInMillis > 0) {

                    countDownTimer = object : CountDownTimer(leftTimeInMillis, 1000) {

                        override fun onFinish() {
                            countdownTextView.text = getString(R.string.default_countdown)
                        }

                        override fun onTick(millisUntilFinished: Long) {
                            leftTimeInMillis = millisUntilFinished
                            updateTextView()
                        }
                    }.start()
                }
            })

    }

    private fun updateTextView() {
        val minutes: Int = ((leftTimeInMillis / 1000) / 60).toInt()
        val seconds: Int = ((leftTimeInMillis / 1000) % 60).toInt()
        countdownTextView.text = String.format("%02d:%02d", minutes, seconds)
    }

    companion object {

        private val BUNDLE_RULE_ID = "bundle_rule_id"

        @JvmStatic
        fun newInstance(ruleId: Int): Fragment {
            return ShortBreakDetailsBackgroundFragment().apply {
                arguments = Bundle().apply {
                    putInt(BUNDLE_RULE_ID, ruleId)
                }
            }
        }

    }
}
