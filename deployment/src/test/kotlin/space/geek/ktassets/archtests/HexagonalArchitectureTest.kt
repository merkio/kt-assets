package space.geek.ktassets.archtests

import com.tngtech.archunit.junit.AnalyzeClasses
import com.tngtech.archunit.junit.ArchTest
import com.tngtech.archunit.library.Architectures

@AnalyzeClasses(packages = ["space.geek.ktassets"])
internal class HexagonalArchitectureTest {

    @ArchTest
    val layeredArchitectureTest: Architectures.LayeredArchitecture = Architectures.layeredArchitecture()
        .consideringAllDependencies()
        .layer("API").definedBy("space.geek.ktassets.api..")
        .layer("Messaging").definedBy("space.geek.ktassets.messaging..")
        .layer("Remote").definedBy("space.geek.ktassets.remote..")
        .layer("Repository").definedBy("space.geek.ktassets.repository..")
        .layer("Domain").definedBy("space.geek.ktassets.domain..")
        .layer("Application").definedBy("space.geek.ktassets.application..")
        .whereLayer("API").mayNotBeAccessedByAnyLayer()
        .whereLayer("Remote").mayNotBeAccessedByAnyLayer()
        .whereLayer("Messaging").mayNotBeAccessedByAnyLayer()
        .whereLayer("Repository").mayNotBeAccessedByAnyLayer()
        .whereLayer("Application").mayOnlyBeAccessedByLayers("Messaging", "API")
        .whereLayer("Domain").mayOnlyBeAccessedByLayers(
            "API",
            "Messaging",
            "Remote",
            "Application",
            "Repository"
        )
}
