package space.geek.ktassets.repository.asset

import com.vladmihalcea.hibernate.type.json.JsonType
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.Type
import org.hibernate.annotations.TypeDef
import org.hibernate.annotations.UpdateTimestamp
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import space.geek.ktassets.domain.asset.Asset
import space.geek.ktassets.domain.asset.AssetCreationCommand
import java.time.OffsetDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EntityListeners
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "asset", schema = "assets")
@EntityListeners(AuditingEntityListener::class)
@TypeDef(name = "json", typeClass = JsonType::class)
data class AssetEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val key: String,
    val url: String?,
    @Type(type = "json")
    @Column(columnDefinition = "jsonb")
    val tags: Map<String, String> = emptyMap(),
    @LastModifiedBy
    // To set username from security context and use UserAuditorAware, property should be mutable (var)
    var username: String? = null,
    @Type(type = "json")
    @Column(columnDefinition = "jsonb")
    val metadata: Map<String, String> = emptyMap(),
    @CreationTimestamp
    val createdAt: OffsetDateTime? = null,
    @UpdateTimestamp
    val updatedAt: OffsetDateTime? = null
) {

    fun to(): Asset =
        Asset(
            id = id!!,
            key = key,
            url = url,
            tags = tags,
            username = username,
            metadata = metadata,
            createdAt = createdAt!!,
            updatedAt = updatedAt!!
        )

    companion object {
        fun from(command: AssetCreationCommand): AssetEntity =
            with(command) {
                AssetEntity(
                    key = key,
                    url = url,
                    tags = tags,
                    metadata = metadata
                )
            }
    }
}