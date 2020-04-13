package org.oppia.app.topic.lessons

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import org.oppia.app.fragment.FragmentScope
import org.oppia.app.model.ProfileId
import org.oppia.app.model.Topic
import org.oppia.domain.topic.TopicController
import org.oppia.util.data.AsyncResult
import org.oppia.util.logging.Logger
import javax.inject.Inject

@FragmentScope
class TopicLessonsViewModel  @Inject constructor (
  private val topicController: TopicController,
  private val logger: Logger,
  val fragment: Fragment
) : ViewModel() {
  private lateinit var profileId: ProfileId
  private lateinit var topicId: String
  private lateinit var storyId: String
  private val storySummarySelector: StorySummarySelector = fragment as StorySummarySelector
  private val itemList: MutableList<TopicLessonsItemViewModel> = ArrayList()


  private val topicLiveData: LiveData<Topic> by lazy { getTopicList() }

  private val topicResultLiveData: LiveData<AsyncResult<Topic>> by lazy {
    topicController.getTopic(profileId, topicId)
  }

  private fun getTopicList(): LiveData<Topic> {
    return Transformations.map(topicResultLiveData, ::processTopicResult)
  }

  private fun processTopicResult(topic: AsyncResult<Topic>): Topic {
    if (topic.isFailure()) {
      logger.e("TopicLessonsFragment", "Failed to retrieve topic", topic.getErrorOrNull()!!)
    }
    return topic.getOrDefault(Topic.getDefaultInstance())
  }

  fun setStoryId(storyId: String){
    this.storyId = storyId
  }

  fun setTopicId(topicId: String) {
    this.topicId = topicId
  }

  fun setInternalProfileId(internalProfileId: Int) {
    this.profileId = ProfileId.newBuilder().setInternalId(internalProfileId).build()
  }
}