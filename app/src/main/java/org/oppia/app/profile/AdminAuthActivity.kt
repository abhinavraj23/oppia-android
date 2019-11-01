package org.oppia.app.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.oppia.app.activity.InjectableAppCompatActivity
import org.oppia.app.fragment.InjectableFragment
import javax.inject.Inject

const val KEY_ADMIN_PIN = "ADMIN_PIN"

/** Fragment that authenticates by checking for admin's PIN. */
class AdminAuthActivity : InjectableAppCompatActivity() {
  @Inject lateinit var adminAuthFragmentPresenter: AdminAuthActivityPresenter

  companion object {
    fun createAdminAuthActivityIntent(context: Context, adminPin: String): Intent {
      val intent = Intent(context, AdminAuthActivity::class.java)
      intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
      intent.putExtra(KEY_ADMIN_PIN, adminPin)
      return intent
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    activityComponent.inject(this)
    adminAuthFragmentPresenter.handleOnCreate()
  }
}