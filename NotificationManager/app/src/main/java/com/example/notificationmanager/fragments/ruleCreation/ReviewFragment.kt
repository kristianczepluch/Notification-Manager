package com.example.notificationmanager.fragments.ruleCreation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableRow
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.notificationmanager.R
import com.example.notificationmanager.ViewModels.RuleType
import com.example.notificationmanager.ViewModels.RuleWizardViewModel
import com.example.notificationmanager.utils.Utils

class ReviewFragment : Fragment(R.layout.fragment_review) {

    private lateinit var ruleWizardViewModel: RuleWizardViewModel
    private lateinit var ruleTextView: TextView
    private lateinit var appsTextView: TextView
    private lateinit var options1TextViewTitle: TextView
    private lateinit var options2TextViewTitle: TextView
    private lateinit var options1TextViewText: TextView
    private lateinit var options2TextViewText: TextView
    private lateinit var options1Row: TableRow
    private lateinit var options2Row: TableRow
    private lateinit var line3: View
    private lateinit var line4: View


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ruleTextView = view.findViewById(R.id.wizard_textview_review_ruletext)
        appsTextView = view.findViewById(R.id.wizard_textview_review_appstext)
        options1TextViewTitle = view.findViewById(R.id.wizard_textview_review_options1)
        options2TextViewTitle = view.findViewById(R.id.wizard_textview_review_options2)
        options1TextViewText = view.findViewById(R.id.wizard_textview_review_options1_text)
        options2TextViewText = view.findViewById(R.id.wizard_textview_review_options2_text)
        options1Row = view.findViewById(R.id.wizard_tablerow_options_1)
        options2Row = view.findViewById(R.id.wizard_tablerow_options_2)
        line3 = view.findViewById(R.id.line3)
        line4 = view.findViewById(R.id.line4)

        ruleTextView.text = Utils.ruleTypeToUiString(ruleWizardViewModel.selectedRuleType)

        when(ruleWizardViewModel.selectedRuleType){
            RuleType.SHORT_BREAK ->{
                options1TextViewTitle.text = getString(R.string.duration)
                options2Row.visibility = View.INVISIBLE
                line4.visibility = View.INVISIBLE
                ruleWizardViewModel.selectedBreakTimeString.observe(viewLifecycleOwner, Observer {
                    options1TextViewText.text = it
                })
            }
            RuleType.ETERNALLY ->{
                options1Row.visibility = View.INVISIBLE
                options2Row.visibility = View.INVISIBLE
                line3.visibility = View.INVISIBLE
                line4.visibility = View.INVISIBLE

            }
            RuleType.LIMIT_NUMBER ->{
                options1TextViewTitle.text = getString(R.string.limitmode)
                options1TextViewText.text = "<limitmode goes here>"
                options2TextViewTitle.text = getString(R.string.number)
                options2TextViewText.text = "<number goes here>"

            }
            RuleType.SCHEDULE ->{
                options1TextViewTitle.text = getString(R.string.time)
                options1TextViewText.text = "<time goes here>"
                options2TextViewTitle.text = getString(R.string.weekdays)
                options2TextViewText.text = "<days go here>"
            }

        }

        ruleWizardViewModel.getSelectedApplications().observe(viewLifecycleOwner, Observer { appList ->
            var selectedCounter = 0
            appsTextView.text = ""
            appList.forEach{
                if(it.selected) {
                    appsTextView.append(Utils.getAppNameFromPackageName(it.packageName) + ", ")
                    selectedCounter++
                }
            }
            if(selectedCounter>0) appsTextView.text = appsTextView.text.substring(0, appsTextView.text.length-2)
        })


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        ruleWizardViewModel = ViewModelProviders.of(activity!!).get(RuleWizardViewModel::class.java)
        return super.onCreateView(inflater, container, savedInstanceState)
    }
}
