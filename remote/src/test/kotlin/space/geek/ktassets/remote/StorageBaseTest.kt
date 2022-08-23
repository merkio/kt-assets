package space.geek.ktassets.remote

import org.junit.jupiter.api.AfterEach
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.DeleteBucketRequest
import space.geek.ktassets.remote.Fixtures.ACCESS_KEY
import space.geek.ktassets.remote.Fixtures.BUCKET
import space.geek.ktassets.remote.Fixtures.SECRET_KEY
import java.net.URI

open class StorageBaseTest {

    protected val client: S3Client by lazy {
        container.start()
        S3Client.builder()
            .region(Region.EU_CENTRAL_1)
            .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(ACCESS_KEY, SECRET_KEY)))
            .endpointOverride(URI.create("http://${container.hostAddress}"))
            .build()
    }

    @AfterEach
    fun tearDown() {
        client.deleteBucket(DeleteBucketRequest.builder().bucket(BUCKET).build())
    }

    companion object {
        private val container = MinioContainer(credentials = CredentialsProvider(ACCESS_KEY, SECRET_KEY))
    }
}
