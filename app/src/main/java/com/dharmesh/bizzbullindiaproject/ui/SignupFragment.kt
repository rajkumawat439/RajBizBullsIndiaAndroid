package com.dharmesh.bizzbullindiaproject.ui


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.dharmesh.bizzbullindiaproject.databinding.FragmentSignupBinding
import com.dharmesh.bizzbullindiaproject.vm.SignupViewModel


class SignupFragment : Fragment() {

    private var mBinding: FragmentSignupBinding? = null
    private var mViewModel: SignupViewModel? = null
    private val binding get() = mBinding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val signupmodel =
            ViewModelProvider(this)[SignupViewModel::class.java]

        mBinding = FragmentSignupBinding.inflate(inflater, container, false)
        val root: View = mBinding!!.root
        return root
    }


//    override fun onCreate(savedInstanceState: Bundle?) {
//        setViewModel()
//        super.onCreate(savedInstanceState)
//        mBinding = getView()
//        mBinding?.lifecycleOwner = this
//        mViewModel?.setActivityNavigator(this)
//        mViewModel?.setVariable(mBinding!!)
//        mViewModel?.SigninTextSpnnable()
//
//        window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
//        window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
//        mBinding?.etFirstName?.requestFocus()
//        val inputMethodManager =
//            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//        inputMethodManager.showSoftInput(mBinding?.etFirstName, InputMethodManager.SHOW_IMPLICIT)
//    }

//    override fun getBindingVariable(): Int {
//        return BR.viewModel
//    }
//
//    override fun getLayoutId(): Int {
//        return R.layout.activity_bbi_signup
//    }
//
//    override fun getViewModel(): SignupViewModel {
//        return mViewModel!!
//    }
//
//    private fun setViewModel() {
//        val factory =
//            ViewModelFactory(SignupViewModel(BaseRepository(RetrofitFactory.getInstance(), this)))
//        mViewModel = ViewModelProvider(this, factory).get(SignupViewModel::class.java)
//    }
}