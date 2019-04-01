package dev.restcountries.app.injection.module

import android.content.Context
import dev.restcountries.app.base.BaseView
import dagger.Module
import dagger.Provides


/**
 * Module providing Context dependencies
 */
@Module
@Suppress("unused")
object ContextModule {


    /**
     * Provides Context.
     * @param baseView the baseView providing the context
     * @return context
     */
    @Provides
    @JvmStatic
    //NOTE (pvalkov) article below explains why passing app context is not a good idea
    //https://www.philosophicalhacker.com/2015/07/14/why-static-references-to-application-contexts-are-probably-not-the-best-idea/
    internal fun provideContext(baseView: BaseView): Context {
        return baseView.getContext()
    }

}