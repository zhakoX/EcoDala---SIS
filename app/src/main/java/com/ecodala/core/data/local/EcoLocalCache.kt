package com.ecodala.core.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.ecodala.core.data.dummy.DummyEcoData
import com.ecodala.core.data.repository.LocalEcoData
import com.ecodala.core.domain.model.*
import kotlinx.coroutines.flow.first
import org.json.JSONArray
import org.json.JSONObject
import java.time.LocalDateTime
import java.time.YearMonth

private val Context.ecoDemoDataStore by preferencesDataStore(name = "sis4_demo_data")

/** Small local snapshot: only added submissions and favourites need persistence. */
object EcoLocalCache {
    private var appContext: Context? = null
    private val snapshotKey = stringPreferencesKey("snapshot")
    fun initialize(context: Context) { appContext = context.applicationContext }

    suspend fun save(data: LocalEcoData) {
        val context = appContext ?: return
        val added = data.submissions.filter { item -> DummyEcoData.wasteSubmissions.none { it.id == item.id } }
        val json = JSONObject().put("favourites", JSONArray(data.favouriteIds.toList()))
            .put("submissions", JSONArray().apply {
                added.forEach { item -> put(JSONObject()
                    .put("id", item.id).put("type", item.wasteType.name).put("quantity", item.quantity)
                    .put("unit", item.unit).put("comment", item.comment ?: "")
                    .put("points", item.earnedPoints).put("createdAt", item.createdAt)) }
            })
        context.ecoDemoDataStore.edit { it[snapshotKey] = json.toString() }
    }

    suspend fun load(): LocalEcoData? {
        val context = appContext ?: return null
        val raw = context.ecoDemoDataStore.data.first()[snapshotKey] ?: return null
        val json = JSONObject(raw)
        val items = json.getJSONArray("submissions")
        val added = (0 until items.length()).map { index ->
            val item = items.getJSONObject(index)
            WasteSubmission(item.getString("id"), DummyEcoData.currentUser.id,
                WasteType.valueOf(item.getString("type")), item.getDouble("quantity"), item.getString("unit"),
                null, item.getString("comment").ifBlank { null }, item.getInt("points"), item.getString("createdAt"))
        }
        val favourites = json.getJSONArray("favourites")
        return LocalEcoData(
            submissions = added + DummyEcoData.wasteSubmissions,
            events = DummyEcoData.pointsLedger + added.map { item ->
                PointsEvent(item.id, item.userId, PointsSource.WasteSubmission, "${item.wasteType.title} recycled",
                    item.earnedPoints, YearMonth.from(LocalDateTime.parse(item.createdAt)).toString(), item.createdAt)
            },
            favouriteIds = (0 until favourites.length()).map { favourites.getString(it) }.toSet()
        )
    }
}
