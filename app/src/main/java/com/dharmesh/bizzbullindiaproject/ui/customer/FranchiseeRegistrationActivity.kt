package com.dharmesh.bizzbullindiaproject.ui.customer

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dharmesh.bizzbullindiaproject.R
import com.dharmesh.bizzbullindiaproject.adapter.AcademicAdapter
import com.dharmesh.bizzbullindiaproject.adapter.ChildAdapter
import com.dharmesh.bizzbullindiaproject.adapter.FamilyAdapter
import com.dharmesh.bizzbullindiaproject.adapter.PersonalRefAdapter
import com.dharmesh.bizzbullindiaproject.common.AppConstants
import com.dharmesh.bizzbullindiaproject.common.Constants
import com.dharmesh.bizzbullindiaproject.common.Preferences
import com.dharmesh.bizzbullindiaproject.common.view.CustomSpinner.OnSpinnerEventsListener
import com.dharmesh.bizzbullindiaproject.databinding.ActivityFranchiseeregistrationBinding
import com.dharmesh.bizzbullindiaproject.fact.LoginViewModelFactory
import com.dharmesh.bizzbullindiaproject.model.FoFranchiseRequest
import com.dharmesh.bizzbullindiaproject.model.FoHealthRegistrationRequest
import com.dharmesh.bizzbullindiaproject.model.FoSocialDetailsRequest
import com.dharmesh.bizzbullindiaproject.model.adaptermodel.Academic
import com.dharmesh.bizzbullindiaproject.model.adaptermodel.Children
import com.dharmesh.bizzbullindiaproject.model.adaptermodel.Family
import com.dharmesh.bizzbullindiaproject.model.adaptermodel.Personalref
import com.dharmesh.bizzbullindiaproject.vm.FranchiseeRegistrationViewModel

class FranchiseeRegistrationActivity : AppCompatActivity(), View.OnClickListener {
    private var userId: String? = null
    private lateinit var appConstants: AppConstants
    var binding: ActivityFranchiseeregistrationBinding? = null
    var handusearrayMaster =
        arrayOf(Constants.SELECT_HAND_USE, Constants.RIGHT_HANDED, Constants.LEFT_HANDED)
    var bloodgrouparrayMaster = arrayOf(
        Constants.SELECT_BLOOD_GROUP,
        Constants.APLUS,
        Constants.OPLUS,
        Constants.BPLUS,
        Constants.ABPLUS,
        Constants.AMINUS,
        Constants.OMINUS,
        Constants.BMINUS,
        Constants.ABMINUS
    )
    var phusicallyhandicapMaster = arrayOf(Constants.SELECT_PH, Constants.YES, Constants.NO)
    var surgeryMaster = arrayOf(Constants.SELECT_SURGERY, Constants.YES, Constants.NO)
    var healthissueMaster = arrayOf(Constants.SELECT_HEALTHISSUES, Constants.YES, Constants.NO)
    var unhealthyhabitsMaster =
        arrayOf(Constants.SELECT_UNHEALTHHABITS, Constants.YES, Constants.NO)
    var accountMaster = arrayOf(Constants.SELECT_ACCOUNT_TYPE, Constants.SAVING, Constants.CURRENT)
    var businessplaceMaster = arrayOf(
        Constants.SELECT_BUSINESSPLACETYPE,
        Constants.OWNED,
        Constants.LEASED,
        Constants.RENTED
    )
    private var dialogacademicBuilder: AlertDialog.Builder? = null
    private var dialogfamilyBuilider: AlertDialog.Builder? = null
    private var dialogchildBuilder: AlertDialog.Builder? = null
    private var dialogreferranceBuilder: AlertDialog.Builder? = null
    private var dialogacademic: AlertDialog? = null
    private var dialogfamily: AlertDialog? = null
    private var dialogchild: AlertDialog? = null
    private var dialogreferrance: AlertDialog? = null
    var academicList: MutableList<Academic> = ArrayList()
    var childrenList: MutableList<Children> = ArrayList()
    var familyList: MutableList<Family> = ArrayList()
    var personalrefList: MutableList<Personalref> = ArrayList()
    var academicAdapter: AcademicAdapter? = null
    var childAdapter: ChildAdapter? = null
    var familyAdapter: FamilyAdapter? = null
    var personalRefAdapter: PersonalRefAdapter? = null
    var gender = ""

    private lateinit var franchiseeRegistrationViewModel: FranchiseeRegistrationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        franchiseeRegistrationViewModel =
            ViewModelProvider(
                this,
                LoginViewModelFactory()
            )[FranchiseeRegistrationViewModel::class.java]

        binding = ActivityFranchiseeregistrationBinding.inflate(
            layoutInflater
        )
        setContentView(binding!!.root)
        init()
    }

    fun init() {
        appConstants = AppConstants(this)
        userId = appConstants.getPrefValue(Preferences.USER_ID)
        loadallspinners()
        loadAcademic()
        loadChildren()
        loadFamily()
        loadpersonalref()
        binding!!.layoutaddacademic.setOnClickListener(this)
        binding!!.layoutaddfamily.setOnClickListener(this)
        binding!!.layoutaddchild.setOnClickListener(this)
        binding!!.layoutaddpersonalref.setOnClickListener(this)
        binding!!.btnHealthdetail.setOnClickListener(this)
        binding!!.btnexpressionofinterest.setOnClickListener(this)
        binding!!.btnsocialidentity.setOnClickListener(this)

        franchiseeRegistrationViewModel.foRegistrationResult.observe(this) {
            Toast.makeText(this, it.msg, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onClick(view: View) {
        if (view === binding!!.layoutaddacademic) {
            popuupAcademic()
        } else if (view === binding!!.layoutaddfamily) {
            popuupfamily()
        } else if (view === binding!!.layoutaddchild) {
            popuupchildren()
        } else if (view === binding!!.layoutaddpersonalref) {
            popuupreferrnaces()
        } else if (view === binding!!.btnHealthdetail) {
            submitHealthDetail()
        } else if (view === binding!!.btnexpressionofinterest) {
            submitFoFranchiseDetails()
        } else if (view === binding!!.btnsocialidentity) {
            submitFoSocialDetails()
        }
    }

    private fun submitHealthDetail() {
        binding?.apply {
            franchiseeRegistrationViewModel.submitHealthDetail(
                FoHealthRegistrationRequest(
                    userid = "",
                    task = "",
                    healthDetail = FoHealthRegistrationRequest.Hdetail(
                        userid = userId!!,
                        birthidentificationmarks = edtbirthmarkesone.text.toString(),
                        birthidentificationmarks2 = edtbirthmarketwo.text.toString(),
                        handuse = spnrhanduse.selectedItem.toString(),
                        height = edtheight.text.toString(),
                        weight = edtweight.text.toString(),
                        bloodgroup = spnrbloodgroup.selectedItem.toString(),
                        willingtodonate = edtwillingdonate.text.toString(),
                        physycalhandicape = spnrhandicapped.selectedItem.toString(),
                        typeofph = edttypeofph.text.toString(),
                        typeofsurgery = spnrsurgeries.selectedItem.toString(),
                        anyotherhealthissue = spnrhealthissued.selectedItem.toString(),
                        otherissuesdetail = edttypeofhealthissue.text.toString(),
                        anyunhealthyhabit = spnrhabbit.selectedItem.toString(),
                        habbitdetails = edttypeofhabbits.text.toString(),
                        surgelesstreatmentundergo = "",
                    )
                )
            )
        }
    }

    private fun submitFoFranchiseDetails() {
        binding?.apply {
            franchiseeRegistrationViewModel.submitFoFranchiseDetails(
                FoFranchiseRequest(
                    userid = "",
                    task = "",
                    franchiseDetails = FoFranchiseRequest.FranchiseDetails(
                        userid = userId!!,
                        locationofinterest = edtlocationinterest.text.toString(),
                        financialassistance = edtfinancialassit.text.toString(),
                        franchiseplaningfor = edtfranchiseplanningfor.text.toString(),
                        franchisePlanningAs = edtfranchiseplanning.text.toString(),
                        businessplacetype = spnrbusinessplace.selectedItem.toString(),
                        businessplacesize = edtbuzplacesize.text.toString()
                    )
                )
            )
        }
    }

    private fun submitFoSocialDetails() {
        binding?.apply {
            franchiseeRegistrationViewModel.submitFoSocialDetails(
                FoSocialDetailsRequest(
                    userid = "",
                    task = "",
                    foSocialDetails = FoSocialDetailsRequest.FoSocialDetails(
                        userid = userId!!,
                        adharcardnumber = edtaadharcard.text.toString(),
                        pancardno = edtpancard.text.toString(),
                        drivinglicenseno = edtdlno.text.toString(),
                        passportno = edtpassport.text.toString(),
                        votteridno = edtvoterno.text.toString(),
                        rationcardno = edtrationno.text.toString()
                    )
                )
            )
        }
    }


    fun loadallspinners() {
        val handuse_adapter: ArrayAdapter<*> =
            ArrayAdapter(this, R.layout.spinner_corridor_selected, handusearrayMaster)
        handuse_adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        binding!!.spnrhanduse.adapter = handuse_adapter
        binding!!.spnrhanduse.setSelection(0)
        binding!!.spnrhanduse.setSpinnerEventsListener(object : OnSpinnerEventsListener {
            override fun onSpinnerOpened() {
                binding!!.spnrhanduse.isSelected = true
            }

            override fun onSpinnerClosed() {
                binding!!.spnrhanduse.isSelected = false
            }
        })
        binding!!.spnrhanduse.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View,
                position: Int,
                id: Long
            ) {
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        val bloodgroup_adapter: ArrayAdapter<*> =
            ArrayAdapter(this, R.layout.spinner_corridor_selected, bloodgrouparrayMaster)
        bloodgroup_adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        binding!!.spnrbloodgroup.adapter = bloodgroup_adapter
        binding!!.spnrbloodgroup.setSelection(0)
        binding!!.spnrbloodgroup.setSpinnerEventsListener(object : OnSpinnerEventsListener {
            override fun onSpinnerOpened() {
                binding!!.spnrbloodgroup.isSelected = true
            }

            override fun onSpinnerClosed() {
                binding!!.spnrbloodgroup.isSelected = false
            }
        })
        binding!!.spnrbloodgroup.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View,
                    position: Int,
                    id: Long
                ) {
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
        val handicapped_adapter: ArrayAdapter<*> =
            ArrayAdapter(this, R.layout.spinner_corridor_selected, phusicallyhandicapMaster)
        handicapped_adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        binding!!.spnrhandicapped.adapter = handicapped_adapter
        binding!!.spnrhandicapped.setSelection(0)
        binding!!.spnrhandicapped.setSpinnerEventsListener(object : OnSpinnerEventsListener {
            override fun onSpinnerOpened() {
                binding!!.spnrhandicapped.isSelected = true
            }

            override fun onSpinnerClosed() {
                binding!!.spnrhandicapped.isSelected = false
            }
        })
        binding!!.spnrhandicapped.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View,
                    position: Int,
                    id: Long
                ) {
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
        val surgeries_adapter: ArrayAdapter<*> =
            ArrayAdapter(this, R.layout.spinner_corridor_selected, surgeryMaster)
        surgeries_adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        binding!!.spnrsurgeries.adapter = surgeries_adapter
        binding!!.spnrsurgeries.setSelection(0)
        binding!!.spnrsurgeries.setSpinnerEventsListener(object : OnSpinnerEventsListener {
            override fun onSpinnerOpened() {
                binding!!.spnrsurgeries.isSelected = true
            }

            override fun onSpinnerClosed() {
                binding!!.spnrsurgeries.isSelected = false
            }
        })
        binding!!.spnrsurgeries.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View,
                    position: Int,
                    id: Long
                ) {
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
        val healthissued_adapter: ArrayAdapter<*> =
            ArrayAdapter(this, R.layout.spinner_corridor_selected, healthissueMaster)
        healthissued_adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        binding!!.spnrhealthissued.adapter = healthissued_adapter
        binding!!.spnrhealthissued.setSelection(0)
        binding!!.spnrhealthissued.setSpinnerEventsListener(object : OnSpinnerEventsListener {
            override fun onSpinnerOpened() {
                binding!!.spnrhealthissued.isSelected = true
            }

            override fun onSpinnerClosed() {
                binding!!.spnrhealthissued.isSelected = false
            }
        })
        binding!!.spnrhealthissued.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View,
                    position: Int,
                    id: Long
                ) {
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
        val habbit_adapter: ArrayAdapter<*> =
            ArrayAdapter(this, R.layout.spinner_corridor_selected, unhealthyhabitsMaster)
        habbit_adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        binding!!.spnrhabbit.adapter = habbit_adapter
        binding!!.spnrhabbit.setSelection(0)
        binding!!.spnrhabbit.setSpinnerEventsListener(object : OnSpinnerEventsListener {
            override fun onSpinnerOpened() {
                binding!!.spnrhabbit.isSelected = true
            }

            override fun onSpinnerClosed() {
                binding!!.spnrhabbit.isSelected = false
            }
        })
        binding!!.spnrhabbit.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View,
                position: Int,
                id: Long
            ) {
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        val account_adapter: ArrayAdapter<*> =
            ArrayAdapter(this, R.layout.spinner_corridor_selected, accountMaster)
        account_adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        binding!!.spnraccounttype.adapter = account_adapter
        binding!!.spnraccounttype.setSelection(0)
        binding!!.spnraccounttype.setSpinnerEventsListener(object : OnSpinnerEventsListener {
            override fun onSpinnerOpened() {
                binding!!.spnraccounttype.isSelected = true
            }

            override fun onSpinnerClosed() {
                binding!!.spnraccounttype.isSelected = false
            }
        })
        binding!!.spnraccounttype.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View,
                    position: Int,
                    id: Long
                ) {
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
        val businessplace_adapter: ArrayAdapter<*> =
            ArrayAdapter(this, R.layout.spinner_corridor_selected, businessplaceMaster)
        businessplace_adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        binding!!.spnrbusinessplace.adapter = businessplace_adapter
        binding!!.spnrbusinessplace.setSelection(0)
        binding!!.spnrbusinessplace.setSpinnerEventsListener(object : OnSpinnerEventsListener {
            override fun onSpinnerOpened() {
                binding!!.spnrbusinessplace.isSelected = true
            }

            override fun onSpinnerClosed() {
                binding!!.spnrbusinessplace.isSelected = false
            }
        })
        binding!!.spnrbusinessplace.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View,
                    position: Int,
                    id: Long
                ) {
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
    }

    fun loadAcademic() {
        binding!!.recyclerviewacademic.setHasFixedSize(true)
        binding!!.recyclerviewacademic.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        academicAdapter = AcademicAdapter(this, academicList)
        binding!!.recyclerviewacademic.adapter = academicAdapter
    }

    fun loadChildren() {
        binding!!.recyclerviewchilds.setHasFixedSize(true)
        binding!!.recyclerviewchilds.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        childAdapter = ChildAdapter(this, childrenList)
        binding!!.recyclerviewchilds.adapter = childAdapter
    }

    fun loadFamily() {
        binding!!.recyclerviewfamilydetail.setHasFixedSize(true)
        binding!!.recyclerviewfamilydetail.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        familyAdapter = FamilyAdapter(this, familyList)
        binding!!.recyclerviewfamilydetail.adapter = familyAdapter
    }

    fun loadpersonalref() {
        binding!!.recyclerviewreferrance.setHasFixedSize(true)
        binding!!.recyclerviewreferrance.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        personalRefAdapter = PersonalRefAdapter(this, personalrefList)
        binding!!.recyclerviewreferrance.adapter = personalRefAdapter
    }

    private fun popuupAcademic() {
        try {
            val li = LayoutInflater.from(this@FranchiseeRegistrationActivity)
            val promptsView = li.inflate(R.layout.dialog_academicedetails, null)
            dialogacademicBuilder = AlertDialog.Builder(this@FranchiseeRegistrationActivity)
            dialogacademicBuilder!!.setCancelable(false)
            dialogacademicBuilder!!.setView(promptsView)
            dialogacademic = dialogacademicBuilder!!.create()
            val btncancelacademic =
                promptsView.findViewById<AppCompatTextView>(R.id.btncancelacademic)
            val btnsubmitacademic =
                promptsView.findViewById<AppCompatTextView>(R.id.btnsubmitacademic)
            val edtacconequalitfication =
                promptsView.findViewById<AppCompatEditText>(R.id.edtacconequalitfication)
            val edtacconeinstitute =
                promptsView.findViewById<AppCompatEditText>(R.id.edtacconeinstitute)
            val edtacconeboard = promptsView.findViewById<AppCompatEditText>(R.id.edtacconeboard)
            val edtacconeyear = promptsView.findViewById<AppCompatEditText>(R.id.edtacconeyear)
            val edtacconepassing =
                promptsView.findViewById<AppCompatEditText>(R.id.edtacconepassing)
            btnsubmitacademic.setOnClickListener { view: View? ->
                val detail = Academic(
                    edtacconequalitfication.text.toString(),
                    edtacconeinstitute.text.toString(),
                    edtacconeboard.text.toString(),
                    edtacconeyear.text.toString(),
                    edtacconepassing.text.toString()
                )
                academicList.add(detail)
                academicAdapter!!.notifyDataSetChanged()
                dialogacademic!!.dismiss()
            }
            btncancelacademic.setOnClickListener { view: View? -> dialogacademic!!.dismiss() }
            dialogacademic!!.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialogacademic!!.show()
        } catch (e: Exception) {
            e.message
        }
    }

    private fun popuupfamily() {
        try {
            val li = LayoutInflater.from(this@FranchiseeRegistrationActivity)
            val promptsView = li.inflate(R.layout.dialog_familydetails, null)
            dialogfamilyBuilider = AlertDialog.Builder(this@FranchiseeRegistrationActivity)
            dialogfamilyBuilider!!.setCancelable(false)
            dialogfamilyBuilider!!.setView(promptsView)
            dialogfamily = dialogfamilyBuilider!!.create()
            val btncancelfamily = promptsView.findViewById<AppCompatTextView>(R.id.btncancelfamily)
            val btnsubmitfamily = promptsView.findViewById<AppCompatTextView>(R.id.btnsubmitfamily)
            val edtfamilyname = promptsView.findViewById<AppCompatEditText>(R.id.edtfamilyname)
            val edtfamilyrelation =
                promptsView.findViewById<AppCompatEditText>(R.id.edtfamilyrelation)
            val edtfamilyage = promptsView.findViewById<AppCompatEditText>(R.id.edtfamilyage)
            val edteducation = promptsView.findViewById<AppCompatEditText>(R.id.edteducation)
            val edtoccupation = promptsView.findViewById<AppCompatEditText>(R.id.edtoccupation)
            val edtmonthlyincome =
                promptsView.findViewById<AppCompatEditText>(R.id.edtmonthlyincome)
            val edtcontactnumber =
                promptsView.findViewById<AppCompatEditText>(R.id.edtcontactnumber)
            val edtaddress = promptsView.findViewById<AppCompatEditText>(R.id.edtaddress)
            btnsubmitfamily.setOnClickListener { view: View? ->
                val detail = Family(
                    edtfamilyname.text.toString(),
                    edtfamilyrelation.text.toString(),
                    edtfamilyage.text.toString(),
                    edteducation.text.toString(),
                    edtoccupation.text.toString(),
                    edtmonthlyincome.text.toString(),
                    edtcontactnumber.text.toString(),
                    edtaddress.text.toString()
                )
                familyList.add(detail)
                familyAdapter!!.notifyDataSetChanged()
                dialogfamily!!.dismiss()
            }
            btncancelfamily.setOnClickListener { view: View? -> dialogfamily!!.dismiss() }
            dialogfamily!!.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialogfamily!!.show()
        } catch (e: Exception) {
            e.message
        }
    }

    private fun popuupchildren() {
        try {
            val li = LayoutInflater.from(this@FranchiseeRegistrationActivity)
            val promptsView = li.inflate(R.layout.dialog_childdetails, null)
            dialogchildBuilder = AlertDialog.Builder(this@FranchiseeRegistrationActivity)
            dialogchildBuilder!!.setCancelable(false)
            dialogchildBuilder!!.setView(promptsView)
            dialogchild = dialogchildBuilder!!.create()
            val btncancelchild = promptsView.findViewById<AppCompatTextView>(R.id.btncancelchild)
            val btnsubmitchild = promptsView.findViewById<AppCompatTextView>(R.id.btnsubmitchild)
            val edtchildname = promptsView.findViewById<AppCompatEditText>(R.id.edtchildname)
            val edtchildage = promptsView.findViewById<AppCompatEditText>(R.id.edtchildage)
            val edtacconeinstitute =
                promptsView.findViewById<AppCompatEditText>(R.id.edtacconeinstitute)
            val edtacconeboard = promptsView.findViewById<AppCompatEditText>(R.id.edtacconeboard)
            val rgGender = promptsView.findViewById<RadioGroup>(R.id.rgGender)
            rgGender.setOnCheckedChangeListener { radioGroup, i ->
                if (i == R.id.radiomale) {
                    gender = "Male"
                }
                if (i == R.id.radiofemale) {
                    gender = "Female"
                }
            }
            btnsubmitchild.setOnClickListener { view: View? ->
                val detail = Children(
                    edtchildname.text.toString(),
                    edtchildage.text.toString(),
                    gender,
                    edtacconeinstitute.text.toString(),
                    edtacconeboard.text.toString()
                )
                childrenList.add(detail)
                childAdapter!!.notifyDataSetChanged()
                dialogchild!!.dismiss()
            }
            btncancelchild.setOnClickListener { view: View? -> dialogchild!!.dismiss() }
            dialogchild!!.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialogchild!!.show()
        } catch (e: Exception) {
            e.message
        }
    }

    private fun popuupreferrnaces() {
        try {
            val li = LayoutInflater.from(this@FranchiseeRegistrationActivity)
            val promptsView = li.inflate(R.layout.dialog_personalreferrance, null)
            dialogreferranceBuilder = AlertDialog.Builder(this@FranchiseeRegistrationActivity)
            dialogreferranceBuilder!!.setCancelable(false)
            dialogreferranceBuilder!!.setView(promptsView)
            dialogreferrance = dialogreferranceBuilder!!.create()
            val btncancelrefferance =
                promptsView.findViewById<AppCompatTextView>(R.id.btncancelrefferance)
            val btnsubmitrefferance =
                promptsView.findViewById<AppCompatTextView>(R.id.btnsubmitrefferance)
            val edtrefname = promptsView.findViewById<AppCompatEditText>(R.id.edtrefname)
            val edtreffrelation = promptsView.findViewById<AppCompatEditText>(R.id.edtreffrelation)
            val edtrefage = promptsView.findViewById<AppCompatEditText>(R.id.edtrefage)
            val edtreffoccupation =
                promptsView.findViewById<AppCompatEditText>(R.id.edtreffoccupation)
            val edtrefflocation = promptsView.findViewById<AppCompatEditText>(R.id.edtrefflocation)
            val edtreffcontactnumber =
                promptsView.findViewById<AppCompatEditText>(R.id.edtreffcontactnumber)
            val edtreffaddress = promptsView.findViewById<AppCompatEditText>(R.id.edtreffaddress)
            val rgGender = promptsView.findViewById<RadioGroup>(R.id.rgGender)
            rgGender.setOnCheckedChangeListener { radioGroup, i ->
                if (i == R.id.radiomale) {
                    gender = "Male"
                }
                if (i == R.id.radiofemale) {
                    gender = "Female"
                }
            }
            btnsubmitrefferance.setOnClickListener { view: View? ->
                val detail = Personalref(
                    edtrefname.text.toString(),
                    edtreffrelation.text.toString(),
                    edtrefage.text.toString(),
                    gender,
                    edtreffoccupation.text.toString(),
                    edtrefflocation.text.toString(),
                    edtreffcontactnumber.text.toString(),
                    edtreffaddress.text.toString()
                )
                personalrefList.add(detail)
                personalRefAdapter!!.notifyDataSetChanged()
                dialogreferrance!!.dismiss()
            }
            btncancelrefferance.setOnClickListener { view: View? -> dialogreferrance!!.dismiss() }
            dialogreferrance!!.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialogreferrance!!.show()
        } catch (e: Exception) {
            e.message
        }
    }
}