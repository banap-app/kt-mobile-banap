package com.banap.banap.data.repository.location

import android.annotation.SuppressLint
import com.banap.banap.data.model.location.Location
import com.banap.banap.domain.repository.location.LocationRepository
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.tasks.CancellationTokenSource
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class LocationRepositoryImpl @Inject constructor(
    private val client: FusedLocationProviderClient
) : LocationRepository {

    @SuppressLint("MissingPermission")
    override suspend fun getCurrentLocation(): Location =
        suspendCancellableCoroutine { cont ->
            val cancellationTokenSource = CancellationTokenSource()

            client.getCurrentLocation(
                /*priority=*/ com.google.android.gms.location.Priority.PRIORITY_HIGH_ACCURACY,
                /*token=*/ cancellationTokenSource.token
            ).addOnSuccessListener { androidLoc ->
                if (androidLoc != null) {
                    cont.resume(
                        Location(
                            latitude  = androidLoc.latitude,
                            longitude = androidLoc.longitude
                        )
                    )
                } else {
                    cont.resumeWithException(IllegalStateException("Localização retornou nula"))
                }
            }.addOnFailureListener { exc ->
                cont.resumeWithException(exc)
            }

            cont.invokeOnCancellation {
                cancellationTokenSource.cancel()
            }
        }
}