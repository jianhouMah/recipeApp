package com.example.recipeapp.view.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.recipeapp.view.home.HomeActivity
import org.koin.androidx.viewmodel.ext.android.viewModelForClass
import org.koin.core.component.KoinComponent
import java.lang.reflect.ParameterizedType
import kotlin.reflect.KClass


abstract class BaseFragment<VM : ViewModel, B : ViewDataBinding> : Fragment(), KoinComponent {
    protected var binding: B? = null

    @Suppress("UNCHECKED_CAST")
    private val clazz: KClass<VM> =
        ((this.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<VM>).kotlin
    protected val viewModel: VM by viewModelForClass(clazz)

    protected val activity: HomeActivity?
        get() = context as? HomeActivity

    abstract fun getLayout(): Int

    abstract fun setupViews()

    abstract fun setupObservers()

    protected open fun injectView(rootView: View?): View? = rootView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = DataBindingUtil.inflate(inflater, getLayout(), container, false)
        return injectView(binding?.root)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        setupObservers()
    }

    public fun navigateTo(nav: NavDirections, options: NavOptions? = null) {
        try {
            findNavController().navigate(nav, options)
        } catch (e: Exception) {
        }

    }

    fun navigateTo(
        destinationId: Int,
        args: Bundle? = null,
        isClearStack: Boolean = false,
    ) {
        val navBuilder = NavOptions.Builder()
        if (isClearStack) {
            findNavController().popBackStack(findNavController().currentDestination?.id ?: -1, true)
        }
        findNavController().navigate(destinationId, args, navBuilder.build())
    }

    fun popBackStack() {
        findNavController().popBackStack()
    }
}