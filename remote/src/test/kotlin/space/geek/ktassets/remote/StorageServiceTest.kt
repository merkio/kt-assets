package space.geek.ktassets.remote

import org.junit.jupiter.api.Test
import software.amazon.awssdk.services.s3.model.Bucket
import software.amazon.awssdk.services.s3.model.CreateBucketRequest
import software.amazon.awssdk.services.s3.model.CreateBucketResponse
import space.geek.ktassets.remote.Fixtures.BUCKET
import java.util.stream.Collectors
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

internal class StorageServiceTest : StorageBaseTest() {

    @Test
    fun `should create a bucket`() {
        val request: CreateBucketRequest = CreateBucketRequest.builder().bucket(BUCKET).build()

        val bucket: CreateBucketResponse = client.createBucket(request)
        assertNotNull(bucket)
        assertEquals("/$BUCKET", bucket.location())
    }

    @Test
    fun `should get a list of buckets`() {
        val request: CreateBucketRequest = CreateBucketRequest.builder().bucket(BUCKET).build()
        client.createBucket(request)

        val buckets: List<Bucket> = client.listBuckets().buckets()
        assertNotNull(buckets)
        assertEquals(1, buckets.size)
        assertTrue(
            buckets.stream()
                .map(Bucket::name)
                .collect(Collectors.toList())
                .contains(BUCKET)
        )
    }
}