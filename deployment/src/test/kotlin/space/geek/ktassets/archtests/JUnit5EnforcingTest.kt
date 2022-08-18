package space.geek.ktassets.archtests

import com.tngtech.archunit.core.domain.JavaClasses
import com.tngtech.archunit.junit.AnalyzeClasses
import com.tngtech.archunit.junit.ArchTest
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition

@AnalyzeClasses(packages = ["space.geek"])
internal class JUnit5EnforcingTest {

    @ArchTest
    fun `no tests should be annotated with JUnit4's @Test annotation`(classes: JavaClasses) {
        ArchRuleDefinition.methods()
            .that().areDeclaredInClassesThat()
            .haveSimpleNameEndingWith("Test")
            .should().notBeAnnotatedWith("org.junit.Test")
            .check(classes)
    }
}
