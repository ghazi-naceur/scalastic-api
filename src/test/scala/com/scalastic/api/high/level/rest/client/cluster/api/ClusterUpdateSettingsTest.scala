package com.scalastic.api.high.level.rest.client.cluster.api

import org.elasticsearch.action.admin.cluster.settings.ClusterUpdateSettingsResponse
import org.elasticsearch.cluster.routing.allocation.decider.EnableAllocationDecider
import org.elasticsearch.common.settings.Settings
import org.elasticsearch.common.unit.ByteSizeUnit
import org.elasticsearch.indices.recovery.RecoverySettings

/**
  * Created by Ghazi Naceur on 28/04/2019
  * Email: ghazi.ennacer@gmail.com
  */
object ClusterUpdateSettingsTest extends App {

  val transientSettingKey = RecoverySettings.INDICES_RECOVERY_MAX_BYTES_PER_SEC_SETTING.getKey
  val transientSettingValue = 10
  val transientSettings: Settings = Settings.builder().put(transientSettingKey, transientSettingValue, ByteSizeUnit.BYTES).build()

  val persistentSettingKey = EnableAllocationDecider.CLUSTER_ROUTING_ALLOCATION_ENABLE_SETTING.getKey
  val persistentSettingValue = EnableAllocationDecider.Allocation.NONE.name()
  val persistentSettings: Settings = Settings.builder().put(persistentSettingKey, persistentSettingValue).build()

  private val response: ClusterUpdateSettingsResponse = ClusterAPIs.clusterUpdateSettings(transientSettings, persistentSettings)
  println(response.isAcknowledged)
  println(response.getPersistentSettings)
  println(response.getTransientSettings)

  System.exit(0)
}
