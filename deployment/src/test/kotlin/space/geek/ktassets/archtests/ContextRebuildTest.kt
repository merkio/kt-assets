package geek.space.ktassets.archtests

import com.tngtech.archunit.base.DescribedPredicate
import com.tngtech.archunit.core.domain.JavaClasses
import com.tngtech.archunit.junit.AnalyzeClasses
import com.tngtech.archunit.junit.ArchTest
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.boot.test.mock.mockito.SpyBean

/**
 * Checks for some commonly-used testing approaches that cause the Spring context to be rebuilt, e.g. [MockBean].
 * These should ideally be avoided in order to keep the build time as low as possible.
 *
 * Ignore lists:
 * In most cases, avoiding these approaches should be doable but there are ignore lists for the cases where
 * it is not or for existing tests that violate the rule (with the intention of fixing those tests in the future).
 *
 * When adding a test to an ignore list, please add a comment to explain why.
 */
@AnalyzeClasses(packages = ["geek.space"])
internal class ContextRebuildTest {

    @ArchTest
    fun `no tests should use @MockBean`(classes: JavaClasses) {
        ArchRuleDefinition
            .fields().that().areDeclaredInClassesThat(
                DescribedPredicate.describe(
                    "are test classes that should not include @MockBean-annotated fields"
                ) {
                    it.simpleName.endsWith("Test") &&
                        !IGNORED_TESTS_WITH_MOCK_BEANS.contains(it.simpleName)
                }
            )
            .should().notBeAnnotatedWith(MockBean::class.java)
            .`as`(
                "No @MockBean fields in tests to avoid rebuilding the Spring context " +
                    "-> instead, use BeanHotSwapperExtension"
            )
            .check(classes)
    }

    @ArchTest
    fun `no tests should use @SpyBean`(classes: JavaClasses) {
        ArchRuleDefinition
            .fields().that().areDeclaredInClassesThat(
                DescribedPredicate.describe(
                    "are test classes that should not include @SpyBean-annotated fields"
                ) {
                    it.simpleName.endsWith("Test") &&
                        !IGNORED_TESTS_WITH_SPY_BEANS.contains(it.simpleName)
                }
            )
            .should().notBeAnnotatedWith(SpyBean::class.java)
            .`as`(
                "No @SpyBean fields in tests to avoid rebuilding the Spring context " +
                    "-> instead, e.g. add a unit test (in addition to the integration test) " +
                    "to verify interaction with other components"
            )
            .check(classes)
    }

    private companion object {

        val IGNORED_TESTS_WITH_MOCK_BEANS = emptyList<String>()

        val IGNORED_TESTS_WITH_SPY_BEANS = emptyList<String>()
    }
}
