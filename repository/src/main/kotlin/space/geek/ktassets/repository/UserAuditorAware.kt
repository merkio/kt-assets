package space.geek.ktassets.repository

import mu.KotlinLogging
import org.springframework.data.domain.AuditorAware
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import java.util.Optional

private val log = KotlinLogging.logger {}

@Component
internal class UserAuditorAware : AuditorAware<String> {

    override fun getCurrentAuditor(): Optional<String> {
        log.info { "User Auditor: ${SecurityContextHolder.getContext().authentication.name}" }
        return Optional.of(SecurityContextHolder.getContext().authentication.name)
    }
}
